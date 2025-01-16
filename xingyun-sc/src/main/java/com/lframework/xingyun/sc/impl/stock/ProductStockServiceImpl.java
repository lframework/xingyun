package com.lframework.xingyun.sc.impl.stock;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.exceptions.impl.DefaultSysException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.NumberUtil;
import com.lframework.starter.mq.core.producer.MqProducer;
import com.lframework.starter.web.impl.BaseMpServiceImpl;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.starter.web.utils.PageHelperUtil;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.xingyun.basedata.entity.Product;
import com.lframework.xingyun.basedata.entity.ProductBundle;
import com.lframework.xingyun.basedata.enums.ProductType;
import com.lframework.xingyun.basedata.service.product.ProductBundleService;
import com.lframework.xingyun.basedata.service.product.ProductService;
import com.lframework.xingyun.core.dto.stock.ProductStockChangeDto;
import com.lframework.xingyun.sc.events.stock.AddStockEvent;
import com.lframework.xingyun.sc.events.stock.SubStockEvent;
import com.lframework.xingyun.core.queue.MqConstants;
import com.lframework.xingyun.sc.entity.ProductStock;
import com.lframework.xingyun.sc.mappers.ProductStockMapper;
import com.lframework.xingyun.sc.service.stock.ProductStockLogService;
import com.lframework.xingyun.sc.service.stock.ProductStockService;
import com.lframework.xingyun.sc.vo.stock.AddProductStockVo;
import com.lframework.xingyun.sc.vo.stock.QueryProductStockVo;
import com.lframework.xingyun.sc.vo.stock.SubProductStockVo;
import com.lframework.xingyun.sc.vo.stock.log.AddLogWithAddStockVo;
import com.lframework.xingyun.sc.vo.stock.log.AddLogWithSubStockVo;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
public class ProductStockServiceImpl extends BaseMpServiceImpl<ProductStockMapper, ProductStock>
    implements ProductStockService {

  @Autowired
  private ProductService productService;

  @Autowired
  private ProductBundleService productBundleService;

  @Autowired
  private ProductStockLogService productStockLogService;

  @Override
  public PageResult<ProductStock> query(Integer pageIndex, Integer pageSize,
      QueryProductStockVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<ProductStock> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<ProductStock> query(QueryProductStockVo vo) {

    return getBaseMapper().query(vo);
  }

  @Override
  public ProductStock getByProductIdAndScId(String productId, String scId) {

    Product product = productService.findById(productId);
    if (product == null) {
      return null;
    }

    if (product.getProductType() == ProductType.NORMAL) {
      return getBaseMapper().getByProductIdAndScId(productId, scId);
    } else {
      List<ProductBundle> productBundles = productBundleService.getByMainProductId(productId);
      if (CollectionUtil.isEmpty(productBundles)) {
        return null;
      }

      List<String> productIds = productBundles.stream().map(ProductBundle::getProductId).collect(
          Collectors.toList());
      List<ProductStock> productStocks = this.getByProductIdsAndScId(productIds, scId,
          ProductType.NORMAL.getCode());

      int stockNum = Integer.MAX_VALUE;
      for (ProductBundle productBundle : productBundles) {
        String id = productBundle.getProductId();
        ProductStock productStock = productStocks.stream().filter(t -> t.getProductId().equals(id))
            .findFirst().orElse(null);
        if (productStock == null || productStock.getStockNum() <= 0) {
          // 此处说明有单品没有库存
          return null;
        }

        stockNum = Math.min(productStock.getStockNum() / productBundle.getBundleNum(), stockNum);
      }

      ProductStock productStock = new ProductStock();
      productStock.setId(IdUtil.getId());
      productStock.setScId(scId);
      productStock.setProductId(productId);
      productStock.setStockNum(stockNum);
      productStock.setTaxPrice(BigDecimal.ZERO);
      productStock.setTaxAmount(BigDecimal.ZERO);

      return productStock;
    }
  }

  @Override
  public List<ProductStock> getByProductIdsAndScId(List<String> productIds, String scId,
      Integer productType) {

    if (CollectionUtil.isEmpty(productIds)) {
      return CollectionUtil.emptyList();
    }

    return getBaseMapper().getByProductIdsAndScId(productIds, scId, productType);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public ProductStockChangeDto addStock(AddProductStockVo vo) {

    Product product = productService.findById(vo.getProductId());
    if (product.getProductType() != ProductType.NORMAL) {
      throw new DefaultClientException(
          "只有商品类型为【" + ProductType.NORMAL.getDesc() + "】的商品支持入库！");
    }

    Wrapper<ProductStock> queryWrapper = Wrappers.lambdaQuery(ProductStock.class)
        .eq(ProductStock::getProductId, vo.getProductId()).eq(ProductStock::getScId, vo.getScId());

    ProductStock productStock = getBaseMapper().selectOne(queryWrapper);

    boolean isStockEmpty = false;
    if (productStock == null) {
      //首次入库，先新增
      productStock = new ProductStock();
      productStock.setId(IdUtil.getId());
      productStock.setScId(vo.getScId());
      productStock.setProductId(vo.getProductId());
      productStock.setStockNum(0);
      productStock.setTaxPrice(BigDecimal.ZERO);
      productStock.setTaxAmount(BigDecimal.ZERO);

      getBaseMapper().insert(productStock);

      isStockEmpty = true;
    }

    // 如果taxAmount为null，代表不重算均价，即：按当前均价直接入库
    boolean reCalcCostPrice = vo.getTaxPrice() != null;

    if (vo.getTaxPrice() == null) {
      vo.setTaxPrice(isStockEmpty ?
          vo.getDefaultTaxPrice() :
          productStock.getTaxPrice());
    }
    if (vo.getTaxPrice() == null) {
      // 如果此时taxPrice还是null，则代表taxPrice和defaultTaxPrice均为null
      throw new DefaultSysException(
          "商品ID：" + vo.getProductId() + "，没有库存，taxPrice和defaultTaxPrice不能同时为null！");
    }

    if (isStockEmpty) {
      // 如果之前没有库存，那么均价必须重算
      reCalcCostPrice = true;
    }

    vo.setTaxPrice(NumberUtil.getNumber(vo.getTaxPrice(), 2));
    int count = getBaseMapper().addStock(vo.getProductId(), vo.getScId(), vo.getStockNum(),
        NumberUtil.mul(vo.getTaxPrice(), vo.getStockNum()), productStock.getStockNum(),
        productStock.getTaxAmount(), reCalcCostPrice);
    if (count != 1) {
      throw new DefaultClientException(
          "商品（" + product.getCode() + "）" + product.getName() + "入库失败，请稍后重试！");
    }

    AddLogWithAddStockVo addLogWithAddStockVo = new AddLogWithAddStockVo();
    addLogWithAddStockVo.setProductId(vo.getProductId());
    addLogWithAddStockVo.setScId(vo.getScId());
    addLogWithAddStockVo.setStockNum(vo.getStockNum());
    addLogWithAddStockVo.setTaxAmount(NumberUtil.mul(vo.getTaxPrice(), vo.getStockNum()));
    addLogWithAddStockVo.setOriStockNum(productStock.getStockNum());
    addLogWithAddStockVo.setCurStockNum(
        NumberUtil.add(productStock.getStockNum(), vo.getStockNum()).intValue());
    addLogWithAddStockVo.setOriTaxPrice(productStock.getTaxPrice());
    addLogWithAddStockVo.setCurTaxPrice(!reCalcCostPrice ?
        productStock.getTaxPrice() :
        addLogWithAddStockVo.getCurStockNum() == 0 ?
            BigDecimal.ZERO :
            NumberUtil.getNumber(
                NumberUtil.div(NumberUtil.add(productStock.getTaxAmount(),
                        NumberUtil.mul(vo.getTaxPrice(), vo.getStockNum())),
                    addLogWithAddStockVo.getCurStockNum()), 6));
    addLogWithAddStockVo.setCreateTime(vo.getCreateTime());
    addLogWithAddStockVo.setBizId(vo.getBizId());
    addLogWithAddStockVo.setBizDetailId(vo.getBizDetailId());
    addLogWithAddStockVo.setBizCode(vo.getBizCode());
    addLogWithAddStockVo.setBizType(vo.getBizType());

    productStockLogService.addLogWithAddStock(addLogWithAddStockVo);
    ProductStockChangeDto stockChange = new ProductStockChangeDto();
    stockChange.setScId(vo.getScId());
    stockChange.setProductId(vo.getProductId());
    stockChange.setNum(vo.getStockNum());
    stockChange.setTaxAmount(addLogWithAddStockVo.getTaxAmount());
    stockChange.setCurTaxPrice(addLogWithAddStockVo.getCurTaxPrice());
    stockChange.setCreateTime(vo.getCreateTime());
    stockChange.setCurStockNum(addLogWithAddStockVo.getCurStockNum());

    AddStockEvent addStockEvent = new AddStockEvent(this, stockChange);
    ApplicationUtil.publishEvent(addStockEvent);

    return stockChange;
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public ProductStockChangeDto subStock(SubProductStockVo vo) {

    Product product = productService.findById(vo.getProductId());
    if (product.getProductType() != ProductType.NORMAL) {
      throw new DefaultClientException(
          "只有商品类型为【" + ProductType.NORMAL.getDesc() + "】的商品支持出库！");
    }

    Wrapper<ProductStock> queryWrapper = Wrappers.lambdaQuery(ProductStock.class)
        .eq(ProductStock::getProductId, vo.getProductId()).eq(ProductStock::getScId, vo.getScId());

    ProductStock productStock = getBaseMapper().selectOne(queryWrapper);
    if (productStock == null) {
      throw new DefaultClientException(
          "商品（" + product.getCode() + "）" + product.getName() + "当前库存为0，无法出库！");
    }

    if (NumberUtil.lt(productStock.getStockNum(), vo.getStockNum())) {
      throw new DefaultClientException(
          "商品（" + product.getCode() + "）" + product.getName() + "当前库存为"
              + productStock.getStockNum()
              + "，库存不足，无法出库！");
    }

    // 如果taxAmount为null，代表不重算均价，即：按当前均价直接出库
    boolean reCalcCostPrice = vo.getTaxAmount() != null;
    if (vo.getTaxAmount() == null) {
      vo.setTaxAmount(NumberUtil.mul(productStock.getTaxPrice(), vo.getStockNum()));
    }

    vo.setTaxAmount(NumberUtil.getNumber(vo.getTaxAmount(), 2));

    BigDecimal subTaxAmount = vo.getTaxAmount();

    int count = getBaseMapper().subStock(vo.getProductId(), vo.getScId(), vo.getStockNum(),
        subTaxAmount,
        productStock.getStockNum(), productStock.getTaxAmount(), reCalcCostPrice);
    if (count != 1) {
      throw new DefaultClientException(
          "商品（" + product.getCode() + "）" + product.getName() + "出库失败，请稍后重试！");
    }

    AddLogWithSubStockVo addLogWithAddStockVo = new AddLogWithSubStockVo();
    addLogWithAddStockVo.setProductId(vo.getProductId());
    addLogWithAddStockVo.setScId(vo.getScId());
    addLogWithAddStockVo.setStockNum(vo.getStockNum());
    addLogWithAddStockVo.setTaxAmount(subTaxAmount);
    addLogWithAddStockVo.setOriStockNum(productStock.getStockNum());
    addLogWithAddStockVo.setCurStockNum(
        NumberUtil.sub(productStock.getStockNum(), vo.getStockNum()).intValue());
    addLogWithAddStockVo.setOriTaxPrice(productStock.getTaxPrice());
    addLogWithAddStockVo.setCurTaxPrice(!reCalcCostPrice ?
        productStock.getTaxPrice() :
        addLogWithAddStockVo.getCurStockNum() == 0 ?
            BigDecimal.ZERO :
            NumberUtil.getNumber(
                NumberUtil.div(NumberUtil.sub(productStock.getTaxAmount(), subTaxAmount),
                    addLogWithAddStockVo.getCurStockNum()), 6));
    addLogWithAddStockVo.setCreateTime(vo.getCreateTime());
    addLogWithAddStockVo.setBizId(vo.getBizId());
    addLogWithAddStockVo.setBizDetailId(vo.getBizDetailId());
    addLogWithAddStockVo.setBizCode(vo.getBizCode());
    addLogWithAddStockVo.setBizType(vo.getBizType());

    productStockLogService.addLogWithSubStock(addLogWithAddStockVo);

    ProductStockChangeDto stockChange = new ProductStockChangeDto();
    stockChange.setScId(vo.getScId());
    stockChange.setProductId(vo.getProductId());
    stockChange.setNum(vo.getStockNum());
    stockChange.setTaxAmount(subTaxAmount);
    stockChange.setCurTaxPrice(addLogWithAddStockVo.getCurTaxPrice());
    stockChange.setCreateTime(vo.getCreateTime());
    stockChange.setCurStockNum(addLogWithAddStockVo.getCurStockNum());

    SubStockEvent subStockEvent = new SubStockEvent(this, stockChange);
    ApplicationUtil.publishEvent(subStockEvent);

    return stockChange;
  }
}

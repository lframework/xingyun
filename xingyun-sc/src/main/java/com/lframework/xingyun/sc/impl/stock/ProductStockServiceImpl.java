package com.lframework.xingyun.sc.impl.stock;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.exceptions.impl.DefaultSysException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.NumberUtil;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageHelperUtil;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.xingyun.basedata.entity.Product;
import com.lframework.xingyun.basedata.service.product.ProductService;
import com.lframework.xingyun.core.dto.stock.ProductStockChangeDto;
import com.lframework.xingyun.core.events.stock.AddStockEvent;
import com.lframework.xingyun.core.events.stock.SubStockEvent;
import com.lframework.xingyun.sc.dto.stock.adjust.StockCostAdjustDiffDto;
import com.lframework.xingyun.sc.entity.ProductStock;
import com.lframework.xingyun.sc.mappers.ProductStockMapper;
import com.lframework.xingyun.sc.service.stock.ProductStockLogService;
import com.lframework.xingyun.sc.service.stock.ProductStockService;
import com.lframework.xingyun.sc.vo.stock.AddProductStockVo;
import com.lframework.xingyun.sc.vo.stock.QueryProductStockVo;
import com.lframework.xingyun.sc.vo.stock.StockCostAdjustVo;
import com.lframework.xingyun.sc.vo.stock.SubProductStockVo;
import com.lframework.xingyun.sc.vo.stock.log.AddLogWithAddStockVo;
import com.lframework.xingyun.sc.vo.stock.log.AddLogWithStockCostAdjustVo;
import com.lframework.xingyun.sc.vo.stock.log.AddLogWithSubStockVo;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductStockServiceImpl extends BaseMpServiceImpl<ProductStockMapper, ProductStock>
    implements ProductStockService {

  @Autowired
  private ProductService productService;

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

    return getBaseMapper().getByProductIdAndScId(productId, scId);
  }

  @Override
  public List<ProductStock> getByProductIdsAndScId(List<String> productIds, String scId) {

    if (CollectionUtil.isEmpty(productIds)) {
      return CollectionUtil.emptyList();
    }

    return getBaseMapper().getByProductIdsAndScId(productIds, scId);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public ProductStockChangeDto addStock(AddProductStockVo vo) {

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
      productStock.setUnTaxPrice(BigDecimal.ZERO);
      productStock.setUnTaxAmount(BigDecimal.ZERO);

      getBaseMapper().insert(productStock);

      isStockEmpty = true;
    }

    // 如果taxAmount为null，代表不重算均价，即：按当前均价直接入库
    boolean reCalcCostPrice = vo.getTaxAmount() != null;

    if (vo.getTaxAmount() == null) {
      vo.setTaxAmount(isStockEmpty ?
          vo.getDefaultTaxAmount() :
          NumberUtil.mul(productStock.getTaxPrice(), vo.getStockNum()));
    }
    if (vo.getTaxAmount() == null) {
      // 如果此时taxAmount还是null，则代表taxAmount和defaultTaxAmount均为null
      throw new DefaultSysException(
          "商品ID：" + vo.getProductId() + "，没有库存，taxAmount和defaultTaxAmount不能同时为null！");
    }

    if (isStockEmpty) {
      // 如果之前没有库存，那么均价必须重算
      reCalcCostPrice = true;
    }

    vo.setTaxAmount(NumberUtil.getNumber(vo.getTaxAmount(), 2));
    BigDecimal changeUnTaxAmount = NumberUtil.getNumber(
        NumberUtil.calcUnTaxPrice(vo.getTaxAmount(), vo.getTaxRate()), 6);
    int count = getBaseMapper().addStock(vo.getProductId(), vo.getScId(), vo.getStockNum(),
        vo.getTaxAmount(),
        NumberUtil.calcUnTaxPrice(vo.getTaxAmount(), vo.getTaxRate()), productStock.getStockNum(),
        productStock.getTaxAmount(), reCalcCostPrice);
    if (count != 1) {
      Product product = productService.findById(vo.getProductId());
      throw new DefaultClientException(
          "商品（" + product.getCode() + "）" + product.getName() + "入库失败，请稍后重试！");
    }

    AddLogWithAddStockVo addLogWithAddStockVo = new AddLogWithAddStockVo();
    addLogWithAddStockVo.setProductId(vo.getProductId());
    addLogWithAddStockVo.setScId(vo.getScId());
    addLogWithAddStockVo.setStockNum(vo.getStockNum());
    addLogWithAddStockVo.setTaxAmount(vo.getTaxAmount());
    addLogWithAddStockVo.setUnTaxAmount(
        NumberUtil.calcUnTaxPrice(vo.getTaxAmount(), vo.getTaxRate()));
    addLogWithAddStockVo.setOriStockNum(productStock.getStockNum());
    addLogWithAddStockVo.setCurStockNum(
        NumberUtil.add(productStock.getStockNum(), vo.getStockNum()).intValue());
    addLogWithAddStockVo.setOriTaxPrice(productStock.getTaxPrice());
    addLogWithAddStockVo.setCurTaxPrice(!reCalcCostPrice ?
        productStock.getTaxPrice() :
        addLogWithAddStockVo.getCurStockNum() == 0 ?
            BigDecimal.ZERO :
            NumberUtil.getNumber(
                NumberUtil.div(NumberUtil.add(productStock.getTaxAmount(), vo.getTaxAmount()),
                    addLogWithAddStockVo.getCurStockNum()), 6));
    addLogWithAddStockVo.setOriUnTaxPrice(productStock.getUnTaxPrice());
    addLogWithAddStockVo.setCurUnTaxPrice(!reCalcCostPrice ?
        productStock.getUnTaxPrice() :
        addLogWithAddStockVo.getCurStockNum() == 0 ?
            BigDecimal.ZERO :
            NumberUtil.getNumber(
                NumberUtil.div(NumberUtil.add(productStock.getUnTaxAmount(), changeUnTaxAmount),
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
    stockChange.setUnTaxAmount(addLogWithAddStockVo.getUnTaxAmount());

    AddStockEvent addStockEvent = new AddStockEvent(this, stockChange);
    ApplicationUtil.publishEvent(addStockEvent);

    return stockChange;
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public ProductStockChangeDto subStock(SubProductStockVo vo) {

    Wrapper<ProductStock> queryWrapper = Wrappers.lambdaQuery(ProductStock.class)
        .eq(ProductStock::getProductId, vo.getProductId()).eq(ProductStock::getScId, vo.getScId());

    ProductStock productStock = getBaseMapper().selectOne(queryWrapper);
    Product product = productService.findById(vo.getProductId());
    if (productStock == null) {
      throw new DefaultClientException(
          "商品（" + product.getCode() + "）" + product.getName() + "当前库存为0，无法出库！");
    }

    if (NumberUtil.lt(productStock.getStockNum(), vo.getStockNum())) {
      throw new DefaultClientException(
          "商品（" + product.getCode() + "）" + product.getName() + "当前库存为" + productStock.getStockNum()
              + "，库存不足，无法出库！");
    }

    // 如果taxAmount为null，代表不重算均价，即：按当前均价直接出库
    boolean reCalcCostPrice = vo.getTaxAmount() != null;
    if (vo.getTaxAmount() == null) {
      vo.setTaxAmount(NumberUtil.mul(productStock.getTaxPrice(), vo.getStockNum()));
    }

    vo.setTaxAmount(NumberUtil.getNumber(vo.getTaxAmount(), 2));

    BigDecimal subTaxAmount = vo.getTaxAmount();
    // 如果没传taxRate那么就按进项税率出库
    BigDecimal subUnTaxAmount = NumberUtil.getNumber(NumberUtil.calcUnTaxPrice(subTaxAmount,
        vo.getTaxRate() == null ? product.getTaxRate() : vo.getTaxRate()), 6);

    int count = getBaseMapper().subStock(vo.getProductId(), vo.getScId(), vo.getStockNum(),
        subTaxAmount,
        subUnTaxAmount,
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
    addLogWithAddStockVo.setUnTaxAmount(subUnTaxAmount);
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
    addLogWithAddStockVo.setOriUnTaxPrice(productStock.getUnTaxPrice());
    addLogWithAddStockVo.setCurUnTaxPrice(!reCalcCostPrice ?
        productStock.getUnTaxPrice() :
        addLogWithAddStockVo.getCurStockNum() == 0 ?
            BigDecimal.ZERO :
            NumberUtil.getNumber(
                NumberUtil.div(NumberUtil.sub(productStock.getUnTaxAmount(), subUnTaxAmount),
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
    stockChange.setUnTaxAmount(subUnTaxAmount);

    SubStockEvent subStockEvent = new SubStockEvent(this, stockChange);
    ApplicationUtil.publishEvent(subStockEvent);

    return stockChange;
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public StockCostAdjustDiffDto stockCostAdjust(StockCostAdjustVo vo) {

    Wrapper<ProductStock> queryWrapper = Wrappers.lambdaQuery(ProductStock.class)
        .eq(ProductStock::getProductId, vo.getProductId()).eq(ProductStock::getScId, vo.getScId());

    ProductStock productStock = getBaseMapper().selectOne(queryWrapper);

    if (productStock == null) {
      // 没有库存，跳过
      return new StockCostAdjustDiffDto();
    }

    BigDecimal taxPrice = NumberUtil.getNumber(vo.getTaxPrice(), 6);
    BigDecimal unTaxPrice = NumberUtil.getNumber(
        NumberUtil.calcUnTaxPrice(vo.getTaxPrice(), vo.getTaxRate()), 6);

    StockCostAdjustDiffDto result = new StockCostAdjustDiffDto();
    result.setStockNum(productStock.getStockNum());
    result.setOriPrice(NumberUtil.getNumber(productStock.getTaxPrice(), 2));
    result.setDiffAmount(NumberUtil.getNumber(
        NumberUtil.mul(NumberUtil.sub(taxPrice, productStock.getTaxPrice()),
            productStock.getStockNum()), 2));

    getBaseMapper().stockCostAdjust(vo.getProductId(), vo.getScId(), taxPrice, unTaxPrice);

    AddLogWithStockCostAdjustVo logVo = new AddLogWithStockCostAdjustVo();
    logVo.setProductId(vo.getProductId());
    logVo.setScId(vo.getScId());
    logVo.setTaxAmount(result.getDiffAmount());
    logVo.setUnTaxAmount(NumberUtil.getNumber(
        NumberUtil.mul(NumberUtil.sub(unTaxPrice, productStock.getUnTaxPrice()),
            productStock.getStockNum()), 2));
    logVo.setOriStockNum(productStock.getStockNum());
    logVo.setOriTaxPrice(productStock.getTaxPrice());
    logVo.setCurTaxPrice(taxPrice);
    logVo.setOriUnTaxPrice(productStock.getUnTaxPrice());
    logVo.setCurUnTaxPrice(unTaxPrice);
    logVo.setCreateTime(vo.getCreateTime());
    logVo.setBizId(vo.getBizId());
    logVo.setBizDetailId(vo.getBizDetailId());
    logVo.setBizCode(vo.getBizCode());

    productStockLogService.addLogWithStockCostAdjust(logVo);

    return result;
  }
}

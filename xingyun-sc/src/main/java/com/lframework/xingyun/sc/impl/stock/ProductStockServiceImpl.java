package com.lframework.xingyun.sc.impl.stock;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.exceptions.impl.DefaultSysException;
import com.lframework.common.utils.Assert;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.NumberUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageHelperUtil;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.web.service.IGenerateCodeService;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.xingyun.basedata.dto.product.info.ProductDto;
import com.lframework.xingyun.basedata.service.product.IProductService;
import com.lframework.xingyun.core.dto.stock.ProductLotChangeDto;
import com.lframework.xingyun.core.dto.stock.ProductStockChangeDto;
import com.lframework.xingyun.core.events.stock.AddStockEvent;
import com.lframework.xingyun.core.events.stock.SubStockEvent;
import com.lframework.xingyun.sc.components.code.GenerateCodeTypePool;
import com.lframework.xingyun.sc.entity.ProductLot;
import com.lframework.xingyun.sc.entity.ProductLotStock;
import com.lframework.xingyun.sc.entity.ProductStock;
import com.lframework.xingyun.sc.mappers.ProductStockMapper;
import com.lframework.xingyun.sc.service.stock.IProductLotService;
import com.lframework.xingyun.sc.service.stock.IProductLotStockService;
import com.lframework.xingyun.sc.service.stock.IProductStockLogService;
import com.lframework.xingyun.sc.service.stock.IProductStockService;
import com.lframework.xingyun.sc.vo.stock.AddProductStockVo;
import com.lframework.xingyun.sc.vo.stock.QueryProductStockVo;
import com.lframework.xingyun.sc.vo.stock.StockCostAdjustVo;
import com.lframework.xingyun.sc.vo.stock.SubProductStockVo;
import com.lframework.xingyun.sc.vo.stock.log.AddLogWithAddStockVo;
import com.lframework.xingyun.sc.vo.stock.log.AddLogWithStockCostAdjustVo;
import com.lframework.xingyun.sc.vo.stock.log.AddLogWithSubStockVo;
import com.lframework.xingyun.sc.vo.stock.lot.AddProductLotStockVo;
import com.lframework.xingyun.sc.vo.stock.lot.CreateProductLotVo;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductStockServiceImpl extends BaseMpServiceImpl<ProductStockMapper, ProductStock>
    implements IProductStockService {

  @Autowired
  private IProductService productService;

  @Autowired
  private IProductLotService productLotService;

  @Autowired
  private IProductLotStockService productLotStockService;

  @Autowired
  private IProductStockLogService productStockLogService;

  @Autowired
  private IGenerateCodeService generateCodeService;

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
      return Collections.EMPTY_LIST;
    }

    return getBaseMapper().getByProductIdsAndScId(productIds, scId);
  }

  @Transactional
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
      ProductDto product = productService.findById(vo.getProductId());
      throw new DefaultClientException(
          "商品（" + product.getCode() + "）" + product.getName() + "入库失败，请稍后重试！");
    }

    CreateProductLotVo createProductLotVo = new CreateProductLotVo();
    createProductLotVo.setLotCode(generateCodeService.generate(GenerateCodeTypePool.LOT_CODE));
    createProductLotVo.setProductId(vo.getProductId());
    createProductLotVo.setSupplierId(vo.getSupplierId());
    createProductLotVo.setTaxRate(vo.getTaxRate());
    createProductLotVo.setCreateTime(vo.getCreateTime());
    createProductLotVo.setBizId(vo.getBizId());
    createProductLotVo.setBizDetailId(vo.getBizDetailId());
    createProductLotVo.setBizCode(vo.getBizCode());
    createProductLotVo.setBizType(vo.getBizType());

    String lotId = productLotService.create(createProductLotVo);

    AddProductLotStockVo addProductLotStockVo = new AddProductLotStockVo();
    addProductLotStockVo.setLotId(lotId);
    addProductLotStockVo.setScId(vo.getScId());
    addProductLotStockVo.setStockNum(vo.getStockNum());
    productLotStockService.addStock(addProductLotStockVo);

    AddLogWithAddStockVo addLogWithAddStockVo = new AddLogWithAddStockVo();
    addLogWithAddStockVo.setLotId(lotId);
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

    List<ProductLotChangeDto> lotChangeList = new ArrayList<>();
    ProductLotChangeDto lotChange = new ProductLotChangeDto();

    lotChange.setLotId(lotId);
    lotChange.setScId(vo.getScId());
    lotChange.setProductId(vo.getProductId());
    lotChange.setSupplierId(vo.getSupplierId());
    lotChange.setTaxAmount(addLogWithAddStockVo.getTaxAmount());
    lotChange.setUnTaxAmount(addLogWithAddStockVo.getUnTaxAmount());
    lotChange.setNum(vo.getStockNum());

    lotChangeList.add(lotChange);

    stockChange.setLotChangeList(lotChangeList);

    AddStockEvent addStockEvent = new AddStockEvent(this, stockChange);
    ApplicationUtil.publishEvent(addStockEvent);

    return stockChange;
  }

  @Transactional
  @Override
  public ProductStockChangeDto subStock(SubProductStockVo vo) {

    Wrapper<ProductStock> queryWrapper = Wrappers.lambdaQuery(ProductStock.class)
        .eq(ProductStock::getProductId, vo.getProductId()).eq(ProductStock::getScId, vo.getScId());

    ProductStock productStock = getBaseMapper().selectOne(queryWrapper);

    if (productStock == null) {
      ProductDto product = productService.findById(vo.getProductId());
      throw new DefaultClientException(
          "商品（" + product.getCode() + "）" + product.getName() + "当前库存为0，无法出库！");
    }

    if (NumberUtil.lt(productStock.getStockNum(), vo.getStockNum())) {
      ProductDto product = productService.findById(vo.getProductId());
      throw new DefaultClientException(
          "商品（" + product.getCode() + "）" + product.getName() + "当前库存为" + productStock.getStockNum()
              + "，库存不足，无法出库！");
    }

    List<ProductLotStock> lotStockList = productLotStockService.getWithSubStock(
        productStock.getProductId(),
        productStock.getScId(), StringUtil.isBlank(vo.getSupplierId()) ? null : vo.getSupplierId(),
        vo.getStockNum());
    if (CollectionUtil.isEmpty(lotStockList)) {
      ProductDto product = productService.findById(vo.getProductId());
      throw new DefaultClientException(
          "商品（" + product.getCode() + "）" + product.getName() + "当前库存不足，无法出库！");
    }
    // 扣减lot Map key:lot value:扣减数量
    Map<ProductLotStock, Integer> subLotMap = new HashMap<>();
    int remainNum = vo.getStockNum();
    for (ProductLotStock lotStock : lotStockList) {
      if (lotStock.getStockNum() >= remainNum) {
        subLotMap.put(lotStock, remainNum);
        remainNum = 0;
        break;
      } else {
        remainNum -= lotStock.getStockNum();
        subLotMap.put(lotStock, lotStock.getStockNum());
      }
    }

    // 如果taxAmount为null，代表不重算均价，即：按当前均价直接出库
    boolean reCalcCostPrice = vo.getTaxAmount() != null;
    if (vo.getTaxAmount() == null) {
      vo.setTaxAmount(NumberUtil.mul(productStock.getTaxPrice(), vo.getStockNum()));
    }

    vo.setTaxAmount(NumberUtil.getNumber(vo.getTaxAmount(), 2));

    List<ProductLotChangeDto> lotChangeList = new ArrayList<>();
    BigDecimal remainTaxAmount = vo.getTaxAmount();
    int index = 0;
    for (Map.Entry<ProductLotStock, Integer> lotEntry : subLotMap.entrySet()) {
      ProductLotStock lotStock = lotEntry.getKey();
      ProductLot lot = productLotService.findById(lotStock.getLotId());
      Integer num = lotEntry.getValue();

      BigDecimal subTaxAmount = subLotMap.size() - 1 == index ?
          remainTaxAmount :
          NumberUtil.getNumber(
              NumberUtil.mul(num, NumberUtil.div(vo.getTaxAmount(), vo.getStockNum())), 2);
      remainTaxAmount = NumberUtil.sub(remainTaxAmount, subTaxAmount);

      index++;

      BigDecimal subUnTaxAmount = NumberUtil.getNumber(NumberUtil.calcUnTaxPrice(subTaxAmount,
          vo.getTaxRate() == null ? lot.getTaxRate() : vo.getTaxRate()), 6);

      int count = getBaseMapper().subStock(vo.getProductId(), vo.getScId(), num, subTaxAmount,
          subUnTaxAmount,
          productStock.getStockNum(), productStock.getTaxAmount(), reCalcCostPrice);
      if (count != 1) {
        ProductDto product = productService.findById(vo.getProductId());
        throw new DefaultClientException(
            "商品（" + product.getCode() + "）" + product.getName() + "出库失败，请稍后重试！");
      }

      //扣减lot库存
      productLotStockService.subStockById(lotStock.getId(), num);

      AddLogWithSubStockVo addLogWithAddStockVo = new AddLogWithSubStockVo();
      addLogWithAddStockVo.setLotId(lotStock.getLotId());
      addLogWithAddStockVo.setProductId(vo.getProductId());
      addLogWithAddStockVo.setScId(vo.getScId());
      addLogWithAddStockVo.setStockNum(num);
      addLogWithAddStockVo.setTaxAmount(subTaxAmount);
      addLogWithAddStockVo.setUnTaxAmount(subUnTaxAmount);
      addLogWithAddStockVo.setOriStockNum(productStock.getStockNum());
      addLogWithAddStockVo.setCurStockNum(
          NumberUtil.sub(productStock.getStockNum(), num).intValue());
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

      productStock.setStockNum(addLogWithAddStockVo.getCurStockNum());
      productStock.setTaxAmount(NumberUtil.sub(productStock.getTaxAmount(), subTaxAmount));
      productStock.setUnTaxAmount(NumberUtil.sub(productStock.getUnTaxAmount(), subUnTaxAmount));
      productStock.setTaxPrice(addLogWithAddStockVo.getCurTaxPrice());
      productStock.setUnTaxPrice(addLogWithAddStockVo.getCurUnTaxPrice());

      ProductLotChangeDto lotChange = new ProductLotChangeDto();
      lotChange.setLotId(lot.getId());
      lotChange.setScId(lotStock.getScId());
      lotChange.setProductId(lot.getProductId());
      lotChange.setSupplierId(lot.getSupplierId());
      lotChange.setTaxAmount(addLogWithAddStockVo.getTaxAmount());
      lotChange.setUnTaxAmount(addLogWithAddStockVo.getUnTaxAmount());
      lotChange.setNum(num);

      lotChangeList.add(lotChange);
    }

    ProductStockChangeDto stockChange = new ProductStockChangeDto();
    stockChange.setScId(vo.getScId());
    stockChange.setProductId(vo.getProductId());
    stockChange.setNum(vo.getStockNum());
    stockChange.setTaxAmount(
        lotChangeList.stream().map(ProductLotChangeDto::getTaxAmount).reduce(NumberUtil::add)
            .orElse(BigDecimal.ZERO));
    stockChange.setUnTaxAmount(
        lotChangeList.stream().map(ProductLotChangeDto::getUnTaxAmount).reduce(NumberUtil::add)
            .orElse(BigDecimal.ZERO));
    stockChange.setLotChangeList(lotChangeList);

    SubStockEvent subStockEvent = new SubStockEvent(this, stockChange);
    ApplicationUtil.publishEvent(subStockEvent);

    return stockChange;
  }

  @Transactional
  @Override
  public void stockCostAdjust(StockCostAdjustVo vo) {

    Wrapper<ProductStock> queryWrapper = Wrappers.lambdaQuery(ProductStock.class)
        .eq(ProductStock::getProductId, vo.getProductId()).eq(ProductStock::getScId, vo.getScId());

    ProductStock productStock = getBaseMapper().selectOne(queryWrapper);

    if (productStock == null) {
      // 没有库存，跳过
      return;
    }

    BigDecimal taxPrice = NumberUtil.getNumber(vo.getTaxPrice(), 6);
    BigDecimal unTaxPrice = NumberUtil.getNumber(
        NumberUtil.calcUnTaxPrice(vo.getTaxPrice(), vo.getTaxRate()), 6);

    getBaseMapper().stockCostAdjust(vo.getProductId(), vo.getScId(), taxPrice, unTaxPrice);

    List<ProductLotStock> lotStocks = productLotStockService.getAllHasStockLots(vo.getProductId(),
        vo.getScId());
    if (!CollectionUtil.isEmpty(lotStocks)) {
      for (ProductLotStock lotStock : lotStocks) {
        BigDecimal taxAmount = NumberUtil.getNumber(
            NumberUtil.mul(lotStock.getStockNum(),
                NumberUtil.sub(taxPrice, productStock.getTaxPrice())),
            2);
        BigDecimal unTaxAmount = NumberUtil.getNumber(NumberUtil.mul(lotStock.getStockNum(),
            NumberUtil.sub(unTaxPrice, productStock.getUnTaxPrice())), 6);
        AddLogWithStockCostAdjustVo logVo = new AddLogWithStockCostAdjustVo();
        logVo.setLotId(lotStock.getLotId());
        logVo.setProductId(vo.getProductId());
        logVo.setScId(vo.getScId());
        logVo.setTaxAmount(taxAmount);
        logVo.setUnTaxAmount(unTaxAmount);
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
      }
    }
  }
}

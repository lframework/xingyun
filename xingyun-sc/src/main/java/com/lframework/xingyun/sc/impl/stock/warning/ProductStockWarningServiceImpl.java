package com.lframework.xingyun.sc.impl.stock.warning;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.NumberUtil;
import com.lframework.starter.web.core.annotations.oplog.OpLog;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.starter.web.core.utils.IdUtil;
import com.lframework.starter.web.core.utils.PageHelperUtil;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.xingyun.basedata.entity.ProductSku;
import com.lframework.xingyun.basedata.service.product.ProductSkuService;
import com.lframework.xingyun.sc.entity.ProductStockWarning;
import com.lframework.xingyun.sc.enums.StockWarningOpLogType;
import com.lframework.xingyun.sc.mappers.ProductStockWarningMapper;
import com.lframework.xingyun.sc.service.stock.warning.ProductStockWarningService;
import com.lframework.xingyun.sc.vo.stock.warning.CreateProductStockWarningVo;
import com.lframework.xingyun.sc.vo.stock.warning.QueryProductStockWarningVo;
import com.lframework.xingyun.sc.vo.stock.warning.UpdateProductStockWarningVo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductStockWarningServiceImpl extends
    BaseMpServiceImpl<ProductStockWarningMapper, ProductStockWarning> implements
    ProductStockWarningService {

  @Autowired
  private ProductSkuService productSkuService;

  @Override
  public PageResult<ProductStockWarning> query(Integer pageIndex, Integer pageSize,
      QueryProductStockWarningVo vo) {
    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<ProductStockWarning> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<ProductStockWarning> query(QueryProductStockWarningVo vo) {
    return getBaseMapper().query(vo);
  }

  @Override
  public ProductStockWarning findById(String id) {
    return this.getById(id);
  }

  @OpLog(type = StockWarningOpLogType.class, name = "创建库存预警，ID：{}", params = "#_result", autoSaveParams = true)
  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(CreateProductStockWarningVo vo) {

    if (NumberUtil.lt(vo.getMaxLimit(), vo.getMinLimit())) {
      throw new DefaultClientException("预警上限必须大于预警下限！");
    }

    ProductSku sku = this.getWarningSku(vo.getSkuId(), vo.getProductId());
    vo.setSkuId(sku.getId());
    vo.setProductId(sku.getProductId());

    Wrapper<ProductStockWarning> checkWrapper = Wrappers.lambdaQuery(ProductStockWarning.class)
        .eq(ProductStockWarning::getScId, vo.getScId())
        .eq(ProductStockWarning::getSkuId, vo.getSkuId());
    if (this.count(checkWrapper) > 0) {
      throw new DefaultClientException("该仓库、商品已存在库存预警，不允许重复添加！");
    }

    ProductStockWarning record = new ProductStockWarning();
    record.setId(IdUtil.getId());
    record.setScId(vo.getScId());
    record.setProductId(vo.getProductId());
    record.setSkuId(vo.getSkuId());
    record.setMaxLimit(vo.getMaxLimit());
    record.setMinLimit(vo.getMinLimit());

    this.save(record);

    return record.getId();
  }

  @OpLog(type = StockWarningOpLogType.class, name = "修改库存预警，ID：{}", params = "#vo.id", autoSaveParams = true)
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateProductStockWarningVo vo) {
    if (NumberUtil.lt(vo.getMaxLimit(), vo.getMinLimit())) {
      throw new DefaultClientException("预警上限必须大于预警下限！");
    }

    ProductStockWarning record = this.getById(vo.getId());
    if (record == null) {
      throw new DefaultClientException("库存预警不存在！");
    }

    ProductSku sku = this.getWarningSku(vo.getSkuId(), vo.getProductId());
    vo.setSkuId(sku.getId());
    vo.setProductId(sku.getProductId());

    Wrapper<ProductStockWarning> checkWrapper = Wrappers.lambdaQuery(ProductStockWarning.class)
        .eq(ProductStockWarning::getScId, vo.getScId())
        .eq(ProductStockWarning::getSkuId, vo.getSkuId())
        .ne(ProductStockWarning::getId, record.getId());
    if (this.count(checkWrapper) > 0) {
      throw new DefaultClientException("该仓库、商品已存在库存预警，不允许重复添加！");
    }

    Wrapper<ProductStockWarning> updateWrapper = Wrappers.lambdaUpdate(ProductStockWarning.class)
        .eq(ProductStockWarning::getId, vo.getId())
        .set(ProductStockWarning::getScId, vo.getScId())
        .set(ProductStockWarning::getProductId, vo.getProductId())
        .set(ProductStockWarning::getSkuId, vo.getSkuId())
        .set(ProductStockWarning::getMaxLimit, vo.getMaxLimit())
        .set(ProductStockWarning::getMinLimit, vo.getMinLimit())
        .set(ProductStockWarning::getAvailable, vo.getAvailable());
    this.update(updateWrapper);
  }

  @OpLog(type = StockWarningOpLogType.class, name = "删除库存预警，ID：{}", params = {"#id"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteById(String id) {
    this.removeById(id);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteByIds(List<String> ids) {
    if (!CollectionUtil.isEmpty(ids)) {
      for (String id : ids) {

        ProductStockWarningService thisService = getThis(this.getClass());
        thisService.deleteById(id);
      }
    }
  }

  private ProductSku getWarningSku(String skuId, String fallbackSkuId) {

    String id = skuId;
    if (id == null || id.isBlank()) {
      id = fallbackSkuId;
    }

    ProductSku sku = productSkuService.findById(id);
    if (sku == null) {
      throw new DefaultClientException("SKU不存在！");
    }

    return sku;
  }
}

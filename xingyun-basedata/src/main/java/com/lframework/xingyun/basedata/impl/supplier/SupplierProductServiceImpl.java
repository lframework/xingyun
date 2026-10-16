package com.lframework.xingyun.basedata.impl.supplier;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.annotations.oplog.OpLog;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.starter.web.core.utils.IdUtil;
import com.lframework.starter.web.core.utils.OpLogUtil;
import com.lframework.starter.web.core.utils.PageHelperUtil;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.xingyun.basedata.entity.SupplierProduct;
import com.lframework.xingyun.basedata.enums.BaseDataOpLogType;
import com.lframework.xingyun.basedata.mappers.SupplierProductMapper;
import com.lframework.xingyun.basedata.service.supplier.SupplierProductService;
import com.lframework.xingyun.basedata.vo.supplier.product.CreateSupplierProductVo;
import com.lframework.xingyun.basedata.vo.supplier.product.QuerySupplierProductVo;
import com.lframework.xingyun.basedata.vo.supplier.product.UpdateSupplierProductVo;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SupplierProductServiceImpl
    extends BaseMpServiceImpl<SupplierProductMapper, SupplierProduct>
    implements SupplierProductService {

  @Override
  public PageResult<SupplierProduct> query(Integer pageIndex, Integer pageSize,
      QuerySupplierProductVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<SupplierProduct> datas = getBaseMapper().query(vo, null);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<SupplierProduct> listBySupplierId(String supplierId) {

    QuerySupplierProductVo vo = new QuerySupplierProductVo();
    vo.setSupplierId(supplierId);
    vo.setAvailable(true);
    return getBaseMapper().query(vo, null);
  }

  @Override
  public List<SupplierProduct> listByProductId(String productId) {

    QuerySupplierProductVo vo = new QuerySupplierProductVo();
    vo.setProductId(productId);
    vo.setAvailable(true);
    return getBaseMapper().query(vo, null);
  }

  @Override
  public SupplierProduct findById(String id) {

    return getBaseMapper().selectById(id);
  }

  @OpLog(type = BaseDataOpLogType.class, name = "新增供应商商品关联，ID：{}", params = "#id")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(CreateSupplierProductVo vo) {

    // 检查是否已存在相同关联
    Wrapper<SupplierProduct> checkWrapper = Wrappers.lambdaQuery(SupplierProduct.class)
        .eq(SupplierProduct::getSupplierId, vo.getSupplierId())
        .eq(SupplierProduct::getProductId, vo.getProductId())
        .eq(vo.getSkuId() != null, SupplierProduct::getSkuId, vo.getSkuId())
        .isNull(vo.getSkuId() == null, SupplierProduct::getSkuId)
        .eq(SupplierProduct::getAvailable, true);
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("该供应商与商品的关联已存在！");
    }

    SupplierProduct data = new SupplierProduct();
    data.setId(IdUtil.getId());
    data.setSupplierId(vo.getSupplierId());
    data.setProductId(vo.getProductId());
    data.setSkuId(StringUtil.isBlank(vo.getSkuId()) ? null : vo.getSkuId());
    data.setSupplierProductCode(
        StringUtil.isBlank(vo.getSupplierProductCode()) ? StringPool.EMPTY_STR
            : vo.getSupplierProductCode());
    data.setSupplierProductName(
        StringUtil.isBlank(vo.getSupplierProductName()) ? StringPool.EMPTY_STR
            : vo.getSupplierProductName());
    data.setDefaultPurchasePrice(vo.getDefaultPurchasePrice());
    data.setMinOrderQty(vo.getMinOrderQty());
    data.setLeadTimeDays(vo.getLeadTimeDays());
    data.setIsDefault(vo.getIsDefault() != null && vo.getIsDefault());
    data.setAvailable(true);
    data.setDescription(
        StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());

    // 如果设为默认供应商，先将该商品的其他默认取消
    if (data.getIsDefault()) {
      Wrapper<SupplierProduct> updateWrapper = Wrappers.lambdaUpdate(SupplierProduct.class)
          .set(SupplierProduct::getIsDefault, false)
          .eq(SupplierProduct::getProductId, vo.getProductId())
          .eq(SupplierProduct::getIsDefault, true);
      getBaseMapper().update(updateWrapper);
    }

    getBaseMapper().insert(data);

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setExtra(vo);

    return data.getId();
  }

  @OpLog(type = BaseDataOpLogType.class, name = "修改供应商商品关联，ID：{}", params = "#id")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateSupplierProductVo vo) {

    SupplierProduct data = getBaseMapper().selectById(vo.getId());
    if (data == null) {
      throw new DefaultClientException("供应商商品关联不存在！");
    }

    Wrapper<SupplierProduct> updateWrapper = Wrappers.lambdaUpdate(SupplierProduct.class)
        .set(SupplierProduct::getSupplierProductCode,
            StringUtil.isBlank(vo.getSupplierProductCode()) ? StringPool.EMPTY_STR
                : vo.getSupplierProductCode())
        .set(SupplierProduct::getSupplierProductName,
            StringUtil.isBlank(vo.getSupplierProductName()) ? StringPool.EMPTY_STR
                : vo.getSupplierProductName())
        .set(SupplierProduct::getDefaultPurchasePrice, vo.getDefaultPurchasePrice())
        .set(SupplierProduct::getMinOrderQty, vo.getMinOrderQty())
        .set(SupplierProduct::getLeadTimeDays, vo.getLeadTimeDays())
        .set(SupplierProduct::getIsDefault, vo.getIsDefault() != null && vo.getIsDefault())
        .set(SupplierProduct::getDescription,
            StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription())
        .eq(SupplierProduct::getId, vo.getId());

    getBaseMapper().update(updateWrapper);

    // 如果设为默认供应商，先将该商品的其他默认取消
    if (vo.getIsDefault() != null && vo.getIsDefault()) {
      Wrapper<SupplierProduct> updateDefaultWrapper = Wrappers.lambdaUpdate(SupplierProduct.class)
          .set(SupplierProduct::getIsDefault, false)
          .eq(SupplierProduct::getProductId, data.getProductId())
          .eq(SupplierProduct::getIsDefault, true)
          .ne(SupplierProduct::getId, vo.getId());
      getBaseMapper().update(updateDefaultWrapper);
    }

    OpLogUtil.setVariable("id", vo.getId());
    OpLogUtil.setExtra(vo);
  }

  @OpLog(type = BaseDataOpLogType.class, name = "删除供应商商品关联，ID：{}", params = "#id")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteById(String id) {

    Wrapper<SupplierProduct> updateWrapper = Wrappers.lambdaUpdate(SupplierProduct.class)
        .set(SupplierProduct::getAvailable, false)
        .eq(SupplierProduct::getId, id);
    getBaseMapper().update(updateWrapper);
  }
}
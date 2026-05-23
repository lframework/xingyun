package com.lframework.xingyun.basedata.impl.product;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.ObjectUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.annotations.oplog.OpLog;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.event.DataChangeEventBuilder;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.starter.web.core.utils.IdUtil;
import com.lframework.starter.web.core.utils.OpLogUtil;
import com.lframework.starter.web.core.utils.PageHelperUtil;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.xingyun.basedata.dto.product.saleproperty.ProductSalePropertyDefinitionModelorDto;
import com.lframework.xingyun.basedata.entity.ProductSalePropertyDefinition;
import com.lframework.xingyun.basedata.enums.BaseDataOpLogType;
import com.lframework.xingyun.basedata.events.DeleteProductSalePropertyDefinitionEvent;
import com.lframework.xingyun.basedata.mappers.ProductSalePropertyDefinitionMapper;
import com.lframework.xingyun.basedata.service.product.ProductSalePropertyDefinitionService;
import com.lframework.xingyun.basedata.service.product.ProductSkuService;
import com.lframework.xingyun.basedata.vo.product.saleproperty.CreateProductSalePropertyDefinitionVo;
import com.lframework.xingyun.basedata.vo.product.saleproperty.QueryProductSalePropertyDefinitionVo;
import com.lframework.xingyun.basedata.vo.product.saleproperty.UpdateProductSalePropertyDefinitionVo;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductSalePropertyDefinitionServiceImpl extends
    BaseMpServiceImpl<ProductSalePropertyDefinitionMapper, ProductSalePropertyDefinition>
    implements ProductSalePropertyDefinitionService {

  @Autowired
  private ProductSkuService productSkuService;

  @Override
  public PageResult<ProductSalePropertyDefinition> query(Integer pageIndex, Integer pageSize,
      QueryProductSalePropertyDefinitionVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<ProductSalePropertyDefinition> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<ProductSalePropertyDefinition> query(QueryProductSalePropertyDefinitionVo vo) {

    return getBaseMapper().query(vo);
  }

  @Cacheable(value = ProductSalePropertyDefinition.CACHE_NAME, key = "@cacheVariables.tenantId() + #id", unless = "#result == null")
  @Override
  public ProductSalePropertyDefinition findById(String id) {

    return getBaseMapper().selectById(id);
  }

  @OpLog(type = BaseDataOpLogType.class, name = "删除商品销售属性，ID：{}", params = "#id")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteById(String id) {

    Wrapper<ProductSalePropertyDefinition> updateWrapper = Wrappers.lambdaUpdate(
            ProductSalePropertyDefinition.class)
        .set(ProductSalePropertyDefinition::getAvailable, Boolean.FALSE)
        .eq(ProductSalePropertyDefinition::getId, id);
    getBaseMapper().update(updateWrapper);

    ProductSalePropertyDefinition ProductSalePropertyDefinition = this.findById(id);

    DataChangeEventBuilder.publishLogicDelete(this, DeleteProductSalePropertyDefinitionEvent.class,
        ProductSalePropertyDefinition);
  }

  @OpLog(type = BaseDataOpLogType.class, name = "新增商品销售属性，ID：{}, 编号：{}", params = {"#id",
      "#code"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(CreateProductSalePropertyDefinitionVo vo) {

    Wrapper<ProductSalePropertyDefinition> checkCodeWrapper = Wrappers.lambdaQuery(
            ProductSalePropertyDefinition.class)
        .eq(ProductSalePropertyDefinition::getCode, vo.getCode())
        .eq(ProductSalePropertyDefinition::getAvailable, Boolean.TRUE);
    if (getBaseMapper().selectCount(checkCodeWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    Wrapper<ProductSalePropertyDefinition> checkNameWrapper = Wrappers.lambdaQuery(
            ProductSalePropertyDefinition.class)
        .eq(ProductSalePropertyDefinition::getName, vo.getName())
        .eq(ProductSalePropertyDefinition::getAvailable, Boolean.TRUE);
    if (getBaseMapper().selectCount(checkNameWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    ProductSalePropertyDefinition data = new ProductSalePropertyDefinition();
    data.setId(IdUtil.getId());
    data.setCode(vo.getCode());
    data.setName(vo.getName());
    data.setAvailable(Boolean.TRUE);
    data.setDescription(
        StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());

    getBaseMapper().insert(data);

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);

    return data.getId();
  }

  @OpLog(type = BaseDataOpLogType.class, name = "修改商品销售属性，ID：{}, 编号：{}", params = {"#id",
      "#code"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateProductSalePropertyDefinitionVo vo) {

    ProductSalePropertyDefinition data = getBaseMapper().selectById(vo.getId());
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("商品销售属性不存在！");
    }

    Wrapper<ProductSalePropertyDefinition> checkWrapper = Wrappers.lambdaQuery(ProductSalePropertyDefinition.class)
        .eq(ProductSalePropertyDefinition::getCode, vo.getCode())
        .eq(ProductSalePropertyDefinition::getAvailable, Boolean.TRUE)
        .ne(ProductSalePropertyDefinition::getId, vo.getId());
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    Wrapper<ProductSalePropertyDefinition> checkNameWrapper = Wrappers.lambdaQuery(
            ProductSalePropertyDefinition.class)
        .eq(ProductSalePropertyDefinition::getName, vo.getName())
        .eq(ProductSalePropertyDefinition::getAvailable, Boolean.TRUE)
        .ne(ProductSalePropertyDefinition::getId, vo.getId());
    if (getBaseMapper().selectCount(checkNameWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    LambdaUpdateWrapper<ProductSalePropertyDefinition> updateWrapper = Wrappers.lambdaUpdate(
            ProductSalePropertyDefinition.class)
        .set(ProductSalePropertyDefinition::getCode, vo.getCode())
        .set(ProductSalePropertyDefinition::getName, vo.getName())
        .set(ProductSalePropertyDefinition::getDescription,
            StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription())
        .eq(ProductSalePropertyDefinition::getId, vo.getId());
    getBaseMapper().update(updateWrapper);
    productSkuService.refreshSalePropertyTextByPropertyId(vo.getId());

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);
  }

  @Override
  public List<ProductSalePropertyDefinitionModelorDto> getModelor() {

    return getBaseMapper().getModelor();
  }

  @CacheEvict(value = ProductSalePropertyDefinition.CACHE_NAME, key = "@cacheVariables.tenantId() + #key")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}

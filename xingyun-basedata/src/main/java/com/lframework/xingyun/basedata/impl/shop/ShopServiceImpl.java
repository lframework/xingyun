package com.lframework.xingyun.basedata.impl.shop;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.ObjectUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.xingyun.template.core.annotations.OpLog;
import com.lframework.xingyun.basedata.enums.BaseDataOpLogType;
import com.lframework.starter.web.impl.BaseMpServiceImpl;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.xingyun.template.core.utils.OpLogUtil;
import com.lframework.starter.web.utils.PageHelperUtil;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.xingyun.basedata.entity.Shop;
import com.lframework.xingyun.basedata.mappers.ShopMapper;
import com.lframework.xingyun.basedata.service.shop.ShopService;
import com.lframework.xingyun.basedata.vo.shop.CreateShopVo;
import com.lframework.xingyun.basedata.vo.shop.QueryShopVo;
import com.lframework.xingyun.basedata.vo.shop.UpdateShopVo;
import java.io.Serializable;
import java.util.List;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShopServiceImpl extends BaseMpServiceImpl<ShopMapper, Shop> implements ShopService {

  @Override
  public PageResult<Shop> query(Integer pageIndex, Integer pageSize, QueryShopVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<Shop> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<Shop> query(QueryShopVo vo) {

    return getBaseMapper().query(vo);
  }

  @Cacheable(value = Shop.CACHE_NAME, key = "@cacheVariables.tenantId() + #id", unless = "#result == null")
  @Override
  public Shop findById(String id) {

    return getBaseMapper().selectById(id);
  }

  @OpLog(type = BaseDataOpLogType.BASE_DATA, name = "新增门店，ID：{}", params = {"#id"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(CreateShopVo vo) {

    Wrapper<Shop> checkWrapper = Wrappers.lambdaQuery(Shop.class).eq(Shop::getCode, vo.getCode());
    if (this.count(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    Shop data = new Shop();
    data.setId(IdUtil.getId());
    data.setCode(vo.getCode());
    data.setName(vo.getName());
    if (!StringUtil.isBlank(vo.getDeptId())) {
      data.setDeptId(vo.getDeptId());
    }
    if (vo.getLng() != null) {
      data.setLng(vo.getLng());
    }
    if (vo.getLat() != null) {
      data.setLat(vo.getLat());
    }
    if (!StringUtil.isBlank(vo.getDescription())) {
      data.setDescription(vo.getDescription());
    }

    getBaseMapper().insert(data);

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setExtra(vo);

    return data.getId();
  }

  @OpLog(type = BaseDataOpLogType.BASE_DATA, name = "修改门店，ID：{}", params = {"#id"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateShopVo vo) {

    Shop data = getBaseMapper().selectById(vo.getId());
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("门店不存在！");
    }

    Wrapper<Shop> checkWrapper = Wrappers.lambdaQuery(Shop.class).eq(Shop::getCode, vo.getCode())
        .ne(Shop::getId, vo.getId());
    if (this.count(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    LambdaUpdateWrapper<Shop> updateWrapper = Wrappers.lambdaUpdate(Shop.class)
        .set(Shop::getCode, vo.getCode())
        .set(Shop::getName, vo.getName())
        .set(Shop::getDeptId, StringUtil.isBlank(vo.getDeptId()) ? null : vo.getDeptId())
        .set(Shop::getLng, vo.getLng() == null ? null : vo.getLng())
        .set(Shop::getLat, vo.getLat() == null ? null : vo.getLat())
        .set(Shop::getAvailable, vo.getAvailable())
        .set(Shop::getDescription,
            StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription())
        .eq(Shop::getId, vo.getId());

    getBaseMapper().update(updateWrapper);

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setExtra(vo);
  }

  @CacheEvict(value = Shop.CACHE_NAME, key = "@cacheVariables.tenantId() + #key")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}

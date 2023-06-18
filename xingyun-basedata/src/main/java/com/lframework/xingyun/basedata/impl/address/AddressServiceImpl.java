package com.lframework.xingyun.basedata.impl.address;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.enums.DefaultOpLogType;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageHelperUtil;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.web.utils.EnumUtil;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.xingyun.basedata.entity.Address;
import com.lframework.xingyun.basedata.enums.AddressEntityType;
import com.lframework.xingyun.basedata.enums.AddressType;
import com.lframework.xingyun.basedata.mappers.AddressMapper;
import com.lframework.xingyun.basedata.service.address.AddressService;
import com.lframework.xingyun.basedata.vo.address.AddressSelectorVo;
import com.lframework.xingyun.basedata.vo.address.CreateAddressVo;
import com.lframework.xingyun.basedata.vo.address.QueryAddressVo;
import com.lframework.xingyun.basedata.vo.address.UpdateAddressVo;
import com.lframework.xingyun.core.dto.dic.city.DicCityDto;
import com.lframework.xingyun.core.service.DicCityService;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddressServiceImpl extends BaseMpServiceImpl<AddressMapper, Address>
    implements AddressService {

  @Autowired
  private DicCityService dicCityService;

  @Override
  public PageResult<Address> query(Integer pageIndex, Integer pageSize, QueryAddressVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<Address> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<Address> query(QueryAddressVo vo) {

    return getBaseMapper().query(vo);
  }

  @Override
  public PageResult<Address> selector(Integer pageIndex, Integer pageSize, AddressSelectorVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<Address> datas = getBaseMapper().selector(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Cacheable(value = Address.CACHE_NAME, key = "@cacheVariables.tenantId() + #id", unless = "#result == null")
  @Override
  public Address findById(String id) {

    return getBaseMapper().selectById(id);
  }

  @OpLog(type = DefaultOpLogType.OTHER, name = "新增地址，ID：{}, 编号：{}", params = {"#_result",
      "#vo.code"}, autoSaveParams = true)
  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(CreateAddressVo vo) {

    if (vo.getIsDefault()) {
      Wrapper<Address> checkWrapper = Wrappers.lambdaQuery(Address.class)
          .eq(Address::getEntityId, vo.getEntityId())
          .eq(Address::getAddressType, vo.getAddressType()).eq(Address::getIsDefault, Boolean.TRUE);
      if (this.count(checkWrapper) > 0) {
        throw new DefaultClientException("实体已存在默认地址，不允许再新增默认地址！");
      }
    }

    Address data = new Address();
    data.setId(IdUtil.getId());
    data.setEntityId(vo.getEntityId());
    data.setEntityType(EnumUtil.getByCode(AddressEntityType.class, vo.getEntityType()));
    data.setAddressType(EnumUtil.getByCode(AddressType.class, vo.getAddressType()));
    data.setName(vo.getName());
    data.setTelephone(vo.getTelephone());
    DicCityDto district = dicCityService.findById(vo.getCityId());
    data.setDistrictId(district.getId());
    DicCityDto city = dicCityService.findById(district.getParentId());
    data.setCityId(city.getId());
    DicCityDto province = dicCityService.findById(city.getParentId());
    data.setProvinceId(province.getId());
    data.setAddress(vo.getAddress());
    data.setIsDefault(vo.getIsDefault());

    getBaseMapper().insert(data);

    return data.getId();
  }

  @OpLog(type = DefaultOpLogType.OTHER, name = "修改地址，ID：{}, 编号：{}", params = {"#vo.id",
      "#vo.code"}, autoSaveParams = true)
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateAddressVo vo) {

    Address data = getBaseMapper().selectById(vo.getId());
    if (data == null) {
      throw new DefaultClientException("地址不存在！");
    }
    if (vo.getIsDefault()) {
      Wrapper<Address> checkWrapper = Wrappers.lambdaQuery(Address.class)
          .eq(Address::getEntityId, vo.getEntityId())
          .eq(Address::getAddressType, vo.getAddressType()).eq(Address::getIsDefault, Boolean.TRUE)
          .ne(Address::getId, vo.getId());
      if (this.count(checkWrapper) > 0) {
        throw new DefaultClientException("实体已存在默认地址，不允许再新增默认地址！");
      }
    }
    data.setEntityId(vo.getEntityId());
    data.setEntityType(EnumUtil.getByCode(AddressEntityType.class, vo.getEntityType()));
    data.setAddressType(EnumUtil.getByCode(AddressType.class, vo.getAddressType()));
    data.setName(vo.getName());
    data.setTelephone(vo.getTelephone());
    DicCityDto district = dicCityService.findById(vo.getCityId());
    data.setDistrictId(district.getId());
    DicCityDto city = dicCityService.findById(district.getParentId());
    data.setCityId(city.getId());
    DicCityDto province = dicCityService.findById(city.getParentId());
    data.setProvinceId(province.getId());
    data.setAddress(vo.getAddress());
    data.setIsDefault(vo.getIsDefault());

    getBaseMapper().updateAllColumnById(data);
  }

  @Override
  public Address getDefaultAddress(String entityId, Integer entityType, Integer addressType) {
    Wrapper<Address> queryWrapper = Wrappers.lambdaQuery(Address.class)
        .eq(Address::getEntityId, entityId).eq(Address::getEntityType, entityType)
        .eq(Address::getAddressType, addressType).eq(Address::getIsDefault, Boolean.TRUE);
    return this.getOne(queryWrapper);
  }

  @CacheEvict(value = Address.CACHE_NAME, key = "@cacheVariables.tenantId() + #key")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}

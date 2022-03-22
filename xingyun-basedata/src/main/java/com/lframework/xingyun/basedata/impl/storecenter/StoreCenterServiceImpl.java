package com.lframework.xingyun.basedata.impl.storecenter;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.common.constants.StringPool;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.exceptions.impl.InputErrorException;
import com.lframework.common.utils.Assert;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.IdUtil;
import com.lframework.common.utils.ObjectUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.enums.OpLogType;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.OpLogUtil;
import com.lframework.starter.mybatis.utils.PageHelperUtil;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.xingyun.basedata.dto.storecenter.StoreCenterDto;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.mappers.StoreCenterMapper;
import com.lframework.xingyun.basedata.service.storecenter.IStoreCenterService;
import com.lframework.xingyun.basedata.vo.storecenter.CreateStoreCenterVo;
import com.lframework.xingyun.basedata.vo.storecenter.QueryStoreCenterSelectorVo;
import com.lframework.xingyun.basedata.vo.storecenter.QueryStoreCenterVo;
import com.lframework.xingyun.basedata.vo.storecenter.UpdateStoreCenterVo;
import com.lframework.xingyun.core.dto.dic.city.DicCityDto;
import com.lframework.xingyun.core.service.IDicCityService;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StoreCenterServiceImpl implements IStoreCenterService {

  @Autowired
  private StoreCenterMapper storeCenterMapper;

  @Autowired
  private IDicCityService dicCityService;

  @Override
  public PageResult<StoreCenterDto> query(Integer pageIndex, Integer pageSize,
      QueryStoreCenterVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<StoreCenterDto> datas = storeCenterMapper.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Cacheable(value = StoreCenterDto.CACHE_NAME, key = "#id", unless = "#result == null")
  @Override
  public StoreCenterDto getById(String id) {

    return storeCenterMapper.getById(id);
  }

  @OpLog(type = OpLogType.OTHER, name = "停用仓库，ID：{}", params = "#ids", loopFormat = true)
  @Transactional
  @Override
  public void batchUnable(Collection<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return;
    }

    Wrapper<StoreCenter> updateWrapper = Wrappers.lambdaUpdate(StoreCenter.class)
        .set(StoreCenter::getAvailable, Boolean.FALSE).in(StoreCenter::getId, ids);
    storeCenterMapper.update(updateWrapper);

    IStoreCenterService thisService = getThis(this.getClass());
    for (String id : ids) {
      thisService.cleanCacheByKey(id);
    }
  }

  @OpLog(type = OpLogType.OTHER, name = "启用仓库，ID：{}", params = "#ids", loopFormat = true)
  @Transactional
  @Override
  public void batchEnable(Collection<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return;
    }

    Wrapper<StoreCenter> updateWrapper = Wrappers.lambdaUpdate(StoreCenter.class)
        .set(StoreCenter::getAvailable, Boolean.TRUE).in(StoreCenter::getId, ids);
    storeCenterMapper.update(updateWrapper);

    IStoreCenterService thisService = getThis(this.getClass());
    for (String id : ids) {
      thisService.cleanCacheByKey(id);
    }
  }

  @OpLog(type = OpLogType.OTHER, name = "新增仓库，ID：{}, 编号：{}", params = {"#id", "#code"})
  @Transactional
  @Override
  public String create(CreateStoreCenterVo vo) {

    Wrapper<StoreCenter> checkWrapper = Wrappers.lambdaQuery(StoreCenter.class)
        .eq(StoreCenter::getCode, vo.getCode());
    if (storeCenterMapper.selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    StoreCenter data = new StoreCenter();
    data.setId(IdUtil.getId());
    data.setCode(vo.getCode());
    data.setName(vo.getName());
    if (!StringUtil.isBlank(vo.getContact())) {
      data.setContact(vo.getContact());
    }
    if (!StringUtil.isBlank(vo.getTelephone())) {
      data.setTelephone(vo.getTelephone());
    }
    data.setAvailable(Boolean.TRUE);
    if (!StringUtil.isBlank(vo.getCityId())) {
      DicCityDto city = dicCityService.getById(vo.getCityId());
      if (!ObjectUtil.isNull(city)) {
        data.setCityId(vo.getCityId());
      }
    }

    if (!StringUtil.isBlank(vo.getAddress())) {
      data.setAddress(vo.getAddress());
    }

    if (vo.getPeopleNum() != null) {
      if (vo.getPeopleNum() < 0) {
        throw new InputErrorException("仓库人数不允许小于0！");
      }
      data.setPeopleNum(vo.getPeopleNum());
    }

    data.setDescription(
        StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());

    storeCenterMapper.insert(data);

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);

    return data.getId();
  }

  @OpLog(type = OpLogType.OTHER, name = "修改仓库，ID：{}, 编号：{}", params = {"#id", "#code"})
  @Transactional
  @Override
  public void update(UpdateStoreCenterVo vo) {

    StoreCenter data = storeCenterMapper.selectById(vo.getId());
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("仓库不存在！");
    }

    Wrapper<StoreCenter> checkWrapper = Wrappers.lambdaQuery(StoreCenter.class)
        .eq(StoreCenter::getCode, vo.getCode()).ne(StoreCenter::getId, vo.getId());
    if (storeCenterMapper.selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    LambdaUpdateWrapper<StoreCenter> updateWrapper = Wrappers.lambdaUpdate(StoreCenter.class)
        .set(StoreCenter::getCode, vo.getCode()).set(StoreCenter::getName, vo.getName())
        .set(StoreCenter::getContact, !StringUtil.isBlank(vo.getContact()) ? vo.getContact() : null)
        .set(StoreCenter::getTelephone,
            !StringUtil.isBlank(vo.getTelephone()) ? vo.getTelephone() : null)
        .set(StoreCenter::getAvailable, vo.getAvailable())
        .set(StoreCenter::getAddress, !StringUtil.isBlank(vo.getAddress()) ? vo.getAddress() : null)
        .set(StoreCenter::getDescription,
            StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription())
        .eq(StoreCenter::getId, vo.getId());

    if (!StringUtil.isBlank(vo.getCityId())) {
      DicCityDto city = dicCityService.getById(vo.getCityId());
      if (!ObjectUtil.isNull(city)) {
        updateWrapper.set(StoreCenter::getCityId, vo.getCityId());
      }
    } else {
      updateWrapper.set(StoreCenter::getCityId, null);
    }

    if (vo.getPeopleNum() != null) {
      if (vo.getPeopleNum() < 0) {
        throw new InputErrorException("仓库人数不允许小于0！");
      }
      updateWrapper.set(StoreCenter::getPeopleNum, vo.getPeopleNum());
    } else {
      updateWrapper.set(StoreCenter::getPeopleNum, null);
    }

    storeCenterMapper.update(updateWrapper);

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);

    IStoreCenterService thisService = getThis(this.getClass());
    thisService.cleanCacheByKey(data.getId());
  }

  @Override
  public PageResult<StoreCenterDto> selector(Integer pageIndex, Integer pageSize,
      QueryStoreCenterSelectorVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<StoreCenterDto> datas = storeCenterMapper.selector(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @CacheEvict(value = StoreCenterDto.CACHE_NAME, key = "#key")
  @Override
  public void cleanCacheByKey(String key) {

  }
}

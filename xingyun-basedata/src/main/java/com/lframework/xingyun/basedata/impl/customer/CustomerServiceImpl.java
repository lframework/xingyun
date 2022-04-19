package com.lframework.xingyun.basedata.impl.customer;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.common.constants.StringPool;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.Assert;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.IdUtil;
import com.lframework.common.utils.ObjectUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.enums.OpLogType;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.OpLogUtil;
import com.lframework.starter.mybatis.utils.PageHelperUtil;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.web.utils.EnumUtil;
import com.lframework.xingyun.basedata.dto.customer.CustomerDto;
import com.lframework.xingyun.basedata.entity.Customer;
import com.lframework.xingyun.basedata.enums.SettleType;
import com.lframework.xingyun.basedata.mappers.CustomerMapper;
import com.lframework.xingyun.basedata.service.customer.ICustomerService;
import com.lframework.xingyun.basedata.vo.customer.CreateCustomerVo;
import com.lframework.xingyun.basedata.vo.customer.QueryCustomerSelectorVo;
import com.lframework.xingyun.basedata.vo.customer.QueryCustomerVo;
import com.lframework.xingyun.basedata.vo.customer.UpdateCustomerVo;
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
public class CustomerServiceImpl extends BaseMpServiceImpl<CustomerMapper, Customer> implements
    ICustomerService {

  @Autowired
  private IDicCityService dicCityService;

  @Override
  public PageResult<CustomerDto> query(Integer pageIndex, Integer pageSize, QueryCustomerVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<CustomerDto> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<CustomerDto> query(QueryCustomerVo vo) {

    return getBaseMapper().query(vo);
  }

  @Cacheable(value = CustomerDto.CACHE_NAME, key = "#id", unless = "#result == null")
  @Override
  public CustomerDto getById(String id) {

    return getBaseMapper().getById(id);
  }

  @Override
  public PageResult<CustomerDto> selector(Integer pageIndex, Integer pageSize,
      QueryCustomerSelectorVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);

    List<CustomerDto> datas = getBaseMapper().selector(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @OpLog(type = OpLogType.OTHER, name = "停用客户，ID：{}", params = "#ids", loopFormat = true)
  @Transactional
  @Override
  public void batchUnable(Collection<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return;
    }

    Wrapper<Customer> updateWrapper = Wrappers.lambdaUpdate(Customer.class)
        .set(Customer::getAvailable, Boolean.FALSE).in(Customer::getId, ids);
    getBaseMapper().update(updateWrapper);

    ICustomerService thisService = getThis(this.getClass());
    for (String id : ids) {
      thisService.cleanCacheByKey(id);
    }
  }

  @OpLog(type = OpLogType.OTHER, name = "启用客户，ID：{}", params = "#ids", loopFormat = true)
  @Transactional
  @Override
  public void batchEnable(Collection<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return;
    }

    Wrapper<Customer> updateWrapper = Wrappers.lambdaUpdate(Customer.class)
        .set(Customer::getAvailable, Boolean.TRUE).in(Customer::getId, ids);
    getBaseMapper().update(updateWrapper);

    ICustomerService thisService = getThis(this.getClass());
    for (String id : ids) {
      thisService.cleanCacheByKey(id);
    }
  }

  @OpLog(type = OpLogType.OTHER, name = "新增客户，ID：{}, 编号：{}", params = {"#id", "#code"})
  @Transactional
  @Override
  public String create(CreateCustomerVo vo) {

    Wrapper<Customer> checkWrapper = Wrappers.lambdaQuery(Customer.class)
        .eq(Customer::getCode, vo.getCode());
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    Customer data = new Customer();
    data.setId(IdUtil.getId());
    data.setCode(vo.getCode());
    data.setName(vo.getName());
    if (!StringUtil.isBlank(vo.getMnemonicCode())) {
      data.setMnemonicCode(vo.getMnemonicCode());
    }
    if (!StringUtil.isBlank(vo.getContact())) {
      data.setContact(vo.getContact());
    }
    if (!StringUtil.isBlank(vo.getTelephone())) {
      data.setTelephone(vo.getTelephone());
    }
    if (!StringUtil.isBlank(vo.getEmail())) {
      data.setEmail(vo.getEmail());
    }
    if (!StringUtil.isBlank(vo.getZipCode())) {
      data.setZipCode(vo.getZipCode());
    }
    if (!StringUtil.isBlank(vo.getFax())) {
      data.setFax(vo.getFax());
    }
    if (!StringUtil.isBlank(vo.getCityId())) {
      DicCityDto city = dicCityService.getById(vo.getCityId());
      if (!ObjectUtil.isNull(city)) {
        data.setCityId(vo.getCityId());
      }
    }
    if (!StringUtil.isBlank(vo.getAddress())) {
      data.setAddress(vo.getAddress());
    }
    if (!StringUtil.isBlank(vo.getReceiver())) {
      data.setReceiver(vo.getReceiver());
    }
    if (!StringUtil.isBlank(vo.getReceiveTelephone())) {
      data.setReceiveTelephone(vo.getReceiveTelephone());
    }
    if (!StringUtil.isBlank(vo.getReceiveAddress())) {
      data.setReceiveAddress(vo.getReceiveAddress());
    }
    data.setSettleType(EnumUtil.getByCode(SettleType.class, vo.getSettleType()));
    if (!StringUtil.isBlank(vo.getCreditCode())) {
      data.setCreditCode(vo.getCreditCode());
    }
    if (!StringUtil.isBlank(vo.getTaxIdentifyNo())) {
      data.setTaxIdentifyNo(vo.getTaxIdentifyNo());
    }
    if (!StringUtil.isBlank(vo.getBankName())) {
      data.setBankName(vo.getBankName());
    }
    if (!StringUtil.isBlank(vo.getAccountName())) {
      data.setAccountName(vo.getAccountName());
    }
    if (!StringUtil.isBlank(vo.getAccountNo())) {
      data.setAccountNo(vo.getAccountNo());
    }
    data.setAvailable(Boolean.TRUE);
    data.setDescription(
        StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());

    getBaseMapper().insert(data);

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);

    return data.getId();
  }

  @OpLog(type = OpLogType.OTHER, name = "修改客户，ID：{}, 编号：{}", params = {"#id", "#code"})
  @Transactional
  @Override
  public void update(UpdateCustomerVo vo) {

    Customer data = getBaseMapper().selectById(vo.getId());
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("客户不存在！");
    }

    Wrapper<Customer> checkWrapper = Wrappers.lambdaQuery(Customer.class)
        .eq(Customer::getCode, vo.getCode())
        .ne(Customer::getId, vo.getId());
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    LambdaUpdateWrapper<Customer> updateWrapper = Wrappers.lambdaUpdate(Customer.class)
        .set(Customer::getCode, vo.getCode()).set(Customer::getName, vo.getName())
        .set(Customer::getMnemonicCode,
            !StringUtil.isBlank(vo.getMnemonicCode()) ? vo.getMnemonicCode() : null)
        .set(Customer::getContact, !StringUtil.isBlank(vo.getContact()) ? vo.getContact() : null)
        .set(Customer::getTelephone,
            !StringUtil.isBlank(vo.getTelephone()) ? vo.getTelephone() : null)
        .set(Customer::getAddress, !StringUtil.isBlank(vo.getAddress()) ? vo.getAddress() : null)
        .set(Customer::getEmail, !StringUtil.isBlank(vo.getEmail()) ? vo.getEmail() : null)
        .set(Customer::getZipCode, !StringUtil.isBlank(vo.getZipCode()) ? vo.getZipCode() : null)
        .set(Customer::getFax, !StringUtil.isBlank(vo.getFax()) ? vo.getFax() : null)
        .set(Customer::getAddress, !StringUtil.isBlank(vo.getAddress()) ? vo.getAddress() : null)
        .set(Customer::getReceiver, !StringUtil.isBlank(vo.getReceiver()) ? vo.getReceiver() : null)
        .set(Customer::getReceiveTelephone,
            !StringUtil.isBlank(vo.getReceiveTelephone()) ? vo.getReceiveTelephone() : null)
        .set(Customer::getReceiveAddress,
            !StringUtil.isBlank(vo.getReceiveAddress()) ? vo.getReceiveAddress() : null)
        .set(Customer::getSettleType, EnumUtil.getByCode(SettleType.class, vo.getSettleType()))
        .set(Customer::getCreditCode,
            !StringUtil.isBlank(vo.getCreditCode()) ? vo.getCreditCode() : null)
        .set(Customer::getTaxIdentifyNo,
            !StringUtil.isBlank(vo.getTaxIdentifyNo()) ? vo.getTaxIdentifyNo() : null)
        .set(Customer::getBankName, !StringUtil.isBlank(vo.getBankName()) ? vo.getBankName() : null)
        .set(Customer::getAccountName,
            !StringUtil.isBlank(vo.getAccountName()) ? vo.getAccountName() : null)
        .set(Customer::getAccountNo,
            !StringUtil.isBlank(vo.getAccountNo()) ? vo.getAccountNo() : null)
        .set(Customer::getAvailable, vo.getAvailable()).set(Customer::getDescription,
            StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription())
        .eq(Customer::getId, vo.getId());

    if (!StringUtil.isBlank(vo.getCityId())) {
      DicCityDto city = dicCityService.getById(vo.getCityId());
      if (!ObjectUtil.isNull(city)) {
        updateWrapper.set(Customer::getCityId, vo.getCityId());
      }
    } else {
      updateWrapper.set(Customer::getCityId, null);
    }

    getBaseMapper().update(updateWrapper);

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);

    ICustomerService thisService = getThis(this.getClass());
    thisService.cleanCacheByKey(data.getId());
  }

  @CacheEvict(value = CustomerDto.CACHE_NAME, key = "#key")
  @Override
  public void cleanCacheByKey(String key) {

  }
}

package com.lframework.xingyun.basedata.impl.logistics;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.ObjectUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.utils.IdUtil;
import com.lframework.starter.web.core.utils.PageHelperUtil;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.xingyun.basedata.entity.LogisticsCompany;
import com.lframework.xingyun.basedata.enums.BaseDataOpLogType;
import com.lframework.xingyun.basedata.mappers.LogisticsCompanyMapper;
import com.lframework.xingyun.basedata.service.logistics.LogisticsCompanyService;
import com.lframework.xingyun.basedata.vo.logistics.company.CreateLogisticsCompanyVo;
import com.lframework.xingyun.basedata.vo.logistics.company.QueryLogisticsCompanySelectorVo;
import com.lframework.xingyun.basedata.vo.logistics.company.QueryLogisticsCompanyVo;
import com.lframework.xingyun.basedata.vo.logistics.company.UpdateLogisticsCompanyVo;
import com.lframework.starter.web.core.annotations.oplog.OpLog;
import com.lframework.starter.web.inner.dto.dic.city.DicCityDto;
import com.lframework.starter.web.inner.service.DicCityService;
import com.lframework.starter.web.core.utils.OpLogUtil;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LogisticsCompanyServiceImpl extends
    BaseMpServiceImpl<LogisticsCompanyMapper, LogisticsCompany> implements
    LogisticsCompanyService {

  @Autowired
  private DicCityService dicCityService;

  @Override
  public PageResult<LogisticsCompany> query(Integer pageIndex, Integer pageSize,
      QueryLogisticsCompanyVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<LogisticsCompany> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<LogisticsCompany> query(QueryLogisticsCompanyVo vo) {

    return getBaseMapper().query(vo);
  }

  @Cacheable(value = LogisticsCompany.CACHE_NAME, key = "@cacheVariables.tenantId() + #id", unless = "#result == null")
  @Override
  public LogisticsCompany findById(String id) {

    return getBaseMapper().selectById(id);
  }

  @OpLog(type = BaseDataOpLogType.class, name = "停用物流公司，ID：{}", params = "#id")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void unable(String id) {

    Wrapper<LogisticsCompany> updateWrapper = Wrappers.lambdaUpdate(LogisticsCompany.class)
        .set(LogisticsCompany::getAvailable, Boolean.FALSE).eq(LogisticsCompany::getId, id);
    getBaseMapper().update(updateWrapper);
  }

  @OpLog(type = BaseDataOpLogType.class, name = "启用物流公司，ID：{}", params = "#id")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void enable(String id) {

    Wrapper<LogisticsCompany> updateWrapper = Wrappers.lambdaUpdate(LogisticsCompany.class)
        .set(LogisticsCompany::getAvailable, Boolean.TRUE).eq(LogisticsCompany::getId, id);
    getBaseMapper().update(updateWrapper);
  }

  @OpLog(type = BaseDataOpLogType.class, name = "新增物流公司，ID：{}, 编号：{}", params = {"#id",
      "#code"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(CreateLogisticsCompanyVo vo) {

    Wrapper<LogisticsCompany> checkWrapper = Wrappers.lambdaQuery(LogisticsCompany.class)
        .eq(LogisticsCompany::getCode, vo.getCode());
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    LogisticsCompany data = new LogisticsCompany();
    data.setId(IdUtil.getId());
    data.setCode(vo.getCode());
    data.setName(vo.getName());
    if (!StringUtil.isBlank(vo.getContact())) {
      data.setContact(vo.getContact());
    }
    if (!StringUtil.isBlank(vo.getTelephone())) {
      data.setTelephone(vo.getTelephone());
    }
    if (!StringUtil.isBlank(vo.getCityId())) {
      DicCityDto city = dicCityService.findById(vo.getCityId());
      if (!ObjectUtil.isNull(city)) {
        data.setCityId(vo.getCityId());
      }
    }
    if (!StringUtil.isBlank(vo.getAddress())) {
      data.setAddress(vo.getAddress());
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

  @OpLog(type = BaseDataOpLogType.class, name = "修改物流公司，ID：{}, 编号：{}", params = {"#id",
      "#code"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateLogisticsCompanyVo vo) {

    LogisticsCompany data = getBaseMapper().selectById(vo.getId());
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("物流公司不存在！");
    }

    Wrapper<LogisticsCompany> checkWrapper = Wrappers.lambdaQuery(LogisticsCompany.class)
        .eq(LogisticsCompany::getCode, vo.getCode())
        .ne(LogisticsCompany::getId, vo.getId());
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    LambdaUpdateWrapper<LogisticsCompany> updateWrapper = Wrappers.lambdaUpdate(
            LogisticsCompany.class)
        .set(LogisticsCompany::getCode, vo.getCode()).set(LogisticsCompany::getName, vo.getName())
        .set(LogisticsCompany::getContact,
            !StringUtil.isBlank(vo.getContact()) ? vo.getContact() : null)
        .set(LogisticsCompany::getTelephone,
            !StringUtil.isBlank(vo.getTelephone()) ? vo.getTelephone() : null)
        .set(LogisticsCompany::getAddress,
            !StringUtil.isBlank(vo.getAddress()) ? vo.getAddress() : null)
        .set(LogisticsCompany::getAvailable, vo.getAvailable())
        .set(LogisticsCompany::getDescription,
            StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription())
        .eq(LogisticsCompany::getId, vo.getId());

    if (!StringUtil.isBlank(vo.getCityId())) {
      DicCityDto city = dicCityService.findById(vo.getCityId());
      if (!ObjectUtil.isNull(city)) {
        updateWrapper.set(LogisticsCompany::getCityId, vo.getCityId());
      }
    } else {
      updateWrapper.set(LogisticsCompany::getCityId, null);
    }

    getBaseMapper().update(updateWrapper);

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);
  }

  @Override
  public PageResult<LogisticsCompany> selector(Integer pageIndex, Integer pageSize,
      QueryLogisticsCompanySelectorVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<LogisticsCompany> datas = getBaseMapper().selector(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @CacheEvict(value = LogisticsCompany.CACHE_NAME, key = "@cacheVariables.tenantId() + #key")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}

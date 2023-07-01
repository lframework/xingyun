package com.lframework.xingyun.template.inner.impl.system;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.exceptions.impl.ParameterNotFoundException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.ObjectUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.impl.BaseMpServiceImpl;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.PageHelperUtil;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.xingyun.template.core.annotations.OpLog;
import com.lframework.xingyun.template.inner.entity.SysParameter;
import com.lframework.xingyun.template.inner.vo.system.parameter.CreateSysParameterVo;
import com.lframework.xingyun.template.inner.vo.system.parameter.QuerySysParameterVo;
import com.lframework.xingyun.template.inner.vo.system.parameter.UpdateSysParameterVo;
import com.lframework.xingyun.template.inner.mappers.system.SysParameterMapper;
import com.lframework.xingyun.template.core.enums.DefaultOpLogType;
import com.lframework.xingyun.template.inner.service.system.SysParameterService;
import com.lframework.xingyun.template.core.utils.OpLogUtil;
import java.io.Serializable;
import java.util.List;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysParameterServiceImpl extends
    BaseMpServiceImpl<SysParameterMapper, SysParameter> implements SysParameterService,
    com.lframework.starter.web.service.SysParameterService {

  @Override
  public PageResult<SysParameter> query(Integer pageIndex, Integer pageSize,
      QuerySysParameterVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<SysParameter> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<SysParameter> query(QuerySysParameterVo vo) {

    return getBaseMapper().query(vo);
  }

  @Cacheable(value = SysParameter.CACHE_NAME, key = "@cacheVariables.tenantId() + #id", unless = "#result == null")
  @Override
  public SysParameter findById(Long id) {

    return getBaseMapper().selectById(id);
  }

  @Cacheable(value = SysParameter.CACHE_NAME, key = "@cacheVariables.tenantId() + #key", unless = "#result == null")
  @Override
  public String findByKey(String key) {

    Wrapper<SysParameter> queryWrapper = Wrappers.lambdaQuery(SysParameter.class)
        .eq(SysParameter::getPmKey, key);
    SysParameter data = getBaseMapper().selectOne(queryWrapper);

    return data == null ? null : data.getPmValue();
  }

  @Override
  public String findRequiredByKey(String key) throws ParameterNotFoundException {
    com.lframework.starter.web.service.SysParameterService thisService = getThis(this.getClass());
    String data = thisService.findByKey(key);
    if (data == null) {
      throw new ParameterNotFoundException();
    }

    return data;
  }

  @Override
  public String findByKey(String key, String defaultValue) {
    com.lframework.starter.web.service.SysParameterService thisService = getThis(this.getClass());
    String data = thisService.findByKey(key);
    if (data == null) {
      return defaultValue;
    }

    return data;
  }

  @OpLog(type = DefaultOpLogType.OTHER, name = "新增系统参数，ID：{}", params = {"#id"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public Long create(CreateSysParameterVo vo) {

    Wrapper<SysParameter> checkWrapper = Wrappers.lambdaQuery(SysParameter.class)
        .eq(SysParameter::getPmKey, vo.getPmKey());
    if (this.count(checkWrapper) > 0) {
      throw new DefaultClientException("键重复，请重新输入！");
    }
    SysParameter data = new SysParameter();
    data.setPmKey(vo.getPmKey());
    if (!StringUtil.isEmpty(vo.getPmValue())) {
      data.setPmValue(vo.getPmValue());
    }
    if (!StringUtil.isBlank(vo.getDescription())) {
      data.setDescription(vo.getDescription());
    }

    getBaseMapper().insert(data);

    OpLogUtil.setVariable("id", String.valueOf(data.getId()));
    OpLogUtil.setExtra(vo);

    return data.getId();
  }

  @OpLog(type = DefaultOpLogType.OTHER, name = "修改系统参数，ID：{}", params = {"#id"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateSysParameterVo vo) {

    SysParameter data = getBaseMapper().selectById(vo.getId());
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("系统参数不存在！");
    }

    LambdaUpdateWrapper<SysParameter> updateWrapper = Wrappers.lambdaUpdate(SysParameter.class)
        .set(SysParameter::getPmValue, StringUtil.isBlank(vo.getPmValue()) ? null : vo.getPmValue())
        .set(SysParameter::getDescription,
            StringUtil.isBlank(vo.getDescription()) ? null : vo.getDescription())
        .eq(SysParameter::getId, vo.getId());

    getBaseMapper().update(updateWrapper);

    OpLogUtil.setVariable("id", String.valueOf(data.getId()));
    OpLogUtil.setExtra(vo);
  }

  @OpLog(type = DefaultOpLogType.OTHER, name = "删除系统参数，ID：{}", params = {"#id"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteById(Long id) {

    getBaseMapper().deleteById(id);
  }

  @Override
  public Boolean getBoolean(String key) {
    com.lframework.starter.web.service.SysParameterService thisService = getThis(getClass());
    String value = thisService.findByKey(key);
    return value == null ? null : "true".equalsIgnoreCase(value);
  }

  @Override
  public Boolean getBoolean(String key, Boolean defaultValue) {
    Boolean value = getBoolean(key);
    if (value == null) {
      return defaultValue;
    }

    return value;
  }

  @Override
  public Integer getInteger(String key) {
    com.lframework.starter.web.service.SysParameterService thisService = getThis(getClass());
    String value = thisService.findByKey(key);
    try {
      return value == null ? null : Integer.valueOf(value);
    } catch (NumberFormatException e) {
      // 转换失败
      return null;
    }
  }

  @Override
  public Integer getInteger(String key, Integer defaultValue) {
    Integer value = getInteger(key);
    if (value == null) {
      return defaultValue;
    }

    return value;
  }

  @CacheEvict(value = SysParameter.CACHE_NAME, key = "@cacheVariables.tenantId() + #key")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}

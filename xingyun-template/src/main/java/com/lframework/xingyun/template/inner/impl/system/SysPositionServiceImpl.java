package com.lframework.xingyun.template.inner.impl.system;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.ObjectUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.impl.BaseMpServiceImpl;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.PageHelperUtil;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.xingyun.template.core.annotations.OpLog;
import com.lframework.xingyun.template.core.enums.DefaultOpLogType;
import com.lframework.xingyun.template.core.utils.OpLogUtil;
import com.lframework.xingyun.template.inner.entity.SysPosition;
import com.lframework.xingyun.template.inner.vo.system.position.CreateSysPositionVo;
import com.lframework.xingyun.template.inner.vo.system.position.QuerySysPositionVo;
import com.lframework.xingyun.template.inner.vo.system.position.SysPositionSelectorVo;
import com.lframework.xingyun.template.inner.vo.system.position.UpdateSysPositionVo;
import com.lframework.xingyun.template.inner.mappers.system.SysPositionMapper;
import com.lframework.xingyun.template.inner.service.system.SysPositionService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysPositionServiceImpl extends
    BaseMpServiceImpl<SysPositionMapper, SysPosition> implements SysPositionService {

  @Override
  public PageResult<SysPosition> query(Integer pageIndex, Integer pageSize,
      QuerySysPositionVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);

    List<SysPosition> datas = this.doQuery(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Cacheable(value = SysPosition.CACHE_NAME, key = "@cacheVariables.tenantId() + #id", unless = "#result == null")
  @Override
  public SysPosition findById(String id) {

    return this.doGetById(id);
  }

  @Override
  public PageResult<SysPosition> selector(Integer pageIndex, Integer pageSize,
      SysPositionSelectorVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);

    List<SysPosition> datas = this.doSelector(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @OpLog(type = DefaultOpLogType.OTHER, name = "停用岗位，ID：{}", params = "#ids", loopFormat = true)
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void batchUnable(Collection<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return;
    }

    this.doBatchUnable(ids);
  }

  @OpLog(type = DefaultOpLogType.OTHER, name = "启用岗位，ID：{}", params = "#ids", loopFormat = true)
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void batchEnable(Collection<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return;
    }

    this.doBatchEnable(ids);
  }

  @OpLog(type = DefaultOpLogType.OTHER, name = "新增岗位，ID：{}, 编号：{}", params = {"#id", "#code"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(CreateSysPositionVo vo) {

    SysPosition data = this.doCreate(vo);

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);

    return data.getId();
  }

  @OpLog(type = DefaultOpLogType.OTHER, name = "修改岗位，ID：{}, 编号：{}", params = {"#id", "#code"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateSysPositionVo vo) {

    this.doUpdate(vo);

    OpLogUtil.setVariable("id", vo.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);
  }

  protected List<SysPosition> doQuery(QuerySysPositionVo vo) {

    return getBaseMapper().query(vo);
  }

  protected SysPosition doGetById(String id) {

    return getBaseMapper().findById(id);
  }

  protected List<SysPosition> doSelector(SysPositionSelectorVo vo) {

    return getBaseMapper().selector(vo);
  }

  protected void doBatchUnable(Collection<String> ids) {

    Wrapper<SysPosition> updateWrapper = Wrappers.lambdaUpdate(SysPosition.class)
        .set(SysPosition::getAvailable, Boolean.FALSE).in(SysPosition::getId, ids);
    getBaseMapper().update(updateWrapper);
  }

  protected void doBatchEnable(Collection<String> ids) {

    Wrapper<SysPosition> updateWrapper = Wrappers.lambdaUpdate(SysPosition.class)
        .set(SysPosition::getAvailable, Boolean.TRUE).in(SysPosition::getId, ids);
    getBaseMapper().update(updateWrapper);
  }

  protected SysPosition doCreate(CreateSysPositionVo vo) {

    Wrapper<SysPosition> checkWrapper = Wrappers.lambdaQuery(SysPosition.class)
        .eq(SysPosition::getCode, vo.getCode());
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    checkWrapper = Wrappers.lambdaQuery(SysPosition.class)
        .eq(SysPosition::getName, vo.getName());
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    SysPosition data = new SysPosition();
    data.setId(IdUtil.getId());
    data.setCode(vo.getCode());
    data.setName(vo.getName());

    data.setAvailable(Boolean.TRUE);
    data.setDescription(
        StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());

    getBaseMapper().insert(data);

    return data;
  }

  protected void doUpdate(UpdateSysPositionVo vo) {

    SysPosition data = this.findById(vo.getId());
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("岗位不存在！");
    }

    Wrapper<SysPosition> checkWrapper = Wrappers.lambdaQuery(SysPosition.class)
        .eq(SysPosition::getCode, vo.getCode()).ne(SysPosition::getId, vo.getId());
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    checkWrapper = Wrappers.lambdaQuery(SysPosition.class)
        .eq(SysPosition::getName, vo.getName())
        .ne(SysPosition::getId, vo.getId());
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    LambdaUpdateWrapper<SysPosition> updateWrapper = Wrappers.lambdaUpdate(
            SysPosition.class)
        .set(SysPosition::getCode, vo.getCode())
        .set(SysPosition::getName, vo.getName())
        .set(SysPosition::getAvailable, vo.getAvailable())
        .set(SysPosition::getDescription,
            StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription())
        .eq(SysPosition::getId, vo.getId());

    getBaseMapper().update(updateWrapper);
  }

  @CacheEvict(value = SysPosition.CACHE_NAME, key = "@cacheVariables.tenantId() + #key")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}

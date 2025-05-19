package com.lframework.xingyun.template.inner.impl.system;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.impl.BaseMpServiceImpl;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.starter.web.utils.PageHelperUtil;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.xingyun.core.annotations.OpLog;
import com.lframework.xingyun.core.enums.DefaultOpLogType;
import com.lframework.xingyun.template.inner.entity.SysUserGroup;
import com.lframework.xingyun.template.inner.entity.SysUserGroupDetail;
import com.lframework.xingyun.template.inner.mappers.system.SysUserGroupMapper;
import com.lframework.xingyun.template.inner.service.system.SysUserGroupDetailService;
import com.lframework.xingyun.template.inner.service.system.SysUserGroupService;
import com.lframework.xingyun.template.inner.vo.system.user.group.CreateSysUserGroupVo;
import com.lframework.xingyun.template.inner.vo.system.user.group.QuerySysUserGroupVo;
import com.lframework.xingyun.template.inner.vo.system.user.group.SysUserGroupSelectorVo;
import com.lframework.xingyun.template.inner.vo.system.user.group.UpdateSysUserGroupVo;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysUserGroupServiceImpl extends
    BaseMpServiceImpl<SysUserGroupMapper, SysUserGroup> implements
    SysUserGroupService {

  @Autowired
  private SysUserGroupDetailService sysUserGroupDetailService;

  @Override
  public PageResult<SysUserGroup> query(Integer pageIndex, Integer pageSize,
      QuerySysUserGroupVo vo) {
    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<SysUserGroup> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<SysUserGroup> query(QuerySysUserGroupVo vo) {
    return getBaseMapper().query(vo);
  }

  @Cacheable(value = SysUserGroup.CACHE_NAME, key = "@cacheVariables.tenantId() + #id", unless = "#result == null")
  @Override
  public SysUserGroup findById(String id) {
    return this.getById(id);
  }

  @Override
  public PageResult<SysUserGroup> selector(Integer pageIndex, Integer pageSize,
      SysUserGroupSelectorVo vo) {

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<SysUserGroup> datas = getBaseMapper().selector(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @OpLog(type = DefaultOpLogType.SYSTEM, name = "创建用户组，ID：{}", params = "#_result", autoSaveParams = true)
  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(CreateSysUserGroupVo vo) {

    Wrapper<SysUserGroup> checkWrapper = Wrappers.lambdaQuery(SysUserGroup.class)
        .eq(SysUserGroup::getCode, vo.getCode());
    if (this.count(checkWrapper) > 0) {
      throw new DefaultClientException("编号不允许重复！");
    }

    checkWrapper = Wrappers.lambdaQuery(SysUserGroup.class)
        .eq(SysUserGroup::getName, vo.getName());
    if (this.count(checkWrapper) > 0) {
      throw new DefaultClientException("名称不允许重复！");
    }

    SysUserGroup record = new SysUserGroup();
    record.setId(IdUtil.getId());
    record.setCode(vo.getCode());
    record.setName(vo.getName());
    record.setAvailable(Boolean.TRUE);
    record.setDescription(
        StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());

    this.save(record);

    List<SysUserGroupDetail> detailList = vo.getUserIds().stream().map(t -> {
      SysUserGroupDetail r = new SysUserGroupDetail();
      r.setId(IdUtil.getId());
      r.setGroupId(record.getId());
      r.setUserId(t);

      return r;
    }).collect(Collectors.toList());

    sysUserGroupDetailService.saveBatch(detailList);

    return record.getId();
  }

  @OpLog(type = DefaultOpLogType.SYSTEM, name = "修改用户组，ID：{}", params = "#vo.id", autoSaveParams = true)
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateSysUserGroupVo vo) {

    SysUserGroup record = this.getById(vo.getId());
    if (record == null) {
      throw new DefaultClientException("用户组不存在！");
    }

    Wrapper<SysUserGroup> checkWrapper = Wrappers.lambdaQuery(SysUserGroup.class)
        .eq(SysUserGroup::getCode, vo.getCode())
        .ne(SysUserGroup::getId, record.getId());
    if (this.count(checkWrapper) > 0) {
      throw new DefaultClientException("编号不允许重复！");
    }

    checkWrapper = Wrappers.lambdaQuery(SysUserGroup.class)
        .eq(SysUserGroup::getName, vo.getName())
        .ne(SysUserGroup::getId, record.getId());
    if (this.count(checkWrapper) > 0) {
      throw new DefaultClientException("名称不允许重复！");
    }

    Wrapper<SysUserGroup> updateWrapper = Wrappers.lambdaUpdate(SysUserGroup.class)
        .eq(SysUserGroup::getId, vo.getId())
        .set(SysUserGroup::getName, vo.getName())
        .set(SysUserGroup::getDescription,
            StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription())
        .set(SysUserGroup::getAvailable, vo.getAvailable());
    this.update(updateWrapper);

    Wrapper<SysUserGroupDetail> deleteDetailWrapper = Wrappers.lambdaQuery(
        SysUserGroupDetail.class).eq(SysUserGroupDetail::getGroupId, record.getId());
    sysUserGroupDetailService.remove(deleteDetailWrapper);

    List<SysUserGroupDetail> receiverList = vo.getUserIds().stream().map(t -> {
      SysUserGroupDetail r = new SysUserGroupDetail();
      r.setId(IdUtil.getId());
      r.setGroupId(record.getId());
      r.setUserId(t);

      return r;
    }).collect(Collectors.toList());

    sysUserGroupDetailService.saveBatch(receiverList);
  }

  @CacheEvict(value = SysUserGroup.CACHE_NAME, key = "@cacheVariables.tenantId() + #key")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}

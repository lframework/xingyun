package com.lframework.xingyun.template.inner.impl.system;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.impl.BaseMpServiceImpl;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.utils.EnumUtil;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.starter.web.utils.PageHelperUtil;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.xingyun.core.annotations.OpLog;
import com.lframework.xingyun.core.enums.DefaultOpLogType;
import com.lframework.xingyun.core.service.RecursionMappingService;
import com.lframework.xingyun.template.inner.entity.SysNotifyGroup;
import com.lframework.xingyun.template.inner.entity.SysNotifyGroupReceiver;
import com.lframework.xingyun.template.inner.entity.SysUserDept;
import com.lframework.xingyun.template.inner.entity.SysUserRole;
import com.lframework.xingyun.template.inner.enums.system.SysDeptNodeType;
import com.lframework.xingyun.template.inner.enums.system.SysNotifyReceiverType;
import com.lframework.xingyun.template.inner.mappers.system.SysNotifyGroupMapper;
import com.lframework.xingyun.template.inner.service.system.SysNotifyGroupReceiverService;
import com.lframework.xingyun.template.inner.service.system.SysNotifyGroupService;
import com.lframework.xingyun.template.inner.service.system.SysUserDeptService;
import com.lframework.xingyun.template.inner.service.system.SysUserRoleService;
import com.lframework.xingyun.template.inner.vo.system.notify.CreateSysNotifyGroupVo;
import com.lframework.xingyun.template.inner.vo.system.notify.QuerySysNotifyGroupVo;
import com.lframework.xingyun.template.inner.vo.system.notify.SysNotifyGroupSelectorVo;
import com.lframework.xingyun.template.inner.vo.system.notify.UpdateSysNotifyGroupVo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysNotifyGroupServiceImpl extends
    BaseMpServiceImpl<SysNotifyGroupMapper, SysNotifyGroup> implements
    SysNotifyGroupService {

  @Autowired
  private SysNotifyGroupReceiverService sysNotifyGroupReceiverService;

  @Autowired
  private RecursionMappingService recursionMappingService;

  @Autowired
  private SysUserDeptService sysUserDeptService;

  @Autowired
  private SysUserRoleService sysUserRoleService;

  @Override
  public PageResult<SysNotifyGroup> query(Integer pageIndex, Integer pageSize,
      QuerySysNotifyGroupVo vo) {
    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<SysNotifyGroup> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<SysNotifyGroup> query(QuerySysNotifyGroupVo vo) {
    return getBaseMapper().query(vo);
  }

  @Cacheable(value = SysNotifyGroup.CACHE_NAME, key = "@cacheVariables.tenantId() + #id", unless = "#result == null")
  @Override
  public SysNotifyGroup findById(String id) {
    return this.getById(id);
  }

  @Override
  public PageResult<SysNotifyGroup> selector(Integer pageIndex, Integer pageSize,
      SysNotifyGroupSelectorVo vo) {

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<SysNotifyGroup> datas = getBaseMapper().selector(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @OpLog(type = DefaultOpLogType.SYSTEM, name = "创建消息通知组，ID：{}", params = "#_result", autoSaveParams = true)
  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(CreateSysNotifyGroupVo vo) {

    Wrapper<SysNotifyGroup> checkWrapper = Wrappers.lambdaQuery(SysNotifyGroup.class)
        .eq(SysNotifyGroup::getName, vo.getName());
    if (this.count(checkWrapper) > 0) {
      throw new DefaultClientException("名称不允许重复！");
    }

    SysNotifyGroup record = new SysNotifyGroup();
    record.setId(IdUtil.getId());
    record.setName(vo.getName());
    record.setReceiverType(EnumUtil.getByCode(SysNotifyReceiverType.class, vo.getReceiverType()));
    record.setMessageType(StringUtil.join(StringPool.STR_SPLIT, vo.getMessageType()));
    record.setAvailable(Boolean.TRUE);
    record.setDescription(
        StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());

    this.save(record);

    List<SysNotifyGroupReceiver> receiverList = vo.getReceiverIds().stream().map(t -> {
      SysNotifyGroupReceiver r = new SysNotifyGroupReceiver();
      r.setId(IdUtil.getId());
      r.setGroupId(record.getId());
      r.setReceiverId(t);

      return r;
    }).collect(Collectors.toList());

    sysNotifyGroupReceiverService.saveBatch(receiverList);

    return record.getId();
  }

  @OpLog(type = DefaultOpLogType.SYSTEM, name = "修改消息通知组，ID：{}", params = "#vo.id", autoSaveParams = true)
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateSysNotifyGroupVo vo) {

    SysNotifyGroup record = this.getById(vo.getId());
    if (record == null) {
      throw new DefaultClientException("消息通知组不存在！");
    }

    Wrapper<SysNotifyGroup> checkWrapper = Wrappers.lambdaQuery(SysNotifyGroup.class)
        .eq(SysNotifyGroup::getName, vo.getName())
        .ne(SysNotifyGroup::getId, record.getId());
    if (this.count(checkWrapper) > 0) {
      throw new DefaultClientException("名称不允许重复！");
    }

    Wrapper<SysNotifyGroup> updateWrapper = Wrappers.lambdaUpdate(SysNotifyGroup.class)
        .eq(SysNotifyGroup::getId, vo.getId())
        .set(SysNotifyGroup::getName, vo.getName())
        .set(SysNotifyGroup::getDescription,
            StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription())
        .set(SysNotifyGroup::getReceiverType,
            EnumUtil.getByCode(SysNotifyReceiverType.class, vo.getReceiverType()))
        .set(SysNotifyGroup::getMessageType,
            StringUtil.join(StringPool.STR_SPLIT, vo.getMessageType()))
        .set(SysNotifyGroup::getAvailable, vo.getAvailable());
    this.update(updateWrapper);

    Wrapper<SysNotifyGroupReceiver> deleteReceiverWrapper = Wrappers.lambdaQuery(
        SysNotifyGroupReceiver.class).eq(SysNotifyGroupReceiver::getGroupId, record.getId());
    sysNotifyGroupReceiverService.remove(deleteReceiverWrapper);

    List<SysNotifyGroupReceiver> receiverList = vo.getReceiverIds().stream().map(t -> {
      SysNotifyGroupReceiver r = new SysNotifyGroupReceiver();
      r.setId(IdUtil.getId());
      r.setGroupId(record.getId());
      r.setReceiverId(t);

      return r;
    }).collect(Collectors.toList());

    sysNotifyGroupReceiverService.saveBatch(receiverList);
  }

  @Override
  public Set<String> getReceiveUserIds(String id) {
    SysNotifyGroupService thisService = ApplicationUtil.getBean(SysNotifyGroupService.class);
    SysNotifyGroup notifyGroup = thisService.findById(id);
    if (notifyGroup == null) {
      throw new DefaultClientException("消息通知组不存在！");
    }
    if (!notifyGroup.getAvailable()) {
      return Collections.emptySet();
    }

    Set<String> userIds = new HashSet<>();
    switch (notifyGroup.getReceiverType()) {
      case USER: {
        List<String> receiverIds = sysNotifyGroupReceiverService.getReceiverIdsByGroupId(
            notifyGroup.getId());
        userIds.addAll(receiverIds);
        break;
      }

      case DEPT: {
        List<String> deptIds = sysNotifyGroupReceiverService.getReceiverIdsByGroupId(
            notifyGroup.getId());
        List<String> allDeptIds = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(deptIds)) {
          for (String deptId : deptIds) {
            List<String> childDeptIds = recursionMappingService.getNodeChildIds(deptId,
                ApplicationUtil.getBean(SysDeptNodeType.class));
            allDeptIds.addAll(childDeptIds);
          }
          allDeptIds.addAll(deptIds);
        }

        if (CollectionUtil.isNotEmpty(allDeptIds)) {
          Wrapper<SysUserDept> queryWrapper = Wrappers.lambdaQuery(SysUserDept.class)
              .select(SysUserDept::getUserId)
              .in(SysUserDept::getDeptId, allDeptIds);
          List<SysUserDept> sysUserDeptList = sysUserDeptService.list(queryWrapper);
          userIds.addAll(
              sysUserDeptList.stream().map(SysUserDept::getUserId).collect(Collectors.toList()));
        }

        break;
      }

      case ROLE: {
        List<String> roleIds = sysNotifyGroupReceiverService.getReceiverIdsByGroupId(
            notifyGroup.getId());
        if (CollectionUtil.isNotEmpty(roleIds)) {
          Wrapper<SysUserRole> queryWrapper = Wrappers.lambdaQuery(SysUserRole.class)
              .select(SysUserRole::getUserId)
              .in(SysUserRole::getRoleId, roleIds);
          List<SysUserRole> sysUserRoleList = sysUserRoleService.list(queryWrapper);
          userIds.addAll(
              sysUserRoleList.stream().map(SysUserRole::getUserId).collect(Collectors.toList()));
        }

        break;
      }
      default:
        throw new DefaultClientException("消息通知组接收者类型错误！");
    }

    return userIds;
  }

  @CacheEvict(value = SysNotifyGroup.CACHE_NAME, key = "@cacheVariables.tenantId() + #key")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}

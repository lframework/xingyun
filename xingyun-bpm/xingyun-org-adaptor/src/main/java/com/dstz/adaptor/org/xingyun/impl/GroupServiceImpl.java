package com.dstz.adaptor.org.xingyun.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.dstz.adaptor.org.xingyun.dao.GroupDao;
import com.dstz.adaptor.org.xingyun.dto.DeptDTO;
import com.dstz.adaptor.org.xingyun.dto.PositionDTO;
import com.dstz.adaptor.org.xingyun.dto.RoleDTO;
import com.dstz.base.core.util.BeanCopierUtils;
import com.dstz.org.api.constant.GroupTypeConstant;
import com.dstz.org.api.model.IGroup;
import com.dstz.org.api.model.IUser;
import com.dstz.org.api.model.dto.GroupDTO;
import com.dstz.org.api.service.GroupService;
import com.dstz.org.api.service.UserService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("defaultGroupService")
public class GroupServiceImpl implements GroupService {

  @Resource
  UserService userService;
  @Autowired
  private GroupDao groupDao;

  @Override
  public List<? extends IGroup> getGroupsByGroupTypeUserId(String groupType, String userId) {

    List<? extends IGroup> listGroup = null;

    if (groupType.equals(GroupTypeConstant.ORG.key())) {
      listGroup = groupDao.getDeptByUserId(userId);
    }
    if (groupType.equals(GroupTypeConstant.ROLE.key())) {
      listGroup = groupDao.getRoleByUserId(userId);
    }

    if (groupType.equals(GroupTypeConstant.POST.key())) {
      listGroup = groupDao.getPositionByUserId(userId);
    }

    if (listGroup != null) {
      return BeanCopierUtils.transformList(listGroup, GroupDTO.class);
    }

    return null;
  }

  @Override
  public Map<String, List<? extends IGroup>> getAllGroupByAccount(String account) {

    IUser user = userService.getUserByAccount(account);
    if (user == null) {
      return Collections.EMPTY_MAP;
    }

    return this.getAllGroupByUserId(user.getUserId());
  }

  @Override
  public Map<String, List<? extends IGroup>> getAllGroupByUserId(String userId) {

    Map<String, List<? extends IGroup>> results = new HashMap<>();

    List<? extends IGroup> depts = this.getGroupsByGroupTypeUserId(GroupTypeConstant.ORG.key(),
        userId);
    if (!CollectionUtil.isEmpty(depts)) {
      List<? extends IGroup> groupList = BeanCopierUtils.transformList(depts, GroupDTO.class);
      results.put(GroupTypeConstant.ORG.key(), groupList);
    }

    List<? extends IGroup> roles = this.getGroupsByGroupTypeUserId(GroupTypeConstant.ROLE.key(),
        userId);
    if (!CollectionUtil.isEmpty(roles)) {
      List<? extends IGroup> groupList = BeanCopierUtils.transformList(roles, GroupDTO.class);
      results.put(GroupTypeConstant.ROLE.key(), groupList);
    }

    List<? extends IGroup> positions = this.getGroupsByGroupTypeUserId(GroupTypeConstant.POST.key(),
        userId);
    if (!CollectionUtil.isEmpty(positions)) {
      List<? extends IGroup> groupList = BeanCopierUtils.transformList(positions, GroupDTO.class);
      results.put(GroupTypeConstant.POST.key(), groupList);
    }

    return results;
  }

  @Override
  public List<? extends IGroup> getGroupsByUserId(String userId) {

    Map<String, List<? extends IGroup>> groups = this.getAllGroupByUserId(userId);
    if (CollectionUtil.isEmpty(groups)) {
      return Collections.EMPTY_LIST;
    }

    List<IGroup> results = new ArrayList<>();
    groups.forEach((k, v) -> {
      if (!CollectionUtil.isEmpty(groups.get(k))) {
        results.addAll(v);
      }
    });

    return results;
  }

  @Override
  public IGroup findById(String groupType, String groupId) {

    if (GroupTypeConstant.ORG.key().equals(groupType)) {
      DeptDTO dept = groupDao.getDeptById(groupId);
      if (dept != null) {
        return BeanCopierUtils.transformBean(dept, GroupDTO.class);
      }
    } else if (GroupTypeConstant.ROLE.key().equals(groupType)) {
      RoleDTO role = groupDao.getRoleById(groupId);
      if (role != null) {
        return BeanCopierUtils.transformBean(role, GroupDTO.class);
      }
    } else if (GroupTypeConstant.POST.key().equals(groupType)) {
      PositionDTO position = groupDao.getPositionById(groupId);
      if (position != null) {
        return BeanCopierUtils.transformBean(position, GroupDTO.class);
      }
    }

    return null;
  }

  @Override
  public IGroup getByCode(String groupType, String code) {

    if (GroupTypeConstant.ORG.key().equals(groupType)) {
      DeptDTO dept = groupDao.getDeptByCode(code);
      if (dept != null) {
        return BeanCopierUtils.transformBean(dept, GroupDTO.class);
      }
    } else if (GroupTypeConstant.ROLE.key().equals(groupType)) {
      RoleDTO role = groupDao.getRoleByCode(code);
      if (role != null) {
        return BeanCopierUtils.transformBean(role, GroupDTO.class);
      }
    } else if (GroupTypeConstant.POST.key().equals(groupType)) {
      PositionDTO position = groupDao.getPositionByCode(code);
      if (position != null) {
        return BeanCopierUtils.transformBean(position, GroupDTO.class);
      }
    }

    return null;
  }

  @Override
  public IGroup getMainGroup(String userId) {

    Map<String, List<? extends IGroup>> datas = this.getAllGroupByUserId(userId);
    if (CollectionUtil.isEmpty(datas)) {
      return null;
    }

    if (!CollectionUtil.isEmpty(datas.get(GroupTypeConstant.ORG.key()))) {
      return datas.get(GroupTypeConstant.ORG.key()).get(0);
    }

    if (!CollectionUtil.isEmpty(datas.get(GroupTypeConstant.POST.key()))) {
      return datas.get(GroupTypeConstant.POST.key()).get(0);
    }

    return null;
  }
}

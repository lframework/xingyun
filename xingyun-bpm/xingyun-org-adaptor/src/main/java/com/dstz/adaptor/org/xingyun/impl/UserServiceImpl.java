package com.dstz.adaptor.org.xingyun.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.dstz.adaptor.org.xingyun.constant.RelationTypeConstant;
import com.dstz.adaptor.org.xingyun.dao.UserDao;
import com.dstz.base.api.constant.StringConstants;
import com.dstz.base.api.exception.BusinessException;
import com.dstz.base.core.util.BeanCopierUtils;
import com.dstz.org.api.model.IUser;
import com.dstz.org.api.model.IUserRole;
import com.dstz.org.api.model.dto.UserDTO;
import com.dstz.org.api.service.UserService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

  @Autowired
  private UserDao userDao;

  @Override
  public IUser getUserById(String userId) {
    return userDao.getUserById(userId);
  }

  @Override
  public IUser getUserByAccount(String account) {
    return userDao.getUserByAccount(account);
  }

  @Override
  public List<? extends IUser> getUserListByGroup(String groupType, String groupId) {
    //此处可以根据不同的groupType去调用真实的实现：如角色下的人，组织下的人
    RelationTypeConstant relationType = RelationTypeConstant.getUserRelationTypeByGroupType(
        groupType);
    if (relationType == null) {
      throw new BusinessException(groupType + "查找不到对应用户的类型！");
    }

    List<? extends IUser> users = new ArrayList<>();

    switch (relationType) {
      case POST: {
        users = userDao.getUserByPositionId(groupId);
        break;
      }
      case POST_USER: {
        // 多个岗位ID
        String[] positionIds = groupId.split(StringConstants.DASH);
        if (positionIds.length != 2) {
          users = Collections.emptyList();
        } else {
          users = userDao.getUserByPositionIds(positionIds);
        }
        break;
      }
      case USER_ROLE: {
        users = userDao.getUserByRoleId(groupId);
        break;
      }
      case GROUP_USER: {
        // TODO 暂不实现组
        users = Collections.emptyList();
        break;
      }
    }

    if (CollectionUtil.isNotEmpty(users)) {
      return BeanCopierUtils.transformList(users, UserDTO.class);
    }

    return Collections.emptyList();
  }

  @Override
  public List<? extends IUserRole> getUserRole(String userId) {
    return userDao.getUserRole(userId);
  }
}

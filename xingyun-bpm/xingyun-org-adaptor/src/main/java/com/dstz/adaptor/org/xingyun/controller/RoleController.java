package com.dstz.adaptor.org.xingyun.controller;

import com.dstz.adaptor.org.xingyun.entity.Role;
import com.dstz.base.rest.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 角色管理 控制器类
 */
@RestController
@RequestMapping("/org/role")
public class RoleController extends BaseController<Role> {

  @Override
  protected String getModelDesc() {
    return "角色";
  }
}

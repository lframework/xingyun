package com.dstz.adaptor.org.xingyun.dto;

import com.dstz.org.api.model.IUser;

public class UserDTO implements IUser {

  /**
   * 用户ID
   */
  private String userId;

  /**
   * 姓名
   */
  private String fullname;

  /**
   * 用户名
   */
  private String account;

  /**
   * 密码
   */
  private String password;

  /**
   * 邮箱
   */
  private String email;

  /**
   * 联系电话
   */
  private String mobile;

  /**
   * 微信
   */
  private String weixin;

  /**
   * 是否启用
   */
  private Integer status;

  @Override
  public String getUserId() {

    return userId;
  }

  @Override
  public void setUserId(String userId) {

    this.userId = userId;
  }

  @Override
  public String getFullname() {

    return fullname;
  }

  @Override
  public void setFullname(String fullname) {

    this.fullname = fullname;
  }

  @Override
  public String getAccount() {

    return account;
  }

  @Override
  public void setAccount(String account) {

    this.account = account;
  }

  @Override
  public String getPassword() {

    return password;
  }

  public void setPassword(String password) {

    this.password = password;
  }

  @Override
  public String getEmail() {

    return email;
  }

  public void setEmail(String email) {

    this.email = email;
  }

  @Override
  public String getMobile() {

    return mobile;
  }

  public void setMobile(String mobile) {

    this.mobile = mobile;
  }

  @Override
  public String getWeixin() {

    return weixin;
  }

  public void setWeixin(String weixin) {

    this.weixin = weixin;
  }

  @Override
  public Integer getStatus() {

    return status;
  }

  public void setStatus(Integer status) {

    this.status = status;
  }
}

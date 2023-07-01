package com.lframework.xingyun.template.inner.dto;

import com.lframework.starter.web.dto.BaseDto;
import java.io.Serializable;
import java.util.Set;
import lombok.Data;

/**
 * 用户登录Dto
 *
 * @author zmj
 */
@Data
public class LoginDto implements BaseDto, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Token
   */
  private String token;

  /**
   * 用户信息
   */
  private UserInfoDto user;

  /**
   * 角色
   */
  private Set<String> roles;

  public LoginDto(String token, String name, Set<String> roles) {

    this.token = token;
    this.setRoles(roles);

    UserInfoDto userInfo = new UserInfoDto();
    userInfo.setName(name);
    this.setUser(userInfo);
  }

  @Data
  public static class UserInfoDto implements BaseDto, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 姓名
     */
    private String name;
  }
}

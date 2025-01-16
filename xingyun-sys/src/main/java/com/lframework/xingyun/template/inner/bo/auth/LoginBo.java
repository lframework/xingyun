package com.lframework.xingyun.template.inner.bo.auth;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.template.inner.dto.LoginDto;
import com.lframework.xingyun.template.inner.dto.LoginDto.UserInfoDto;
import io.swagger.annotations.ApiModelProperty;
import java.util.Set;
import lombok.Data;

@Data
public class LoginBo extends BaseBo<LoginDto> {

  /**
   * Token
   */
  @ApiModelProperty("Token")
  private String token;

  /**
   * 用户信息
   */
  @ApiModelProperty("用户信息")
  private UserInfoBo user;

  /**
   * 角色
   */
  @ApiModelProperty("角色")
  private Set<String> roles;

  public LoginBo() {
  }

  public LoginBo(LoginDto dto) {
    super(dto);
  }

  @Override
  protected void afterInit(LoginDto dto) {

    this.user = new UserInfoBo(dto.getUser());
  }

  @Data
  public static class UserInfoBo extends BaseBo<LoginDto.UserInfoDto> {

    private static final long serialVersionUID = 1L;

    /**
     * 姓名
     */
    @ApiModelProperty("姓名")
    private String name;

    public UserInfoBo() {
    }

    public UserInfoBo(UserInfoDto dto) {
      super(dto);
    }
  }
}

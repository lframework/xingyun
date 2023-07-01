package com.lframework.xingyun.template.inner.bo.usercenter;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.template.inner.dto.UserInfoDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserInfoBo extends BaseBo<UserInfoDto> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 登录名
   */
  @ApiModelProperty("登录名")
  private String username;

  /**
   * 编号
   */
  @ApiModelProperty("编号")
  private String code;

  /**
   * 姓名
   */
  @ApiModelProperty("姓名")
  private String name;

  /**
   * 邮箱
   */
  @ApiModelProperty("邮箱")
  private String email;

  /**
   * 联系电话
   */
  @ApiModelProperty("联系电话")
  private String telephone;

  /**
   * 性别
   */
  @ApiModelProperty("性别")
  private Integer gender;

  public UserInfoBo() {

  }

  public UserInfoBo(UserInfoDto dto) {

    super(dto);
  }
}

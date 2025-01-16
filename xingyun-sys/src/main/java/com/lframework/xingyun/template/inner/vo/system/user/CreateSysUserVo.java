package com.lframework.xingyun.template.inner.vo.system.user;

import com.lframework.starter.common.constants.PatternPool;
import com.lframework.starter.web.components.validation.IsCode;
import com.lframework.starter.web.components.validation.IsEnum;
import com.lframework.starter.web.components.validation.Pattern;
import com.lframework.starter.web.vo.BaseVo;
import com.lframework.xingyun.core.enums.Gender;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateSysUserVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 编号
   */
  @IsCode
  @NotBlank(message = "请输入编号！")
  @ApiModelProperty(value = "编号", required = true)
  private String code;

  /**
   * 姓名
   */
  @NotBlank(message = "请输入姓名！")
  @ApiModelProperty(value = "姓名", required = true)
  private String name;

  /**
   * 用户名
   */
  @NotBlank(message = "请输入用户名！")
  @ApiModelProperty(value = "用户名", required = true)
  private String username;

  /**
   * 密码
   */
  @Pattern(regexp = PatternPool.PATTERN_STR_PASSWORD, message = "密码长度必须为5-16位，只允许包含大写字母、小写字母、数字、下划线")
  @NotBlank(message = "请输入密码！")
  @ApiModelProperty(value = "密码", required = true)
  private String password;

  /**
   * 邮箱
   */
  @Pattern(regexp = PatternPool.PATTERN_STR_EMAIL, message = "邮箱地址格式不正确！")
  @ApiModelProperty("邮箱")
  private String email;

  /**
   * 联系电话
   */
  @Pattern(regexp = PatternPool.PATTERN_STR_CN_TEL, message = "联系电话格式不正确！")
  @ApiModelProperty("联系电话")
  private String telephone;

  /**
   * 性别 0-未知 1-男 2-女
   */
  @NotNull(message = "请选择性别！")
  @IsEnum(message = "请选择性别！", enumClass = Gender.class)
  @ApiModelProperty("性别")
  private Integer gender;

  /**
   * 部门ID
   */
  @ApiModelProperty("部门ID")
  private List<String> deptIds;

  /**
   * 角色ID
   */
  @ApiModelProperty("角色ID")
  private List<String> roleIds;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;
}

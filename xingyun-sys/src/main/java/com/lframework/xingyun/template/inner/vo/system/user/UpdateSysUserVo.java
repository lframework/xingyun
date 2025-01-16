package com.lframework.xingyun.template.inner.vo.system.user;

import com.lframework.starter.web.components.validation.IsCode;
import com.lframework.starter.web.components.validation.IsEnum;
import com.lframework.starter.web.vo.BaseVo;
import com.lframework.xingyun.core.enums.Gender;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateSysUserVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @ApiModelProperty(value = "ID", required = true)
  @NotBlank(message = "ID不能为空！")
  private String id;

  /**
   * 编号
   */
  @ApiModelProperty(value = "编号", required = true)
  @IsCode
  @NotBlank(message = "请输入编号！")
  private String code;

  /**
   * 姓名
   */
  @ApiModelProperty(value = "姓名", required = true)
  @NotBlank(message = "请输入姓名！")
  private String name;

  /**
   * 用户名
   */
  @ApiModelProperty(value = "用户名", required = true)
  @NotBlank(message = "请输入用户名！")
  private String username;

  /**
   * 密码 如果不为空则为修改密码
   */
  @ApiModelProperty("密码 如果不为空则为修改密码")
  private String password;

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
  @ApiModelProperty(value = "性别", required = true)
  @NotNull(message = "请选择性别！")
  @IsEnum(message = "请选择性别！", enumClass = Gender.class)
  private Integer gender;

  /**
   * 角色ID
   */
  @ApiModelProperty("角色ID")
  private List<String> roleIds;

  /**
   * 部门ID
   */
  @ApiModelProperty("部门ID")
  private List<String> deptIds;

  /**
   * 状态
   */
  @ApiModelProperty(value = "状态", required = true)
  @NotNull(message = "请选择状态！")
  private Boolean available;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;
}

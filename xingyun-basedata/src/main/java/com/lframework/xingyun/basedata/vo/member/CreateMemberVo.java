package com.lframework.xingyun.basedata.vo.member;

import com.lframework.starter.mybatis.enums.Gender;
import com.lframework.starter.web.components.validation.IsEnum;
import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDate;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateMemberVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 编号
   */
  @ApiModelProperty(value = "编号", required = true)
  @NotBlank(message = "请输入编号！")
  private String code;

  /**
   * 名称
   */
  @ApiModelProperty(value = "名称", required = true)
  @NotBlank(message = "请输入名称！")
  private String name;

  /**
   * 性别
   */
  @ApiModelProperty(value = "性别", required = true)
  @NotNull(message = "请选择性别！")
  @IsEnum(message = "请选择性别！", enumClass = Gender.class)
  private Integer gender;

  /**
   * 联系电话
   */
  @ApiModelProperty("联系电话")
  private String telephone;

  /**
   * 电子邮箱
   */
  @ApiModelProperty("电子邮箱")
  @Email(message = "电子邮箱格式不正确！")
  private String email;

  /**
   * 出生日期
   */
  @ApiModelProperty("出生日期")
  private LocalDate birthday;

  /**
   * 入会日期
   */
  @ApiModelProperty(value = "入会日期", required = true)
  @NotNull(message = "入会日期不能为空！")
  private LocalDate joinDay;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;
}

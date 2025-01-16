package com.lframework.xingyun.basedata.vo.member;

import com.lframework.xingyun.core.enums.Gender;
import com.lframework.starter.web.components.validation.IsCode;
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
public class UpdateMemberVo implements BaseVo, Serializable {

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
   * 所属门店ID
   */
  @ApiModelProperty("所属门店ID")
  private String shopId;

  /**
   * 所属导购ID
   */
  @ApiModelProperty("所属导购ID")
  private String guiderId;

  /**
   * 状态
   */
  @ApiModelProperty(value = "状态", required = true)
  @NotNull(message = "状态不能为空！")
  private Boolean available;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;
}

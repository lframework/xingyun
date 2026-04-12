package com.lframework.xingyun.basedata.vo.member;

import com.lframework.starter.web.inner.enums.system.Gender;
import com.lframework.starter.web.core.components.validation.IsCode;
import com.lframework.starter.web.core.components.validation.IsEnum;
import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateMemberVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 编号
   */
  @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED)
  @IsCode
  @NotBlank(message = "请输入编号！")
  private String code;

  /**
   * 名称
   */
  @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "请输入名称！")
  private String name;

  /**
   * 性别
   */
  @Schema(description = "性别", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "请选择性别！")
  @IsEnum(message = "请选择性别！", enumClass = Gender.class)
  private Integer gender;

  /**
   * 联系电话
   */
  @Schema(description = "联系电话")
  private String telephone;

  /**
   * 电子邮箱
   */
  @Schema(description = "电子邮箱")
  @Email(message = "电子邮箱格式不正确！")
  private String email;

  /**
   * 出生日期
   */
  @Schema(description = "出生日期")
  private LocalDate birthday;


  /**
   * 入会日期
   */
  @Schema(description = "入会日期", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "入会日期不能为空！")
  private LocalDate joinDay;

  /**
   * 所属门店ID
   */
  @Schema(description = "所属门店ID")
  private String shopId;

  /**
   * 所属导购ID
   */
  @Schema(description = "所属导购ID")
  private String guiderId;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;
}

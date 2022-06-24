package com.lframework.xingyun.api.excel.basedata.member;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.lframework.common.constants.StringPool;
import com.lframework.starter.mybatis.enums.Gender;
import com.lframework.starter.web.components.excel.ExcelModel;
import com.lframework.starter.web.components.validation.IsCode;
import com.lframework.starter.web.components.validation.IsEnum;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MemberImportModel implements ExcelModel {

  /**
   * ID
   */
  @ExcelIgnore
  private String id;

  /**
   * 是否为新增
   */
  @ExcelIgnore
  private Boolean isInsert;

  /**
   * 编号
   */
  @ApiModelProperty(value = "编号", required = true)
  @IsCode
  @NotBlank(message = "请输入编号！")
  @ExcelProperty("编号")
  private String code;

  /**
   * 名称
   */
  @ApiModelProperty(value = "名称", required = true)
  @NotBlank(message = "请输入名称！")
  @ExcelProperty("名称")
  private String name;

  /**
   * 性别
   */
  @ApiModelProperty(value = "性别", required = true)
  @NotNull(message = "请选择性别！")
  @IsEnum(message = "请选择性别！", enumClass = Gender.class)
  @ExcelProperty("性别")
  private String gender;

  /**
   * 性别
   */
  @ExcelIgnore
  private Gender genderEnum;

  /**
   * 联系电话
   */
  @ApiModelProperty("联系电话")
  @ExcelProperty("联系电话")
  private String telephone;

  /**
   * 电子邮箱
   */
  @ApiModelProperty("电子邮箱")
  @Email(message = "电子邮箱格式不正确！")
  @ExcelProperty("电子邮箱")
  private String email;

  /**
   * 出生日期
   */
  @ApiModelProperty("出生日期")
  @ExcelProperty("出生日期")
  @DateTimeFormat(StringPool.DATE_PATTERN)
  private Date birthday;

  /**
   * 入会日期
   */
  @ApiModelProperty(value = "入会日期", required = true)
  @NotNull(message = "入会日期不能为空！")
  @ExcelProperty("入会日期")
  private Date joinDay;

  /**
   * 所属门店ID
   */
  @ExcelIgnore
  private String shopId;

  /**
   * 所属门店编号
   */
  @ApiModelProperty("所属门店ID")
  @ExcelProperty("所属门店编号")
  private String shopCode;

  /**
   * 所属导购ID
   */
  @ApiModelProperty("所属导购ID")
  @ExcelIgnore
  private String guiderId;

  /**
   * 所属导购编号
   */
  @ApiModelProperty("所属导购ID")
  @ExcelProperty("所属导购编号")
  private String guiderCode;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  @ExcelProperty("备注")
  private String description;
}

package com.lframework.xingyun.basedata.vo.customer;

import com.lframework.starter.web.core.components.validation.IsCode;
import com.lframework.starter.web.core.components.validation.IsEnum;
import com.lframework.starter.web.core.vo.BaseVo;
import com.lframework.xingyun.basedata.enums.SettleType;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCustomerVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

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
   * 简码
   */
  @ApiModelProperty(value = "简码", required = true)
  @NotBlank(message = "请输入简码！")
  private String mnemonicCode;

  /**
   * 联系人
   */
  @ApiModelProperty("联系人")
  private String contact;

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
   * 邮编
   */
  @ApiModelProperty("邮编")
  private String zipCode;

  /**
   * 传真
   */
  @ApiModelProperty("传真")
  private String fax;

  /**
   * 地区ID
   */
  @ApiModelProperty("地区ID")
  private String cityId;

  /**
   * 地址
   */
  @ApiModelProperty("地址")
  private String address;

  /**
   * 结算方式
   */
  @ApiModelProperty(value = "结算方式", required = true)
  @NotNull(message = "请选择结算方式！")
  @IsEnum(message = "请选择结算方式！", enumClass = SettleType.class)
  private Integer settleType;

  /**
   * 统一社会信用代码
   */
  @ApiModelProperty("统一社会信用代码")
  private String creditCode;

  /**
   * 纳税人识别号
   */
  @ApiModelProperty("纳税人识别号")
  private String taxIdentifyNo;

  /**
   * 开户银行
   */
  @ApiModelProperty("开户银行")
  private String bankName;

  /**
   * 户名
   */
  @ApiModelProperty("户名")
  private String accountName;

  /**
   * 银行账号
   */
  @ApiModelProperty("银行账号")
  private String accountNo;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;
}

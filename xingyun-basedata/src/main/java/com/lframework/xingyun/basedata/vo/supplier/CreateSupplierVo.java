package com.lframework.xingyun.basedata.vo.supplier;

import com.lframework.starter.web.core.components.validation.IsCode;
import com.lframework.starter.web.core.components.validation.IsEnum;
import com.lframework.starter.web.core.vo.BaseVo;
import com.lframework.xingyun.basedata.enums.ManageType;
import com.lframework.xingyun.basedata.enums.SettleType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateSupplierVo implements BaseVo, Serializable {

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
   * 简码
   */
  @Schema(description = "简码", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "请输入简码！")
  private String mnemonicCode;

  /**
   * 联系人
   */
  @Schema(description = "联系人")
  private String contact;

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
   * 邮编
   */
  @Schema(description = "邮编")
  private String zipCode;

  /**
   * 传真
   */
  @Schema(description = "传真")
  private String fax;

  /**
   * 地区ID
   */
  @Schema(description = "地区ID")
  private String cityId;

  /**
   * 地址
   */
  @Schema(description = "地址")
  private String address;

  /**
   * 送货周期（天）
   */
  @Schema(description = "送货周期（天）")
  @Min(message = "送货周期（天）必须大于0！", value = 1)
  private Integer deliveryCycle;

  /**
   * 经营方式
   */
  @Schema(description = "经营方式", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "请选择经营方式！")
  @IsEnum(message = "请选择经营方式！", enumClass = ManageType.class)
  private Integer manageType;

  /**
   * 结算方式
   */
  @Schema(description = "结算方式", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "请选择结算方式！")
  @IsEnum(message = "请选择结算方式！", enumClass = SettleType.class)
  private Integer settleType;

  /**
   * 统一社会信用代码
   */
  @Schema(description = "统一社会信用代码")
  private String creditCode;

  /**
   * 纳税人识别号
   */
  @Schema(description = "纳税人识别号")
  private String taxIdentifyNo;

  /**
   * 开户银行
   */
  @Schema(description = "开户银行")
  private String bankName;

  /**
   * 户名
   */
  @Schema(description = "户名")
  private String accountName;

  /**
   * 银行账号
   */
  @Schema(description = "银行账号")
  private String accountNo;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;
}

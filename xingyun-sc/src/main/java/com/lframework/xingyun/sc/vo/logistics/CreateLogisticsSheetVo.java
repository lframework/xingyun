package com.lframework.xingyun.sc.vo.logistics;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateLogisticsSheetVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 物流单号
   */
  @Schema(description = "物流单号")
  private String logisticsNo;

  /**
   * 物流公司ID
   */
  @Schema(description = "物流公司ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "物流公司ID不能为空！")
  private String logisticsCompanyId;

  /**
   * 寄件人姓名
   */
  @Schema(description = "寄件人姓名", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "寄件人姓名不能为空！")
  private String senderName;

  /**
   * 寄件人联系电话
   */
  @Schema(description = "寄件人联系电话", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "寄件人联系电话不能为空！")
  private String senderTelephone;

  /**
   * 寄件人省
   */
  @Schema(description = "寄件人省", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "寄件人省不能为空！")
  private String senderProvinceId;

  /**
   * 寄件人市
   */
  @Schema(description = "寄件人市", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "寄件人市不能为空！")
  private String senderCityId;

  /**
   * 寄件人区
   */
  @Schema(description = "寄件人区", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "寄件人区不能为空！")
  private String senderDistrictId;

  /**
   * 寄件人地址
   */
  @Schema(description = "寄件人地址", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "寄件人地址不能为空！")
  private String senderAddress;

  /**
   * 收件人姓名
   */
  @Schema(description = "收件人姓名", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "收件人姓名不能为空！")
  private String receiverName;

  /**
   * 收件人联系电话
   */
  @Schema(description = "收件人联系电话", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "收件人联系电话不能为空！")
  private String receiverTelephone;

  /**
   * 收件人省
   */
  @Schema(description = "收件人省", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "收件人省不能为空！")
  private String receiverProvinceId;

  /**
   * 收件人市
   */
  @Schema(description = "收件人市", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "收件人市不能为空！")
  private String receiverCityId;

  /**
   * 收件人区
   */
  @Schema(description = "收件人区", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "收件人区不能为空！")
  private String receiverDistrictId;

  /**
   * 收件人地址
   */
  @Schema(description = "收件人地址", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "收件人地址不能为空！")
  private String receiverAddress;

  /**
   * 总重量
   */
  @Schema(description = "总重量")
  @Min(value = 0, message = "总重量必须大于0！")
  private BigDecimal totalWeight;

  /**
   * 总体积
   */
  @Schema(description = "总体积")
  @Min(value = 0, message = "总体积必须大于0！")
  private BigDecimal totalVolume;

  /**
   * 物流费
   */
  @Schema(description = "物流费")
  @Min(value = 0, message = "物流费必须大于0！")
  private BigDecimal totalAmount;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;

  /**
   * 业务单据
   */
  @NotEmpty(message = "业务单据不能为空！")
  @Valid
  private List<LogisticsSheetBizOrderVo> bizOrders;
}

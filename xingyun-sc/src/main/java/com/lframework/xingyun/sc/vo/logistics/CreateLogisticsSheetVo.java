package com.lframework.xingyun.sc.vo.logistics;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateLogisticsSheetVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 物流单号
   */
  @ApiModelProperty(value = "物流单号")
  private String logisticsNo;

  /**
   * 物流公司ID
   */
  @ApiModelProperty(value = "物流公司ID", required = true)
  @NotBlank(message = "物流公司ID不能为空！")
  private String logisticsCompanyId;

  /**
   * 寄件人姓名
   */
  @ApiModelProperty(value = "寄件人姓名", required = true)
  @NotBlank(message = "寄件人姓名不能为空！")
  private String senderName;

  /**
   * 寄件人联系电话
   */
  @ApiModelProperty(value = "寄件人联系电话", required = true)
  @NotBlank(message = "寄件人联系电话不能为空！")
  private String senderTelephone;

  /**
   * 寄件人省
   */
  @ApiModelProperty(value = "寄件人省", required = true)
  @NotBlank(message = "寄件人省不能为空！")
  private String senderProvinceId;

  /**
   * 寄件人市
   */
  @ApiModelProperty(value = "寄件人市", required = true)
  @NotBlank(message = "寄件人市不能为空！")
  private String senderCityId;

  /**
   * 寄件人区
   */
  @ApiModelProperty(value = "寄件人区", required = true)
  @NotBlank(message = "寄件人区不能为空！")
  private String senderDistrictId;

  /**
   * 寄件人地址
   */
  @ApiModelProperty(value = "寄件人地址", required = true)
  @NotBlank(message = "寄件人地址不能为空！")
  private String senderAddress;

  /**
   * 收件人姓名
   */
  @ApiModelProperty(value = "收件人姓名", required = true)
  @NotBlank(message = "收件人姓名不能为空！")
  private String receiverName;

  /**
   * 收件人联系电话
   */
  @ApiModelProperty(value = "收件人联系电话", required = true)
  @NotBlank(message = "收件人联系电话不能为空！")
  private String receiverTelephone;

  /**
   * 收件人省
   */
  @ApiModelProperty(value = "收件人省", required = true)
  @NotBlank(message = "收件人省不能为空！")
  private String receiverProvinceId;

  /**
   * 收件人市
   */
  @ApiModelProperty(value = "收件人市", required = true)
  @NotBlank(message = "收件人市不能为空！")
  private String receiverCityId;

  /**
   * 收件人区
   */
  @ApiModelProperty(value = "收件人区", required = true)
  @NotBlank(message = "收件人区不能为空！")
  private String receiverDistrictId;

  /**
   * 收件人地址
   */
  @ApiModelProperty(value = "收件人地址", required = true)
  @NotBlank(message = "收件人地址不能为空！")
  private String receiverAddress;

  /**
   * 总重量
   */
  @ApiModelProperty(value = "总重量")
  @Min(value = 0, message = "总重量必须大于0！")
  private BigDecimal totalWeight;

  /**
   * 总体积
   */
  @ApiModelProperty(value = "总体积")
  @Min(value = 0, message = "总体积必须大于0！")
  private BigDecimal totalVolume;

  /**
   * 物流费
   */
  @ApiModelProperty(value = "物流费")
  @Min(value = 0, message = "物流费必须大于0！")
  private BigDecimal totalAmount;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  /**
   * 业务单据
   */
  @NotEmpty(message = "业务单据不能为空！")
  @Valid
  private List<LogisticsSheetBizOrderVo> bizOrders;
}

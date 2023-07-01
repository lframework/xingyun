package com.lframework.xingyun.sc.bo.logistics;

import com.lframework.starter.common.utils.StringUtil;
import com.lframework.xingyun.template.core.service.UserService;
import com.lframework.starter.web.annotations.convert.EnumConvert;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import com.lframework.xingyun.template.core.dto.UserDto;
import com.lframework.xingyun.basedata.entity.LogisticsCompany;
import com.lframework.xingyun.basedata.service.logistics.LogisticsCompanyService;
import com.lframework.xingyun.sc.entity.LogisticsSheet;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class QueryLogisticsSheetBo extends BaseBo<LogisticsSheet> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 业务单据号
   */
  @ApiModelProperty("业务单据号")
  private String code;

  /**
   * 物流单号
   */
  @ApiModelProperty("物流单号")
  private String logisticsNo;

  /**
   * 物流公司名称
   */
  @ApiModelProperty("物流公司名称")
  private String logisticsCompanyName;

  /**
   * 总重量
   */
  @ApiModelProperty("总重量")
  private BigDecimal totalWeight;

  /**
   * 总体积
   */
  @ApiModelProperty("总体积")
  private BigDecimal totalVolume;

  /**
   * 物流费
   */
  @ApiModelProperty("物流费")
  private BigDecimal totalAmount;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  @EnumConvert
  private Integer status;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  /**
   * 创建人
   */
  @ApiModelProperty("创建人")
  private String createBy;

  /**
   * 创建时间
   */
  @ApiModelProperty("创建时间")
  private LocalDateTime createTime;

  /**
   * 发货人
   */
  @ApiModelProperty("发货人")
  private String deliveryBy;

  /**
   * 发货时间
   */
  @ApiModelProperty("发货时间")
  private LocalDateTime deliveryTime;

  public QueryLogisticsSheetBo(LogisticsSheet dto) {

    super(dto);
  }

  @Override
  public BaseBo<LogisticsSheet> convert(LogisticsSheet dto) {

    return super.convert(dto);
  }

  @Override
  protected void afterInit(LogisticsSheet dto) {
    LogisticsCompanyService logisticsCompanyService = ApplicationUtil.getBean(
        LogisticsCompanyService.class);
    LogisticsCompany logisticsCompany = logisticsCompanyService.findById(
        dto.getLogisticsCompanyId());
    this.logisticsCompanyName = logisticsCompany.getName();

    if (StringUtil.isNotBlank(dto.getDeliveryBy())) {
      UserService userService = ApplicationUtil.getBean(UserService.class);
      UserDto deliveryBy = userService.findById(dto.getDeliveryBy());
      this.deliveryBy = deliveryBy.getName();
    }
  }
}

package com.lframework.xingyun.api.bo.stock.adjust;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.common.constants.StringPool;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.service.IUserService;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.dto.storecenter.StoreCenterDto;
import com.lframework.xingyun.basedata.service.storecenter.IStoreCenterService;
import com.lframework.xingyun.sc.dto.stock.adjust.StockCostAdjustSheetDto;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 库存成本调整单 QueryBo
 * </p>
 *
 * @author zmj
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QueryStockCostAdjustSheetBo extends BaseBo<StockCostAdjustSheetDto> {

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
   * 仓库编号
   */
  @ApiModelProperty("仓库编号")
  private String scCode;

  /**
   * 仓库名称
   */
  @ApiModelProperty("仓库名称")
  private String scName;

  /**
   * 调价品种数
   */
  @ApiModelProperty("调价品种数")
  private Integer productNum;

  /**
   * 库存调价差额
   */
  @ApiModelProperty("库存调价差额")
  private BigDecimal diffAmount;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  private Integer status;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  /**
   * 修改人
   */
  @ApiModelProperty("修改人")
  private String updateBy;

  /**
   * 修改时间
   */
  @ApiModelProperty("修改时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime updateTime;

  /**
   * 审核人
   */
  @ApiModelProperty("审核人")
  private String approveBy;

  /**
   * 审核时间
   */
  @ApiModelProperty("审核时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime approveTime;

  public QueryStockCostAdjustSheetBo() {

  }

  public QueryStockCostAdjustSheetBo(StockCostAdjustSheetDto dto) {

    super(dto);
  }

  @Override
  public BaseBo<StockCostAdjustSheetDto> convert(StockCostAdjustSheetDto dto) {

    return super.convert(dto, QueryStockCostAdjustSheetBo::getStatus);
  }

  @Override
  protected void afterInit(StockCostAdjustSheetDto dto) {

    this.status = dto.getStatus().getCode();

    IStoreCenterService storeCenterService = ApplicationUtil.getBean(IStoreCenterService.class);
    StoreCenterDto sc = storeCenterService.getById(dto.getScId());
    this.scCode = sc.getCode();
    this.scName = sc.getName();

    IUserService userService = ApplicationUtil.getBean(IUserService.class);
    this.updateBy = userService.getById(dto.getUpdateBy()).getName();
    if (!StringUtil.isBlank(dto.getApproveBy())) {
      this.approveBy = userService.getById(dto.getApproveBy()).getName();
    }
  }
}

package com.lframework.xingyun.sc.bo.stock.adjust.stock;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.mybatis.service.UserService;
import com.lframework.starter.web.annotations.convert.EnumConvert;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.service.storecenter.StoreCenterService;
import com.lframework.xingyun.sc.entity.StockAdjustReason;
import com.lframework.xingyun.sc.entity.StockAdjustSheet;
import com.lframework.xingyun.sc.service.stock.adjust.StockAdjustReasonService;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <p>
 * 库存成本调整单 QueryBo
 * </p>
 *
 * @author zmj
 */
@Data
public class QueryStockAdjustSheetBo extends BaseBo<StockAdjustSheet> {

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
   * 业务类型
   */
  @ApiModelProperty("业务类型")
  @EnumConvert
  private Integer bizType;

  /**
   * 调整原因
   */
  @ApiModelProperty("调整原因")
  private String reasonName;

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

  public QueryStockAdjustSheetBo() {

  }

  public QueryStockAdjustSheetBo(StockAdjustSheet dto) {

    super(dto);
  }

  @Override
  public BaseBo<StockAdjustSheet> convert(StockAdjustSheet dto) {

    return super.convert(dto, QueryStockAdjustSheetBo::getStatus);
  }

  @Override
  protected void afterInit(StockAdjustSheet dto) {

    this.status = dto.getStatus().getCode();

    StoreCenterService storeCenterService = ApplicationUtil.getBean(StoreCenterService.class);
    StoreCenter sc = storeCenterService.findById(dto.getScId());
    this.scCode = sc.getCode();
    this.scName = sc.getName();

    UserService userService = ApplicationUtil.getBean(UserService.class);
    if (!StringUtil.isBlank(dto.getApproveBy())) {
      this.approveBy = userService.findById(dto.getApproveBy()).getName();
    }

    StockAdjustReasonService stockAdjustReasonService = ApplicationUtil.getBean(
        StockAdjustReasonService.class);
    StockAdjustReason reason = stockAdjustReasonService.findById(dto.getReasonId());
    this.reasonName = reason.getName();
  }
}

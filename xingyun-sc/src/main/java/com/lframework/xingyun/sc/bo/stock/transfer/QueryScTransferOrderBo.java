package com.lframework.xingyun.sc.bo.stock.transfer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.service.storecenter.StoreCenterService;
import com.lframework.xingyun.sc.entity.ScTransferOrder;
import com.lframework.xingyun.template.core.service.UserService;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <p>
 * 仓库调拨单 QueryBo
 * </p>
 *
 * @author zmj
 */
@Data
public class QueryScTransferOrderBo extends BaseBo<ScTransferOrder> {

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
   * 转出仓库编号
   */
  @ApiModelProperty("转出仓库编号")
  private String sourceScCode;

  /**
   * 转出仓库名称
   */
  @ApiModelProperty("转出仓库名称")
  private String sourceScName;

  /**
   * 转入仓库编号
   */
  @ApiModelProperty("转入仓库编号")
  private String targetScCode;

  /**
   * 转入仓库名称
   */
  @ApiModelProperty("转入仓库名称")
  private String targetScName;

  /**
   * 调拨数量
   */
  @ApiModelProperty("调拨数量")
  private Integer totalNum;

  /**
   * 调拨成本金额
   */
  @ApiModelProperty("调拨成本金额")
  private BigDecimal totalAmount;

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

  public QueryScTransferOrderBo() {

  }

  public QueryScTransferOrderBo(ScTransferOrder dto) {

    super(dto);
  }

  @Override
  public BaseBo<ScTransferOrder> convert(ScTransferOrder dto) {

    return super.convert(dto, QueryScTransferOrderBo::getStatus);
  }

  @Override
  protected void afterInit(ScTransferOrder dto) {

    this.status = dto.getStatus().getCode();

    StoreCenterService storeCenterService = ApplicationUtil.getBean(StoreCenterService.class);
    StoreCenter sourceSc = storeCenterService.findById(dto.getSourceScId());
    this.sourceScCode = sourceSc.getCode();
    this.sourceScName = sourceSc.getName();

    StoreCenter targetSc = storeCenterService.findById(dto.getTargetScId());
    this.targetScCode = targetSc.getCode();
    this.targetScName = targetSc.getName();

    UserService userService = ApplicationUtil.getBean(UserService.class);
    if (!StringUtil.isBlank(dto.getApproveBy())) {
      this.approveBy = userService.findById(dto.getApproveBy()).getName();
    }
  }
}

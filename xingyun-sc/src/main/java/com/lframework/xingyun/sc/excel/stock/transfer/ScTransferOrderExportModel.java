package com.lframework.xingyun.sc.excel.stock.transfer;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.components.excel.ExcelModel;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.service.storecenter.StoreCenterService;
import com.lframework.xingyun.sc.entity.ScTransferOrder;
import com.lframework.xingyun.sc.enums.ScTransferOrderStatus;
import com.lframework.xingyun.template.inner.service.system.SysUserService;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@Data
public class ScTransferOrderExportModel extends BaseBo<ScTransferOrder> implements ExcelModel {

  /**
   * 业务单据号
   */
  @ExcelProperty("业务单据号")
  private String code;

  /**
   * 转出仓库编号
   */
  @ExcelProperty("转出仓库编号")
  private String sourceScCode;

  /**
   * 转出仓库名称
   */
  @ExcelProperty("转出仓库名称")
  private String sourceScName;

  /**
   * 转入仓库编号
   */
  @ExcelProperty("转入仓库编号")
  private String targetScCode;

  /**
   * 转入仓库名称
   */
  @ExcelProperty("转入仓库名称")
  private String targetScName;

  /**
   * 调拨数量
   */
  @ExcelProperty("调拨数量")
  private Integer totalNum;

  /**
   * 调拨成本金额
   */
  @ExcelProperty("调拨成本金额")
  private BigDecimal totalAmount;

  /**
   * 状态
   */
  @ExcelProperty("状态")
  private String status;

  /**
   * 备注
   */
  @ExcelProperty("备注")
  private String description;

  /**
   * 修改人
   */
  @ExcelProperty("修改人")
  private String updateBy;

  /**
   * 修改时间
   */
  @ExcelProperty("修改时间")
  @DateTimeFormat(StringPool.DATE_TIME_PATTERN)
  private Date updateTime;

  /**
   * 审核人
   */
  @ExcelProperty("审核人")
  private String approveBy;

  /**
   * 审核时间
   */
  @ExcelProperty("审核时间")
  @DateTimeFormat(StringPool.DATE_TIME_PATTERN)
  private Date approveTime;

  public ScTransferOrderExportModel(ScTransferOrder dto) {

    super(dto);
  }

  @Override
  public <A> BaseBo<ScTransferOrder> convert(ScTransferOrder dto) {

    return super.convert(dto);
  }

  @Override
  protected void afterInit(ScTransferOrder dto) {

    this.status = dto.getStatus().getDesc();

    StoreCenterService storeCenterService = ApplicationUtil.getBean(StoreCenterService.class);
    StoreCenter sourceSc = storeCenterService.findById(dto.getSourceScId());
    this.sourceScCode = sourceSc.getCode();
    this.sourceScName = sourceSc.getName();

    StoreCenter targetSc = storeCenterService.findById(dto.getTargetScId());
    this.targetScCode = targetSc.getCode();
    this.targetScName = targetSc.getName();

    SysUserService userService = ApplicationUtil.getBean(SysUserService.class);
    if (!StringUtil.isBlank(dto.getApproveBy())) {
      this.approveBy = userService.findById(dto.getApproveBy()).getName();
    }

    if (dto.getStatus() != ScTransferOrderStatus.APPROVE_PASS
        && dto.getStatus() != ScTransferOrderStatus.PART_RECEIVED
        && dto.getStatus() != ScTransferOrderStatus.RECEIVED) {
      this.totalAmount = null;
    }
  }
}

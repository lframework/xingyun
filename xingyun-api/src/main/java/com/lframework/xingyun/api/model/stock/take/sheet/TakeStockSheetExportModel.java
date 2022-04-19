package com.lframework.xingyun.api.model.stock.take.sheet;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.lframework.common.constants.StringPool;
import com.lframework.common.utils.DateUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.service.IUserService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.components.excel.ExcelModel;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.dto.storecenter.StoreCenterDto;
import com.lframework.xingyun.basedata.service.storecenter.IStoreCenterService;
import com.lframework.xingyun.sc.dto.stock.take.plan.TakeStockPlanDto;
import com.lframework.xingyun.sc.dto.stock.take.sheet.TakeStockSheetDto;
import com.lframework.xingyun.sc.service.stock.take.ITakeStockPlanService;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TakeStockSheetExportModel extends BaseBo<TakeStockSheetDto> implements ExcelModel {

  /**
   * 业务单据号
   */
  @ExcelProperty("业务单据号")
  private String code;

  /**
   * 盘点任务号
   */
  @ExcelProperty("关联盘点任务")
  private String planCode;

  /**
   * 盘点任务-盘点状态
   */
  @ExcelProperty("盘点状态")
  private String takeStatus;

  /**
   * 仓库编号
   */
  @ExcelProperty("仓库编号")
  private String scCode;

  /**
   * 仓库名称
   */
  @ExcelProperty("仓库名称")
  private String scName;

  /**
   * 盘点任务-盘点类别
   */
  @ExcelProperty("盘点类别")
  private String takeType;

  /**
   * 状态
   */
  @ExcelProperty("审核状态")
  private String status;

  /**
   * 修改时间
   */
  @ExcelProperty("操作时间")
  @DateTimeFormat(StringPool.DATE_TIME_PATTERN)
  private Date updateTime;

  /**
   * 修改人
   */
  @ExcelProperty("操作人")
  private String updateBy;

  /**
   * 审核时间
   */
  @ExcelProperty("审核时间")
  @DateTimeFormat(StringPool.DATE_TIME_PATTERN)
  private Date approveTime;

  /**
   * 审核人
   */
  @ExcelProperty("审核人")
  private String approveBy;

  /**
   * 备注
   */
  @ExcelProperty("备注")
  private String description;

  public TakeStockSheetExportModel(TakeStockSheetDto dto) {
    super(dto);
  }

  @Override
  public <A> BaseBo<TakeStockSheetDto> convert(TakeStockSheetDto dto) {
    return super.convert(dto, TakeStockSheetExportModel::getTakeStatus,
        TakeStockSheetExportModel::getStatus, TakeStockSheetExportModel::getUpdateTime,
        TakeStockSheetExportModel::getApproveTime);
  }

  @Override
  protected void afterInit(TakeStockSheetDto dto) {
    this.status = dto.getStatus().getDesc();

    ITakeStockPlanService takeStockPlanService = ApplicationUtil
        .getBean(ITakeStockPlanService.class);
    TakeStockPlanDto takeStockPlan = takeStockPlanService.getById(dto.getPlanId());

    this.planCode = takeStockPlan.getCode();
    this.takeStatus = takeStockPlan.getTakeStatus().getDesc();

    IStoreCenterService storeCenterService = ApplicationUtil.getBean(IStoreCenterService.class);
    StoreCenterDto sc = storeCenterService.getById(dto.getScId());
    this.scCode = sc.getCode();
    this.scName = sc.getName();

    IUserService userService = ApplicationUtil.getBean(IUserService.class);
    this.updateBy = userService.getById(dto.getUpdateBy()).getName();

    if (!StringUtil.isBlank(dto.getApproveBy())) {
      this.approveBy = userService.getById(dto.getApproveBy()).getName();
    }

    this.takeType = takeStockPlan.getTakeType().getDesc();

    this.updateTime = DateUtil.toDate(dto.getUpdateTime());
    this.approveTime = DateUtil.toDate(dto.getApproveTime());
  }
}

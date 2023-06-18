package com.lframework.xingyun.sc.excel.stock.adjust;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.mybatis.service.UserService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import com.lframework.starter.web.components.excel.ExcelModel;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.service.storecenter.StoreCenterService;
import com.lframework.xingyun.sc.entity.StockAdjustSheet;
import com.lframework.xingyun.sc.entity.StockAdjustSheetDetail;
import com.lframework.xingyun.sc.service.stock.adjust.StockAdjustSheetDetailService;
import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class StockAdjustSheetExportModel extends BaseBo<StockAdjustSheet> implements ExcelModel {

  /**
   * 单号
   */
  @ExcelProperty("业务单据号")
  private String code;

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
   * 调整品种数
   */
  @ExcelProperty("调整品种数")
  private Integer productNum;

  /**
   * 库存调整数量
   */
  @ExcelProperty("库存调整数量")
  private Integer diffStockNum;

  /**
   * 修改时间
   */
  @DateTimeFormat(StringPool.DATE_TIME_PATTERN)
  @ExcelProperty("操作时间")
  private Date updateTime;

  /**
   * 修改人
   */
  @ExcelProperty("操作人")
  private String updateBy;

  /**
   * 状态
   */
  @ExcelProperty("状态")
  private String status;

  /**
   * 审核时间
   */
  @DateTimeFormat(StringPool.DATE_TIME_PATTERN)
  @ExcelProperty("审核时间")
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

  public StockAdjustSheetExportModel(StockAdjustSheet dto) {

    super(dto);
  }

  @Override
  public <A> BaseBo<StockAdjustSheet> convert(StockAdjustSheet dto) {

    return super.convert(dto);
  }

  @Override
  protected void afterInit(StockAdjustSheet dto) {

    this.status = dto.getStatus().getDesc();

    StoreCenterService storeCenterService = ApplicationUtil.getBean(StoreCenterService.class);
    StoreCenter sc = storeCenterService.findById(dto.getScId());
    this.scCode = sc.getCode();
    this.scName = sc.getName();

    UserService userService = ApplicationUtil.getBean(UserService.class);
    if (!StringUtil.isBlank(dto.getApproveBy())) {
      this.approveBy = userService.findById(dto.getApproveBy()).getName();
    }

    StockAdjustSheetDetailService stockAdjustSheetDetailService = ApplicationUtil.getBean(
        StockAdjustSheetDetailService.class);
    Wrapper<StockAdjustSheetDetail> queryWrapper = Wrappers.lambdaQuery(
            StockAdjustSheetDetail.class).eq(StockAdjustSheetDetail::getSheetId, dto.getId())
        .orderByAsc(StockAdjustSheetDetail::getOrderNo);
    List<StockAdjustSheetDetail> details = stockAdjustSheetDetailService.list(queryWrapper);
    this.productNum = details.size();
    this.diffStockNum = details.stream().mapToInt(StockAdjustSheetDetail::getStockNum).sum();
  }
}

package com.lframework.xingyun.sc.excel.stock.take.sheet;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.DateUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.xingyun.template.core.service.UserService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import com.lframework.starter.web.components.excel.ExcelModel;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.service.storecenter.StoreCenterService;
import com.lframework.xingyun.sc.entity.TakeStockPlan;
import com.lframework.xingyun.sc.entity.TakeStockSheet;
import com.lframework.xingyun.sc.service.stock.take.TakeStockPlanService;
import java.util.Date;
import lombok.Data;

@Data
public class TakeStockSheetExportModel extends BaseBo<TakeStockSheet> implements ExcelModel {

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

    public TakeStockSheetExportModel(TakeStockSheet dto) {

        super(dto);
    }

    @Override
    public <A> BaseBo<TakeStockSheet> convert(TakeStockSheet dto) {

        return super.convert(dto, TakeStockSheetExportModel::getTakeStatus, TakeStockSheetExportModel::getStatus,
                TakeStockSheetExportModel::getUpdateTime, TakeStockSheetExportModel::getApproveTime);
    }

    @Override
    protected void afterInit(TakeStockSheet dto) {

        this.status = dto.getStatus().getDesc();

        TakeStockPlanService takeStockPlanService = ApplicationUtil.getBean(TakeStockPlanService.class);
        TakeStockPlan takeStockPlan = takeStockPlanService.getById(dto.getPlanId());

        this.planCode = takeStockPlan.getCode();
        this.takeStatus = takeStockPlan.getTakeStatus().getDesc();

        StoreCenterService storeCenterService = ApplicationUtil.getBean(StoreCenterService.class);
        StoreCenter sc = storeCenterService.findById(dto.getScId());
        this.scCode = sc.getCode();
        this.scName = sc.getName();

        UserService userService = ApplicationUtil.getBean(UserService.class);

        if (!StringUtil.isBlank(dto.getApproveBy())) {
            this.approveBy = userService.findById(dto.getApproveBy()).getName();
        }

        this.takeType = takeStockPlan.getTakeType().getDesc();

        this.updateTime = DateUtil.toDate(dto.getUpdateTime());
        this.approveTime = DateUtil.toDate(dto.getApproveTime());
    }
}

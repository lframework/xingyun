package com.lframework.xingyun.sc.excel.stock.adjust;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.xingyun.template.core.service.UserService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import com.lframework.starter.web.components.excel.ExcelModel;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.service.storecenter.StoreCenterService;
import com.lframework.xingyun.sc.entity.StockCostAdjustSheet;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@Data
public class StockCostAdjustSheetExportModel extends BaseBo<StockCostAdjustSheet> implements ExcelModel {

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
     * 调价品种数
     */
    @ExcelProperty("调价品种数")
    private Integer productNum;

    /**
     * 库存调价差额
     */
    @ExcelProperty("库存调价差额")
    private BigDecimal diffAmount;

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

    public StockCostAdjustSheetExportModel(StockCostAdjustSheet dto) {

        super(dto);
    }

    @Override
    public <A> BaseBo<StockCostAdjustSheet> convert(StockCostAdjustSheet dto) {

        return super.convert(dto);
    }

    @Override
    protected void afterInit(StockCostAdjustSheet dto) {

        this.status = dto.getStatus().getDesc();

        StoreCenterService storeCenterService = ApplicationUtil.getBean(StoreCenterService.class);
        StoreCenter sc = storeCenterService.findById(dto.getScId());
        this.scCode = sc.getCode();
        this.scName = sc.getName();

        UserService userService = ApplicationUtil.getBean(UserService.class);
        if (!StringUtil.isBlank(dto.getApproveBy())) {
            this.approveBy = userService.findById(dto.getApproveBy()).getName();
        }
    }
}

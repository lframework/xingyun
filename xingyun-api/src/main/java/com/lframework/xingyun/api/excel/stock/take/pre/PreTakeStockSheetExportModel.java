package com.lframework.xingyun.api.excel.stock.take.pre;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.lframework.common.constants.StringPool;
import com.lframework.common.utils.DateUtil;
import com.lframework.starter.mybatis.service.IUserService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.components.excel.ExcelModel;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.service.storecenter.IStoreCenterService;
import com.lframework.xingyun.sc.entity.PreTakeStockSheet;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PreTakeStockSheetExportModel extends BaseBo<PreTakeStockSheet> implements ExcelModel {

    /**
     * 业务单据号
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
     * 盘点状态
     */
    @ExcelProperty("盘点状态")
    private String takeStatus;

    /**
     * 备注
     */
    @ExcelProperty("备注")
    private String description;

    /**
     * 操作时间
     */
    @DateTimeFormat(StringPool.DATE_TIME_PATTERN)
    @ExcelProperty("操作时间")
    private Date updateTime;

    /**
     * 操作人
     */
    @ExcelProperty("操作人")
    private String updateBy;

    public PreTakeStockSheetExportModel(PreTakeStockSheet dto) {

        super(dto);
    }

    @Override
    public <A> BaseBo<PreTakeStockSheet> convert(PreTakeStockSheet dto) {

        return super.convert(dto, PreTakeStockSheetExportModel::getTakeStatus,
                PreTakeStockSheetExportModel::getUpdateTime);
    }

    @Override
    protected void afterInit(PreTakeStockSheet dto) {

        this.takeStatus = dto.getTakeStatus().getDesc();

        IUserService userService = ApplicationUtil.getBean(IUserService.class);
        this.updateTime = DateUtil.toDate(dto.getUpdateTime());

        IStoreCenterService storeCenterService = ApplicationUtil.getBean(IStoreCenterService.class);
        StoreCenter sc = storeCenterService.findById(dto.getScId());
        this.scCode = sc.getCode();
        this.scName = sc.getName();
    }
}

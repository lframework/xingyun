package com.lframework.xingyun.sc.vo.stock.adjust;

import com.lframework.starter.web.components.validation.IsEnum;
import com.lframework.starter.web.components.validation.TypeMismatch;
import com.lframework.starter.web.vo.BaseVo;
import com.lframework.starter.web.vo.PageVo;
import com.lframework.xingyun.sc.enums.StockCostAdjustSheetStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class QueryStockCostAdjustSheetVo extends PageVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 业务单据号
     */
    private String code;

    /**
     * 仓库ID
     */
    private String scId;

    /**
     * 状态
     */
    @TypeMismatch(message = "状态格式有误！")
    @IsEnum(message = "状态格式有误！", enumClass = StockCostAdjustSheetStatus.class)
    private Integer status;

    /**
     * 修改人
     */
    private String updateBy;

    /**
     * 修改时间 起始时间
     */
    @TypeMismatch(message = "修改时间起始时间格式有误！")
    private LocalDateTime updateTimeStart;

    /**
     * 修改时间 截止时间
     */
    @TypeMismatch(message = "修改时间截止时间格式有误！")
    private LocalDateTime updateTimeEnd;

    /**
     * 审核人
     */
    private String approveBy;

    /**
     * 审核时间 起始时间
     */
    @TypeMismatch(message = "审核时间起始时间格式有误！")
    private LocalDateTime approveTimeStart;

    /**
     * 审核时间 截止时间
     */
    @TypeMismatch(message = "审核时间截止时间格式有误！")
    private LocalDateTime approveTimeEnd;

}

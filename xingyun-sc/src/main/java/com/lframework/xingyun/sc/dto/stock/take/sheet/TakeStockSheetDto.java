package com.lframework.xingyun.sc.dto.stock.take.sheet;

import com.lframework.starter.web.dto.BaseDto;
import com.lframework.xingyun.sc.enums.TakeStockSheetStatus;
import java.time.LocalDateTime;
import lombok.Data;
import java.io.Serializable;

/**
 * <p>
 * 盘点单 Dto
 * </p>
 *
 * @author zmj
 */
@Data
public class TakeStockSheetDto implements BaseDto, Serializable {

    private static final long serialVersionUID = 1L;

    public static final String CACHE_NAME = "TakeStockSheetDto";

    /**
     * ID
     */
    private String id;

    /**
     * 业务单据号
     */
    private String code;

    /**
     * 盘点任务ID
     */
    private String planId;

    /**
     * 预先盘点单ID
     */
    private String preSheetId;

    /**
     * 仓库ID
     */
    private String scId;

    /**
     * 状态
     */
    private TakeStockSheetStatus status;

    /**
     * 备注
     */
    private String description;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改人
     */
    private String updateBy;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 审核人
     */
    private String approveBy;

    /**
     * 审核时间
     */
    private LocalDateTime approveTime;

}

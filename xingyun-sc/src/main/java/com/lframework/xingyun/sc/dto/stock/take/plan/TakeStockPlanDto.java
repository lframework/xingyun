package com.lframework.xingyun.sc.dto.stock.take.plan;

import com.lframework.starter.web.dto.BaseDto;
import java.time.LocalDateTime;
import com.lframework.xingyun.sc.enums.TakeStockPlanType;
import com.lframework.xingyun.sc.enums.TakeStockPlanStatus;
import lombok.Data;
import java.io.Serializable;

/**
 * <p>
 * 盘点任务 Dto
 * </p>
 *
 * @author zmj
 */
@Data
public class TakeStockPlanDto implements BaseDto, Serializable {

    private static final long serialVersionUID = 1L;

    public static final String CACHE_NAME = "TakeStockPlanDto";

    /**
     * ID
     */
    private String id;

    /**
     * 业务单据号
     */
    private String code;

    /**
     * 仓库ID
     */
    private String scId;

    /**
     * 盘点类别
     */
    private TakeStockPlanType takeType;

    /**
     * 业务ID
     */
    private String bizId;

    /**
     * 盘点状态
     */
    private TakeStockPlanStatus takeStatus;

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

}

package com.lframework.xingyun.sc.dto.stock.take.pre;

import com.lframework.starter.web.dto.BaseDto;
import com.lframework.xingyun.sc.enums.PreTakeStockSheetStatus;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 预先盘点单 Dto
 * </p>
 *
 * @author zmj
 */
@Data
public class PreTakeStockSheetDto implements BaseDto, Serializable {

    private static final long serialVersionUID = 1L;

    public static final String CACHE_NAME = "PreTakeStockSheetDto";

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
     * 盘点状态
     */
    private PreTakeStockSheetStatus takeStatus;

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

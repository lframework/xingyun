package com.lframework.xingyun.settle.vo.check;

import com.lframework.starter.web.vo.BaseVo;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class QueryUnCheckBizItemVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 供应商ID
     */
    @NotNull(message = "供应商ID不能为空！")
    private String supplierId;

    /**
     * 起始时间
     */
    private LocalDateTime startTime;

    /**
     * 截至时间
     */
    private LocalDateTime endTime;
}

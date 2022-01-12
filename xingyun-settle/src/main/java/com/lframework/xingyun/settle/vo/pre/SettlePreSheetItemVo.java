package com.lframework.xingyun.settle.vo.pre;

import com.lframework.starter.web.vo.BaseVo;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class SettlePreSheetItemVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 项目ID
     */
    private String id;

    /**
     * 金额
     */
    private BigDecimal amount;
}

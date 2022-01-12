package com.lframework.xingyun.basedata.vo.product.category;

import com.lframework.starter.web.vo.BaseVo;
import lombok.Data;

import java.io.Serializable;

@Data
public class QueryProductCategorySelectorVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 状态
     */
    private Boolean available;
}

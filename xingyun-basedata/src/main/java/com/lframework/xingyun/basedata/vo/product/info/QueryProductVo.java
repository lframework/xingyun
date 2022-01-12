package com.lframework.xingyun.basedata.vo.product.info;

import com.lframework.starter.web.vo.BaseVo;
import com.lframework.starter.web.vo.PageVo;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class QueryProductVo extends PageVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * SKU
     */
    private String skuCode;

    /**
     * 品牌ID
     */
    private String brandId;

    /**
     * 类目ID
     */
    private String categoryId;

    /**
     * 创建起始时间
     */
    private LocalDateTime startTime;

    /**
     * 创建截止时间
     */
    private LocalDateTime endTime;

    /**
     * 状态
     */
    private Boolean available;
}

package com.lframework.xingyun.core.dto.dic.city;

import com.lframework.starter.web.dto.BaseDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class DicCityDto implements BaseDto, Serializable {

    public static final String CACHE_NAME = "DicCityDto";

    public static final String SELECTOR_CACHE_NAME = "DicCitySelectorDto";

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 编号
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 父级ID
     */
    private String parentId;

    /**
     * 层级
     */
    private Integer level;
}

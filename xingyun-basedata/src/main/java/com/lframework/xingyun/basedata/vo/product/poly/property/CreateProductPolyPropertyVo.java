package com.lframework.xingyun.basedata.vo.product.poly.property;

import com.lframework.starter.web.vo.BaseVo;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class CreateProductPolyPropertyVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * polyID
     */
    @NotBlank(message = "polyId不能为空！")
    private String polyId;

    /**
     * 属性ID
     */
    @NotBlank(message = "属性ID不能为空！")
    private String propertyId;

    /**
     * 属性值ID
     */
    private String propertyItemId;

    /**
     * 属性值文本
     */
    private String propertyText;
}

package com.lframework.xingyun.basedata.vo.product.property.item;

import com.lframework.starter.web.vo.BaseVo;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class CreateProductPropertyItemVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @NotBlank(message = "请输入编号！")
    private String code;

    /**
     * 名称
     */
    @NotBlank(message = "请输入名称！")
    private String name;

    /**
     * 属性ID
     */
    @NotBlank(message = "属性ID不能为空！")
    private String propertyId;

    /**
     * 备注
     */
    private String description;
}

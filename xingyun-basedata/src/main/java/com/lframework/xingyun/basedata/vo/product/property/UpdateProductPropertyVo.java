package com.lframework.xingyun.basedata.vo.product.property;

import com.lframework.starter.web.components.validation.IsEnum;
import com.lframework.starter.web.vo.BaseVo;
import com.lframework.xingyun.basedata.enums.ColumnDataType;
import com.lframework.xingyun.basedata.enums.ColumnType;
import com.lframework.xingyun.basedata.enums.PropertyType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class UpdateProductPropertyVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @NotBlank(message = "ID不能为空！")
    private String id;

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
     * 是否必填
     */
    @NotNull(message = "请选择是否必填！")
    private Boolean isRequired;

    /**
     * 录入类型
     */
    @NotNull(message = "请选择录入类型！")
    @IsEnum(enumClass = ColumnType.class, message = "请选择录入类型！")
    private Integer columnType;

    /**
     * 数据类型
     */
    @IsEnum(enumClass = ColumnDataType.class, message = "请选择数据类型！")
    private Integer columnDataType;

    /**
     * 属性类别
     */
    @NotNull(message = "请选择属性类别！")
    @IsEnum(enumClass = PropertyType.class, message = "请选择属性类别！")
    private Integer propertyType;

    /**
     * 类目ID
     */
    private List<String> categoryIds;

    /**
     * 状态
     */
    @NotNull(message = "请选择状态！")
    private Boolean available;

    /**
     * 备注
     */
    private String description;
}

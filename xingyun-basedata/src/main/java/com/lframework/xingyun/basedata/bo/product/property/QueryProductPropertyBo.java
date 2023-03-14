package com.lframework.xingyun.basedata.bo.product.property;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.basedata.entity.ProductProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class QueryProductPropertyBo extends BaseBo<ProductProperty> {

    /**
     * ID
     */
    @ApiModelProperty("ID")
    private String id;

    /**
     * 编号
     */
    @ApiModelProperty("编号")
    private String code;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String name;

    /**
     * 是否必填
     */
    @ApiModelProperty("是否必填")
    private Boolean isRequired;

    /**
     * 录入类型
     */
    @ApiModelProperty("录入类型")
    private Integer columnType;

    /**
     * 属性类别
     */
    @ApiModelProperty("属性类别")
    private Integer propertyType;

    /**
     * 状态
     */
    @ApiModelProperty("状态")
    private Boolean available;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String description;

    public QueryProductPropertyBo() {

    }

    public QueryProductPropertyBo(ProductProperty dto) {

        super(dto);
    }

    @Override
    protected void afterInit(ProductProperty dto) {

        this.columnType = dto.getColumnType().getCode();
        this.propertyType = dto.getPropertyType().getCode();
    }
}

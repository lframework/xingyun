package com.lframework.xingyun.basedata.bo.product.property.item;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.basedata.entity.ProductPropertyItem;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class QueryProductPropertyItemBo extends BaseBo<ProductPropertyItem> {

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
     * 状态
     */
    @ApiModelProperty("状态")
    private Boolean available;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String description;

    public QueryProductPropertyItemBo() {

    }

    public QueryProductPropertyItemBo(ProductPropertyItem dto) {

        super(dto);
    }
}

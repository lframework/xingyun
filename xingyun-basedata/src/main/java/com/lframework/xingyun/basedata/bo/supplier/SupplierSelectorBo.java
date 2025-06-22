package com.lframework.xingyun.basedata.bo.supplier;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.xingyun.basedata.entity.Supplier;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SupplierSelectorBo extends BaseBo<Supplier> {

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

    public SupplierSelectorBo() {

    }

    public SupplierSelectorBo(Supplier dto) {

        super(dto);
    }
}

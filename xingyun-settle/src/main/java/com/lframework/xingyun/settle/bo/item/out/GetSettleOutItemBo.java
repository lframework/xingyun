package com.lframework.xingyun.settle.bo.item.out;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.settle.entity.SettleOutItem;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GetSettleOutItemBo extends BaseBo<SettleOutItem> {

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

    public GetSettleOutItemBo() {

    }

    public GetSettleOutItemBo(SettleOutItem dto) {

        super(dto);
    }
}

package com.lframework.xingyun.settle.bo.item.in;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.settle.entity.SettleInItem;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GetSettleInItemBo extends BaseBo<SettleInItem> {

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

    public GetSettleInItemBo() {

    }

    public GetSettleInItemBo(SettleInItem dto) {

        super(dto);
    }
}

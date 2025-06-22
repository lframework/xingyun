package com.lframework.xingyun.settle.bo.item.out;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.xingyun.settle.entity.SettleOutItem;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SettleOutItemSelectorBo extends BaseBo<SettleOutItem> {

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

    public SettleOutItemSelectorBo() {

    }

    public SettleOutItemSelectorBo(SettleOutItem dto) {

        super(dto);
    }
}

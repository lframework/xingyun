package com.lframework.xingyun.settle.api.bo.item.out;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.settle.facade.entity.SettleOutItem;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SettleOutItemSelectorBo extends BaseBo<SettleOutItem> {

    /**
     * ID
     */
    @ApiModelProperty("ID")
    private String id;

    /**
     * 岗位编号
     */
    @ApiModelProperty("岗位编号")
    private String code;

    /**
     * 岗位名称
     */
    @ApiModelProperty("岗位名称")
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

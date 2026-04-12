package com.lframework.xingyun.settle.bo.item.out;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.xingyun.settle.entity.SettleOutItem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SettleOutItemSelectorBo extends BaseBo<SettleOutItem> {

    /**
     * ID
     */
    @Schema(description = "ID")
    private String id;

    /**
     * 编号
     */
    @Schema(description = "编号")
    private String code;

    /**
     * 名称
     */
    @Schema(description = "名称")
    private String name;

    public SettleOutItemSelectorBo() {

    }

    public SettleOutItemSelectorBo(SettleOutItem dto) {

        super(dto);
    }
}

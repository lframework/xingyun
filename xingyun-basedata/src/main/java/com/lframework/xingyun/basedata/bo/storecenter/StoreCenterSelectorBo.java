package com.lframework.xingyun.basedata.bo.storecenter;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class StoreCenterSelectorBo extends BaseBo<StoreCenter> {

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

    public StoreCenterSelectorBo() {

    }

    public StoreCenterSelectorBo(StoreCenter dto) {

        super(dto);
    }
}

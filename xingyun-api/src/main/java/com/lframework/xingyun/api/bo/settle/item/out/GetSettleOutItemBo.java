package com.lframework.xingyun.api.bo.settle.item.out;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.settle.dto.item.out.SettleOutItemDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetSettleOutItemBo extends BaseBo<SettleOutItemDto> {

    /**
     * ID
     */
    private String id;

    /**
     * 岗位编号
     */
    private String code;

    /**
     * 岗位名称
     */
    private String name;

    /**
     * 状态
     */
    private Boolean available;

    /**
     * 备注
     */
    private String description;

    public GetSettleOutItemBo() {

    }

    public GetSettleOutItemBo(SettleOutItemDto dto) {

        super(dto);
    }
}

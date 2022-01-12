package com.lframework.xingyun.api.bo.basedata.storecenter;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.basedata.dto.storecenter.StoreCenterDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class StoreCenterSelectorBo extends BaseBo<StoreCenterDto> {

    /**
     * ID
     */
    private String id;

    /**
     * 编号
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 状态
     */
    private Boolean available;

    public StoreCenterSelectorBo() {

    }

    public StoreCenterSelectorBo(StoreCenterDto dto) {

        super(dto);
    }
}

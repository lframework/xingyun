package com.lframework.xingyun.api.bo.basedata.member;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.basedata.dto.member.MemberDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MemberSelectorBo extends BaseBo<MemberDto> {

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

    public MemberSelectorBo(MemberDto dto) {

        super(dto);
    }
}

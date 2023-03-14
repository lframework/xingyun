package com.lframework.xingyun.basedata.bo.member;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.basedata.entity.Member;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MemberSelectorBo extends BaseBo<Member> {

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

    public MemberSelectorBo(Member dto) {

        super(dto);
    }
}

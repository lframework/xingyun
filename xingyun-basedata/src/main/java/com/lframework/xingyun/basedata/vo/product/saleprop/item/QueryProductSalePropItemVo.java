package com.lframework.xingyun.basedata.vo.product.saleprop.item;

import com.lframework.starter.web.vo.BaseVo;
import com.lframework.starter.web.vo.PageVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class QueryProductSalePropItemVo extends PageVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 销售属性组ID
     */
    @NotBlank(message = "GroupID不能为空！")
    private String groupId;

    /**
     * 状态
     */
    private Boolean available;
}

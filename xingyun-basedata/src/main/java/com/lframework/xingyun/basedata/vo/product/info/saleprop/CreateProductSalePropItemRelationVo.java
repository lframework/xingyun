package com.lframework.xingyun.basedata.vo.product.info.saleprop;

import com.lframework.starter.web.vo.BaseVo;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Data
public class CreateProductSalePropItemRelationVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品ID
     */
    @NotBlank(message = "商品ID不能为空！")
    private String productId;

    /**
     * 销售属性值ID
     */
    @NotEmpty(message = "销售属性值ID不能为空！")
    private List<String> salePropItemIds;
}

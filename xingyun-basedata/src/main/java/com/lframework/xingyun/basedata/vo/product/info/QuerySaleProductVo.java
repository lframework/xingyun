package com.lframework.xingyun.basedata.vo.product.info;

import com.lframework.starter.web.vo.PageVo;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class QuerySaleProductVo extends PageVo {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "仓库ID不能为空！")
    private String scId;

    /**
     * 检索关键字
     */
    private String condition;

    /**
     * 类目ID
     */
    private String categoryId;

    /**
     * 品牌ID
     */
    private String brandId;
}

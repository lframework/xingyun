package com.lframework.xingyun.sc.vo.stock;

import com.lframework.starter.web.vo.PageVo;
import lombok.Data;

@Data
public class QueryProductStockVo extends PageVo {

    /**
     * 仓库ID
     */
    private String scId;

    /**
     * 商品编号
     */
    private String productCode;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品类目ID
     */
    private String categoryId;

    /**
     * 商品品牌ID
     */
    private String brandId;
}

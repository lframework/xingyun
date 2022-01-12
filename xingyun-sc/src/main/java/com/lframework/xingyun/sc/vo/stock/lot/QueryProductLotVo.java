package com.lframework.xingyun.sc.vo.stock.lot;

import com.lframework.starter.web.vo.PageVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class QueryProductLotVo extends PageVo {

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

    /**
     * 供应商ID
     */
    private String supplierId;

    /**
     * 批次号
     */
    private String lotCode;

    /**
     * 生成起始时间
     */
    private LocalDateTime createStartTime;

    /**
     * 生成截止时间
     */
    private LocalDateTime createEndTime;
}

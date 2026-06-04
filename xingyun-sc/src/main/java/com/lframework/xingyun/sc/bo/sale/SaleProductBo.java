package com.lframework.xingyun.sc.bo.sale;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.xingyun.sc.dto.sale.SaleProductDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class SaleProductBo extends BaseBo<SaleProductDto> {

    /**
     * ID
     */
    @Schema(description = "ID")
    private String productId;

    /**
     * SKU ID
     */
    @Schema(description = "SKU ID")
    private String skuId;

    /**
     * 商品编号
     */
    @Schema(description = "商品编号")
    private String productCode;

    /**
     * SKU编号
     */
    @Schema(description = "SKU编号")
    private String skuCode;

    /**
     * 销售属性
     */
    @Schema(description = "销售属性")
    private String salePropertyText;

    /**
     * 名称
     */
    @Schema(description = "名称")
    private String productName;

    /**
     * 分类名称
     */
    @Schema(description = "分类名称")
    private String categoryName;

    /**
     * 品牌名称
     */
    @Schema(description = "品牌名称")
    private String brandName;

    /**
     * 是否多销售属性
     */
    @Schema(description = "是否多销售属性")
    private Boolean multiSaleProp;

    /**
     * 规格
     */
    @Schema(description = "规格")
    private String spec;

    /**
     * 单位
     */
    @Schema(description = "单位")
    private String unit;

    /**
     * 销售价
     */
    @Schema(description = "销售价")
    private BigDecimal salePrice;

    /**
     * 库存数量
     */
    @Schema(description = "库存数量")
    private BigDecimal stockNum;

    /**
     * 税率（%）
     */
    @Schema(description = "税率（%）")
    private BigDecimal taxRate;

    public SaleProductBo(SaleProductDto dto) {

        super(dto);
    }

    @Override
    protected void afterInit(SaleProductDto dto) {

        this.productCode = dto.getCode();
        this.productName = dto.getName();
        this.stockNum = dto.getStockNum() == null ? BigDecimal.ZERO : dto.getStockNum();
    }
}

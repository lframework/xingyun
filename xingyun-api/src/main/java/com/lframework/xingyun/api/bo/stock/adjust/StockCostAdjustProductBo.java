package com.lframework.xingyun.api.bo.stock.adjust;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.basedata.dto.product.info.StockCostAdjustProductDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
public class StockCostAdjustProductBo extends BaseBo<StockCostAdjustProductDto> {

    /**
     * ID
     */
    private String productId;

    /**
     * 编号
     */
    private String productCode;

    /**
     * 名称
     */
    private String productName;

    /**
     * 类目名称
     */
    private String categoryName;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * SKU
     */
    private String skuCode;

    /**
     * 外部编号
     */
    private String externalCode;

    /**
     * 规格
     */
    private String spec;

    /**
     * 单位
     */
    private String unit;

    /**
     * 档案采购价
     */
    private BigDecimal purchasePrice;

    /**
     * 库存数量
     */
    private Integer stockNum;

    /**
     * 调价前成本价
     */
    private BigDecimal oriPrice;

    public StockCostAdjustProductBo() {
    }

    public StockCostAdjustProductBo(StockCostAdjustProductDto dto) {
        super(dto);
    }

    @Override
    protected void afterInit(StockCostAdjustProductDto dto) {

        this.productId = dto.getId();
        this.productCode = dto.getCode();
        this.productName = dto.getName();
    }
}

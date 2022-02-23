package com.lframework.xingyun.api.bo.stock.take.pre;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.basedata.dto.product.info.PreTakeStockProductDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PreTakeStockProductBo extends BaseBo<PreTakeStockProductDto> {

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

    public PreTakeStockProductBo() {
    }

    public PreTakeStockProductBo(PreTakeStockProductDto dto) {
        super(dto);
    }

    @Override
    protected void afterInit(PreTakeStockProductDto dto) {

        this.productId = dto.getId();
        this.productCode = dto.getCode();
        this.productName = dto.getName();
    }
}

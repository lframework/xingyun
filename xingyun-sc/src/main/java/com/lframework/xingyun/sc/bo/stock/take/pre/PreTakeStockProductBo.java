package com.lframework.xingyun.sc.bo.stock.take.pre;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.xingyun.sc.dto.stock.take.pre.PreTakeStockProductDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PreTakeStockProductBo extends BaseBo<PreTakeStockProductDto> {

    /**
     * ID
     */
    @Schema(description = "ID")
    private String productId;

    /**
     * 编号
     */
    @Schema(description = "编号")
    private String productCode;

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
     * 规格
     */
    @Schema(description = "规格")
    private String spec;

    /**
     * 单位
     */
    @Schema(description = "单位")
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

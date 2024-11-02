package com.lframework.xingyun.sc.bo.stock.take.pre;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.sc.dto.stock.take.pre.PreTakeStockProductDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PreTakeStockProductBo extends BaseBo<PreTakeStockProductDto> {

    /**
     * ID
     */
    @ApiModelProperty("ID")
    private String productId;

    /**
     * 编号
     */
    @ApiModelProperty("编号")
    private String productCode;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String productName;

    /**
     * 分类名称
     */
    @ApiModelProperty("分类名称")
    private String categoryName;

    /**
     * 品牌名称
     */
    @ApiModelProperty("品牌名称")
    private String brandName;

    /**
     * SKU
     */
    @ApiModelProperty("SKU")
    private String skuCode;

    /**
     * 简码
     */
    @ApiModelProperty("简码")
    private String externalCode;

    /**
     * 规格
     */
    @ApiModelProperty("规格")
    private String spec;

    /**
     * 单位
     */
    @ApiModelProperty("单位")
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

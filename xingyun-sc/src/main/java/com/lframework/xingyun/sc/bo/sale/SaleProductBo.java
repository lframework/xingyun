package com.lframework.xingyun.sc.bo.sale;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import com.lframework.xingyun.sc.dto.sale.SaleProductDto;
import com.lframework.xingyun.sc.entity.ProductStock;
import com.lframework.xingyun.sc.service.stock.ProductStockService;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SaleProductBo extends BaseBo<SaleProductDto> {

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
     * 类目名称
     */
    @ApiModelProperty("类目名称")
    private String categoryName;

    /**
     * 品牌名称
     */
    @ApiModelProperty("品牌名称")
    private String brandName;

    /**
     * 是否多销售属性
     */
    @ApiModelProperty("是否多销售属性")
    private Boolean multiSaleProp;

    /**
     * SKU
     */
    @ApiModelProperty("SKU")
    private String skuCode;

    /**
     * 外部编号
     */
    @ApiModelProperty("外部编号")
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

    /**
     * 销售价
     */
    @ApiModelProperty("销售价")
    private BigDecimal salePrice;

    /**
     * 库存数量
     */
    @ApiModelProperty("库存数量")
    private Integer stockNum;

    /**
     * 税率（%）
     */
    @ApiModelProperty("税率（%）")
    private BigDecimal taxRate;

    /**
     * 仓库ID
     */
    @ApiModelProperty(value = "仓库ID", hidden = true)
    @JsonIgnore
    private String scId;

    public SaleProductBo(String scId, SaleProductDto dto) {

        this.scId = scId;

        if (dto != null) {
            this.convert(dto);

            this.afterInit(dto);
        }
    }

    @Override
    protected void afterInit(SaleProductDto dto) {

        this.productId = dto.getId();
        this.productCode = dto.getCode();
        this.productName = dto.getName();

        ProductStockService productStockService = ApplicationUtil.getBean(
            ProductStockService.class);
        ProductStock productStock = productStockService.getByProductIdAndScId(this.getProductId(),
            this.getScId());
        this.stockNum = productStock == null ? 0 : productStock.getStockNum();
    }
}

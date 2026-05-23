package com.lframework.xingyun.sc.bo.stock.take.pre;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.xingyun.sc.dto.stock.take.pre.QueryPreTakeStockSheetProductDto;
import com.lframework.xingyun.sc.entity.ProductStock;
import com.lframework.xingyun.sc.entity.TakeStockConfig;
import com.lframework.xingyun.sc.enums.PreTakeStockSheetStatus;
import com.lframework.xingyun.sc.service.stock.ProductStockService;
import com.lframework.xingyun.sc.service.stock.take.TakeStockConfigService;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class QueryPreTakeStockSheetProductBo extends BaseBo<QueryPreTakeStockSheetProductDto> {

    /**
     * 仓库ID
     */
    @Schema(description = "仓库ID", hidden = true)
    @JsonIgnore
    private String scId;

    /**
     * 商品ID
     */
    @Schema(description = "商品ID")
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
     * 商品名称
     */
    @Schema(description = "商品名称")
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

    /**
     * 库存数量
     */
    @Schema(description = "库存数量")
    private BigDecimal stockNum;

    /**
     * 盘点数量
     */
    @Schema(description = "盘点数量")
    private BigDecimal takeNum;

    public QueryPreTakeStockSheetProductBo(QueryPreTakeStockSheetProductDto dto, String scId) {

        this.scId = scId;

        this.init(dto);
    }

    @Override
    protected void afterInit(QueryPreTakeStockSheetProductDto dto) {

        this.productId = dto.getProductId();
        this.skuId = dto.getSkuId();
        this.productCode = dto.getProductCode();
        this.skuCode = dto.getSkuCode();
        this.productName = dto.getProductName();
        this.salePropertyText = dto.getSalePropertyText();
        this.categoryName = dto.getCategoryName();
        this.brandName = dto.getBrandName();
        this.spec = dto.getSpec();
        this.unit = dto.getUnit();

        if (dto.getTakeStatus() == PreTakeStockSheetStatus.FIRST_TAKE) {
            this.takeNum = dto.getFirstNum();
        } else if (dto.getTakeStatus() == PreTakeStockSheetStatus.SECOND_TAKE) {
            this.takeNum = dto.getSecondNum();
        } else if (dto.getTakeStatus() == PreTakeStockSheetStatus.RAND_TAKE) {
            this.takeNum = dto.getRandNum();
        }

        TakeStockConfigService takeStockConfigService = ApplicationUtil.getBean(
            TakeStockConfigService.class);
        TakeStockConfig config = takeStockConfigService.get();
        if (config.getShowStock()) {
            ProductStockService productStockService = ApplicationUtil.getBean(ProductStockService.class);
            ProductStock productStock = productStockService.getBySkuIdAndScId(this.skuId, this.scId);
            this.stockNum = productStock == null ? BigDecimal.ZERO : productStock.getStockNum();
        }
    }
}

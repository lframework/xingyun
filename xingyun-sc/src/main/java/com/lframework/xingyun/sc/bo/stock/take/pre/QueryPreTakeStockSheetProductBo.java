package com.lframework.xingyun.sc.bo.stock.take.pre;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.Product;
import com.lframework.xingyun.basedata.entity.ProductBrand;
import com.lframework.xingyun.basedata.entity.ProductCategory;
import com.lframework.xingyun.basedata.service.product.ProductBrandService;
import com.lframework.xingyun.basedata.service.product.ProductCategoryService;
import com.lframework.xingyun.basedata.service.product.ProductService;
import com.lframework.xingyun.sc.dto.stock.take.pre.QueryPreTakeStockSheetProductDto;
import com.lframework.xingyun.sc.entity.ProductStock;
import com.lframework.xingyun.sc.entity.TakeStockConfig;
import com.lframework.xingyun.sc.enums.PreTakeStockSheetStatus;
import com.lframework.xingyun.sc.service.stock.ProductStockService;
import com.lframework.xingyun.sc.service.stock.take.TakeStockConfigService;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryPreTakeStockSheetProductBo extends BaseBo<QueryPreTakeStockSheetProductDto> {

    /**
     * 仓库ID
     */
    @ApiModelProperty(value = "仓库ID", hidden = true)
    @JsonIgnore
    private String scId;

    /**
     * 商品ID
     */
    @ApiModelProperty("商品ID")
    private String productId;

    /**
     * 商品编号
     */
    @ApiModelProperty("商品编号")
    private String productCode;

    /**
     * 商品名称
     */
    @ApiModelProperty("商品名称")
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
     * 库存数量
     */
    @ApiModelProperty("库存数量")
    private Integer stockNum;

    /**
     * 盘点数量
     */
    @ApiModelProperty("盘点数量")
    private Integer takeNum;

    public QueryPreTakeStockSheetProductBo(QueryPreTakeStockSheetProductDto dto, String scId) {

        this.scId = scId;

        this.init(dto);
    }

    @Override
    protected void afterInit(QueryPreTakeStockSheetProductDto dto) {

        ProductService productService = ApplicationUtil.getBean(ProductService.class);
        Product product = productService.findById(dto.getProductId());

        ProductCategoryService productCategoryService = ApplicationUtil.getBean(
            ProductCategoryService.class);
        ProductCategory productCategory = productCategoryService.findById(product.getCategoryId());

        ProductBrandService productBrandService = ApplicationUtil.getBean(ProductBrandService.class);
        ProductBrand productBrand = productBrandService.findById(product.getBrandId());

        this.productCode = product.getCode();
        this.productName = product.getName();
        this.categoryName = productCategory.getName();
        this.brandName = productBrand.getName();
        this.skuCode = product.getSkuCode();
        this.externalCode = product.getExternalCode();
        this.spec = product.getSpec();
        this.unit = product.getUnit();

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
            ProductStock productStock = productStockService.getByProductIdAndScId(this.productId, this.scId);
            this.stockNum = productStock == null ? 0 : productStock.getStockNum();
        }
    }
}

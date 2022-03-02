package com.lframework.xingyun.api.bo.stock.take.pre;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.dto.product.info.ProductDto;
import com.lframework.xingyun.basedata.service.product.IProductService;
import com.lframework.xingyun.sc.dto.stock.ProductStockDto;
import com.lframework.xingyun.sc.dto.stock.take.config.TakeStockConfigDto;
import com.lframework.xingyun.sc.dto.stock.take.pre.QueryPreTakeStockSheetProductDto;
import com.lframework.xingyun.sc.enums.PreTakeStockSheetStatus;
import com.lframework.xingyun.sc.service.stock.IProductStockService;
import com.lframework.xingyun.sc.service.stock.take.ITakeStockConfigService;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class QueryPreTakeStockSheetProductBo extends BaseBo<QueryPreTakeStockSheetProductDto> {

    /**
     * 仓库ID
     */
    @JsonIgnore
    private String scId;

    /**
     * 商品ID
     */
    private String productId;

    /**
     * 商品编号
     */
    private String productCode;

    /**
     * 商品名称
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
     * 库存数量
     */
    private Integer stockNum;

    /**
     * 盘点数量
     */
    private Integer takeNum;

    public QueryPreTakeStockSheetProductBo(QueryPreTakeStockSheetProductDto dto, String scId) {

        this.scId = scId;

        if (dto != null) {
            this.convert(dto);

            this.afterInit(dto);
        }
    }

    @Override
    protected void afterInit(QueryPreTakeStockSheetProductDto dto) {

        IProductService productService = ApplicationUtil.getBean(IProductService.class);
        ProductDto product = productService.getById(dto.getProductId());

        this.productCode = product.getCode();
        this.productName = product.getName();
        this.brandName = product.getPoly().getBrandName();
        this.categoryName = product.getPoly().getCategoryName();
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

        ITakeStockConfigService takeStockConfigService = ApplicationUtil.getBean(ITakeStockConfigService.class);
        TakeStockConfigDto config = takeStockConfigService.get();
        if (config.getShowStock()) {
            IProductStockService productStockService = ApplicationUtil.getBean(IProductStockService.class);
            ProductStockDto productStock = productStockService.getByProductIdAndScId(this.productId, this.scId);
            this.stockNum = productStock == null ? 0 : productStock.getStockNum();
        }
    }
}

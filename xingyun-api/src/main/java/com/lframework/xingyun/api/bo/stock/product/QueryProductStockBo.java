package com.lframework.xingyun.api.bo.stock.product;

import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.NumberUtil;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.dto.product.info.ProductDto;
import com.lframework.xingyun.basedata.dto.product.saleprop.item.SalePropItemByProductDto;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.service.product.IProductSalePropItemService;
import com.lframework.xingyun.basedata.service.product.IProductService;
import com.lframework.xingyun.basedata.service.storecenter.IStoreCenterService;
import com.lframework.xingyun.sc.entity.ProductStock;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class QueryProductStockBo extends BaseBo<ProductStock> {

    /**
     * ID
     */
    @ApiModelProperty("ID")
    private String id;

    /**
     * 仓库ID
     */
    @ApiModelProperty("仓库ID")
    private String scId;

    /**
     * 仓库编号
     */
    @ApiModelProperty("仓库编号")
    private String scCode;

    /**
     * 仓库名称
     */
    @ApiModelProperty("仓库名称")
    private String scName;

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
     * 商品类目
     */
    @ApiModelProperty("商品类目")
    private String categoryName;

    /**
     * 商品品牌
     */
    @ApiModelProperty("商品品牌")
    private String brandName;

    /**
     * 销售属性1
     */
    @ApiModelProperty("销售属性1")
    private String salePropItem1;

    /**
     * 销售属性2
     */
    @ApiModelProperty("销售属性2")
    private String salePropItem2;

    /**
     * 库存数量
     */
    @ApiModelProperty("库存数量")
    private Integer stockNum;

    /**
     * 含税价格
     */
    @ApiModelProperty("含税价格")
    private BigDecimal taxPrice;

    /**
     * 含税金额
     */
    @ApiModelProperty("含税金额")
    private BigDecimal taxAmount;

    /**
     * 无税价格
     */
    @ApiModelProperty("无税价格")
    private BigDecimal unTaxPrice;

    /**
     * 无税金额
     */
    @ApiModelProperty("无税金额")
    private BigDecimal unTaxAmount;

    public QueryProductStockBo() {

    }

    public QueryProductStockBo(ProductStock dto) {

        super(dto);
    }

    @Override
    protected void afterInit(ProductStock dto) {

        IStoreCenterService storeCenterService = ApplicationUtil.getBean(IStoreCenterService.class);
        StoreCenter sc = storeCenterService.findById(dto.getScId());
        this.scCode = sc.getCode();
        this.scName = sc.getName();

        IProductService productService = ApplicationUtil.getBean(IProductService.class);
        ProductDto product = productService.findById(dto.getProductId());
        this.productCode = product.getCode();
        this.productName = product.getName();
        this.categoryName = product.getPoly().getCategoryName();
        this.brandName = product.getPoly().getBrandName();
        if (product.getPoly().getMultiSaleProp()) {
            IProductSalePropItemService productSalePropItemService = ApplicationUtil.getBean(
                    IProductSalePropItemService.class);
            List<SalePropItemByProductDto> saleProps = productSalePropItemService.getByProductId(product.getId());
            if (!CollectionUtil.isEmpty(saleProps)) {
                this.salePropItem1 = saleProps.get(0).getName();
                if (saleProps.size() > 1) {
                    this.salePropItem2 = saleProps.get(1).getName();
                }
            }
        }

        this.taxPrice = NumberUtil.getNumber(dto.getTaxPrice(), 2);
        this.taxAmount = NumberUtil.getNumber(dto.getTaxAmount(), 2);
        this.unTaxPrice = NumberUtil.getNumber(dto.getUnTaxPrice(), 2);
        this.unTaxAmount = NumberUtil.getNumber(dto.getUnTaxAmount(), 2);
    }
}

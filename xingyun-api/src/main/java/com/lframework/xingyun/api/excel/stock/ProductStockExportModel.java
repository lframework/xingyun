package com.lframework.xingyun.api.excel.stock;

import com.alibaba.excel.annotation.ExcelProperty;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.NumberUtil;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.components.excel.ExcelModel;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.dto.product.info.ProductDto;
import com.lframework.xingyun.basedata.dto.product.saleprop.item.SalePropItemByProductDto;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.service.product.IProductSalePropItemService;
import com.lframework.xingyun.basedata.service.product.IProductService;
import com.lframework.xingyun.basedata.service.storecenter.IStoreCenterService;
import com.lframework.xingyun.sc.entity.ProductStock;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProductStockExportModel extends BaseBo<ProductStock> implements ExcelModel {

    /**
     * 仓库编号
     */
    @ExcelProperty("仓库编号")
    private String scCode;

    /**
     * 仓库名称
     */
    @ExcelProperty("仓库名称")
    private String scName;

    /**
     * 商品编号
     */
    @ExcelProperty("商品编号")
    private String productCode;

    /**
     * 商品名称
     */
    @ExcelProperty("商品名称")
    private String productName;

    /**
     * 商品类目
     */
    @ExcelProperty("商品类目")
    private String categoryName;

    /**
     * 商品品牌
     */
    @ExcelProperty("商品品牌")
    private String brandName;

    /**
     * 销售属性1
     */
    @ExcelProperty("销售属性1")
    private String salePropItem1;

    /**
     * 销售属性2
     */
    @ExcelProperty("销售属性2")
    private String salePropItem2;

    /**
     * 库存数量
     */
    @ExcelProperty("库存数量")
    private Integer stockNum;

    /**
     * 含税价格
     */
    @ExcelProperty("含税价格")
    private BigDecimal taxPrice;

    /**
     * 含税金额
     */
    @ExcelProperty("含税金额")
    private BigDecimal taxAmount;

    /**
     * 无税价格
     */
    @ExcelProperty("无税价格")
    private BigDecimal unTaxPrice;

    /**
     * 无税金额
     */
    @ExcelProperty("无税金额")
    private BigDecimal unTaxAmount;

    public ProductStockExportModel() {

    }

    public ProductStockExportModel(ProductStock dto) {

        super(dto);
    }

    @Override
    public <A> BaseBo<ProductStock> convert(ProductStock dto) {

        return this;
    }

    @Override
    protected void afterInit(ProductStock dto) {

        IStoreCenterService storeCenterService = ApplicationUtil.getBean(IStoreCenterService.class);
        StoreCenter sc = storeCenterService.findById(dto.getScId());

        this.setScCode(sc.getCode());
        this.setScName(sc.getName());

        IProductService productService = ApplicationUtil.getBean(IProductService.class);
        IProductSalePropItemService productSalePropItemService = ApplicationUtil.getBean(
                IProductSalePropItemService.class);

        ProductDto product = productService.findById(dto.getProductId());

        this.setProductCode(product.getCode());
        this.setProductName(product.getName());
        this.setCategoryName(product.getPoly().getCategoryName());
        this.setBrandName(product.getPoly().getBrandName());
        if (product.getPoly().getMultiSaleProp()) {
            List<SalePropItemByProductDto> saleProps = productSalePropItemService.getByProductId(product.getId());
            if (!CollectionUtil.isEmpty(saleProps)) {
                this.setSalePropItem1(saleProps.get(0).getName());
            }
            if (saleProps.size() > 1) {
                this.setSalePropItem2(saleProps.get(1).getName());
            }
        }

        this.setStockNum(dto.getStockNum());
        this.setTaxPrice(NumberUtil.getNumber(dto.getTaxPrice(), 2));
        this.setTaxAmount(NumberUtil.getNumber(dto.getTaxAmount(), 2));
        this.setUnTaxPrice(NumberUtil.getNumber(dto.getUnTaxPrice(), 2));
        this.setUnTaxAmount(NumberUtil.getNumber(dto.getUnTaxAmount(), 2));
    }
}

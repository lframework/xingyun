package com.lframework.xingyun.sc.excel.stock;

import com.alibaba.excel.annotation.ExcelProperty;
import com.lframework.starter.common.utils.NumberUtil;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import com.lframework.starter.web.components.excel.ExcelModel;
import com.lframework.xingyun.basedata.entity.Product;
import com.lframework.xingyun.basedata.entity.ProductBrand;
import com.lframework.xingyun.basedata.entity.ProductCategory;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.service.product.ProductBrandService;
import com.lframework.xingyun.basedata.service.product.ProductCategoryService;
import com.lframework.xingyun.basedata.service.product.ProductService;
import com.lframework.xingyun.basedata.service.storecenter.StoreCenterService;
import com.lframework.xingyun.sc.entity.ProductStock;
import java.math.BigDecimal;
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

    StoreCenterService storeCenterService = ApplicationUtil.getBean(StoreCenterService.class);
    StoreCenter sc = storeCenterService.findById(dto.getScId());

    this.setScCode(sc.getCode());
    this.setScName(sc.getName());

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

    this.setStockNum(dto.getStockNum());
    this.setTaxPrice(NumberUtil.getNumber(dto.getTaxPrice(), 2));
    this.setTaxAmount(NumberUtil.getNumber(dto.getTaxAmount(), 2));
  }
}

package com.lframework.xingyun.sc.excel.stock;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.DateUtil;
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
import com.lframework.xingyun.sc.entity.ProductStockLog;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProductStockLogExportModel extends BaseBo<ProductStockLog> implements ExcelModel {

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
   * 原库存数量
   */
  @ExcelProperty("变动前库存数量")
  private Integer oriStockNum;

  /**
   * 现库存数量
   */
  @ExcelProperty("变动后库存数量")
  private Integer curStockNum;

  /**
   * 库存数量
   */
  @ExcelProperty("变动库存数量")
  private Integer stockNum;

  /**
   * 原含税成本价
   */
  @ExcelProperty("变动前含税成本价")
  private BigDecimal oriTaxPrice;

  /**
   * 现含税成本价
   */
  @ExcelProperty("变动后含税成本价")
  private BigDecimal curTaxPrice;

  /**
   * 含税金额
   */
  @ExcelProperty("变动含税金额")
  private BigDecimal taxAmount;

  /**
   * 创建时间
   */
  @ExcelProperty("操作时间")
  @DateTimeFormat(StringPool.DATE_TIME_PATTERN)
  private Date createTime;

  /**
   * 创建人
   */
  @ExcelProperty("操作人")
  private String createBy;

  /**
   * 业务单据号
   */
  @ExcelProperty("业务单据号")
  private String bizCode;

  /**
   * 业务类型
   */
  @ExcelProperty("业务类型")
  private String bizType;

  public ProductStockLogExportModel() {

  }

  public ProductStockLogExportModel(ProductStockLog dto) {

    super(dto);
  }

  @Override
  public <A> BaseBo<ProductStockLog> convert(ProductStockLog dto) {

    return this;
  }

  @Override
  protected void afterInit(ProductStockLog dto) {

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

    this.setOriStockNum(dto.getOriStockNum());
    this.setCurStockNum(dto.getCurStockNum());
    this.setStockNum(dto.getStockNum());
    this.setOriTaxPrice(NumberUtil.getNumber(dto.getOriTaxPrice(), 2));
    this.setCurTaxPrice(NumberUtil.getNumber(dto.getCurTaxPrice(), 2));
    this.setTaxAmount(NumberUtil.getNumber(dto.getTaxAmount(), 2));

    this.setCreateTime(DateUtil.toDate(dto.getCreateTime()));
    this.setBizCode(dto.getBizCode());
    this.setBizType(dto.getBizType().getDesc());
  }
}

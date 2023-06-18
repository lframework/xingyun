package com.lframework.xingyun.sc.bo.stock.product.log;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.NumberUtil;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.Product;
import com.lframework.xingyun.basedata.entity.ProductBrand;
import com.lframework.xingyun.basedata.entity.ProductCategory;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.service.product.ProductBrandService;
import com.lframework.xingyun.basedata.service.product.ProductCategoryService;
import com.lframework.xingyun.basedata.service.product.ProductService;
import com.lframework.xingyun.basedata.service.storecenter.StoreCenterService;
import com.lframework.xingyun.sc.entity.ProductStockLog;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class QueryProductStockLogBo extends BaseBo<ProductStockLog> {

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
   * 库存数量
   */
  @ApiModelProperty("库存数量")
  private Integer stockNum;

  /**
   * 原库存数量
   */
  @ApiModelProperty("原库存数量")
  private Integer oriStockNum;

  /**
   * 现库存数量
   */
  @ApiModelProperty("现库存数量")
  private Integer curStockNum;

  /**
   * 原含税成本价
   */
  @ApiModelProperty("原含税成本价")
  private BigDecimal oriTaxPrice;

  /**
   * 现含税成本价
   */
  @ApiModelProperty("现含税成本价")
  private BigDecimal curTaxPrice;

  /**
   * 含税金额
   */
  @ApiModelProperty("含税金额")
  private BigDecimal taxAmount;

  /**
   * 创建人
   */
  @ApiModelProperty("创建人")
  private String createBy;

  /**
   * 创建时间
   */
  @ApiModelProperty("创建时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime createTime;

  /**
   * 业务单据ID
   */
  @ApiModelProperty("业务单据ID")
  private String bizId;

  /**
   * 业务单据号
   */
  @ApiModelProperty("业务单据号")
  private String bizCode;

  /**
   * 业务类型
   */
  @ApiModelProperty("业务类型")
  private Integer bizType;

  public QueryProductStockLogBo() {

  }

  public QueryProductStockLogBo(ProductStockLog dto) {

    super(dto);
  }

  @Override
  public <A> BaseBo<ProductStockLog> convert(ProductStockLog dto) {

    return super.convert(dto, QueryProductStockLogBo::getBizType);
  }

  @Override
  protected void afterInit(ProductStockLog dto) {

    StoreCenterService storeCenterService = ApplicationUtil.getBean(StoreCenterService.class);
    StoreCenter sc = storeCenterService.findById(dto.getScId());
    this.scCode = sc.getCode();
    this.scName = sc.getName();

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

    this.oriTaxPrice = NumberUtil.getNumber(dto.getOriTaxPrice(), 2);
    this.curTaxPrice = NumberUtil.getNumber(dto.getCurTaxPrice(), 2);
    this.taxAmount = NumberUtil.getNumber(dto.getTaxAmount(), 2);

    this.bizType = dto.getBizType().getCode();
  }
}

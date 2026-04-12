package com.lframework.xingyun.sc.bo.stock.product.log;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.NumberUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.Product;
import com.lframework.xingyun.basedata.entity.ProductBrand;
import com.lframework.xingyun.basedata.entity.ProductCategory;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.service.product.ProductBrandService;
import com.lframework.xingyun.basedata.service.product.ProductCategoryService;
import com.lframework.xingyun.basedata.service.product.ProductService;
import com.lframework.xingyun.basedata.service.storecenter.StoreCenterService;
import com.lframework.xingyun.sc.entity.ProductStockLog;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class QueryProductStockLogBo extends BaseBo<ProductStockLog> {

  /**
   * ID
   */
  @Schema(description = "ID")
  private String id;

  /**
   * 仓库ID
   */
  @Schema(description = "仓库ID")
  private String scId;

  /**
   * 仓库编号
   */
  @Schema(description = "仓库编号")
  private String scCode;

  /**
   * 仓库名称
   */
  @Schema(description = "仓库名称")
  private String scName;

  /**
   * 商品ID
   */
  @Schema(description = "商品ID")
  private String productId;

  /**
   * 商品编号
   */
  @Schema(description = "商品编号")
  private String productCode;

  /**
   * 商品名称
   */
  @Schema(description = "商品名称")
  private String productName;

  /**
   * 商品分类
   */
  @Schema(description = "商品分类")
  private String categoryName;

  /**
   * 商品品牌
   */
  @Schema(description = "商品品牌")
  private String brandName;

  /**
   * 库存数量
   */
  @Schema(description = "库存数量")
  private BigDecimal stockNum;

  /**
   * 原库存数量
   */
  @Schema(description = "原库存数量")
  private BigDecimal oriStockNum;

  /**
   * 现库存数量
   */
  @Schema(description = "现库存数量")
  private BigDecimal curStockNum;

  /**
   * 原含税成本价
   */
  @Schema(description = "原含税成本价")
  private BigDecimal oriTaxPrice;

  /**
   * 现含税成本价
   */
  @Schema(description = "现含税成本价")
  private BigDecimal curTaxPrice;

  /**
   * 含税金额
   */
  @Schema(description = "含税金额")
  private BigDecimal taxAmount;

  /**
   * 创建人
   */
  @Schema(description = "创建人")
  private String createBy;

  /**
   * 创建时间
   */
  @Schema(description = "创建时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime createTime;

  /**
   * 业务单据ID
   */
  @Schema(description = "业务单据ID")
  private String bizId;

  /**
   * 业务单据号
   */
  @Schema(description = "业务单据号")
  private String bizCode;

  /**
   * 业务类型
   */
  @Schema(description = "业务类型")
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

    if(StringUtil.isNotBlank(product.getBrandId())) {
      ProductBrandService productBrandService = ApplicationUtil.getBean(ProductBrandService.class);
      ProductBrand productBrand = productBrandService.findById(product.getBrandId());
      this.brandName = productBrand.getName();
    }

    this.productCode = product.getCode();
    this.productName = product.getName();
    this.categoryName = productCategory.getName();

    this.bizType = dto.getBizType().getCode();
  }
}

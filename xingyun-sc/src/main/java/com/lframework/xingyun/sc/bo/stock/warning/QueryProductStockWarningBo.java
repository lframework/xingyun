package com.lframework.xingyun.sc.bo.stock.warning;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.Product;
import com.lframework.xingyun.basedata.entity.ProductSku;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.service.product.ProductService;
import com.lframework.xingyun.basedata.service.product.ProductSkuService;
import com.lframework.xingyun.basedata.service.storecenter.StoreCenterService;
import com.lframework.xingyun.sc.entity.ProductStockWarning;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <p>
 * 库存预警 QueryBo
 * </p>
 *
 * @author zmj
 */
@Data
public class QueryProductStockWarningBo extends BaseBo<ProductStockWarning> {

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
   * 预警上限
   */
  @Schema(description = "预警上限")
  private BigDecimal maxLimit;

  /**
   * 预警下限
   */
  @Schema(description = "预警下限")
  private BigDecimal minLimit;

  /**
   * 操作人
   */
  @Schema(description = "操作人")
  private String updateBy;

  /**
   * 操作时间
   */
  @Schema(description = "操作时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime updateTime;

  /**
   * 状态
   */
  @Schema(description = "状态")
  private Boolean available;

  public QueryProductStockWarningBo() {

  }

  public QueryProductStockWarningBo(ProductStockWarning dto) {

    super(dto);
  }

  @Override
  protected void afterInit(ProductStockWarning dto) {

    StoreCenterService storeCenterService = ApplicationUtil.getBean(StoreCenterService.class);
    StoreCenter sc = storeCenterService.findById(dto.getScId());
    this.scCode = sc.getCode();
    this.scName = sc.getName();

    ProductService productService = ApplicationUtil.getBean(ProductService.class);
    Product product = productService.findById(dto.getProductId());
    this.productCode = product.getCode();
    this.productName = product.getName();

    ProductSkuService productSkuService = ApplicationUtil.getBean(ProductSkuService.class);
    ProductSku sku = productSkuService.findById(dto.getSkuId());
    this.skuCode = sku == null ? null : sku.getCode();
    this.salePropertyText = sku == null ? null : sku.getSalePropertyText();
  }
}

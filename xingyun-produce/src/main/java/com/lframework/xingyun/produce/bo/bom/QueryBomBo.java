package com.lframework.xingyun.produce.bo.bom;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.Product;
import com.lframework.xingyun.basedata.entity.ProductSku;
import com.lframework.xingyun.basedata.service.product.ProductService;
import com.lframework.xingyun.basedata.service.product.ProductSkuService;
import com.lframework.xingyun.produce.entity.Bom;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class QueryBomBo extends BaseBo<Bom> {

  /**
   * ID
   */
  @Schema(description = "ID")
  private String id;

  /**
   * BOM编号
   */
  @Schema(description = "BOM编号")
  private String code;

  /**
   * BOM名称
   */
  @Schema(description = "BOM名称")
  private String name;

  /**
   * 成品商品编号
   */
  @Schema(description = "成品商品编号")
  private String mainProductCode;

  /**
   * 成品商品名称
   */
  @Schema(description = "成品商品名称")
  private String mainProductName;

  /**
   * 成品SKU编号
   */
  @Schema(description = "成品SKU编号")
  private String mainSkuCode;

  /**
   * 产出数量
   */
  @Schema(description = "产出数量")
  private BigDecimal outputNum;

  /**
   * 状态
   */
  @Schema(description = "状态")
  private Integer status;

  /**
   * 状态描述
   */
  @Schema(description = "状态描述")
  private String statusDesc;

  /**
   * 版本
   */
  @Schema(description = "版本")
  private String version;

  /**
   * 是否默认版本
   */
  @Schema(description = "是否默认版本")
  private Boolean isDefault;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;

  /**
   * 创建时间
   */
  @Schema(description = "创建时间")
  private String createTime;

  public QueryBomBo() {
  }

  public QueryBomBo(Bom dto) {
    super(dto);
  }

  @Override
  protected void afterInit(Bom dto) {
    this.status = dto.getStatus().getCode();
    this.statusDesc = dto.getStatus().getDesc();

    ProductService productService = ApplicationUtil.getBean(ProductService.class);
    Product product = productService.findById(dto.getMainProductId());
    if (product != null) {
      this.mainProductCode = product.getCode();
      this.mainProductName = product.getName();
    }

    ProductSkuService skuService = ApplicationUtil.getBean(ProductSkuService.class);
    ProductSku sku = skuService.findById(dto.getMainSkuId());
    if (sku != null) {
      this.mainSkuCode = sku.getCode();
    }
  }
}

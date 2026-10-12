package com.lframework.xingyun.produce.bo.assemble;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.Product;
import com.lframework.xingyun.basedata.entity.ProductSku;
import com.lframework.xingyun.basedata.service.product.ProductService;
import com.lframework.xingyun.basedata.service.product.ProductSkuService;
import com.lframework.xingyun.produce.entity.AssembleSheetDetail;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 组装单明细BO
 *
 * @author zmj
 */
@Data
public class AssembleSheetDetailBo extends BaseBo<AssembleSheetDetail> {

  @Schema(description = "ID")
  private String id;

  @Schema(description = "单据ID")
  private String sheetId;

  @Schema(description = "子件商品ID")
  private String productId;

  @Schema(description = "子件SKU ID")
  private String skuId;

  @Schema(description = "子件SKU编号")
  private String skuCode;

  @Schema(description = "子件商品编号")
  private String productCode;

  @Schema(description = "子件商品名称")
  private String productName;

  @Schema(description = "子件规格")
  private String productSpec;

  @Schema(description = "子件单位")
  private String productUnit;

  @Schema(description = "单位用量")
  private BigDecimal num;

  @Schema(description = "本次消耗数量")
  private BigDecimal stockNum;

  @Schema(description = "备注")
  private String description;

  @Schema(description = "排序")
  private Integer orderNo;

  public AssembleSheetDetailBo() {
  }

  public AssembleSheetDetailBo(AssembleSheetDetail dto) {
    super(dto);
  }

  @Override
  protected void afterInit(AssembleSheetDetail dto) {
    ProductService productService = ApplicationUtil.getBean(ProductService.class);
    Product product = productService.findById(dto.getProductId());
    if (product != null) {
      this.productCode = product.getCode();
      this.productName = product.getName();
      this.productSpec = product.getSpec();
      this.productUnit = product.getUnit();
    }

    ProductSkuService skuService = ApplicationUtil.getBean(ProductSkuService.class);
    ProductSku sku = skuService.findById(dto.getSkuId());
    if (sku != null) {
      this.skuCode = sku.getCode();
    }
  }
}

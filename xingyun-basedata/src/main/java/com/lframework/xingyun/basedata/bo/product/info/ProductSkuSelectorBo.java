package com.lframework.xingyun.basedata.bo.product.info;

import com.lframework.starter.web.core.annotations.convert.EnumConvert;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.xingyun.basedata.dto.product.ProductSkuSelectorDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ProductSkuSelectorBo extends BaseBo<ProductSkuSelectorDto> {

  @Schema(description = "ID")
  private String id;

  @Schema(description = "SKU ID")
  private String skuId;

  @Schema(description = "SKU编号")
  private String skuCode;

  @Schema(description = "编号")
  private String code;

  @Schema(description = "商品ID")
  private String productId;

  @Schema(description = "商品编号")
  private String productCode;

  @Schema(description = "商品名称")
  private String name;

  @Schema(description = "商品名称")
  private String productName;

  @Schema(description = "销售属性")
  private String salePropertyText;

  @Schema(description = "分类ID")
  private String categoryId;

  @Schema(description = "分类名称")
  private String categoryName;

  @Schema(description = "品牌ID")
  private String brandId;

  @Schema(description = "品牌名称")
  private String brandName;

  @Schema(description = "规格")
  private String spec;

  @Schema(description = "单位")
  private String unit;

  @Schema(description = "商品类型")
  @EnumConvert
  private Integer productType;

  @Schema(description = "状态")
  private Boolean available;

  public ProductSkuSelectorBo() {

  }

  public ProductSkuSelectorBo(ProductSkuSelectorDto dto) {

    super(dto);
  }
}

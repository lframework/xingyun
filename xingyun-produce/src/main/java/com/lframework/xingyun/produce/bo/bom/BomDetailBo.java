package com.lframework.xingyun.produce.bo.bom;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.xingyun.produce.dto.bom.BomDetailDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class BomDetailBo extends BaseBo<BomDetailDto> {

  @Schema(description = "ID")
  private String id;

  @Schema(description = "BOM ID")
  private String bomId;

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
  private BigDecimal unitNum;

  @Schema(description = "损耗率%")
  private BigDecimal scrapRate;

  @Schema(description = "是否关键物料")
  private Boolean isKeyMaterial;

  @Schema(description = "排序")
  private Integer sortOrder;

  @Schema(description = "备注")
  private String description;

  public BomDetailBo() {
  }

  public BomDetailBo(BomDetailDto dto) {
    super(dto);
  }
}

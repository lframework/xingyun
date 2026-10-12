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
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class GetBomBo extends BaseBo<Bom> {

  @Schema(description = "ID")
  private String id;

  @Schema(description = "BOM编号")
  private String code;

  @Schema(description = "BOM名称")
  private String name;

  @Schema(description = "成品商品ID")
  private String mainProductId;

  @Schema(description = "成品SKU ID")
  private String mainSkuId;

  @Schema(description = "成品SKU编号")
  private String mainSkuCode;

  @Schema(description = "成品商品编号")
  private String mainProductCode;

  @Schema(description = "成品商品名称")
  private String mainProductName;

  @Schema(description = "产出数量")
  private BigDecimal outputNum;

  @Schema(description = "废品率%")
  private BigDecimal scrapRate;

  @Schema(description = "状态")
  private Integer status;

  @Schema(description = "状态描述")
  private String statusDesc;

  @Schema(description = "是否默认版本")
  private Boolean isDefault;

  @Schema(description = "版本")
  private String version;

  @Schema(description = "备注")
  private String description;

  @Schema(description = "审核人")
  private String approveBy;

  @Schema(description = "审核时间")
  private LocalDateTime approveTime;

  @Schema(description = "创建人")
  private String createBy;

  @Schema(description = "创建时间")
  private LocalDateTime createTime;

  @Schema(description = "修改人")
  private String updateBy;

  @Schema(description = "修改时间")
  private LocalDateTime updateTime;

  /**
   * BOM明细
   */
  @Schema(description = "BOM明细")
  private List<BomDetailBo> details;

  public GetBomBo() {
  }

  public GetBomBo(Bom dto) {
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

package com.lframework.xingyun.produce.bo.assemble;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.Product;
import com.lframework.xingyun.basedata.entity.ProductSku;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.service.product.ProductService;
import com.lframework.xingyun.basedata.service.product.ProductSkuService;
import com.lframework.xingyun.basedata.service.storecenter.StoreCenterService;
import com.lframework.xingyun.produce.entity.AssembleSheet;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

/**
 * 组装单详情BO
 *
 * @author zmj
 */
@Data
public class GetAssembleSheetBo extends BaseBo<AssembleSheet> {

  @Schema(description = "ID")
  private String id;

  @Schema(description = "单据号")
  private String code;

  @Schema(description = "仓库ID")
  private String scId;

  @Schema(description = "仓库名称")
  private String scName;

  @Schema(description = "BOM ID")
  private String bomId;

  @Schema(description = "成品商品ID")
  private String productId;

  @Schema(description = "成品SKU ID")
  private String skuId;

  @Schema(description = "成品SKU编号")
  private String skuCode;

  @Schema(description = "成品商品编号")
  private String productCode;

  @Schema(description = "成品商品名称")
  private String productName;

  @Schema(description = "组装数量")
  private BigDecimal totalNum;

  @Schema(description = "状态")
  private Integer status;

  @Schema(description = "状态描述")
  private String statusDesc;

  @Schema(description = "备注")
  private String description;

  @Schema(description = "拒绝原因")
  private String refuseReason;

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

  @Schema(description = "子件明细")
  private List<AssembleSheetDetailBo> details;

  public GetAssembleSheetBo() {
  }

  public GetAssembleSheetBo(AssembleSheet dto) {
    super(dto);
  }

  @Override
  protected void afterInit(AssembleSheet dto) {
    this.status = dto.getStatus().getCode();
    this.statusDesc = dto.getStatus().getDesc();

    StoreCenterService scService = ApplicationUtil.getBean(StoreCenterService.class);
    StoreCenter sc = scService.findById(dto.getScId());
    if (sc != null) {
      this.scName = sc.getName();
    }

    ProductService productService = ApplicationUtil.getBean(ProductService.class);
    Product product = productService.findById(dto.getProductId());
    if (product != null) {
      this.productCode = product.getCode();
      this.productName = product.getName();
    }

    ProductSkuService skuService = ApplicationUtil.getBean(ProductSkuService.class);
    ProductSku sku = skuService.findById(dto.getSkuId());
    if (sku != null) {
      this.skuCode = sku.getCode();
    }
  }
}

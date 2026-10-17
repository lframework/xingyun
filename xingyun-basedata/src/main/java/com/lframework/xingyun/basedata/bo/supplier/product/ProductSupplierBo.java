package com.lframework.xingyun.basedata.bo.supplier.product;

import com.lframework.xingyun.basedata.entity.Product;
import com.lframework.xingyun.basedata.entity.Supplier;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class ProductSupplierBo {

  @Schema(description = "商品ID")
  private String id;

  @Schema(description = "商品编号")
  private String code;

  @Schema(description = "商品名称")
  private String name;

  @Schema(description = "已关联供应商，同一供应商的多个SKU关联合并展示")
  private List<SupplierBo> suppliers = new ArrayList<>();

  public ProductSupplierBo(Product product) {
    this.id = product.getId();
    this.code = product.getCode();
    this.name = product.getName();
  }

  @Data
  public static class SupplierBo {

    private String id;

    private String name;

    public SupplierBo(Supplier supplier) {
      this.id = supplier.getId();
      this.name = supplier.getName();
    }
  }
}

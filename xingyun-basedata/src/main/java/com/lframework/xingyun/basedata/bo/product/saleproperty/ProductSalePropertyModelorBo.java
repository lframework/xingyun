package com.lframework.xingyun.basedata.bo.product.saleproperty;

import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.xingyun.basedata.dto.product.saleproperty.ProductSalePropertyModelorDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class ProductSalePropertyModelorBo extends BaseBo<ProductSalePropertyModelorDto> {

  /**
   * ID
   */
  @Schema(description = "ID")
  private String id;

  /**
   * 名称
   */
  @Schema(description = "名称")
  private String name;

  /**
   * 可选项
   */
  @Schema(description = "可选项")
  private List<ProductSalePropertyItemModelorBo> items;

  public ProductSalePropertyModelorBo() {

  }

  public ProductSalePropertyModelorBo(ProductSalePropertyModelorDto dto) {

    super(dto);
  }

  @Override
  public BaseBo<ProductSalePropertyModelorDto> convert(ProductSalePropertyModelorDto dto) {

    return super.convert(dto, ProductSalePropertyModelorBo::getItems);
  }

  @Override
  protected void afterInit(ProductSalePropertyModelorDto dto) {

    if (!CollectionUtil.isEmpty(dto.getItems())) {
      this.items = dto.getItems().stream().map(ProductSalePropertyItemModelorBo::new)
          .collect(Collectors.toList());
    }
  }

  @Data
  public static class ProductSalePropertyItemModelorBo
      extends BaseBo<ProductSalePropertyModelorDto.ProductSalePropertyItemModelorDto> {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    private String id;

    /**
     * 名称
     */
    @Schema(description = "名称")
    private String name;

    public ProductSalePropertyItemModelorBo() {

    }

    public ProductSalePropertyItemModelorBo(
        ProductSalePropertyModelorDto.ProductSalePropertyItemModelorDto dto) {

      super(dto);
    }
  }
}

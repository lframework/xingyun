package com.lframework.xingyun.basedata.bo.product.saleproperty;

import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.xingyun.basedata.dto.product.saleproperty.ProductSalePropertyDefinitionModelorDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class ProductSalePropertyDefinitionModelorBo extends BaseBo<ProductSalePropertyDefinitionModelorDto> {

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

  public ProductSalePropertyDefinitionModelorBo() {

  }

  public ProductSalePropertyDefinitionModelorBo(ProductSalePropertyDefinitionModelorDto dto) {

    super(dto);
  }

  @Override
  public BaseBo<ProductSalePropertyDefinitionModelorDto> convert(ProductSalePropertyDefinitionModelorDto dto) {

    return super.convert(dto, ProductSalePropertyDefinitionModelorBo::getItems);
  }

  @Override
  protected void afterInit(ProductSalePropertyDefinitionModelorDto dto) {

    if (!CollectionUtil.isEmpty(dto.getItems())) {
      this.items = dto.getItems().stream().map(ProductSalePropertyItemModelorBo::new)
          .collect(Collectors.toList());
    }
  }

  @Data
  public static class ProductSalePropertyItemModelorBo
      extends BaseBo<ProductSalePropertyDefinitionModelorDto.ProductSalePropertyItemModelorDto> {

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
        ProductSalePropertyDefinitionModelorDto.ProductSalePropertyItemModelorDto dto) {

      super(dto);
    }
  }
}

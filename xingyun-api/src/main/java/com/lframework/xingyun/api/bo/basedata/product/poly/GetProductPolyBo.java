package com.lframework.xingyun.api.bo.basedata.product.poly;

import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.api.bo.basedata.product.property.ProductPropertyModelorBo;
import com.lframework.xingyun.basedata.dto.product.poly.ProductPolyDto;
import com.lframework.xingyun.basedata.dto.product.poly.ProductPolyPropertyDto;
import com.lframework.xingyun.basedata.dto.product.property.ProductPropertyModelorDto;
import com.lframework.xingyun.basedata.enums.ColumnType;
import com.lframework.xingyun.basedata.service.product.IProductPropertyService;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 商品SPU GetBo
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GetProductPolyBo extends BaseBo<ProductPolyDto> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 编号
   */
  @ApiModelProperty("编号")
  private String code;

  /**
   * 名称
   */
  @ApiModelProperty("名称")
  private String name;

  /**
   * 简称
   */
  @ApiModelProperty("简称")
  private String shortName;

  /**
   * 类目ID
   */
  @ApiModelProperty("类目ID")
  private String categoryId;

  /**
   * 类目名称
   */
  @ApiModelProperty("类目名称")
  private String categoryName;

  /**
   * 品牌ID
   */
  @ApiModelProperty("品牌ID")
  private String brandId;

  /**
   * 品牌名称
   */
  @ApiModelProperty("品牌名称")
  private String brandName;

  /**
   * 是否多规格
   */
  @ApiModelProperty("是否多规格")
  private Boolean multiSaleProp;

  /**
   * 进项税率（%）
   */
  @ApiModelProperty("进项税率（%）")
  private BigDecimal taxRate;

  /**
   * 销项税率
   */
  @ApiModelProperty("销项税率")
  private BigDecimal saleTaxRate;

  /**
   * 属性
   */
  @ApiModelProperty("属性")
  private List<PropertyBo> properties;

  @ApiModelProperty("属性模型")
  private List<ProductPropertyModelorBo> modelors;

  public GetProductPolyBo() {

  }

  public GetProductPolyBo(ProductPolyDto dto) {

    super(dto);
  }

  @Override
  protected void afterInit(ProductPolyDto dto) {
    if (!CollectionUtil.isEmpty(dto.getProperties())) {

      this.properties = new ArrayList<>();
      for (ProductPolyPropertyDto property : dto.getProperties()) {
        if (property.getPropertyColumnType() == ColumnType.MULTIPLE) {
          PropertyBo propertyBo = this.properties.stream()
              .filter(t -> t.getId().equals(property.getId())).findFirst().orElse(null);
          if (propertyBo == null) {
            this.properties.add(new PropertyBo(property));
          } else {
            propertyBo.setText(propertyBo.getText().concat(",").concat(property.getPropertyName()));
          }
        } else {
          this.properties.add(new PropertyBo(property));
        }
      }
    }

    IProductPropertyService productPropertyService = ApplicationUtil.getBean(IProductPropertyService.class);
    List<ProductPropertyModelorDto> modelors = productPropertyService.getModelorByCategoryId(this.categoryId);
    if (!CollectionUtil.isEmpty(modelors)) {
      this.modelors = modelors.stream().map(ProductPropertyModelorBo::new).collect(Collectors.toList());
      if (!CollectionUtil.isEmpty(this.properties)) {
        for (ProductPropertyModelorBo modelor : this.modelors) {
          PropertyBo property = this.properties.stream().filter(t -> t.getId().equals(modelor.getId())).findFirst().orElse(null);
          if (property == null) {
            continue;
          }

          modelor.setText(property.getText());
        }
      }
    }
  }

  @Data
  @EqualsAndHashCode(callSuper = true)
  public static class PropertyBo extends BaseBo<ProductPolyPropertyDto> {

    /**
     * 属性ID
     */
    @ApiModelProperty("属性ID")
    private String id;

    /**
     * 属性名
     */
    @ApiModelProperty("属性名")
    private String name;

    /**
     * 字段类型
     */
    @ApiModelProperty("字段类型")
    private Integer columnType;

    /**
     * 属性文本
     */
    @ApiModelProperty("属性文本")
    private String text;

    public PropertyBo() {

    }

    public PropertyBo(ProductPolyPropertyDto dto) {

      super(dto);
    }

    @Override
    public BaseBo<ProductPolyPropertyDto> convert(ProductPolyPropertyDto dto) {

      return super.convert(dto, PropertyBo::getColumnType);
    }

    @Override
    protected void afterInit(ProductPolyPropertyDto dto) {

      this.id = dto.getPropertyId();
      this.name = dto.getPropertyName();
      this.text = dto.getPropertyText();
      this.columnType = dto.getPropertyColumnType().getCode();
    }
  }
}

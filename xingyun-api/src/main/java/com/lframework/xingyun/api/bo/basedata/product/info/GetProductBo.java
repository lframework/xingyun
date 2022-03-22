package com.lframework.xingyun.api.bo.basedata.product.info;

import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.basedata.dto.product.info.GetProductDto;
import com.lframework.xingyun.basedata.enums.ColumnType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetProductBo extends BaseBo<GetProductDto> {

  /**
   * ID
   */
  private String id;

  /**
   * 编号
   */
  private String code;

  /**
   * 名称
   */
  private String name;

  /**
   * 类目ID
   */
  private String categoryId;

  /**
   * 类目名称
   */
  private String categoryName;

  /**
   * 品牌ID
   */
  private String brandId;

  /**
   * 品牌名称
   */
  private String brandName;

  /**
   * SKU
   */
  private String skuCode;

  /**
   * 外部编号
   */
  private String externalCode;

  /**
   * 规格
   */
  private String spec;

  /**
   * 单位
   */
  private String unit;

  /**
   * 采购价
   */
  private BigDecimal purchasePrice;

  /**
   * 销售价
   */
  private BigDecimal salePrice;

  /**
   * 零售价
   */
  private BigDecimal retailPrice;

  /**
   * 状态
   */
  private Boolean available;

  /**
   * 属性
   */
  private List<PropertyBo> properties;

  public GetProductBo() {

  }

  public GetProductBo(GetProductDto dto) {

    super(dto);
  }

  @Override
  public BaseBo<GetProductDto> convert(GetProductDto dto) {

    return super.convert(dto, GetProductBo::getProperties);
  }

  @Override
  protected void afterInit(GetProductDto dto) {

    if (!CollectionUtil.isEmpty(dto.getProperties())) {

      this.properties = new ArrayList<>();
      for (GetProductDto.PropertyDto property : dto.getProperties()) {
        if (property.getColumnType() == ColumnType.MULTIPLE) {
          PropertyBo propertyBo = this.properties.stream()
              .filter(t -> t.getId().equals(property.getId()))
              .findFirst().orElse(null);
          if (propertyBo == null) {
            this.properties.add(new PropertyBo(property));
          } else {
            propertyBo.setText(propertyBo.getText().concat(",").concat(property.getText()));
          }
        } else {
          this.properties.add(new PropertyBo(property));
        }
      }
    }
  }

  @Data
  @EqualsAndHashCode(callSuper = true)
  public static class PropertyBo extends BaseBo<GetProductDto.PropertyDto> {

    /**
     * 属性ID
     */
    private String id;

    /**
     * 属性名
     */
    private String name;

    /**
     * 字段类型
     */
    private Integer columnType;

    /**
     * 属性文本
     */
    private String text;

    public PropertyBo() {

    }

    public PropertyBo(GetProductDto.PropertyDto dto) {

      super(dto);
    }

    @Override
    public BaseBo<GetProductDto.PropertyDto> convert(GetProductDto.PropertyDto dto) {

      return super.convert(dto, PropertyBo::getColumnType);
    }

    @Override
    protected void afterInit(GetProductDto.PropertyDto dto) {

      this.columnType = dto.getColumnType().getCode();
    }
  }
}

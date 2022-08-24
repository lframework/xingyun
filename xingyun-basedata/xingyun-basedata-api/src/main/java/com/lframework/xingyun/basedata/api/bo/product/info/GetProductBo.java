package com.lframework.xingyun.basedata.api.bo.product.info;

import com.alibaba.excel.annotation.ExcelProperty;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.biz.service.product.IProductSalePropItemRelationService;
import com.lframework.xingyun.basedata.facade.dto.product.info.GetProductDto;
import com.lframework.xingyun.basedata.facade.dto.product.saleprop.item.SalePropItemByProductDto;
import com.lframework.xingyun.basedata.facade.enums.ColumnType;
import io.swagger.annotations.ApiModelProperty;
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
   * SKU
   */
  @ApiModelProperty("SKU")
  private String skuCode;

  /**
   * 外部编号
   */
  @ApiModelProperty("外部编号")
  private String externalCode;

  /**
   * 规格
   */
  @ApiModelProperty("规格")
  private String spec;

  /**
   * 单位
   */
  @ApiModelProperty("单位")
  private String unit;

  /**
   * 采购价
   */
  @ApiModelProperty("采购价")
  private BigDecimal purchasePrice;

  /**
   * 销售价
   */
  @ApiModelProperty("销售价")
  private BigDecimal salePrice;

  /**
   * 零售价
   */
  @ApiModelProperty("零售价")
  private BigDecimal retailPrice;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  private Boolean available;

  /**
   * 属性
   */
  @ApiModelProperty("属性")
  private List<PropertyBo> properties;

  /**
   * 是否多销售属性
   */
  @ApiModelProperty("是否多销售属性")
  private Boolean multiSaleProp;

  /**
   * 销售属性组1ID
   */
  @ApiModelProperty("销售属性组1ID")
  private String salePropGroup1Id;

  /**
   * 销售属性组1名称
   */
  @ApiModelProperty("销售属性组1名称")
  private String salePropGroup1Name;

  /**
   * 销售属性组2ID
   */
  @ApiModelProperty("销售属性组2ID")
  private String salePropGroup2Id;

  /**
   * 销售属性组2名称
   */
  @ApiModelProperty("销售属性组2名称")
  private String salePropGroup2Name;

  /**
   * 销售属性1ID
   */
  @ExcelProperty("销售属性1ID")
  private String salePropItem1Id;

  /**
   * 销售属性1名称
   */
  @ApiModelProperty("销售属性1名称")
  private String salePropItem1Name;

  /**
   * 销售属性2ID
   */
  @ExcelProperty("销售属性2ID")
  private String salePropItem2Id;

  /**
   * 销售属性2名称
   */
  @ApiModelProperty("销售属性2名称")
  private String salePropItem2Name;

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
              .filter(t -> t.getId().equals(property.getId())).findFirst().orElse(null);
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
    if (dto.getMultiSaleProp()) {
      IProductSalePropItemRelationService productSalePropItemRelationService = ApplicationUtil.getBean(
          IProductSalePropItemRelationService.class);
      SalePropItemByProductDto relation = productSalePropItemRelationService.getByProductId(
          dto.getId());
      if (relation != null) {
        this.salePropItem1Id = relation.getItemId1();
        this.salePropItem1Name = relation.getItemName1();
        this.salePropGroup1Id = relation.getGroupId1();
        this.salePropGroup1Name = relation.getGroupName1();

        this.salePropItem2Id = relation.getItemId2();
        this.salePropItem2Name = relation.getItemName2();
        this.salePropGroup2Id = relation.getGroupId2();
        this.salePropGroup2Name = relation.getGroupName2();
      }
    }
  }

  @Data
  @EqualsAndHashCode(callSuper = true)
  public static class PropertyBo extends BaseBo<GetProductDto.PropertyDto> {

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

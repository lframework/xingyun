package com.lframework.xingyun.basedata.api.bo.product.info;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.common.constants.StringPool;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.biz.service.product.IProductSalePropItemRelationService;
import com.lframework.xingyun.basedata.facade.dto.product.info.ProductDto;
import com.lframework.xingyun.basedata.facade.dto.product.saleprop.item.SalePropItemByProductDto;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class QueryProductBo extends BaseBo<ProductDto> {

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
   * SKU
   */
  @ApiModelProperty("SKU")
  private String skuCode;

  /**
   * 类目名称
   */
  @ApiModelProperty("类目名称")
  private String categoryName;

  /**
   * 品牌名称
   */
  @ApiModelProperty("品牌名称")
  private String brandName;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  private Boolean available;

  /**
   * 创建时间
   */
  @ApiModelProperty("创建时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime createTime;

  /**
   * 修改时间
   */
  @ApiModelProperty("修改时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime updateTime;

  /**
   * 销售属性1名称
   */
  @ApiModelProperty("销售属性1名称")
  private String salePropItem1Name;

  /**
   * 销售属性2名称
   */
  @ApiModelProperty("销售属性2名称")
  private String salePropItem2Name;

  public QueryProductBo() {

  }

  public QueryProductBo(ProductDto dto) {

    super(dto);
  }

  @Override
  protected void afterInit(ProductDto dto) {

    this.categoryName = dto.getPoly().getCategoryName();
    this.brandName = dto.getPoly().getBrandName();
    if (dto.getPoly().getMultiSaleProp()) {
      IProductSalePropItemRelationService productSalePropItemRelationService = ApplicationUtil.getBean(
          IProductSalePropItemRelationService.class);
      SalePropItemByProductDto relation = productSalePropItemRelationService.getByProductId(
          dto.getId());
      if (relation != null) {
        this.salePropItem1Name = relation.getItemName1();
        this.salePropItem2Name = relation.getItemName2();
      }
    }
  }
}

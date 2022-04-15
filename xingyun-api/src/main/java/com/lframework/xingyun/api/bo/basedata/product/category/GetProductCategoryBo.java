package com.lframework.xingyun.api.bo.basedata.product.category;

import com.lframework.common.utils.ObjectUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.dto.product.category.ProductCategoryDto;
import com.lframework.xingyun.basedata.service.product.IProductCategoryService;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetProductCategoryBo extends BaseBo<ProductCategoryDto> {

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
   * 父级ID
   */
  @ApiModelProperty("父级ID")
  private String parentId;

  /**
   * 父级名称
   */
  @ApiModelProperty("父级名称")
  private String parentName;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  private Boolean available;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  public GetProductCategoryBo() {

  }

  public GetProductCategoryBo(ProductCategoryDto dto) {

    super(dto);
  }

  @Override
  protected void afterInit(ProductCategoryDto dto) {

    if (!StringUtil.isBlank(this.getParentId())) {
      IProductCategoryService productCategoryService = ApplicationUtil
          .getBean(IProductCategoryService.class);
      ProductCategoryDto parentCategory = productCategoryService.getById(this.getParentId());
      if (!ObjectUtil.isNull(parentCategory)) {
        this.setParentName(parentCategory.getName());
      }
    }
  }
}

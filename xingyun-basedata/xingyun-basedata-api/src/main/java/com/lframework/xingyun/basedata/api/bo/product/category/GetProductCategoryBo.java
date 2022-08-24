package com.lframework.xingyun.basedata.api.bo.product.category;

import com.lframework.common.utils.ObjectUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.biz.service.product.IProductCategoryService;
import com.lframework.xingyun.basedata.facade.entity.ProductCategory;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetProductCategoryBo extends BaseBo<ProductCategory> {

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

  public GetProductCategoryBo(ProductCategory dto) {

    super(dto);
  }

  @Override
  protected void afterInit(ProductCategory dto) {

    if (!StringUtil.isBlank(this.getParentId())) {
      IProductCategoryService productCategoryService = ApplicationUtil.getBean(
          IProductCategoryService.class);
      ProductCategory parentCategory = productCategoryService.findById(this.getParentId());
      if (!ObjectUtil.isNull(parentCategory)) {
        this.setParentName(parentCategory.getName());
      }
    }
  }
}

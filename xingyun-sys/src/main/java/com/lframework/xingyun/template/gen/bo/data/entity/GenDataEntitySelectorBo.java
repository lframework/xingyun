package com.lframework.xingyun.template.gen.bo.data.entity;

import com.lframework.starter.common.utils.StringUtil;
import com.lframework.xingyun.template.gen.entity.GenDataEntity;
import com.lframework.xingyun.template.gen.entity.GenDataEntityCategory;
import com.lframework.xingyun.template.gen.service.GenDataEntityCategoryService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.utils.ApplicationUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GenDataEntitySelectorBo extends BaseBo<GenDataEntity> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 名称
   */
  @ApiModelProperty("名称")
  private String name;

  /**
   * 分类名称
   */
  @ApiModelProperty("分类名称")
  private String categoryName;

  public GenDataEntitySelectorBo() {
  }

  public GenDataEntitySelectorBo(GenDataEntity dto) {
    super(dto);
  }

  @Override
  protected void afterInit(GenDataEntity dto) {
    if (!StringUtil.isBlank(dto.getCategoryId())) {
      GenDataEntityCategoryService genDataEntityCategoryService = ApplicationUtil.getBean(
          GenDataEntityCategoryService.class);
      GenDataEntityCategory category = genDataEntityCategoryService.findById(dto.getCategoryId());
      this.categoryName = category.getName();
    }
  }
}

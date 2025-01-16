package com.lframework.xingyun.template.gen.bo.custom.selector;

import com.lframework.starter.common.utils.StringUtil;
import com.lframework.xingyun.template.gen.entity.GenCustomSelector;
import com.lframework.xingyun.template.gen.entity.GenCustomSelectorCategory;
import com.lframework.xingyun.template.gen.service.GenCustomSelectorCategoryService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.utils.ApplicationUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GenCustomSelectorSelectorBo extends BaseBo<GenCustomSelector> {

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

  public GenCustomSelectorSelectorBo() {
  }

  public GenCustomSelectorSelectorBo(GenCustomSelector dto) {
    super(dto);
  }

  @Override
  protected void afterInit(GenCustomSelector dto) {
    if (!StringUtil.isBlank(dto.getCategoryId())) {
      GenCustomSelectorCategoryService genCustomListCategoryService = ApplicationUtil.getBean(
          GenCustomSelectorCategoryService.class);
      GenCustomSelectorCategory category = genCustomListCategoryService
          .findById(dto.getCategoryId());
      this.categoryName = category.getName();
    }
  }
}

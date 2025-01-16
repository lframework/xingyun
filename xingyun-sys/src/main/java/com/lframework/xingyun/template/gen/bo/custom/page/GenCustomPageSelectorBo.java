package com.lframework.xingyun.template.gen.bo.custom.page;

import com.lframework.starter.common.utils.StringUtil;
import com.lframework.xingyun.template.gen.entity.GenCustomPage;
import com.lframework.xingyun.template.gen.entity.GenCustomPageCategory;
import com.lframework.xingyun.template.gen.service.GenCustomPageCategoryService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.utils.ApplicationUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GenCustomPageSelectorBo extends BaseBo<GenCustomPage> {

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

  public GenCustomPageSelectorBo() {
  }

  public GenCustomPageSelectorBo(GenCustomPage dto) {
    super(dto);
  }

  @Override
  protected void afterInit(GenCustomPage dto) {
    if (!StringUtil.isBlank(dto.getCategoryId())) {
      GenCustomPageCategoryService genCustomPageCategoryService = ApplicationUtil.getBean(
          GenCustomPageCategoryService.class);
      GenCustomPageCategory category = genCustomPageCategoryService
          .findById(dto.getCategoryId());
      this.categoryName = category.getName();
    }
  }
}

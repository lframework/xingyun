package com.lframework.xingyun.template.gen.bo.custom.page;

import com.lframework.starter.common.utils.StringUtil;
import com.lframework.xingyun.template.gen.entity.GenCustomPage;
import com.lframework.xingyun.template.gen.entity.GenCustomPageCategory;
import com.lframework.xingyun.template.gen.service.GenCustomPageCategoryService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GetGenCustomPageBo extends BaseBo<GenCustomPage> {

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
   * 分类ID
   */
  @ApiModelProperty("分类ID")
  private String categoryId;

  /**
   * 分类名称
   */
  @ApiModelProperty("分类名称")
  private String categoryName;

  /**
   * 页面代码
   */
  @ApiModelProperty("页面代码")
  private String pageCode;

  /**
   * 脚本代码
   */
  @ApiModelProperty("脚本代码")
  private String scriptCode;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  public GetGenCustomPageBo() {
  }

  public GetGenCustomPageBo(GenCustomPage dto) {
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

package com.lframework.xingyun.template.gen.bo.custom.list;

import com.lframework.starter.common.utils.StringUtil;
import com.lframework.xingyun.template.gen.entity.GenCustomList;
import com.lframework.xingyun.template.gen.entity.GenCustomListCategory;
import com.lframework.xingyun.template.gen.service.GenCustomListCategoryService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GenCustomListSelectorBo extends BaseBo<GenCustomList> {

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
   * 数据对象ID
   */
  @ApiModelProperty("数据对象ID")
  private String dataObjId;

  /**
   * 分类名称
   */
  @ApiModelProperty("分类名称")
  private String categoryName;

  public GenCustomListSelectorBo() {
  }

  public GenCustomListSelectorBo(GenCustomList dto) {
    super(dto);
  }

  @Override
  protected void afterInit(GenCustomList dto) {
    if (!StringUtil.isBlank(dto.getCategoryId())) {
      GenCustomListCategoryService genCustomListCategoryService = ApplicationUtil.getBean(
          GenCustomListCategoryService.class);
      GenCustomListCategory category = genCustomListCategoryService.findById(dto.getCategoryId());
      this.categoryName = category.getName();
    }
  }
}

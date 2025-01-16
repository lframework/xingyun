package com.lframework.xingyun.template.gen.bo.custom.selector;

import com.lframework.starter.common.utils.StringUtil;
import com.lframework.xingyun.template.gen.entity.GenCustomList;
import com.lframework.xingyun.template.gen.entity.GenCustomSelector;
import com.lframework.xingyun.template.gen.entity.GenCustomSelectorCategory;
import com.lframework.xingyun.template.gen.service.GenCustomListService;
import com.lframework.xingyun.template.gen.service.GenCustomSelectorCategoryService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.utils.ApplicationUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GetGenCustomSelectorBo extends BaseBo<GenCustomSelector> {

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
   * 数据对象ID
   */
  @ApiModelProperty("数据对象ID")
  private String dataObjId;

  /**
   * 自定义列表ID
   */
  @ApiModelProperty("自定义列表ID")
  private String customListId;

  /**
   * 自定义列表名称
   */
  @ApiModelProperty("自定义列表名称")
  private String customListName;

  /**
   * 对话框标题
   */
  @ApiModelProperty("对话框标题")
  private String dialogTittle;

  /**
   * 对话框宽度
   */
  @ApiModelProperty("对话框宽度")
  private String dialogWidth;

  /**
   * 占位符
   */
  @ApiModelProperty("占位符")
  private String placeholder;

  /**
   * ID字段
   */
  @ApiModelProperty("ID字段")
  private String idColumn;

  /**
   * 名称字段
   */
  @ApiModelProperty("名称字段")
  private String nameColumn;

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

  public GetGenCustomSelectorBo() {
  }

  public GetGenCustomSelectorBo(GenCustomSelector dto) {
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

    GenCustomListService genCustomListService = ApplicationUtil
        .getBean(GenCustomListService.class);
    GenCustomList customList = genCustomListService.findById(dto.getCustomListId());
    this.customListName = customList.getName();

    this.dataObjId = customList.getDataObjId();
  }
}

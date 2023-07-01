package com.lframework.xingyun.template.gen.bo.data.obj;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.xingyun.template.gen.entity.GenDataObj;
import com.lframework.xingyun.template.gen.entity.GenDataObjCategory;
import com.lframework.xingyun.template.gen.service.GenDataObjCategoryService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class QueryGenDataObjBo extends BaseBo<GenDataObj> {

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
   * 状态
   */
  @ApiModelProperty("状态")
  private Boolean available;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  /**
   * 创建人ID
   */
  @ApiModelProperty("创建人ID")
  private String createBy;

  /**
   * 创建时间
   */
  @ApiModelProperty("创建时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime createTime;

  /**
   * 修改人ID
   */
  @ApiModelProperty("修改人ID")
  private String updateBy;

  /**
   * 修改时间
   */
  @ApiModelProperty("修改时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime updateTime;

  public QueryGenDataObjBo() {
  }

  public QueryGenDataObjBo(GenDataObj dto) {
    super(dto);
  }

  @Override
  protected void afterInit(GenDataObj dto) {

    if (!StringUtil.isBlank(dto.getCategoryId())) {
      GenDataObjCategoryService genDataObjCategoryService = ApplicationUtil.getBean(
          GenDataObjCategoryService.class);
      GenDataObjCategory category = genDataObjCategoryService.findById(dto.getCategoryId());
      this.categoryName = category.getName();
    }
  }
}

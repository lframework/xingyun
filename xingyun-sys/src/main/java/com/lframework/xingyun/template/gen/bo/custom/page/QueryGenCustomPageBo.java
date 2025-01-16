package com.lframework.xingyun.template.gen.bo.custom.page;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.xingyun.template.gen.entity.GenCustomPage;
import com.lframework.xingyun.template.gen.entity.GenCustomPageCategory;
import com.lframework.xingyun.template.gen.service.GenCustomPageCategoryService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.utils.ApplicationUtil;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class QueryGenCustomPageBo extends BaseBo<GenCustomPage> {

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

  public QueryGenCustomPageBo() {
  }

  public QueryGenCustomPageBo(GenCustomPage dto) {
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

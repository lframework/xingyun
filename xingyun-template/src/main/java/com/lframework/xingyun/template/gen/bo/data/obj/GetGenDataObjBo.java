package com.lframework.xingyun.template.gen.bo.data.obj;

import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.xingyun.template.gen.entity.GenDataEntity;
import com.lframework.xingyun.template.gen.entity.GenDataObj;
import com.lframework.xingyun.template.gen.entity.GenDataObjCategory;
import com.lframework.xingyun.template.gen.entity.GenDataObjDetail;
import com.lframework.xingyun.template.gen.entity.GenDataObjQueryDetail;
import com.lframework.xingyun.template.gen.service.GenDataEntityService;
import com.lframework.xingyun.template.gen.service.GenDataObjCategoryService;
import com.lframework.xingyun.template.gen.service.GenDataObjDetailService;
import com.lframework.xingyun.template.gen.service.GenDataObjQueryDetailService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class GetGenDataObjBo extends BaseBo<GenDataObj> {

  private static final long serialVersionUID = 1L;

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
   * 主表ID
   */
  @ApiModelProperty("主表ID")
  private String mainTableId;

  /**
   * 主表名称
   */
  @ApiModelProperty("主表名称")
  private String mainTableName;

  /**
   * 主表别名
   */
  @ApiModelProperty("主表别名")
  private String mainTableAlias;

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
   * 关联字段
   */
  @ApiModelProperty("关联字段")
  private List<GenDataObjDetailBo> columns;

  /**
   * 自定义查询
   */
  @ApiModelProperty("自定义查询")
  private List<GenDataObjQueryDetailBo> queryColumns;

  public GetGenDataObjBo() {

  }

  public GetGenDataObjBo(GenDataObj dto) {

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

    GenDataEntityService genDataEntityService = ApplicationUtil.getBean(
        GenDataEntityService.class);
    GenDataEntity entity = genDataEntityService.findById(dto.getMainTableId());
    this.mainTableName = entity.getName();

    GenDataObjDetailService genDataObjDetailService = ApplicationUtil.getBean(
        GenDataObjDetailService.class);
    List<GenDataObjDetail> details = genDataObjDetailService.getByObjId(dto.getId());
    if (!CollectionUtil.isEmpty(details)) {
      this.columns = details.stream().map(GenDataObjDetailBo::new).collect(Collectors.toList());
    }

    GenDataObjQueryDetailService genDataObjQueryDetailService = ApplicationUtil.getBean(
        GenDataObjQueryDetailService.class);
    List<GenDataObjQueryDetail> queryDetails = genDataObjQueryDetailService.getByObjId(dto.getId());
    if (!CollectionUtil.isEmpty(queryDetails)) {
      this.queryColumns = queryDetails.stream().map(GenDataObjQueryDetailBo::new)
          .collect(Collectors.toList());
    }
  }
}

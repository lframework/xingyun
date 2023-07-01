package com.lframework.xingyun.template.gen.vo.data.obj;

import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateGenDataObjVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @ApiModelProperty(value = "ID", required = true)
  @NotBlank(message = "ID不能为空！")
  private String id;

  /**
   * 名称
   */
  @ApiModelProperty(value = "名称", required = true)
  @NotBlank(message = "名称不能为空！")
  private String name;

  /**
   * 分类ID
   */
  @ApiModelProperty("分类ID")
  private String categoryId;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  /**
   * 主表别名
   */
  @ApiModelProperty(value = "主表别名", required = true)
  @NotBlank(message = "主表别名不能为空！")
  private String mainTableAlias;

  /**
   * 状态
   */
  @ApiModelProperty(value = "状态", required = true)
  @NotNull(message = "状态不能为空！")
  private Boolean available;

  /**
   * 关联信息
   */
  @ApiModelProperty(value = "关联信息")
  @Valid
  private List<GenDataObjDetailVo> columns;

  /**
   * 自定义查询
   */
  @ApiModelProperty(value = "自定义查询")
  @Valid
  private List<GenDataObjQueryDetailVo> queryColumns;
}

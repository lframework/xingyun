package com.lframework.xingyun.template.gen.vo.data.entity;

import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateDataEntityVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

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
   * 数据表
   */
  @ApiModelProperty(value = "数据表", required = true)
  @NotBlank(message = "数据表不能为空！")
  private String tableName;

  /**
   * 字段信息
   */
  @ApiModelProperty(value = "字段信息", required = true)
  @Valid
  @NotEmpty(message = "字段信息不能为空！")
  private List<GenDataEntityDetailVo> columns;
}

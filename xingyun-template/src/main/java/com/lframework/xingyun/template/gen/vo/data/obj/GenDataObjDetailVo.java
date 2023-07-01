package com.lframework.xingyun.template.gen.vo.data.obj;

import com.lframework.xingyun.template.gen.enums.GenRelaMode;
import com.lframework.xingyun.template.gen.enums.GenRelaType;
import com.lframework.starter.web.components.validation.IsEnum;
import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GenDataObjDetailVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @ApiModelProperty(value = "ID", required = true)
  @NotBlank(message = "ID不能为空！")
  private String id;

  /**
   * 主表字段
   */
  @ApiModelProperty(value = "主表字段", required = true)
  @NotEmpty(message = "主表字段不能为空！")
  private List<String> mainTableDetailIds;

  /**
   * 关联类型
   */
  @ApiModelProperty(value = "关联类型", required = true)
  @NotNull(message = "关联类型不能为空！")
  @IsEnum(message = "关联类型不能为空！", enumClass = GenRelaType.class)
  private Integer relaType;

  /**
   * 关联方式
   */
  @ApiModelProperty(value = "关联方式", required = true)
  @NotNull(message = "关联方式不能为空！")
  @IsEnum(message = "关联方式不能为空！", enumClass = GenRelaMode.class)
  private Integer relaMode;

  /**
   * 子表ID
   */
  @ApiModelProperty(value = "子表ID", required = true)
  @NotBlank(message = "子表ID不能为空！")
  private String subTableId;

  /**
   * 子表别名
   */
  @ApiModelProperty(value = "子表别名", required = true)
  @NotBlank(message = "子表别名不能为空！")
  private String subTableAlias;

  /**
   * 子表字段
   */
  @ApiModelProperty(value = "子表字段", required = true)
  @NotEmpty(message = "子表字段不能为空！")
  private List<String> subTableDetailIds;
}

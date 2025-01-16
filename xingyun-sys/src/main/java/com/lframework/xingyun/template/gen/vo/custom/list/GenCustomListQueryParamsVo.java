package com.lframework.xingyun.template.gen.vo.custom.list;

import com.lframework.xingyun.template.gen.enums.GenCustomListDetailType;
import com.lframework.xingyun.template.gen.enums.GenQueryType;
import com.lframework.starter.web.components.validation.IsEnum;
import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GenCustomListQueryParamsVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @ApiModelProperty(value = "ID", required = true)
  @NotBlank(message = "ID不能为空！")
  private String id;

  /**
   * 关联ID
   */
  @ApiModelProperty(value = "关联ID", required = true)
  @NotBlank(message = "关联ID不能为空！")
  private String relaId;

  /**
   * 前端显示
   */
  @ApiModelProperty(value = "前端显示", required = true)
  @NotNull(message = "前端显示不能为空！")
  private Boolean frontShow;

  /**
   * 查询类型
   */
  @ApiModelProperty(value = "查询类型", required = true)
  @NotNull(message = "查询类型不能为空！")
  @IsEnum(message = "查询类型不能为空！", enumClass = GenQueryType.class)
  private Integer queryType;

  /**
   * 表单宽度
   */
  @ApiModelProperty(value = "表单宽度", required = true)
  @NotNull(message = "表单宽度不能为空！")
  @Min(value = 1, message = "表单宽度不允许小于0！")
  @Max(value = 24, message = "表单宽度不允许大于24！")
  private Integer formWidth;

  /**
   * 默认值
   */
  @ApiModelProperty("默认值")
  private Object defaultValue;

  /**
   * 类型
   */
  @ApiModelProperty(value = "类型", required = true)
  @NotNull(message = "类型不能为空！")
  @IsEnum(message = "类型不能为空！", enumClass = GenCustomListDetailType.class)
  private Integer type;
}

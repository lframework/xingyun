package com.lframework.xingyun.template.gen.vo.gen;

import com.lframework.xingyun.template.gen.enums.GenQueryWidthType;
import com.lframework.starter.web.components.validation.IsEnum;
import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateQueryColumnConfigVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @ApiModelProperty(value = "ID", required = true)
  @NotNull(message = "ID不能为空！")
  private String id;

  /**
   * 宽度类型
   */
  @ApiModelProperty(value = "宽度类型", required = true)
  @NotNull(message = "宽度类型不能为空！")
  @IsEnum(message = "宽度类型不能为空！", enumClass = GenQueryWidthType.class)
  private Integer widthType;

  /**
   * 是否页面排序
   */
  @ApiModelProperty(value = "是否页面排序", required = true)
  @NotNull(message = "请选择是否页面排序！")
  private Boolean sortable;

  /**
   * 宽度
   */
  @ApiModelProperty(value = "宽度", required = true)
  @NotNull(message = "宽度不能为空！")
  @Min(value = 1, message = "宽度必须大于0！")
  private Integer width;
}

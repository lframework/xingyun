package com.lframework.xingyun.template.gen.vo.data.obj;

import com.lframework.xingyun.template.gen.enums.GenDataType;
import com.lframework.starter.web.components.validation.IsEnum;
import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GenDataObjQueryDetailVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @ApiModelProperty(value = "ID", required = true)
  @NotBlank(message = "ID不能为空！")
  private String id;

  /**
   * 显示名称
   */
  @ApiModelProperty(value = "显示名称", required = true)
  @NotBlank(message = "显示名称不能为空！")
  private String customName;

  /**
   * 自定义SQL
   */
  @ApiModelProperty(value = "自定义SQL", required = true)
  @NotBlank(message = "自定义SQL不能为空！")
  private String customSql;

  /**
   * 别名
   */
  @ApiModelProperty(value = "别名", required = true)
  @NotBlank(message = "别名不能为空！")
  private String customAlias;

  /**
   * 数据类型
   */
  @ApiModelProperty(value = "数据类型", required = true)
  @NotNull(message = "数据类型不能为空！")
  @IsEnum(message = "数据类型格式不正确！", enumClass = GenDataType.class)
  private Integer dataType;

}

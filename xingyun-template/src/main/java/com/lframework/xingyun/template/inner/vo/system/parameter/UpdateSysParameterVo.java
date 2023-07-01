package com.lframework.xingyun.template.inner.vo.system.parameter;

import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateSysParameterVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @ApiModelProperty(value = "ID", required = true)
  @NotNull(message = "id不能为空！")
  private Long id;

  /**
   * 值
   */
  @ApiModelProperty("值")
  private String pmValue;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

}

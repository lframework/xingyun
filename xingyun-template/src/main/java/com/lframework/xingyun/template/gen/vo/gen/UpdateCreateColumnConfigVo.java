package com.lframework.xingyun.template.gen.vo.gen;

import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateCreateColumnConfigVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @ApiModelProperty(value = "ID", required = true)
  @NotNull(message = "ID不能为空！")
  private String id;

  /**
   * 是否必填
   */
  @ApiModelProperty(value = "是否必填", required = true)
  @NotNull(message = "是否必填不能为空！")
  private Boolean required;
}

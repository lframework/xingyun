package com.lframework.xingyun.template.inner.vo.system.parameter;

import com.lframework.starter.web.components.validation.Pattern;
import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateSysParameterVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 键
   */
  @ApiModelProperty(value = "键", required = true)
  @NotBlank(message = "请输入键！")
  @Pattern(regexp = "^[A-Za-z0-9\\.\\-_]+$", message = "键只能由大写字母、小写字母、数字或._-组成")
  private String pmKey;

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

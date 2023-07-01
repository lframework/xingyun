package com.lframework.xingyun.template.gen.vo.custom.form;

import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GenCustomFormSetConfigVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @ApiModelProperty(value = "ID", required = true)
  @NotBlank(message = "ID不能为空！")
  private String id;

  /**
   * 表单配置
   */
  @ApiModelProperty(value = "表单配置", required = true)
  @NotBlank(message = "表单配置不能为空！")
  private String formConfig;
}

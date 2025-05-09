package com.lframework.xingyun.basedata.vo.print;

import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdatePrintTemplateSettingVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @ApiModelProperty(value = "ID", required = true)
  @NotNull(message = "ID不能为空！")
  private Integer id;

  /**
   * 模板配置
   */
  @ApiModelProperty(value = "模板配置", required = true)
  @NotBlank(message = "模板配置不能为空！")
  private String templateJson;
}

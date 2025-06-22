package com.lframework.xingyun.basedata.vo.print;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdatePrintTemplateDemoDataVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @ApiModelProperty(value = "ID", required = true)
  @NotNull(message = "ID不能为空！")
  private Integer id;

  /**
   * 示例数据
   */
  @ApiModelProperty(value = "示例数据", required = true)
  @NotBlank(message = "示例数据不能为空！")
  private String demoData;
}

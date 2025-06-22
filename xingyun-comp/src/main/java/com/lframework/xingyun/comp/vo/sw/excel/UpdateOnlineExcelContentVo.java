package com.lframework.xingyun.comp.vo.sw.excel;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateOnlineExcelContentVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @ApiModelProperty(value = "ID", required = true)
  @NotBlank(message = "id不能为空！")
  private String id;

  /**
   * 内容
   */
  @ApiModelProperty(value = "内容", required = true)
  @NotBlank(message = "内容不能为空！")
  private String content;
}

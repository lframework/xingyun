package com.lframework.xingyun.core.vo.sw.excel;

import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateOnlineExcelVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 名称
   */
  @ApiModelProperty(value = "名称", required = true)
  @NotBlank(message = "请输入名称！")
  private String name;

  /**
   * 内容
   */
  @ApiModelProperty(value = "内容", required = true)
  @NotBlank(message = "请输入内容！")
  private String content;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

}

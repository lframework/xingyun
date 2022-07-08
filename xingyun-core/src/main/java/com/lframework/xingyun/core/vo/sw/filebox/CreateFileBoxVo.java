package com.lframework.xingyun.core.vo.sw.filebox;

import com.lframework.starter.web.components.validation.UploadUrl;
import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateFileBoxVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 名称
   */
  @ApiModelProperty(value = "名称", required = true)
  @NotBlank(message = "请输入名称！")
  private String name;

  /**
   * Url
   */
  @ApiModelProperty("Url")
  @NotBlank(message = "Url不能为空！")
  @UploadUrl(message = "Url格式有误！")
  private String url;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

}

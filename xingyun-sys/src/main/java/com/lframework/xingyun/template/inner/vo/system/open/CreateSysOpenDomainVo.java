package com.lframework.xingyun.template.inner.vo.system.open;

import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateSysOpenDomainVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 名称
   */
  @ApiModelProperty(value = "名称", required = true)
  @NotBlank(message = "名称不能为空！")
  private String name;

  /**
   * API密钥
   */
  @ApiModelProperty(value = "API密钥", required = true)
  @NotBlank(message = "API密钥不能为空！")
  private String apiSecret;

  /**
   * 租户ID
   */
  @ApiModelProperty("租户ID")
  private Integer tenantId;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;
}

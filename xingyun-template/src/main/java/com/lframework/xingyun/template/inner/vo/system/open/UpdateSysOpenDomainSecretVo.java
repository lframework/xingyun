package com.lframework.xingyun.template.inner.vo.system.open;

import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateSysOpenDomainSecretVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @ApiModelProperty(value = "ID", required = true)
  @NotNull(message = "ID不能为空！")
  private Long id;

  /**
   * Api密钥
   */
  @ApiModelProperty(value = "Api密钥", required = true)
  @NotBlank(message = "Api密钥不能为空！")
  private String apiSecret;
}

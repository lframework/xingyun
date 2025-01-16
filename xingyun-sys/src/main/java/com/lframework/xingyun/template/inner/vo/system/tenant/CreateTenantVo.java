package com.lframework.xingyun.template.inner.vo.system.tenant;

import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateTenantVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 名称
   */
  @ApiModelProperty(value = "名称", required = true)
  @NotBlank(message = "请输入名称！")
  private String name;

  /**
   * JdbcUrl
   */
  @ApiModelProperty(value = "JdbcUrl", required = true)
  @NotBlank(message = "请输入JdbcUrl！")
  private String jdbcUrl;

  /**
   * Jdbc用户名
   */
  @ApiModelProperty(value = "Jdbc用户名", required = true)
  @NotBlank(message = "请输入Jdbc用户名！")
  private String jdbcUsername;

  /**
   * Jdbc密码
   */
  @ApiModelProperty(value = "Jdbc密码", required = true)
  @NotBlank(message = "请输入Jdbc密码！")
  private String jdbcPassword;
}

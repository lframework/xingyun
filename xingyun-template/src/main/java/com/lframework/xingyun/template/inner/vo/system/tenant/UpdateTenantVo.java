package com.lframework.xingyun.template.inner.vo.system.tenant;

import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateTenantVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @ApiModelProperty(value = "ID", required = true)
  @NotNull(message = "ID不能为空！")
  private Integer id;

  /**
   * 名称
   */
  @ApiModelProperty(value = "名称", required = true)
  @NotBlank(message = "请输入名称！")
  private String name;

  /**
   * JdbcUrl
   */
  @ApiModelProperty("JdbcUrl")
  private String jdbcUrl;

  /**
   * Jdbc用户名
   */
  @ApiModelProperty("Jdbc用户名")
  private String jdbcUsername;

  /**
   * Jdbc密码
   */
  @ApiModelProperty("Jdbc密码")
  private String jdbcPassword;

  /**
   * 状态
   */
  @ApiModelProperty(value = "状态", required = true)
  @NotNull(message = "请选择状态！")
  private Boolean available;
}

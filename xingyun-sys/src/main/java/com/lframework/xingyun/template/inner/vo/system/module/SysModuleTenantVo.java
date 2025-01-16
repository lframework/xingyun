package com.lframework.xingyun.template.inner.vo.system.module;

import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SysModuleTenantVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  @Data
  public static class SysModuleVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 模块ID
     */
    @ApiModelProperty(value = "模块ID", required = true)
    @NotNull(message = "模块ID不能为空！")
    private Integer moduleId;

    /**
     * 过期时间
     */
    @ApiModelProperty(value = "过期时间", required = true)
    @NotNull(message = "过期时间不能为空！")
    private LocalDateTime expireTime;
  }

  /**
   * 租户ID
   */
  @ApiModelProperty(value = "租户ID", required = true)
  @NotNull(message = "租户ID不能为空！")
  private Integer tenantId;

  /**
   * 模块
   */
  @ApiModelProperty("模块")
  @Valid
  private List<SysModuleVo> modules;
}

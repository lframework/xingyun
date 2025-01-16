package com.lframework.xingyun.template.inner.bo.system.module;

import com.lframework.xingyun.template.inner.entity.SysModule;
import com.lframework.starter.web.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class QuerySysModuleBo extends BaseBo<SysModule> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private Integer id;

  /**
   * 名称
   */
  @ApiModelProperty("名称")
  private String name;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  /**
   * 是否启用
   */
  @ApiModelProperty("是否启用")
  private Boolean enabled;

  /**
   * 过期时间
   */
  @ApiModelProperty("过期时间")
  private LocalDateTime expireTime;

  public QuerySysModuleBo(SysModule dto) {
    super(dto);
  }
}

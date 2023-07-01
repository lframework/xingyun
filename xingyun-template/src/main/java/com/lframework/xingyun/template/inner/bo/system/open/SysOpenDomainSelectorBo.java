package com.lframework.xingyun.template.inner.bo.system.open;

import com.lframework.xingyun.template.inner.entity.SysOpenDomain;
import com.lframework.starter.web.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SysOpenDomainSelectorBo extends BaseBo<SysOpenDomain> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 名称
   */
  @ApiModelProperty("名称")
  private String name;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  private Boolean available;

  public SysOpenDomainSelectorBo() {
  }

  public SysOpenDomainSelectorBo(SysOpenDomain dto) {
    super(dto);
  }
}

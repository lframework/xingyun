package com.lframework.xingyun.template.inner.bo.system.notify;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.template.inner.entity.SysNotifyGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SysNotifyGroupSelectorBo extends BaseBo<SysNotifyGroup> {

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

  public SysNotifyGroupSelectorBo() {

  }

  public SysNotifyGroupSelectorBo(SysNotifyGroup dto) {

    super(dto);
  }
}

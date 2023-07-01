package com.lframework.xingyun.template.inner.bo.system.position;

import com.lframework.xingyun.template.inner.entity.SysPosition;
import com.lframework.starter.web.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GetSysPositionBo extends BaseBo<SysPosition> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 岗位编号
   */
  @ApiModelProperty("岗位编号")
  private String code;

  /**
   * 岗位名称
   */
  @ApiModelProperty("岗位名称")
  private String name;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  private Boolean available;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  public GetSysPositionBo() {

  }

  public GetSysPositionBo(SysPosition dto) {

    super(dto);
  }

  @Override
  protected void afterInit(SysPosition dto) {

  }
}

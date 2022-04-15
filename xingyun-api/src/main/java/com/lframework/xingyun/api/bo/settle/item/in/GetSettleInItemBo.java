package com.lframework.xingyun.api.bo.settle.item.in;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.settle.dto.item.in.SettleInItemDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetSettleInItemBo extends BaseBo<SettleInItemDto> {

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

  public GetSettleInItemBo() {

  }

  public GetSettleInItemBo(SettleInItemDto dto) {

    super(dto);
  }
}

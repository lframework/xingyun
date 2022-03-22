package com.lframework.xingyun.api.bo.settle.item.in;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.settle.dto.item.in.SettleInItemDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SettleInItemSelectorBo extends BaseBo<SettleInItemDto> {

  /**
   * ID
   */
  private String id;

  /**
   * 岗位编号
   */
  private String code;

  /**
   * 岗位名称
   */
  private String name;

  /**
   * 状态
   */
  private Boolean available;

  public SettleInItemSelectorBo() {

  }

  public SettleInItemSelectorBo(SettleInItemDto dto) {

    super(dto);
  }
}

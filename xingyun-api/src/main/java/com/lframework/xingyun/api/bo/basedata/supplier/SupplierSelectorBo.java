package com.lframework.xingyun.api.bo.basedata.supplier;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.basedata.dto.supplier.SupplierDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SupplierSelectorBo extends BaseBo<SupplierDto> {

  /**
   * ID
   */
  private String id;

  /**
   * 编号
   */
  private String code;

  /**
   * 名称
   */
  private String name;

  /**
   * 状态
   */
  private Boolean available;

  public SupplierSelectorBo() {

  }

  public SupplierSelectorBo(SupplierDto dto) {

    super(dto);
  }
}

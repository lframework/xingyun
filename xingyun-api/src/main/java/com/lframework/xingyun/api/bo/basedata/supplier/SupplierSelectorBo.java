package com.lframework.xingyun.api.bo.basedata.supplier;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.basedata.dto.supplier.SupplierDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SupplierSelectorBo extends BaseBo<SupplierDto> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 编号
   */
  @ApiModelProperty("编号")
  private String code;

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

  public SupplierSelectorBo() {

  }

  public SupplierSelectorBo(SupplierDto dto) {

    super(dto);
  }
}

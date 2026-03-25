package com.lframework.xingyun.basedata.bo.stockcell;

import com.lframework.starter.web.core.annotations.convert.EnumConvert;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.xingyun.basedata.dto.stockcell.StockCellDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GetStockCellBo extends BaseBo<StockCellDto> {

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
   * 仓位类别
   */
  @ApiModelProperty("仓位类别")
  @EnumConvert
  private Integer cellType;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  public GetStockCellBo() {

  }

  public GetStockCellBo(StockCellDto dto) {

    super(dto);
  }
}

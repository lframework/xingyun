package com.lframework.xingyun.basedata.bo.stockcell;

import com.lframework.starter.web.core.annotations.convert.EnumConvert;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.xingyun.basedata.dto.stockcell.StockCellDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class StockCellSelectorBo extends BaseBo<StockCellDto> {

  /**
   * ID
   */
  @Schema(description = "ID")
  private String id;

  /**
   * 编号
   */
  @Schema(description = "编号")
  private String code;

  /**
   * 名称
   */
  @Schema(description = "名称")
  private String name;

  /**
   * 仓库编号
   */
  @Schema(description = "仓库编号")
  private String scCode;

  /**
   * 仓库名称
   */
  @Schema(description = "仓库名称")
  private String scName;

  /**
   * 仓位类别
   */
  @Schema(description = "仓位类别")
  @EnumConvert
  private Integer cellType;

  public StockCellSelectorBo() {

  }

  public StockCellSelectorBo(StockCellDto dto) {

    super(dto);
  }
}

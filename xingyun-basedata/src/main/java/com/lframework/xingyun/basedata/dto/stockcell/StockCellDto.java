package com.lframework.xingyun.basedata.dto.stockcell;

import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.xingyun.basedata.enums.StockCellType;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class StockCellDto implements BaseDto, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 仓库ID
   */
  private String scId;

  /**
   * 仓库编号
   */
  private String scCode;

  /**
   * 仓库名称
   */
  private String scName;

  /**
   * 编号
   */
  private String code;

  /**
   * 名称
   */
  private String name;

  /**
   * 仓位类别
   */
  private StockCellType cellType;

  /**
   * 状态
   */
  private Boolean available;

  /**
   * 备注
   */
  private String description;

  /**
   * 创建人ID
   */
  private String createById;

  /**
   * 创建人
   */
  private String createBy;

  /**
   * 创建时间
   */
  private LocalDateTime createTime;

  /**
   * 修改人
   */
  private String updateBy;

  /**
   * 修改人ID
   */
  private String updateById;

  /**
   * 修改时间
   */
  private LocalDateTime updateTime;
}

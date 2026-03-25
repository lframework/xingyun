package com.lframework.xingyun.basedata.vo.stockcell;

import com.lframework.starter.web.core.vo.BaseVo;
import com.lframework.starter.web.core.vo.SortPageVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class QueryStockCellVo extends SortPageVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 仓库ID
   */
  @ApiModelProperty(value = "仓库ID")
  private String scId;

  /**
   * 仓库编号
   */
  @ApiModelProperty(value = "仓库编号")
  private String scCode;

  /**
   * 仓库名称
   */
  @ApiModelProperty(value = "仓库名称")
  private String scName;

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
  private Integer cellType;
}

package com.lframework.xingyun.basedata.vo.stockcell.product;

import com.lframework.starter.web.core.vo.BaseVo;
import com.lframework.starter.web.core.vo.SortPageVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@Data
public class QueryStockCellProductVo extends SortPageVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 仓位ID
   */
  @ApiModelProperty("仓位ID")
  private String stockCellId;

  /**
   * 仓位编号
   */
  @ApiModelProperty("仓位编号")
  private String stockCellCode;

  /**
   * 仓位名称
   */
  @ApiModelProperty("仓位名称")
  private String stockCellName;

  /**
   * 仓位类别
   */
  @ApiModelProperty("仓位类别")
  private Integer stockCellType;

  /**
   * 仓库编号
   */
  @ApiModelProperty("仓库编号")
  private String scCode;

  /**
   * 仓库名称
   */
  @ApiModelProperty("仓库名称")
  private String scName;

  /**
   * 商品编号
   */
  @ApiModelProperty("商品编号")
  private String productCode;

  /**
   * 商品名称
   */
  @ApiModelProperty("商品名称")
  private String productName;

  /**
   * 商品分类ID
   */
  @ApiModelProperty("商品分类ID")
  private String categoryId;

  /**
   * 商品品牌ID
   */
  @ApiModelProperty("商品品牌ID")
  private String brandId;
}

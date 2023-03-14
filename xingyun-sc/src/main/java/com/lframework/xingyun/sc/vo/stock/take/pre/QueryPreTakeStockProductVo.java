package com.lframework.xingyun.sc.vo.stock.take.pre;

import com.lframework.starter.web.vo.PageVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryPreTakeStockProductVo extends PageVo {

  private static final long serialVersionUID = 1L;

  /**
   * 检索关键字
   */
  @ApiModelProperty("检索关键字")
  private String condition;

  /**
   * 类目ID
   */
  @ApiModelProperty("类目ID")
  private String categoryId;

  /**
   * 品牌ID
   */
  @ApiModelProperty("品牌ID")
  private String brandId;
}

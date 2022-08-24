package com.lframework.xingyun.basedata.facade.vo.product.info;

import com.lframework.starter.web.vo.PageVo;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class QueryTakeStockSheetProductVo extends PageVo {

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

  /**
   * 盘点任务ID
   */
  @ApiModelProperty(value = "盘点任务ID", required = true)
  @NotBlank(message = "盘点任务ID不能为空！")
  private String planId;
}

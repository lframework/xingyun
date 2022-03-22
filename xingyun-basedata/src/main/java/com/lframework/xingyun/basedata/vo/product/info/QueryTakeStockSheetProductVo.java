package com.lframework.xingyun.basedata.vo.product.info;

import com.lframework.starter.web.vo.PageVo;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class QueryTakeStockSheetProductVo extends PageVo {

  private static final long serialVersionUID = 1L;

  /**
   * 检索关键字
   */
  private String condition;

  /**
   * 类目ID
   */
  private String categoryId;

  /**
   * 品牌ID
   */
  private String brandId;

  /**
   * 盘点任务ID
   */
  @NotBlank(message = "盘点任务ID不能为空！")
  private String planId;
}

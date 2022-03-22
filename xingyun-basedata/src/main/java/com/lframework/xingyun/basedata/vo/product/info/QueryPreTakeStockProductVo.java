package com.lframework.xingyun.basedata.vo.product.info;

import com.lframework.starter.web.vo.PageVo;
import lombok.Data;

@Data
public class QueryPreTakeStockProductVo extends PageVo {

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
}

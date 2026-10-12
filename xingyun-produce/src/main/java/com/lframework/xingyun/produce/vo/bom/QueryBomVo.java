package com.lframework.xingyun.produce.vo.bom;

import com.lframework.starter.web.core.vo.BaseVo;
import com.lframework.starter.web.core.vo.SortPageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.Data;

@Data
public class QueryBomVo extends SortPageVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * BOM编号
   */
  @Schema(description = "BOM编号")
  private String code;

  /**
   * BOM名称
   */
  @Schema(description = "BOM名称")
  private String name;

  /**
   * 成品商品编号/名称（模糊）
   */
  @Schema(description = "成品商品")
  private String mainProduct;

  /**
   * 状态
   */
  @Schema(description = "状态 0-草稿 1-启用 2-停用")
  private Integer status;
}

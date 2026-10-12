package com.lframework.xingyun.produce.vo.assemble;

import com.lframework.starter.web.core.vo.SortPageVo;
import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 查询组装单VO
 *
 * @author zmj
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QueryAssembleSheetVo extends SortPageVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 单据号
   */
  @Schema(description = "单据号")
  private String code;

  /**
   * 仓库ID
   */
  @Schema(description = "仓库ID")
  private String scId;

  /**
   * 成品商品编号/名称（模糊）
   */
  @Schema(description = "成品商品")
  private String product;

  /**
   * 状态
   */
  @Schema(description = "状态 0-待审核 3-审核通过 6-审核拒绝")
  private Integer status;

  /**
   * 创建时间 起
   */
  @Schema(description = "创建时间 起")
  private String createTimeStart;

  /**
   * 创建时间 止
   */
  @Schema(description = "创建时间 止")
  private String createTimeEnd;
}

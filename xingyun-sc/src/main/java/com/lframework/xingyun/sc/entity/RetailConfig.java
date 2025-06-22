package com.lframework.xingyun.sc.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.starter.web.core.entity.BaseEntity;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author zmj
 * @since 2021-09-12
 */
@Data
@TableName("tbl_retail_config")
public class RetailConfig extends BaseEntity implements BaseDto {

  public static final String CACHE_NAME = "RetailConfig";
  private static final long serialVersionUID = 1L;
  /**
   * ID
   */
  private String id;

  /**
   * 零售出库单上的会员是否必填
   */
  private Boolean retailOutSheetRequireMember;

  /**
   * 零售退货单是否关联零售出库单
   */
  private Boolean retailReturnRequireOutStock;

  /**
   * 零售退货单是否多次关联零售出库单
   */
  private Boolean retailReturnMultipleRelateOutStock;

  /**
   * 零售退货单上的会员是否必填
   */
  private Boolean retailReturnRequireMember;

  /**
   * 零售出库单是否需要发货
   */
  private Boolean retailOutSheetRequireLogistics;
}

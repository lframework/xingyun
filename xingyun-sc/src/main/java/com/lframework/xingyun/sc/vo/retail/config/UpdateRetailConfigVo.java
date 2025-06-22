package com.lframework.xingyun.sc.vo.retail.config;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateRetailConfigVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 零售出库单上的会员是否必填
   */
  @ApiModelProperty(value = "零售出库单上的会员是否必填", required = true)
  @NotNull(message = "零售出库单上的会员是否必填不能为空！")
  private Boolean retailOutSheetRequireMember;

  /**
   * 零售退货单是否关联零售出库单
   */
  @ApiModelProperty(value = "零售退货单是否关联零售出库单", required = true)
  @NotNull(message = "零售退货单是否关联零售出库单不能为空！")
  private Boolean retailReturnRequireOutStock;

  /**
   * 零售退货单是否多次关联零售出库单
   */
  @ApiModelProperty(value = "零售退货单是否多次关联零售出库单", required = true)
  @NotNull(message = "零售退货单是否多次关联零售出库单不能为空！")
  private Boolean retailReturnMultipleRelateOutStock;

  /**
   * 零售退货单上的会员是否必填
   */
  @ApiModelProperty(value = "零售退货单上的会员是否必填", required = true)
  @NotNull(message = "零售退货单上的会员是否必填不能为空！")
  private Boolean retailReturnRequireMember;

  /**
   * 零售出库单是否需要发货
   */
  @ApiModelProperty(value = "零售出库单是否需要发货", required = true)
  @NotNull(message = "零售出库单是否需要发货不能为空！")
  private Boolean retailOutSheetRequireLogistics;
}

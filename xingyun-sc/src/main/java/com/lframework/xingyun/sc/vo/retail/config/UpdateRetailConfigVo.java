package com.lframework.xingyun.sc.vo.retail.config;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateRetailConfigVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 零售出库单上的会员是否必填
   */
  @Schema(description = "零售出库单上的会员是否必填", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "零售出库单上的会员是否必填不能为空！")
  private Boolean retailOutSheetRequireMember;

  /**
   * 零售退货单是否关联零售出库单
   */
  @Schema(description = "零售退货单是否关联零售出库单", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "零售退货单是否关联零售出库单不能为空！")
  private Boolean retailReturnRequireOutStock;

  /**
   * 零售退货单是否多次关联零售出库单
   */
  @Schema(description = "零售退货单是否多次关联零售出库单", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "零售退货单是否多次关联零售出库单不能为空！")
  private Boolean retailReturnMultipleRelateOutStock;

  /**
   * 零售退货单上的会员是否必填
   */
  @Schema(description = "零售退货单上的会员是否必填", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "零售退货单上的会员是否必填不能为空！")
  private Boolean retailReturnRequireMember;

  /**
   * 零售出库单是否需要发货
   */
  @Schema(description = "零售出库单是否需要发货", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "零售出库单是否需要发货不能为空！")
  private Boolean retailOutSheetRequireLogistics;
}

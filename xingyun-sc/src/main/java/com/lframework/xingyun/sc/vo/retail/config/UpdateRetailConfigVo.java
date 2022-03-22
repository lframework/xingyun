package com.lframework.xingyun.sc.vo.retail.config;

import com.lframework.starter.web.vo.BaseVo;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateRetailConfigVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 零售退货单是否关联零售出库单
   */
  @NotNull(message = "零售退货单是否关联零售出库单不能为空！")
  private Boolean retailReturnRequireOutStock;

  /**
   * 零售退货单是否多次关联零售出库单
   */
  @NotNull(message = "零售退货单是否多次关联零售出库单不能为空！")
  private Boolean retailReturnMultipleRelateOutStock;
}

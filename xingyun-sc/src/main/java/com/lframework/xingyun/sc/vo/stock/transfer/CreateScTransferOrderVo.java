package com.lframework.xingyun.sc.vo.stock.transfer;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.NumberUtil;
import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateScTransferOrderVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 转出仓库ID
   */
  @Schema(description = "转出仓库ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "转出仓库ID不能为空！")
  private String sourceScId;

  /**
   * 转入仓库ID
   */
  @Schema(description = "转入仓库ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "转入仓库ID不能为空！")
  private String targetScId;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;

  /**
   * 商品信息
   */
  @Schema(description = "商品信息", requiredMode = Schema.RequiredMode.REQUIRED)
  @Valid
  @NotEmpty(message = "请录入商品！")
  private List<ScTransferProductVo> products;

  public void validate() {

    int orderNo = 1;
    for (ScTransferProductVo product : this.products) {
      if (NumberUtil.le(product.getTransferNum(), BigDecimal.ZERO)) {
        throw new DefaultClientException("第" + orderNo + "行商品的调拨数量必须大于0！");
      }

      if (!NumberUtil.isNumberPrecision(product.getTransferNum(), 8)) {
        throw new DefaultClientException("第" + orderNo + "行商品的调拨数量最多允许8位小数！");
      }
    }
  }
}

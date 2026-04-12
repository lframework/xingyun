package com.lframework.xingyun.sc.vo.stock.transfer;

import com.lframework.starter.web.core.components.validation.TypeMismatch;
import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReceiveScTransferOrderVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "id不能为空！")
  private String id;

  /**
   * 商品信息
   */
  @Schema(description = "收货商品", requiredMode = Schema.RequiredMode.REQUIRED)
  @Valid
  @NotEmpty(message = "收货商品不能为空！")
  private List<ReceiveScTransferProductVo> products;

  @Data
  public static class ReceiveScTransferProductVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品ID
     */
    @Schema(description = "商品ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "商品ID不能为空！")
    private String productId;

    /**
     * 收货数量
     */
    @Schema(description = "收货数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "收货数量不能为空！")
    @TypeMismatch(message = "收货数量格式有误！")
    private BigDecimal receiveNum;
  }
}

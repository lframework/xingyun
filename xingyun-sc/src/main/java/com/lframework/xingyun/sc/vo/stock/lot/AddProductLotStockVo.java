package com.lframework.xingyun.sc.vo.stock.lot;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddProductLotStockVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 仓库ID
   */
  @Schema(description = "仓库ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "仓库ID不能为空！")
  private String scId;

  /**
   * 入库数量
   */
  @Schema(description = "入库数量", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "入库数量不能为空！")
  @Min(message = "入库数量必须大于0！", value = 1)
  private Integer stockNum;
}

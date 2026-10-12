package com.lframework.xingyun.produce.vo.assemble;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

/**
 * 新增组装单VO
 *
 * @author zmj
 */
@Data
public class CreateAssembleSheetVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 仓库ID
   */
  @Schema(description = "仓库ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "请选择仓库！")
  private String scId;

  /**
   * BOM ID
   */
  @Schema(description = "BOM ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "请选择BOM！")
  private String bomId;

  /**
   * 组装数量（成品数量）
   */
  @Schema(description = "组装数量", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "组装数量不能为空！")
  private BigDecimal totalNum;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;

  /**
   * 子件明细
   */
  @Schema(description = "子件明细", requiredMode = Schema.RequiredMode.REQUIRED)
  @Valid
  private List<AssembleProductVo> products;

  public void validate() {

    if (this.totalNum == null || this.totalNum.compareTo(BigDecimal.ZERO) <= 0) {
      throw new DefaultClientException("组装数量必须大于0！");
    }
    if (CollectionUtil.isEmpty(this.products)) {
      throw new DefaultClientException("子件明细不能为空！");
    }
    for (AssembleProductVo product : this.products) {
      if (product.getStockNum() == null || product.getStockNum().compareTo(BigDecimal.ZERO) <= 0) {
        throw new DefaultClientException("子件消耗数量必须大于0！");
      }
    }
  }
}

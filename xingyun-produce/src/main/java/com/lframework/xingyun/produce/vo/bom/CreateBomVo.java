package com.lframework.xingyun.produce.vo.bom;

import com.lframework.starter.common.exceptions.impl.InputErrorException;
import com.lframework.starter.common.utils.NumberUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.components.validation.IsCode;
import com.lframework.starter.web.core.components.validation.TypeMismatch;
import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateBomVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * BOM编号
   */
  @Schema(description = "BOM编号", requiredMode = Schema.RequiredMode.REQUIRED)
  @IsCode
  @NotBlank(message = "请输入BOM编号！")
  private String code;

  /**
   * BOM名称
   */
  @Schema(description = "BOM名称", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "请输入BOM名称！")
  private String name;

  /**
   * 成品/半成品商品ID
   */
  @Schema(description = "成品商品ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "请选择成品商品！")
  private String mainProductId;

  /**
   * 成品SKU ID
   */
  @Schema(description = "成品SKU ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "请选择成品SKU！")
  private String mainSkuId;

  /**
   * 产出数量
   */
  @Schema(description = "产出数量", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "产出数量不能为空！")
  @TypeMismatch(message = "产出数量格式有误！")
  private BigDecimal outputNum;

  /**
   * 废品率%
   */
  @Schema(description = "废品率%")
  @TypeMismatch(message = "废品率格式有误！")
  private BigDecimal scrapRate;

  /**
   * 是否默认版本
   */
  @Schema(description = "是否默认版本")
  private Boolean isDefault;

  /**
   * 版本
   */
  @Schema(description = "版本")
  private String version;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;

  /**
   * BOM明细
   */
  @Schema(description = "BOM明细", requiredMode = Schema.RequiredMode.REQUIRED)
  @Valid
  @NotEmpty(message = "请录入BOM明细！")
  private List<BomDetailVo> details;

  public void validate() {

    if (NumberUtil.le(this.outputNum, BigDecimal.ZERO)) {
      throw new InputErrorException("产出数量必须大于0！");
    }

    if (this.scrapRate != null && NumberUtil.lt(this.scrapRate, BigDecimal.ZERO)) {
      throw new InputErrorException("废品率不允许小于0！");
    }

    // 校验子件不重复、子件不能等于成品
    Set<String> skuIds = this.details.stream().map(BomDetailVo::getSkuId)
        .collect(Collectors.toSet());
    if (skuIds.size() != this.details.size()) {
      throw new InputErrorException("BOM明细中存在重复商品！");
    }
    if (skuIds.contains(this.mainSkuId)) {
      throw new InputErrorException("成品商品不能作为子件！");
    }

    int orderNo = 1;
    for (BomDetailVo detail : this.details) {
      if (detail.getUnitNum() == null) {
        throw new InputErrorException("第" + orderNo + "行单位用量不能为空！");
      }
      if (NumberUtil.le(detail.getUnitNum(), BigDecimal.ZERO)) {
        throw new InputErrorException("第" + orderNo + "行单位用量必须大于0！");
      }
      if (!NumberUtil.isNumberPrecision(detail.getUnitNum(), 6)) {
        throw new InputErrorException("第" + orderNo + "行单位用量最多允许6位小数！");
      }
      if (detail.getScrapRate() != null && NumberUtil.lt(detail.getScrapRate(), BigDecimal.ZERO)) {
        throw new InputErrorException("第" + orderNo + "行损耗率不允许小于0！");
      }
      orderNo++;
    }

    if (StringUtil.isBlank(this.version)) {
      this.version = "1.0";
    }
    if (this.isDefault == null) {
      this.isDefault = Boolean.FALSE;
    }
  }
}

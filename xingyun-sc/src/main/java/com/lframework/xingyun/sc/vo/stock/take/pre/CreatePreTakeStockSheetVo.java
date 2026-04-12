package com.lframework.xingyun.sc.vo.stock.take.pre;

import com.lframework.starter.common.exceptions.impl.InputErrorException;
import com.lframework.starter.common.utils.NumberUtil;
import com.lframework.starter.web.core.components.validation.IsEnum;
import com.lframework.starter.web.core.components.validation.TypeMismatch;
import com.lframework.starter.web.core.utils.EnumUtil;
import com.lframework.starter.web.core.vo.BaseVo;
import com.lframework.xingyun.sc.enums.PreTakeStockSheetStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreatePreTakeStockSheetVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 仓库ID
   */
  @Schema(description = "仓库ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "请选择仓库！")
  private String scId;

  /**
   * 预先盘点状态
   */
  @Schema(description = "预先盘点状态", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "请选择预先盘点状态！")
  @IsEnum(message = "预先盘点状态格式错误！", enumClass = PreTakeStockSheetStatus.class)
  @TypeMismatch(message = "预先盘点状态格式错误！")
  private Integer takeStatus;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;

  /**
   * 商品
   */
  @Schema(description = "商品", requiredMode = Schema.RequiredMode.REQUIRED)
  @Valid
  @NotEmpty(message = "请录入商品！")
  private List<PreTakeStockProductVo> products;

  public void validate() {

    Set<String> checkSet = new HashSet<>();
    PreTakeStockSheetStatus takeStatus = EnumUtil.getByCode(PreTakeStockSheetStatus.class,
        this.getTakeStatus());
    for (int i = 0; i < this.getProducts().size(); i++) {
      PreTakeStockProductVo product = this.getProducts().get(i);
      if (checkSet.contains(product.getProductId())) {
        throw new InputErrorException("第" + (i + 1) + "行商品已存在列表中，请勿重复添加！");
      }

      checkSet.add(product.getProductId());

      if (takeStatus == PreTakeStockSheetStatus.FIRST_TAKE) {
        if (product.getFirstNum() == null) {
          throw new InputErrorException("第" + (i + 1) + "行商品的初盘数量不允许为空！");
        }

        if (NumberUtil.lt(product.getFirstNum(), 0)) {
          throw new InputErrorException("第" + (i + 1) + "行商品的初盘数量不允许小于0！");
        }

        if (!NumberUtil.isNumberPrecision(product.getFirstNum(), 8)) {
          throw new InputErrorException("第" + (i + 1) + "行商品的初盘数量最多允许8位小数！");
        }
      } else if (takeStatus == PreTakeStockSheetStatus.SECOND_TAKE) {
        if (product.getSecondNum() == null) {
          throw new InputErrorException("第" + (i + 1) + "行商品的复盘数量不允许为空！");
        }

        if (NumberUtil.lt(product.getSecondNum(), 0)) {
          throw new InputErrorException("第" + (i + 1) + "行商品的复盘数量不允许小于0！");
        }

        if (!NumberUtil.isNumberPrecision(product.getSecondNum(), 8)) {
          throw new InputErrorException("第" + (i + 1) + "行商品的复盘数量最多允许8位小数！");
        }
      } else if (takeStatus == PreTakeStockSheetStatus.RAND_TAKE) {
        if (product.getRandNum() == null) {
          throw new InputErrorException("第" + (i + 1) + "行商品的抽盘数量不允许为空！");
        }

        if (NumberUtil.lt(product.getRandNum(), 0)) {
          throw new InputErrorException("第" + (i + 1) + "行商品的抽盘数量不允许小于0！");
        }

        if (!NumberUtil.isNumberPrecision(product.getRandNum(), 8)) {
          throw new InputErrorException("第" + (i + 1) + "行商品的抽盘数量最多允许8位小数！");
        }
      } else {
        throw new InputErrorException("预先盘点状态格式错误！");
      }
    }
  }
}

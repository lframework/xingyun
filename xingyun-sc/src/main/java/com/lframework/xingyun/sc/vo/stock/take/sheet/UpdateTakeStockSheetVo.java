package com.lframework.xingyun.sc.vo.stock.take.sheet;

import com.lframework.starter.common.exceptions.impl.InputErrorException;
import com.lframework.starter.common.utils.NumberUtil;
import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UpdateTakeStockSheetVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @ApiModelProperty(value = "ID", required = true)
  @NotBlank(message = "id不能为空！")
  private String id;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  /**
   * 商品信息
   */
  @ApiModelProperty(value = "商品信息", required = true)
  @Valid
  @NotEmpty(message = "请录入商品！")
  private List<TakeStockSheetProductVo> products;

  public void validate() {

    int orderNo = 1;
    for (TakeStockSheetProductVo product : this.products) {
      if (product.getTakeNum() == null) {
        throw new InputErrorException("第" + orderNo + "行商品盘点数量不能为空！");
      }

      if (NumberUtil.le(product.getTakeNum(), 0)) {
        throw new InputErrorException("第" + orderNo + "行商品盘点数量必须大于0！");
      }

      if (!NumberUtil.isNumberPrecision(product.getTakeNum(), 8)) {
        throw new InputErrorException("第" + orderNo + "行商品盘点数量最多允许8位小数！");
      }
      orderNo++;
    }
  }
}

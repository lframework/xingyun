package com.lframework.xingyun.sc.vo.stock.take.sheet;

import com.lframework.starter.web.components.validation.TypeMismatch;
import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TakeStockSheetProductVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 商品ID
   */
  @ApiModelProperty(value = "商品ID", required = true)
  @NotBlank(message = "商品ID不能为空！")
  private String productId;

  /**
   * 盘点数量
   */
  @ApiModelProperty(value = "盘点数量", required = true)
  @NotNull(message = "盘点数量不能为空！")
  @TypeMismatch(message = "盘点数量格式有误！")
  private Integer takeNum;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;
}

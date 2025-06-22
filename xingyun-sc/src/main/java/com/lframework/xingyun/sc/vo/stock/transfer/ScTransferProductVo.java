package com.lframework.xingyun.sc.vo.stock.transfer;

import com.lframework.starter.web.core.components.validation.TypeMismatch;
import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ScTransferProductVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 商品ID
   */
  @ApiModelProperty(value = "商品ID", required = true)
  @NotBlank(message = "商品ID不能为空！")
  private String productId;

  /**
   * 调拨数量
   */
  @ApiModelProperty(value = "调拨数量", required = true)
  @NotNull(message = "调拨数量不能为空！")
  @TypeMismatch(message = "调拨数量格式有误！")
  private Integer transferNum;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;
}

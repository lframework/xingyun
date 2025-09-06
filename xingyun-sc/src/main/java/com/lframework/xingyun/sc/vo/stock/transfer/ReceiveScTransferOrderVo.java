package com.lframework.xingyun.sc.vo.stock.transfer;

import com.lframework.starter.web.core.components.validation.TypeMismatch;
import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReceiveScTransferOrderVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @ApiModelProperty(value = "ID", required = true)
  @NotBlank(message = "id不能为空！")
  private String id;

  /**
   * 商品信息
   */
  @ApiModelProperty(value = "收货商品", required = true)
  @Valid
  @NotEmpty(message = "收货商品不能为空！")
  private List<ReceiveScTransferProductVo> products;

  @Data
  public static class ReceiveScTransferProductVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品ID
     */
    @ApiModelProperty(value = "商品ID", required = true)
    @NotBlank(message = "商品ID不能为空！")
    private String productId;

    /**
     * 收货数量
     */
    @ApiModelProperty(value = "收货数量", required = true)
    @NotNull(message = "收货数量不能为空！")
    @TypeMismatch(message = "收货数量格式有误！")
    private BigDecimal receiveNum;
  }
}

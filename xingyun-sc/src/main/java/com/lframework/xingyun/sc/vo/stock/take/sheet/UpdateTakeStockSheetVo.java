package com.lframework.xingyun.sc.vo.stock.take.sheet;

import com.lframework.starter.web.vo.BaseVo;
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
}

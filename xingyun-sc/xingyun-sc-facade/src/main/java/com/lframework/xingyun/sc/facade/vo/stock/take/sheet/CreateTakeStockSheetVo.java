package com.lframework.xingyun.sc.facade.vo.stock.take.sheet;

import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateTakeStockSheetVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 盘点任务ID
   */
  @ApiModelProperty(value = "盘点任务ID", required = true)
  @NotBlank(message = "请选择关联盘点任务！")
  private String planId;

  /**
   * 预先盘点单ID
   */
  @ApiModelProperty("预先盘点单ID")
  private String preSheetId;

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

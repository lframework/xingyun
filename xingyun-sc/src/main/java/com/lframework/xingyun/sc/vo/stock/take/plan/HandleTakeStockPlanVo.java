package com.lframework.xingyun.sc.vo.stock.take.plan;

import com.lframework.starter.web.vo.BaseVo;
import java.io.Serializable;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class HandleTakeStockPlanVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  @NotBlank(message = "ID不能为空！")
  private String id;

  /**
   * 商品信息
   */
  @Valid
  @NotEmpty(message = "商品信息不能为空！")
  private List<ProductVo> products;

  /**
   * 备注
   */
  private String description;

  /**
   * 是否允许修改数量
   */
  private Boolean allowChangeNum;

  /**
   * 是否自动计算数量
   */
  private Boolean autoChangeStock;

  @Data
  public static class ProductVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品ID
     */
    @NotBlank(message = "商品ID不能为空！")
    private String productId;

    /**
     * 修改后盘点数量
     */
    private Integer takeNum;

    /**
     * 备注
     */
    private String description;
  }
}

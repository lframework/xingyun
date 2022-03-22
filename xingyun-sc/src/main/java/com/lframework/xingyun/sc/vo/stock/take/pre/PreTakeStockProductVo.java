package com.lframework.xingyun.sc.vo.stock.take.pre;

import com.lframework.starter.web.vo.BaseVo;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PreTakeStockProductVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 商品ID
   */
  @NotBlank(message = "商品ID不能为空！")
  private String productId;

  /**
   * 初盘数量
   */
  private Integer firstNum;

  /**
   * 复盘数量
   */
  private Integer secondNum;

  /**
   * 抽盘数量
   */
  private Integer randNum;
}

package com.lframework.xingyun.sc.vo.purchase;

import com.lframework.starter.web.vo.BaseVo;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ApprovePassPurchaseOrderVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 订单ID
   */
  @NotBlank(message = "订单ID不能为空！")
  private String id;

  /**
   * 备注
   */
  private String description;
}

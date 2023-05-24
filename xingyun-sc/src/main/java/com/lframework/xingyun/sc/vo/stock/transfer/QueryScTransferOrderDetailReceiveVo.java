package com.lframework.xingyun.sc.vo.stock.transfer;

import com.lframework.starter.web.vo.BaseVo;
import com.lframework.starter.web.vo.PageVo;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class QueryScTransferOrderDetailReceiveVo extends PageVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  @NotBlank(message = "调拨单ID不能为空！")
  private String orderId;

  @NotBlank(message = "明细ID不能为空！")
  private String detailId;
}

package com.lframework.xingyun.sc.vo.purchase.receive;

import com.lframework.starter.web.vo.BaseVo;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ApproveRefuseReceiveSheetVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 收货单ID
   */
  @NotBlank(message = "收货单ID不能为空！")
  private String id;

  /**
   * 拒绝理由
   */
  @NotBlank(message = "拒绝理由不能为空！")
  private String refuseReason;
}

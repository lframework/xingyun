package com.lframework.xingyun.sc.vo.retail.returned;

import com.lframework.starter.web.vo.BaseVo;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class BatchApproveRefuseRetailReturnVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 退单ID
   */
  @NotEmpty(message = "退单ID不能为空！")
  private List<String> ids;

  /**
   * 拒绝理由
   */
  @NotBlank(message = "拒绝理由不能为空！")
  private String refuseReason;
}

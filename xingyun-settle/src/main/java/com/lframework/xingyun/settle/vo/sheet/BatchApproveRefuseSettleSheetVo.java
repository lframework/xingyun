package com.lframework.xingyun.settle.vo.sheet;

import com.lframework.starter.web.vo.BaseVo;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BatchApproveRefuseSettleSheetVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @NotNull(message = "ID不能为空！")
  private List<String> ids;

  /**
   * 拒绝理由
   */
  @NotBlank(message = "拒绝理由不能为空！")
  private String refuseReason;
}

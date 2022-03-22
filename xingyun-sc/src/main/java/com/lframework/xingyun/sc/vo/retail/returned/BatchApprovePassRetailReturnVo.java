package com.lframework.xingyun.sc.vo.retail.returned;

import com.lframework.starter.web.vo.BaseVo;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class BatchApprovePassRetailReturnVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 退单ID
   */
  @NotEmpty(message = "退单ID不能为空！")
  private List<String> ids;
}

package com.lframework.xingyun.sc.vo.retail.returned;

import com.lframework.starter.web.vo.BaseVo;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ApprovePassRetailReturnVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 退单ID
   */
  @NotBlank(message = "退单ID不能为空！")
  private String id;

  /**
   * 备注
   */
  private String description;
}

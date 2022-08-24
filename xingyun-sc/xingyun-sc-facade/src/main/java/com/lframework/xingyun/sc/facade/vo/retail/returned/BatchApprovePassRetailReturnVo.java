package com.lframework.xingyun.sc.facade.vo.retail.returned;

import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
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
  @ApiModelProperty(value = "退单ID", required = true)
  @NotEmpty(message = "退单ID不能为空！")
  private List<String> ids;
}

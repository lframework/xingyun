package com.lframework.xingyun.basedata.vo.product.category;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@Data
public class QueryProductCategorySelectorVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  private Boolean available;
}

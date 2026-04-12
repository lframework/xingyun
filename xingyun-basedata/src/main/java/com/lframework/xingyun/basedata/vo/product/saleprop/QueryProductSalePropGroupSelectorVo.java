package com.lframework.xingyun.basedata.vo.product.saleprop;

import com.lframework.starter.web.core.vo.BaseVo;
import com.lframework.starter.web.core.vo.PageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.Data;

@Data
public class QueryProductSalePropGroupSelectorVo extends PageVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 编号
   */
  @Schema(description = "编号")
  private String code;

  /**
   * 名称
   */
  @Schema(description = "名称")
  private String name;

  /**
   * 是否过滤空的销售属性组
   */
  @Schema(description = "是否过滤空的销售属性组")
  private Boolean filterEmpty;

  /**
   * 状态
   */
  @Schema(description = "状态")
  private Boolean available;
}

package com.lframework.xingyun.basedata.facade.vo.product.saleprop;

import com.lframework.starter.web.vo.BaseVo;
import com.lframework.starter.web.vo.PageVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@Data
public class QueryProductSalePropGroupSelectorVo extends PageVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 编号
   */
  @ApiModelProperty("编号")
  private String code;

  /**
   * 名称
   */
  @ApiModelProperty("名称")
  private String name;

  /**
   * 是否过滤空的销售属性组
   */
  @ApiModelProperty("是否过滤空的销售属性组")
  private Boolean filterEmpty;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  private Boolean available;
}

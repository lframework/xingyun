package com.lframework.xingyun.basedata.vo.product.saleprop.item;

import com.lframework.starter.web.vo.BaseVo;
import com.lframework.starter.web.vo.PageVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@Data
public class QueryProductSalePropItemSelectorVo extends PageVo implements BaseVo, Serializable {

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
   * 销售属性组ID
   */
  @ApiModelProperty("销售属性组ID")
  private String salePropGroupId;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  private Boolean available;
}

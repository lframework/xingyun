package com.lframework.xingyun.basedata.facade.vo.product.poly.property;

import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateProductPolyPropertyVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * polyID
   */
  @ApiModelProperty(value = "polyID", required = true)
  @NotBlank(message = "polyId不能为空！")
  private String polyId;

  /**
   * 属性ID
   */
  @ApiModelProperty(value = "属性ID", required = true)
  @NotBlank(message = "属性ID不能为空！")
  private String propertyId;

  /**
   * 属性值ID
   */
  @ApiModelProperty("属性值ID")
  private String propertyItemId;

  /**
   * 属性值文本
   */
  @ApiModelProperty("属性值文本")
  private String propertyText;
}

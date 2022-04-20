package com.lframework.xingyun.basedata.vo.product.property.item;

import com.lframework.starter.web.components.validation.IsCode;
import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateProductPropertyItemVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 编号
   */
  @ApiModelProperty(value = "编号", required = true)
  @IsCode
  @NotBlank(message = "请输入编号！")
  private String code;

  /**
   * 名称
   */
  @ApiModelProperty(value = "名称", required = true)
  @NotBlank(message = "请输入名称！")
  private String name;

  /**
   * 属性ID
   */
  @ApiModelProperty(value = "属性ID", required = true)
  @NotBlank(message = "属性ID不能为空！")
  private String propertyId;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;
}

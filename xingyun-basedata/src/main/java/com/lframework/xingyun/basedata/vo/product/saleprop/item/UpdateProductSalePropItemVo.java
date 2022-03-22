package com.lframework.xingyun.basedata.vo.product.saleprop.item;

import com.lframework.starter.web.vo.BaseVo;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateProductSalePropItemVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @NotBlank(message = "ID不能为空！")
  private String id;

  /**
   * 编号
   */
  @NotBlank(message = "请输入编号！")
  private String code;

  /**
   * 名称
   */
  @NotBlank(message = "请输入名称！")
  private String name;

  /**
   * 状态
   */
  @NotNull(message = "状态不能为空！")
  private Boolean available;

  /**
   * 备注
   */
  private String description;
}

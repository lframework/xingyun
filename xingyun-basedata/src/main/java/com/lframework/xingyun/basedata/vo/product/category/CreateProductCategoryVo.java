package com.lframework.xingyun.basedata.vo.product.category;

import com.lframework.starter.web.vo.BaseVo;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateProductCategoryVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 编号
   */
  @NotBlank(message = "编号不能为空！")
  private String code;

  /**
   * 名称
   */
  @NotBlank(message = "名称不能为空！")
  private String name;

  /**
   * 父级ID
   */
  private String parentId;

  /**
   * 备注
   */
  private String description;
}

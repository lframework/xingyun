package com.lframework.xingyun.basedata.vo.product.info;

import com.lframework.starter.web.core.vo.BaseVo;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProductPropertyRelationVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 属性ID
   */
  @NotBlank(message = "属性值ID不能为空！")
  private String id;

  /**
   * 属性值内容
   */
  private String text;
}

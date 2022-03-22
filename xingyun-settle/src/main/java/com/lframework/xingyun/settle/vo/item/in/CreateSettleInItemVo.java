package com.lframework.xingyun.settle.vo.item.in;

import com.lframework.starter.web.vo.BaseVo;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateSettleInItemVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

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
   * 备注
   */
  private String description;
}

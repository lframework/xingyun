package com.lframework.xingyun.template.gen.vo.data.entity;

import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GenDataEntityDetailSelectorVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 数据实体ID
   */
  @ApiModelProperty("数据实体ID")
  @NotBlank(message = "数据实体ID不能为空！")
  private String entityId;
}

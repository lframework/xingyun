package com.lframework.xingyun.template.inner.vo.system.position;

import com.lframework.starter.web.components.validation.IsCode;
import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateSysPositionVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 岗位编号
   */
  @ApiModelProperty(value = "岗位编号", required = true)
  @IsCode
  @NotBlank(message = "请输入编号！")
  private String code;

  /**
   * 岗位名称
   */
  @ApiModelProperty(value = "岗位名称", required = true)
  @NotBlank(message = "请输入名称！")
  private String name;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;
}

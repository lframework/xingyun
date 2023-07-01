package com.lframework.xingyun.template.inner.vo.system.position;

import com.lframework.starter.web.components.validation.IsCode;
import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateSysPositionVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @ApiModelProperty("ID")
  @NotBlank(message = "ID不能为空！")
  private String id;

  /**
   * 岗位编号
   */
  @ApiModelProperty("岗位编号")
  @IsCode
  @NotBlank(message = "请输入编号！")
  private String code;

  /**
   * 岗位名称
   */
  @ApiModelProperty("岗位名称")
  @NotBlank(message = "请输入名称！")
  private String name;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  @NotNull(message = "状态不能为空！")
  private Boolean available;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;
}

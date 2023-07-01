package com.lframework.xingyun.template.inner.vo.qrtz;

import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author zmj
 * @since 2022/8/20
 */
@Data
public class UpdateQrtzVo extends CreateQrtzVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 原始任务名称
   */
  @ApiModelProperty(value = "原始任务名称", required = true)
  @NotBlank(message = "原始任务名称不能为空！")
  private String oriName;

  /**
   * 原始任务分组
   */
  @ApiModelProperty(value = "原始任务分组", required = true)
  @NotBlank(message = "原始任务分组不能为空！")
  private String oriGroup;
}

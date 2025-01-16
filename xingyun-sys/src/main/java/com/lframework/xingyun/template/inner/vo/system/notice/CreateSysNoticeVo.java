package com.lframework.xingyun.template.inner.vo.system.notice;

import com.lframework.starter.web.components.validation.TypeMismatch;
import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateSysNoticeVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 标题
   */
  @ApiModelProperty(value = "标题", required = true)
  @NotBlank(message = "请输入标题！")
  private String title;

  /**
   * 内容
   */
  @ApiModelProperty(value = "内容", required = true)
  @NotBlank(message = "请输入内容！")
  private String content;

  /**
   * 是否发布
   */
  @ApiModelProperty(value = "是否发布", required = true)
  @TypeMismatch(message = "是否发布格式有误！")
  @NotNull(message = "请选择是否发布！")
  private Boolean published;

}

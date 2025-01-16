package com.lframework.xingyun.template.inner.vo.qrtz;

import com.lframework.starter.web.components.validation.IsEnum;
import com.lframework.starter.web.vo.PageVo;
import com.lframework.xingyun.template.inner.enums.TriggerState;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

/**
 * @author zmj
 * @since 2022/8/20
 */
@Data
public class QueryQrtzVo extends PageVo {

  /**
   * 名称
   */
  @ApiModelProperty("名称")
  private String name;

  /**
   * 组
   */
  @ApiModelProperty("组")
  private String group;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  @IsEnum(message = "状态格式错误！", enumClass = TriggerState.class)
  private String state;

  /**
   * 任务类
   */
  @ApiModelProperty(hidden = true)
  private List<String> jobClasses;
}

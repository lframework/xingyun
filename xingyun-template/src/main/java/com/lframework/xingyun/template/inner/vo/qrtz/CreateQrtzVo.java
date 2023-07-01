package com.lframework.xingyun.template.inner.vo.qrtz;

import com.lframework.starter.web.components.validation.IsEnum;
import com.lframework.starter.web.vo.BaseVo;
import com.lframework.xingyun.template.inner.enums.QrtzJobType;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author zmj
 * @since 2022/8/20
 */
@Data
public class CreateQrtzVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 名称
   */
  @ApiModelProperty(value = "名称", required = true)
  @NotBlank(message = "名称不能为空！")
  private String name;

  /**
   * 分组
   */
  @ApiModelProperty(value = "分组", required = true)
  @NotBlank(message = "分组不能为空！")
  private String group;

  /**
   * 租户ID
   */
  @ApiModelProperty("租户ID")
  private Integer tenantId;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  /**
   * 执行类名
   */
  @ApiModelProperty("执行类名")
  private String targetClassName;

  /**
   * 执行方法名
   */
  @ApiModelProperty("执行方法名")
  private String targetMethodName;

  /**
   * 执行参数类型
   */
  @ApiModelProperty("执行参数类型")
  private List<String> targetParamTypes;

  /**
   * 执行参数
   */
  @ApiModelProperty("执行参数")
  private List<String> targetParams;

  /**
   * Cron表达式
   */
  @ApiModelProperty("Cron表达式")
  @NotBlank(message = "Cron表达式不能为空！")
  private String cron;

  /**
   * 任务类型
   */
  @NotNull(message = "任务类型不能为空！")
  @IsEnum(message = "任务类型不能为空！", enumClass = QrtzJobType.class)
  private Integer jobType;

  /**
   * 脚本
   */
  @ApiModelProperty("脚本")
  private String script;
}

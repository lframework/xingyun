package com.lframework.xingyun.template.inner.dto.qrtz;

import com.lframework.xingyun.template.inner.enums.QrtzJobType;
import com.lframework.xingyun.template.inner.enums.TriggerState;
import com.lframework.starter.web.dto.BaseDto;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * @author zmj
 * @since 2022/8/20
 */
@Data
public class QrtzDto implements BaseDto, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 名称
   */
  private String name;

  /**
   * 分组
   */
  private String group;

  /**
   * 租户ID
   */
  private Integer tenantId;

  /**
   * 备注
   */
  private String description;

  /**
   * 执行类名
   */
  private String targetClassName;

  /**
   * 执行方法名
   */
  private String targetMethodName;

  /**
   * 执行参数类型
   */
  private List<String> targetParamTypes;

  /**
   * 执行参数
   */
  private List<String> targetParams;

  /**
   * 脚本
   */
  private String script;

  /**
   * 任务类型
   */
  private QrtzJobType jobType;

  /**
   * Cron表达式
   */
  private String cron;

  /**
   * 状态
   */
  private TriggerState state;
}

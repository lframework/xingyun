package com.lframework.xingyun.core.dto.notify;

import com.lframework.starter.web.dto.BaseDto;
import java.io.Serializable;
import lombok.Data;

@Data
public class SysNotifyParamsDto implements BaseDto, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 动态参数
   */
  private Object variables;
}

package com.lframework.xingyun.core.dto.export;

import java.io.Serializable;
import lombok.Data;

@Data
public class AddExportTaskDto implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 名称
   */
  private String name;

  /**
   * 请求类名
   */
  private String reqClassName;

  /**
   * 请求类方法参数
   */
  private String reqParams;

  /**
   * 用户Token
   */
  private String token;
}

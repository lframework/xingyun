package com.lframework.xingyun.template.core.dto;

import com.lframework.starter.web.dto.BaseDto;
import java.io.Serializable;
import lombok.Data;

@Data
public class DeptDto implements BaseDto, Serializable {

  private static final long serialVersionUID = 1L;

  public static final String CACHE_NAME = "DeptDto";

  /**
   * ID
   */
  private String id;

  /**
   * 编号
   */
  private String code;

  /**
   * 名称
   */
  private String name;
}

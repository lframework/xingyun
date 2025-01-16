package com.lframework.xingyun.core.dto;

import com.lframework.starter.web.dto.BaseDto;
import java.io.Serializable;
import lombok.Data;

@Data
public class GenerateCodeDto implements BaseDto, Serializable {

  public static final String CACHE_NAME = "GenerateCodeDto";

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private Integer id;

  /**
   * 规则配置
   */
  private String configStr;
}

package com.lframework.xingyun.template.gen.dto.gen;

import com.lframework.xingyun.template.gen.components.CreateColumnConfig;
import com.lframework.starter.web.dto.BaseDto;
import java.io.Serializable;
import lombok.Data;

@Data
public class GenCreateColumnConfigDto implements BaseDto, CreateColumnConfig, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 是否必填
   */
  private Boolean required;

  /**
   * 排序编号
   */
  private Integer orderNo;
}

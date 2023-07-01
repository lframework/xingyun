package com.lframework.xingyun.template.gen.dto.simpledb;

import com.lframework.starter.web.dto.BaseDto;
import java.io.Serializable;
import lombok.Data;

@Data
public class SimpleDBDto implements BaseDto, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 库名
   */
  private String tableSchema;

  /**
   * 表名
   */
  private String tableName;
}

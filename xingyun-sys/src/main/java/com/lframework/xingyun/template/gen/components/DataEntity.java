package com.lframework.xingyun.template.gen.components;

import com.lframework.xingyun.template.gen.dto.gen.GenGenerateInfoDto;
import java.util.List;
import lombok.Data;

/**
 * 数据对象
 */
@Data
public class DataEntity {

  /**
   * ID
   */
  private String id;

  /**
   * 名称
   */
  private String name;

  /**
   * 备注
   */
  private String description;

  /**
   * 数据表信息
   */
  private Table table;

  /**
   * 字段信息
   */
  private List<DataEntityColumn> columns;

  /**
   * 配置信息
   */
  private GenGenerateInfoDto generateInfo;
}

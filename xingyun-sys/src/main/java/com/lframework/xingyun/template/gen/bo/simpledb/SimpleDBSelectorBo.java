package com.lframework.xingyun.template.gen.bo.simpledb;

import com.lframework.xingyun.template.gen.dto.simpledb.SimpleDBDto;
import com.lframework.starter.web.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SimpleDBSelectorBo extends BaseBo<SimpleDBDto> {

  private static final long serialVersionUID = 1L;

  /**
   * 库名
   */
  @ApiModelProperty("库名")
  private String tableSchema;

  /**
   * 表名
   */
  @ApiModelProperty("表名")
  private String tableName;

  public SimpleDBSelectorBo() {

  }

  public SimpleDBSelectorBo(SimpleDBDto dto) {

    super(dto);
  }
}

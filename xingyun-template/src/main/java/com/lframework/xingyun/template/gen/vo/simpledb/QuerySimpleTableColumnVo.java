package com.lframework.xingyun.template.gen.vo.simpledb;

import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class QuerySimpleTableColumnVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 数据表所属的数据库名
   */
  @ApiModelProperty(value = "数据表所属的数据库名", required = true)
  private String tableSchema;

  /**
   * 数据库表名
   */
  @ApiModelProperty(value = "数据库表名", required = true)
  @NotBlank(message = "请输入数据库表名！")
  private String tableName;
}

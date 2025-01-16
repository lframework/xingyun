package com.lframework.xingyun.template.gen.bo.data.obj;

import com.lframework.starter.web.bo.SuperBo;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

@Data
public class GenDataObjColumnBo implements SuperBo {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 数据对象名称
   */
  @ApiModelProperty("数据对象名称")
  private String name;

  /**
   * 列
   */
  @ApiModelProperty("列")
  private List<ColumnBo> columns;

  @Data
  public static class ColumnBo implements SuperBo {

    /**
     * 数据实体明细ID
     */
    @ApiModelProperty("数据实体明细ID")
    private String id;

    /**
     * 关联ID
     */
    @ApiModelProperty("关联ID")
    private String relaId;

    /**
     * 显示名称
     */
    @ApiModelProperty("显示名称")
    private String name;

    /**
     * 类型
     */
    @ApiModelProperty("类型")
    private Integer type;

    /**
     * 数据类型
     */
    @ApiModelProperty("数据对象")
    private Integer dataType;

    /**
     * 显示类型
     */
    @ApiModelProperty("显示类型")
    private Integer viewType;
  }
}

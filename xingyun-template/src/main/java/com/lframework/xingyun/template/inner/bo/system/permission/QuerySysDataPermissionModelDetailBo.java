package com.lframework.xingyun.template.inner.bo.system.permission;

import com.lframework.starter.web.annotations.convert.EnumConvert;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.dto.VoidDto;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

@Data
public class QuerySysDataPermissionModelDetailBo extends BaseBo<VoidDto> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 明细ID
   */
  @ApiModelProperty("明细ID")
  private Integer detailId;

  /**
   * 节点类型
   */
  @ApiModelProperty("节点类型")
  private Integer nodeType;

  /**
   * 计算类型
   */
  @ApiModelProperty("计算类型")
  private Integer calcType;

  /**
   * 值
   */
  @ApiModelProperty("值")
  private String value;

  /**
   * 值
   */
  @ApiModelProperty("值")
  private List<String> values;

  /**
   * 名称
   */
  @ApiModelProperty("名称")
  private String name;

  /**
   * 模型ID
   */
  @ApiModelProperty("模型ID")
  private Integer modelId;

  /**
   * 条件
   */
  @ApiModelProperty("条件")
  private Integer[] conditionTypes;

  /**
   * 输入类型
   */
  @ApiModelProperty("输入类型")
  @EnumConvert
  private Integer inputType;

  /**
   * 条件类型
   */
  @ApiModelProperty("条件类型")
  private Integer conditionType;

  /**
   * 前段枚举名
   */
  @ApiModelProperty("前端枚举名")
  private String enumName;

  /**
   * 子节点
   */
  @ApiModelProperty("子节点")
  private List<QuerySysDataPermissionModelDetailBo> children;

  public QuerySysDataPermissionModelDetailBo() {
  }
}

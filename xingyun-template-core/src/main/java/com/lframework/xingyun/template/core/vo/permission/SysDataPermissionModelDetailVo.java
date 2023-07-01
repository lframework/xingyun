package com.lframework.xingyun.template.core.vo.permission;

import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SysDataPermissionModelDetailVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @ApiModelProperty("ID")

  private String id;

  /**
   * 明细ID
   */
  @ApiModelProperty(value = "明细ID", required = true)
  @NotNull(message = "明细ID不能为空！")
  private Integer detailId;

  /**
   * 节点类型
   */
  @ApiModelProperty(value = "节点类型", required = true)
  @NotNull(message = "节点类型不能为空！")
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
   * 条件类型
   */
  @ApiModelProperty("条件类型")
  private Integer conditionType;

  /**
   * 子节点
   */
  @ApiModelProperty("子节点")
  private List<SysDataPermissionModelDetailVo> children;
}

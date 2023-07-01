package com.lframework.xingyun.template.inner.dto;

import com.lframework.starter.web.dto.BaseDto;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
 * 菜单Dto
 *
 * @author zmj
 */
@Data
public class MenuDto implements BaseDto, Serializable {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 菜单名称
   */
  @ApiModelProperty("菜单名称")
  private String name;

  /**
   * 路径
   */
  @ApiModelProperty("路径")
  private String path;

  /**
   * 是否隐藏
   */
  @ApiModelProperty("是否隐藏")
  private Boolean hidden;

  /**
   * 类型 0-目录 1-功能菜单 2-权限
   */
  @ApiModelProperty("类型 0-目录 1-功能菜单 2-权限")
  private Integer display;

  /**
   * 组件类型
   */
  @ApiModelProperty("组件类型")
  private Integer componentType;

  /**
   * 组件
   */
  @ApiModelProperty("组件")
  private String component;

  /**
   * 自定义请求参数
   */
  @ApiModelProperty("自定义请求参数")
  private String requestParam;


  /**
   * meta
   */
  @ApiModelProperty("meta")
  private MenuMetaDto meta;

  /**
   * 父节点ID
   */
  @ApiModelProperty("父节点ID")
  private String parentId;

  /**
   * 是否收藏
   */
  @ApiModelProperty("是否收藏")
  private Boolean isCollect = Boolean.FALSE;
}

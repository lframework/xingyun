package com.lframework.xingyun.template.inner.vo.system.menu;

import com.lframework.xingyun.template.inner.enums.system.SysMenuComponentType;
import com.lframework.xingyun.template.inner.enums.system.SysMenuDisplay;
import com.lframework.starter.web.components.validation.IsCode;
import com.lframework.starter.web.components.validation.IsEnum;
import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * 创建菜单Vo
 */
@Data
public class CreateSysMenuVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 编号
   */
  @ApiModelProperty(value = "编号", required = true)
  @IsCode
  @NotBlank(message = "请输入编号！")
  private String code;

  /**
   * 标题
   */
  @ApiModelProperty(value = "标题", required = true)
  @NotBlank(message = "请输入标题！")
  private String title;

  /**
   * 图标
   */
  @ApiModelProperty("图标")
  private String icon;

  /**
   * 类型
   */
  @ApiModelProperty(value = "类型", required = true)
  @NotNull(message = "请选择类型！")
  @IsEnum(message = "请选择类型！", enumClass = SysMenuDisplay.class)
  private Integer display;

  /**
   * 父级ID
   */
  @ApiModelProperty("父级ID")
  private String parentId;

  /**
   * 权限
   */
  @ApiModelProperty("权限")
  private String permission;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  // 以下均为前端配置项

  /**
   * 路由名称
   */
  @ApiModelProperty("路由名称")
  private String name;

  /**
   * 组件类型
   */
  @ApiModelProperty("组件类型")
  @IsEnum(message = "组件类型格式错误！", enumClass = SysMenuComponentType.class)
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
   * 是否不缓存
   */
  @ApiModelProperty("是否不缓存")
  private Boolean noCache;
}

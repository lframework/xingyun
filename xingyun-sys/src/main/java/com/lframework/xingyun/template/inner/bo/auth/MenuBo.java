package com.lframework.xingyun.template.inner.bo.auth;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.dto.VoidDto;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

@Data
public class MenuBo extends BaseBo<VoidDto> {

  /**
   * 名称
   */
  @ApiModelProperty("名称")
  private String name;

  /**
   * 组件
   */
  @ApiModelProperty("组件")
  private String component;

  /**
   * 子节点
   */
  @ApiModelProperty("子节点")
  private List<MenuBo> children;

  /**
   * 路由路径
   */
  @ApiModelProperty("路由路径")
  private String path;

  /**
   * 元数据
   */
  @ApiModelProperty("元数据")
  private MetaBo meta;

  public MenuBo() {
  }

  @Data
  public static class MetaBo extends BaseBo<VoidDto> {

    /**
     * ID
     */
    @ApiModelProperty("ID")
    private String id;

    /**
     * 标题
     */
    @ApiModelProperty("标题")
    private String title;

    /**
     * 图标
     */
    @ApiModelProperty("图标")
    private String icon;

    /**
     * 是否隐藏
     */
    @ApiModelProperty("是否隐藏")
    private Boolean hideMenu;

    /**
     * 是否不缓存
     */
    @ApiModelProperty("是否不缓存")
    private Boolean ignoreKeepAlive;

    /**
     * 是否固定
     */
    @ApiModelProperty("是否固定")
    private Boolean affix = Boolean.FALSE;

    /**
     * 是否外部链接
     */
    @ApiModelProperty("是否外部链接")
    private Boolean isLink = Boolean.FALSE;

    /**
     * 是否收藏
     */
    @ApiModelProperty("是否收藏")
    private Boolean isCollect = Boolean.FALSE;

    /**
     * 内嵌链接地址
     */
    @ApiModelProperty("内嵌链接地址")
    private String frameSrc;

    /**
     * 自定义列表ID
     */
    @ApiModelProperty("自定义列表ID")
    private String customListId;

    /**
     * 自定义页面ID
     */
    @ApiModelProperty("自定义页面ID")
    private String customPageId;
  }
}

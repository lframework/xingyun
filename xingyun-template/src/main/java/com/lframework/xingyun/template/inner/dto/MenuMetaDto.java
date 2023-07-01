package com.lframework.xingyun.template.inner.dto;

import com.lframework.starter.web.dto.BaseDto;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
 * 菜单Meta Dto
 *
 * @author zmj
 */
@Data
public class MenuMetaDto implements BaseDto, Serializable {

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
   * 是否不缓存
   */
  @ApiModelProperty("是否不缓存")
  private Boolean noCache;
}

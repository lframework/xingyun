package com.lframework.xingyun.template.inner.dto.system.notice;

import com.lframework.xingyun.template.inner.entity.SysNotice;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.dto.BaseDto;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
 * @author zmj
 * @since 2022/8/18
 */
@Data
public class SysNoticeDto extends BaseBo<SysNotice> implements BaseDto, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 标题
   */
  @ApiModelProperty("标题")
  private String title;

  /**
   * 内容
   */
  @ApiModelProperty("内容")
  private String content;

  public SysNoticeDto(SysNotice dto) {
    super(dto);
  }
}

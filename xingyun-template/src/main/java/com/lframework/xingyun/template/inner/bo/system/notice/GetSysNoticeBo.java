package com.lframework.xingyun.template.inner.bo.system.notice;

import com.lframework.xingyun.template.inner.entity.SysNotice;
import com.lframework.starter.web.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 系统通知 GetBo
 * </p>
 *
 * @author zmj
 */
@Data
public class GetSysNoticeBo extends BaseBo<SysNotice> {

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
   * 内容
   */
  @ApiModelProperty("内容")
  private String content;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  private Boolean available;

  /**
   * 是否发布
   */
  @ApiModelProperty("是否发布")
  private Boolean published;

  public GetSysNoticeBo() {

  }

  public GetSysNoticeBo(SysNotice dto) {

    super(dto);
  }

}

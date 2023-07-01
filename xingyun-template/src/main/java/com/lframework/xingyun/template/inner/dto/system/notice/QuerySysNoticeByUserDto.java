package com.lframework.xingyun.template.inner.dto.system.notice;

import com.lframework.starter.web.dto.BaseDto;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * @author zmj
 * @since 2022/8/19
 */
@Data
public class QuerySysNoticeByUserDto implements BaseDto, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 标题
   */
  private String title;

  /**
   * 是否已读
   */
  private Boolean readed;

  /**
   * 发布时间
   */
  private LocalDateTime publishTime;

}

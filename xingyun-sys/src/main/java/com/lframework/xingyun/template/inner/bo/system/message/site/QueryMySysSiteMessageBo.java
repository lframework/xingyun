package com.lframework.xingyun.template.inner.bo.system.message.site;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.template.inner.entity.SysSiteMessage;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <p>
 * 我的站内信 QueryBo
 * </p>
 *
 * @author zmj
 */
@Data
public class QueryMySysSiteMessageBo extends BaseBo<SysSiteMessage> {

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
   * 是否已读
   */
  @ApiModelProperty("是否已读")
  private Boolean readed;

  /**
   * 创建时间
   */
  @ApiModelProperty("创建时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime createTime;

  public QueryMySysSiteMessageBo() {

  }

  public QueryMySysSiteMessageBo(SysSiteMessage dto) {

    super(dto);
  }
}

package com.lframework.xingyun.template.inner.bo.system.message.mail;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.template.inner.entity.SysMailMessage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 邮件消息 GetBo
 * </p>
 *
 * @author zmj
 */
@Data
public class GetSysMailMessageBo extends BaseBo<SysMailMessage> {

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

  public GetSysMailMessageBo() {

  }

  public GetSysMailMessageBo(SysMailMessage dto) {

    super(dto);
  }

}

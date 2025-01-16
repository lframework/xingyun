package com.lframework.xingyun.template.inner.bo.system.message.mail;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.web.annotations.constants.EncryType;
import com.lframework.starter.web.annotations.convert.EncryptConvert;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.template.inner.entity.SysMailMessage;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <p>
 * 邮件消息 QueryBo
 * </p>
 *
 * @author zmj
 */
@Data
public class QuerySysMailMessageBo extends BaseBo<SysMailMessage> {

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
   * 接收邮箱
   */
  @ApiModelProperty("接收邮箱")
  @EncryptConvert(type = EncryType.EMAIL)
  private String mail;

  /**
   * 创建人
   */
  @ApiModelProperty("创建人")
  private String createBy;

  /**
   * 创建时间
   */
  @ApiModelProperty("创建时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime createTime;

  /**
   * 发送状态
   */
  @ApiModelProperty("发送状态")
  private Integer sendStatus;

  public QuerySysMailMessageBo() {

  }

  public QuerySysMailMessageBo(SysMailMessage dto) {

    super(dto);
  }
}

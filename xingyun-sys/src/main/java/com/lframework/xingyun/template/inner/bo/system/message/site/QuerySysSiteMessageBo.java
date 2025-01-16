package com.lframework.xingyun.template.inner.bo.system.message.site;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.template.inner.entity.SysSiteMessage;
import com.lframework.xingyun.template.inner.entity.SysUser;
import com.lframework.xingyun.template.inner.service.system.SysUserService;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <p>
 * 站内信 QueryBo
 * </p>
 *
 * @author zmj
 */
@Data
public class QuerySysSiteMessageBo extends BaseBo<SysSiteMessage> {

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
   * 接收人姓名
   */
  @ApiModelProperty("接收人姓名")
  private String receiverName;

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
   * 是否已读
   */
  @ApiModelProperty("是否已读")
  private Boolean readed;

  /**
   * 已读时间
   */
  @ApiModelProperty("已读时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime readTime;

  public QuerySysSiteMessageBo() {

  }

  public QuerySysSiteMessageBo(SysSiteMessage dto) {

    super(dto);
  }

  @Override
  protected void afterInit(SysSiteMessage dto) {
    SysUserService sysUserService = ApplicationUtil.getBean(SysUserService.class);
    SysUser receiver = sysUserService.findById(dto.getReceiverId());
    this.receiverName = receiver.getName();
  }
}

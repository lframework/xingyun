package com.lframework.xingyun.template.inner.bo.system.notify;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.utils.EnumUtil;
import com.lframework.xingyun.template.inner.entity.SysNotifyGroup;
import com.lframework.xingyun.template.inner.enums.system.SysNotifyMessageType;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;
import lombok.Data;

/**
 * <p>
 * 消息通知组 QueryBo
 * </p>
 *
 * @author zmj
 */
@Data
public class QuerySysNotifyGroupBo extends BaseBo<SysNotifyGroup> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 名称
   */
  @ApiModelProperty("名称")
  private String name;

  /**
   * 接收者类型
   */
  @ApiModelProperty("接收者类型")
  private String receiverType;

  /**
   * 消息类型
   */
  @ApiModelProperty("消息类型")
  private String messageType;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

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
   * 状态
   */
  @ApiModelProperty("状态")
  private Boolean available;

  public QuerySysNotifyGroupBo() {

  }

  public QuerySysNotifyGroupBo(SysNotifyGroup dto) {

    super(dto);
  }

  @Override
  public BaseBo<SysNotifyGroup> convert(SysNotifyGroup dto) {
    return super.convert(dto, QuerySysNotifyGroupBo::getReceiverType,
        QuerySysNotifyGroupBo::getMessageType);
  }

  @Override
  protected void afterInit(SysNotifyGroup dto) {

    this.receiverType = dto.getReceiverType().getDesc();
    this.messageType = Arrays.stream(dto.getMessageType().split(StringPool.STR_SPLIT))
        .map(Integer::valueOf).map(
            t -> EnumUtil.getDesc(SysNotifyMessageType.class, t))
        .collect(Collectors.joining(StringPool.STR_SPLIT_CN));
  }
}

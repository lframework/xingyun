package com.lframework.xingyun.template.inner.bo.system.notify;

import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.template.inner.entity.SysNotifyGroup;
import com.lframework.xingyun.template.inner.service.system.SysNotifyGroupReceiverService;
import io.swagger.annotations.ApiModelProperty;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

/**
 * <p>
 * 消息通知组 GetBo
 * </p>
 *
 * @author zmj
 */
@Data
public class GetSysNotifyGroupBo extends BaseBo<SysNotifyGroup> {

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
  private Integer receiverType;

  /**
   * 接收者ID
   */
  @ApiModelProperty("接收者ID")
  private List<String> receiverIds;

  /**
   * 消息类型
   */
  @ApiModelProperty("消息类型")
  private List<Integer> messageType;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  private Boolean available;

  public GetSysNotifyGroupBo() {

  }

  public GetSysNotifyGroupBo(SysNotifyGroup dto) {

    super(dto);
  }

  @Override
  public BaseBo<SysNotifyGroup> convert(SysNotifyGroup dto) {
    return super.convert(dto, GetSysNotifyGroupBo::getReceiverType,
        GetSysNotifyGroupBo::getMessageType);
  }

  @Override
  protected void afterInit(SysNotifyGroup dto) {

    this.receiverType = dto.getReceiverType().getCode();

    SysNotifyGroupReceiverService sysNotifyGroupReceiverService = ApplicationUtil.getBean(
        SysNotifyGroupReceiverService.class);
    this.receiverIds = sysNotifyGroupReceiverService.getReceiverIdsByGroupId(dto.getId());

    this.messageType = Arrays.stream(dto.getMessageType().split(StringPool.STR_SPLIT))
        .map(Integer::valueOf).collect(
            Collectors.toList());
  }
}

package com.lframework.xingyun.sc.bo.stock.warning;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.sc.entity.ProductStockWarningNotify;
import com.lframework.xingyun.template.inner.entity.SysNotifyGroup;
import com.lframework.xingyun.template.inner.service.system.SysNotifyGroupService;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GetProductStockWarningNotifyBo extends BaseBo<ProductStockWarningNotify> {

  /**
   * 消息通知组ID
   */
  @ApiModelProperty("消息通知组ID")
  private String id;

  /**
   * 消息通知组名称
   */
  @ApiModelProperty("消息通知组名称")
  private String name;

  /**
   * 消息通知组状态
   */
  @ApiModelProperty("消息通知组状态")
  private Boolean available;

  public GetProductStockWarningNotifyBo(ProductStockWarningNotify dto) {
    super(dto);
  }

  @Override
  protected void afterInit(ProductStockWarningNotify dto) {
    SysNotifyGroupService sysNotifyGroupService = ApplicationUtil.getBean(
        SysNotifyGroupService.class);

    SysNotifyGroup sysNotifyGroup = sysNotifyGroupService.findById(dto.getNotifyGroupId());
    this.id = sysNotifyGroup.getId();
    this.name = sysNotifyGroup.getName();
    this.available = sysNotifyGroup.getAvailable();
  }
}

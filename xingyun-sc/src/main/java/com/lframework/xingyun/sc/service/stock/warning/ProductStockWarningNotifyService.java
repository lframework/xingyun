package com.lframework.xingyun.sc.service.stock.warning;

import com.lframework.starter.web.service.BaseMpService;
import com.lframework.xingyun.sc.entity.ProductStockWarningNotify;
import java.time.LocalDateTime;
import java.util.List;

public interface ProductStockWarningNotifyService extends BaseMpService<ProductStockWarningNotify> {

  /**
   * 获取最后通知时间
   * @param warningId
   * @param groupId
   * @return
   */
  LocalDateTime getLastNotifyTime(String warningId, String groupId);

  /**
   * 设置最后通知时间
   * @param warningId
   * @param groupId
   */
  LocalDateTime setLastNotifyTime(String warningId, String groupId);

  /**
   * 新增消息通知组设置
   * @param groupId
   */
  void createSetting(String groupId);

  /**
   * 删除消息通知组设置
   * @param groupId
   */
  void deleteSetting(String groupId);
}

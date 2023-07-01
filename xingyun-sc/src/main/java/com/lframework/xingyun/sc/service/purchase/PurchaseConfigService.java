package com.lframework.xingyun.sc.service.purchase;

import com.lframework.starter.web.service.BaseMpService;
import com.lframework.xingyun.sc.entity.PurchaseConfig;
import com.lframework.xingyun.sc.vo.purchase.config.UpdatePurchaseConfigVo;

public interface PurchaseConfigService extends BaseMpService<PurchaseConfig> {

  /**
   * 查询
   *
   * @return
   */
  PurchaseConfig get();

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdatePurchaseConfigVo vo);
}

package com.lframework.xingyun.sc.biz.service.purchase;

import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.xingyun.sc.facade.entity.PurchaseConfig;
import com.lframework.xingyun.sc.facade.vo.purchase.config.UpdatePurchaseConfigVo;

public interface IPurchaseConfigService extends BaseMpService<PurchaseConfig> {

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

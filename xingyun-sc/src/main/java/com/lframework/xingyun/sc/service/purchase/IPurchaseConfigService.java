package com.lframework.xingyun.sc.service.purchase;

import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.xingyun.sc.dto.purchase.config.PurchaseConfigDto;
import com.lframework.xingyun.sc.entity.PurchaseConfig;
import com.lframework.xingyun.sc.vo.purchase.config.UpdatePurchaseConfigVo;

public interface IPurchaseConfigService extends BaseMpService<PurchaseConfig> {

  /**
   * 查询
   *
   * @return
   */
  PurchaseConfigDto get();

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdatePurchaseConfigVo vo);
}

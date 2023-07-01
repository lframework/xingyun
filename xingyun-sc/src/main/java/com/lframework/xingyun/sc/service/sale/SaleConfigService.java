package com.lframework.xingyun.sc.service.sale;

import com.lframework.starter.web.service.BaseMpService;
import com.lframework.xingyun.sc.entity.SaleConfig;
import com.lframework.xingyun.sc.vo.sale.config.UpdateSaleConfigVo;

public interface SaleConfigService extends BaseMpService<SaleConfig> {

  /**
   * 查询
   *
   * @return
   */
  SaleConfig get();

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateSaleConfigVo vo);
}

package com.lframework.xingyun.sc.service.sale;

import com.lframework.starter.web.service.BaseService;
import com.lframework.xingyun.sc.dto.sale.config.SaleConfigDto;
import com.lframework.xingyun.sc.vo.sale.config.UpdateSaleConfigVo;

public interface ISaleConfigService extends BaseService {

  /**
   * 查询
   *
   * @return
   */
  SaleConfigDto get();

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateSaleConfigVo vo);
}

package com.lframework.xingyun.sc.biz.service.stock.take;

import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.xingyun.sc.facade.entity.TakeStockConfig;
import com.lframework.xingyun.sc.facade.vo.stock.take.config.UpdateTakeStockConfigVo;

/**
 * 盘点参数 Service
 *
 * @author zmj
 */
public interface ITakeStockConfigService extends BaseMpService<TakeStockConfig> {

  /**
   * 根据ID查询
   *
   * @return
   */
  TakeStockConfig get();


  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateTakeStockConfigVo vo);

}

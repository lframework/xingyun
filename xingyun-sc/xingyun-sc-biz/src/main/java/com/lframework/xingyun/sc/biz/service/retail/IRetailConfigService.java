package com.lframework.xingyun.sc.biz.service.retail;

import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.xingyun.sc.facade.entity.RetailConfig;
import com.lframework.xingyun.sc.facade.vo.retail.config.UpdateRetailConfigVo;

public interface IRetailConfigService extends BaseMpService<RetailConfig> {

  /**
   * 查询
   *
   * @return
   */
  RetailConfig get();

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateRetailConfigVo vo);
}

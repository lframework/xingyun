package com.lframework.xingyun.sc.service.retail;

import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.xingyun.sc.entity.RetailConfig;
import com.lframework.xingyun.sc.vo.retail.config.UpdateRetailConfigVo;

public interface RetailConfigService extends BaseMpService<RetailConfig> {

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

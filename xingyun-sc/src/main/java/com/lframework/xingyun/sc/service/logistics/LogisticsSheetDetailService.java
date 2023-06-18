package com.lframework.xingyun.sc.service.logistics;

import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.xingyun.sc.entity.LogisticsSheetDetail;
import com.lframework.xingyun.sc.enums.LogisticsSheetDetailBizType;

public interface LogisticsSheetDetailService extends BaseMpService<LogisticsSheetDetail> {

  /**
   * 根据业务ID查询
   *
   * @param bizId
   * @param bizType
   * @return
   */
  LogisticsSheetDetail getByBizId(String bizId, LogisticsSheetDetailBizType bizType);
}

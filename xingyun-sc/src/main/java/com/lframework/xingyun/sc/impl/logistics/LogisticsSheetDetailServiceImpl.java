package com.lframework.xingyun.sc.impl.logistics;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.web.impl.BaseMpServiceImpl;
import com.lframework.xingyun.sc.entity.LogisticsSheetDetail;
import com.lframework.xingyun.sc.enums.LogisticsSheetDetailBizType;
import com.lframework.xingyun.sc.mappers.LogisticsSheetDetailMapper;
import com.lframework.xingyun.sc.service.logistics.LogisticsSheetDetailService;
import org.springframework.stereotype.Service;

@Service
public class LogisticsSheetDetailServiceImpl extends
    BaseMpServiceImpl<LogisticsSheetDetailMapper, LogisticsSheetDetail> implements
    LogisticsSheetDetailService {

  @Override
  public LogisticsSheetDetail getByBizId(String bizId, LogisticsSheetDetailBizType bizType) {
    Wrapper<LogisticsSheetDetail> queryWrapper = Wrappers.lambdaQuery(LogisticsSheetDetail.class)
        .eq(LogisticsSheetDetail::getBizId, bizId).eq(LogisticsSheetDetail::getBizType, bizType);
    return this.getOne(queryWrapper);
  }
}

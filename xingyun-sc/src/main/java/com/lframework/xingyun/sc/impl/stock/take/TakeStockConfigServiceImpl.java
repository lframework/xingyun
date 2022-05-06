package com.lframework.xingyun.sc.impl.stock.take;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.ObjectUtil;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.enums.OpLogType;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.mybatis.utils.OpLogUtil;
import com.lframework.xingyun.sc.entity.TakeStockConfig;
import com.lframework.xingyun.sc.mappers.TakeStockConfigMapper;
import com.lframework.xingyun.sc.service.stock.take.ITakeStockConfigService;
import com.lframework.xingyun.sc.vo.stock.take.config.UpdateTakeStockConfigVo;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TakeStockConfigServiceImpl extends
    BaseMpServiceImpl<TakeStockConfigMapper, TakeStockConfig>
    implements ITakeStockConfigService {

  @Cacheable(value = TakeStockConfig.CACHE_NAME, key = "'config'", unless = "#result == null")
  @Override
  public TakeStockConfig get() {

    return getBaseMapper().selectOne(Wrappers.query());
  }

  @OpLog(type = OpLogType.OTHER, name = "修改盘点参数，ID：{}", params = {"#id"})
  @Transactional
  @Override
  public void update(UpdateTakeStockConfigVo vo) {

    TakeStockConfig data = getBaseMapper().selectById(vo.getId());
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("盘点参数不存在！");
    }

    LambdaUpdateWrapper<TakeStockConfig> updateWrapper = Wrappers.lambdaUpdate(
            TakeStockConfig.class)
        .set(TakeStockConfig::getShowProduct, vo.getShowProduct())
        .set(TakeStockConfig::getShowStock, vo.getShowStock())
        .set(TakeStockConfig::getAutoChangeStock, vo.getAutoChangeStock())
        .set(TakeStockConfig::getAllowChangeNum, vo.getAllowChangeNum())
        .set(TakeStockConfig::getCancelHours, vo.getCancelHours())
        .eq(TakeStockConfig::getId, vo.getId());

    getBaseMapper().update(updateWrapper);

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setExtra(vo);
  }

  @CacheEvict(value = TakeStockConfig.CACHE_NAME, key = "'config'")
  @Override
  public void cleanCacheByKey(String key) {

  }
}

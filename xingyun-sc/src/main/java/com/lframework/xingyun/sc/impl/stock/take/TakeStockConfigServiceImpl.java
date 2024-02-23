package com.lframework.xingyun.sc.impl.stock.take;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.ObjectUtil;
import com.lframework.starter.web.impl.BaseMpServiceImpl;
import com.lframework.xingyun.sc.entity.TakeStockConfig;
import com.lframework.xingyun.sc.enums.ScOpLogType;
import com.lframework.xingyun.sc.mappers.TakeStockConfigMapper;
import com.lframework.xingyun.sc.service.stock.take.TakeStockConfigService;
import com.lframework.xingyun.sc.vo.stock.take.config.UpdateTakeStockConfigVo;
import com.lframework.xingyun.template.core.annotations.OpLog;
import com.lframework.xingyun.template.core.utils.OpLogUtil;
import java.io.Serializable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TakeStockConfigServiceImpl extends
    BaseMpServiceImpl<TakeStockConfigMapper, TakeStockConfig>
    implements TakeStockConfigService {

  @Cacheable(value = TakeStockConfig.CACHE_NAME, key = "@cacheVariables.tenantId() + 'config'", unless = "#result == null")
  @Override
  public TakeStockConfig get() {

    return getBaseMapper().selectOne(Wrappers.query());
  }

  @OpLog(type = ScOpLogType.TAKE_STOCK, name = "修改盘点参数，ID：{}", params = {"#id"})
  @Transactional(rollbackFor = Exception.class)
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

  @CacheEvict(value = TakeStockConfig.CACHE_NAME, key = "@cacheVariables.tenantId() + 'config'")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}

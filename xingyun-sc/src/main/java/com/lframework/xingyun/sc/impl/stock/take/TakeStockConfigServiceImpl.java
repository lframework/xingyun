package com.lframework.xingyun.sc.impl.stock.take;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.ObjectUtil;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.enums.OpLogType;
import com.lframework.starter.mybatis.utils.OpLogUtil;
import com.lframework.xingyun.sc.dto.stock.take.config.TakeStockConfigDto;
import com.lframework.xingyun.sc.entity.TakeStockConfig;
import com.lframework.xingyun.sc.mappers.TakeStockConfigMapper;
import com.lframework.xingyun.sc.service.stock.take.ITakeStockConfigService;
import com.lframework.xingyun.sc.vo.stock.take.config.UpdateTakeStockConfigVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TakeStockConfigServiceImpl implements ITakeStockConfigService {

    @Autowired
    private TakeStockConfigMapper takeStockConfigMapper;

    @Cacheable(value = TakeStockConfigDto.CACHE_NAME, key = "'config'", unless = "#result == null")
    @Override
    public TakeStockConfigDto get() {

        return takeStockConfigMapper.get();
    }

    @OpLog(type = OpLogType.OTHER, name = "修改盘点参数，ID：{}", params = {"#id"})
    @Transactional
    @Override
    public void update(UpdateTakeStockConfigVo vo) {

        TakeStockConfig data = takeStockConfigMapper.selectById(vo.getId());
        if (ObjectUtil.isNull(data)) {
            throw new DefaultClientException("盘点参数不存在！");
        }

        LambdaUpdateWrapper<TakeStockConfig> updateWrapper = Wrappers.lambdaUpdate(TakeStockConfig.class)
                .set(TakeStockConfig::getShowProduct, vo.getShowProduct())
                .set(TakeStockConfig::getShowStock, vo.getShowStock())
                .set(TakeStockConfig::getAutoChangeStock, vo.getAutoChangeStock())
                .set(TakeStockConfig::getAllowChangeNum, vo.getAllowChangeNum())
                .set(TakeStockConfig::getCancelHours, vo.getCancelHours())
                .eq(TakeStockConfig::getId, vo.getId());

        takeStockConfigMapper.update(updateWrapper);

        OpLogUtil.setVariable("id", data.getId());
        OpLogUtil.setExtra(vo);

        ITakeStockConfigService thisService = getThis(this.getClass());
        thisService.cleanCacheByKey(data.getId());

    }

    @CacheEvict(value = TakeStockConfigDto.CACHE_NAME, key = "'config'")
    @Override
    public void cleanCacheByKey(String key) {

    }
}

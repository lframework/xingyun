package com.lframework.xingyun.sc.impl.retail;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.enums.OpLogType;
import com.lframework.starter.mybatis.utils.OpLogUtil;
import com.lframework.xingyun.sc.dto.retail.config.RetailConfigDto;
import com.lframework.xingyun.sc.entity.RetailConfig;
import com.lframework.xingyun.sc.mappers.RetailConfigMapper;
import com.lframework.xingyun.sc.service.retail.IRetailConfigService;
import com.lframework.xingyun.sc.vo.retail.config.UpdateRetailConfigVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RetailConfigServiceImpl implements IRetailConfigService {

  @Autowired
  private RetailConfigMapper retailConfigMapper;

  @Cacheable(value = RetailConfigDto.CACHE_NAME, key = "'config'", unless = "#result == null")
  @Override
  public RetailConfigDto get() {

    RetailConfig config = retailConfigMapper.selectOne(Wrappers.query());

    RetailConfigDto result = new RetailConfigDto();

    result.setId(config.getId());
    result.setRetailReturnRequireOutStock(config.getRetailReturnRequireOutStock());
    result.setRetailReturnMultipleRelateOutStock(config.getRetailReturnMultipleRelateOutStock());

    return result;
  }

  @OpLog(type = OpLogType.OTHER, name = "修改零售参数设置")
  @Transactional
  @Override
  public void update(UpdateRetailConfigVo vo) {

    RetailConfig config = retailConfigMapper.selectOne(Wrappers.query());
    config.setRetailReturnRequireOutStock(vo.getRetailReturnRequireOutStock());
    config.setRetailReturnMultipleRelateOutStock(vo.getRetailReturnMultipleRelateOutStock());

    retailConfigMapper.updateById(config);

    OpLogUtil.setExtra(vo);

    IRetailConfigService thisService = getThis(this.getClass());
    thisService.cleanCacheByKey(config.getId());
  }

  @CacheEvict(value = RetailConfigDto.CACHE_NAME, key = "'config'")
  @Override
  public void cleanCacheByKey(String key) {

  }
}

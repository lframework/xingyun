package com.lframework.xingyun.sc.impl.retail;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.enums.OpLogType;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.mybatis.utils.OpLogUtil;
import com.lframework.xingyun.sc.entity.RetailConfig;
import com.lframework.xingyun.sc.mappers.RetailConfigMapper;
import com.lframework.xingyun.sc.service.retail.IRetailConfigService;
import com.lframework.xingyun.sc.vo.retail.config.UpdateRetailConfigVo;
import java.io.Serializable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RetailConfigServiceImpl extends BaseMpServiceImpl<RetailConfigMapper, RetailConfig>
    implements IRetailConfigService {

  @Cacheable(value = RetailConfig.CACHE_NAME, key = "'config'", unless = "#result == null")
  @Override
  public RetailConfig get() {

    RetailConfig config = getBaseMapper().selectOne(Wrappers.query());

    return config;
  }

  @OpLog(type = OpLogType.OTHER, name = "修改零售参数设置")
  @Transactional
  @Override
  public void update(UpdateRetailConfigVo vo) {

    RetailConfig config = getBaseMapper().selectOne(Wrappers.query());
    config.setRetailReturnRequireOutStock(vo.getRetailReturnRequireOutStock());
    config.setRetailReturnMultipleRelateOutStock(vo.getRetailReturnMultipleRelateOutStock());

    getBaseMapper().updateById(config);

    OpLogUtil.setExtra(vo);
  }

  @CacheEvict(value = RetailConfig.CACHE_NAME, key = "'config'")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}

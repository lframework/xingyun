package com.lframework.xingyun.sc.impl.retail;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.web.impl.BaseMpServiceImpl;
import com.lframework.xingyun.sc.entity.RetailConfig;
import com.lframework.xingyun.sc.enums.ScOpLogType;
import com.lframework.xingyun.sc.mappers.RetailConfigMapper;
import com.lframework.xingyun.sc.service.retail.RetailConfigService;
import com.lframework.xingyun.sc.vo.retail.config.UpdateRetailConfigVo;
import com.lframework.xingyun.core.annotations.OpLog;
import com.lframework.xingyun.core.utils.OpLogUtil;
import java.io.Serializable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RetailConfigServiceImpl extends BaseMpServiceImpl<RetailConfigMapper, RetailConfig>
    implements RetailConfigService {

  @Cacheable(value = RetailConfig.CACHE_NAME, key = "@cacheVariables.tenantId() + 'config'", unless = "#result == null")
  @Override
  public RetailConfig get() {

    RetailConfig config = getBaseMapper().selectOne(Wrappers.query());

    return config;
  }

  @OpLog(type = ScOpLogType.RETAIL, name = "修改零售参数设置")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateRetailConfigVo vo) {

    RetailConfig config = getBaseMapper().selectOne(Wrappers.query());
    config.setRetailReturnRequireOutStock(vo.getRetailReturnRequireOutStock());
    config.setRetailReturnMultipleRelateOutStock(vo.getRetailReturnMultipleRelateOutStock());
    config.setRetailOutSheetRequireMember(vo.getRetailOutSheetRequireMember());
    config.setRetailReturnRequireMember(vo.getRetailReturnRequireMember());
    config.setRetailOutSheetRequireLogistics(vo.getRetailOutSheetRequireLogistics());

    getBaseMapper().updateById(config);

    OpLogUtil.setExtra(vo);
  }

  @CacheEvict(value = RetailConfig.CACHE_NAME, key = "@cacheVariables.tenantId() + 'config'")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}

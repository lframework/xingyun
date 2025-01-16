package com.lframework.xingyun.sc.impl.purchase;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.web.impl.BaseMpServiceImpl;
import com.lframework.xingyun.sc.entity.PurchaseConfig;
import com.lframework.xingyun.sc.enums.ScOpLogType;
import com.lframework.xingyun.sc.mappers.PurchaseConfigMapper;
import com.lframework.xingyun.sc.service.purchase.PurchaseConfigService;
import com.lframework.xingyun.sc.vo.purchase.config.UpdatePurchaseConfigVo;
import com.lframework.xingyun.core.annotations.OpLog;
import com.lframework.xingyun.core.utils.OpLogUtil;
import java.io.Serializable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PurchaseConfigServiceImpl extends
    BaseMpServiceImpl<PurchaseConfigMapper, PurchaseConfig>
    implements PurchaseConfigService {

  @Cacheable(value = PurchaseConfig.CACHE_NAME, key = "@cacheVariables.tenantId() + 'config'", unless = "#result == null")
  @Override
  public PurchaseConfig get() {

    PurchaseConfig config = getBaseMapper().selectOne(Wrappers.query());

    return config;
  }

  @OpLog(type = ScOpLogType.PURCHASE, name = "修改采购参数设置")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdatePurchaseConfigVo vo) {

    PurchaseConfig config = getBaseMapper().selectOne(Wrappers.query());
    config.setReceiveRequirePurchase(vo.getReceiveRequirePurchase());
    config.setReceiveMultipleRelatePurchase(vo.getReceiveMultipleRelatePurchase());
    config.setPurchaseReturnRequireReceive(vo.getPurchaseReturnRequireReceive());
    config.setPurchaseReturnMultipleRelateReceive(vo.getPurchaseReturnMultipleRelateReceive());

    getBaseMapper().updateById(config);

    OpLogUtil.setExtra(vo);
  }

  @CacheEvict(value = PurchaseConfig.CACHE_NAME, key = "@cacheVariables.tenantId() + 'config'")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}

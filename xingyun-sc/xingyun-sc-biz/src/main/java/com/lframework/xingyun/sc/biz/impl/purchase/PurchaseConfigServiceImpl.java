package com.lframework.xingyun.sc.biz.impl.purchase;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.enums.OpLogType;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.mybatis.utils.OpLogUtil;
import com.lframework.xingyun.sc.biz.mappers.PurchaseConfigMapper;
import com.lframework.xingyun.sc.biz.service.purchase.IPurchaseConfigService;
import com.lframework.xingyun.sc.facade.entity.PurchaseConfig;
import com.lframework.xingyun.sc.facade.vo.purchase.config.UpdatePurchaseConfigVo;
import java.io.Serializable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PurchaseConfigServiceImpl extends
    BaseMpServiceImpl<PurchaseConfigMapper, PurchaseConfig>
    implements IPurchaseConfigService {

  @Cacheable(value = PurchaseConfig.CACHE_NAME, key = "'config'", unless = "#result == null")
  @Override
  public PurchaseConfig get() {

    PurchaseConfig config = getBaseMapper().selectOne(Wrappers.query());

    return config;
  }

  @OpLog(type = OpLogType.OTHER, name = "修改采购参数设置")
  @Transactional
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

  @CacheEvict(value = PurchaseConfig.CACHE_NAME, key = "'config'")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}

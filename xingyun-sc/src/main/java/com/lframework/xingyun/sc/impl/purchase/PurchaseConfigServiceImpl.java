package com.lframework.xingyun.sc.impl.purchase;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.bpm.entity.FlowDefinitionWrapper;
import com.lframework.starter.bpm.service.FlowDefinitionWrapperService;
import com.lframework.starter.web.core.annotations.oplog.OpLog;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.starter.web.core.utils.OpLogUtil;
import com.lframework.xingyun.sc.entity.PurchaseConfig;
import com.lframework.xingyun.sc.enums.PurchaseOpLogType;
import com.lframework.xingyun.sc.mappers.PurchaseConfigMapper;
import com.lframework.xingyun.sc.service.purchase.PurchaseConfigService;
import com.lframework.xingyun.sc.vo.purchase.config.UpdatePurchaseConfigVo;
import java.io.Serializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PurchaseConfigServiceImpl extends
    BaseMpServiceImpl<PurchaseConfigMapper, PurchaseConfig>
    implements PurchaseConfigService {

  @Autowired
  private FlowDefinitionWrapperService flowDefinitionWrapperService;

  @Cacheable(value = PurchaseConfig.CACHE_NAME, key = "@cacheVariables.tenantId() + 'config'", unless = "#result == null")
  @Override
  public PurchaseConfig get() {

    PurchaseConfig config = getBaseMapper().selectOne(Wrappers.query());

    return config;
  }

  @OpLog(type = PurchaseOpLogType.class, name = "修改采购参数设置")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdatePurchaseConfigVo vo) {

    PurchaseConfig config = getBaseMapper().selectOne(Wrappers.query());
    LambdaUpdateWrapper<PurchaseConfig> updateWrapper = Wrappers.lambdaUpdate(PurchaseConfig.class)
        .eq(PurchaseConfig::getId, config.getId())
        .set(PurchaseConfig::getReceiveRequirePurchase, vo.getReceiveRequirePurchase())
        .set(PurchaseConfig::getReceiveMultipleRelatePurchase,
            vo.getReceiveMultipleRelatePurchase())
        .set(PurchaseConfig::getPurchaseReturnRequireReceive, vo.getPurchaseReturnRequireReceive())
        .set(PurchaseConfig::getPurchaseReturnMultipleRelateReceive,
            vo.getPurchaseReturnMultipleRelateReceive())
        .set(PurchaseConfig::getPurchaseRequireBpm, vo.getPurchaseRequireBpm());

    if (vo.getPurchaseRequireBpm()) {
      FlowDefinitionWrapper flowDefinition = flowDefinitionWrapperService.getById(vo.getPurchaseBpmProcessId());
      updateWrapper.set(PurchaseConfig::getPurchaseBpmProcessId, flowDefinition.getId())
          .set(PurchaseConfig::getPurchaseBpmProcessCode, flowDefinition.getFlowCode());
    } else {
      updateWrapper.set(PurchaseConfig::getPurchaseBpmProcessId, null)
          .set(PurchaseConfig::getPurchaseBpmProcessCode, null);
    }

    this.update(updateWrapper);

    OpLogUtil.setExtra(vo);
  }

  @CacheEvict(value = PurchaseConfig.CACHE_NAME, key = "@cacheVariables.tenantId() + 'config'")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}

package com.lframework.xingyun.sc.impl.purchase;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.enums.OpLogType;
import com.lframework.starter.mybatis.utils.OpLogUtil;
import com.lframework.xingyun.sc.dto.purchase.config.PurchaseConfigDto;
import com.lframework.xingyun.sc.entity.PurchaseConfig;
import com.lframework.xingyun.sc.mappers.PurchaseConfigMapper;
import com.lframework.xingyun.sc.service.purchase.IPurchaseConfigService;
import com.lframework.xingyun.sc.vo.purchase.config.UpdatePurchaseConfigVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PurchaseConfigServiceImpl implements IPurchaseConfigService {

  @Autowired
  private PurchaseConfigMapper purchaseConfigMapper;

  @Cacheable(value = PurchaseConfigDto.CACHE_NAME, key = "'config'", unless = "#result == null")
  @Override
  public PurchaseConfigDto get() {

    PurchaseConfig config = purchaseConfigMapper.selectOne(Wrappers.query());

    PurchaseConfigDto result = new PurchaseConfigDto();
    result.setId(config.getId());
    result.setReceiveRequirePurchase(config.getReceiveRequirePurchase());
    result.setReceiveMultipleRelatePurchase(config.getReceiveMultipleRelatePurchase());
    result.setPurchaseReturnRequireReceive(config.getPurchaseReturnRequireReceive());
    result.setPurchaseReturnMultipleRelateReceive(config.getPurchaseReturnMultipleRelateReceive());

    return result;
  }

  @OpLog(type = OpLogType.OTHER, name = "修改采购参数设置")
  @Transactional
  @Override
  public void update(UpdatePurchaseConfigVo vo) {

    PurchaseConfig config = purchaseConfigMapper.selectOne(Wrappers.query());
    config.setReceiveRequirePurchase(vo.getReceiveRequirePurchase());
    config.setReceiveMultipleRelatePurchase(vo.getReceiveMultipleRelatePurchase());
    config.setPurchaseReturnRequireReceive(vo.getPurchaseReturnRequireReceive());
    config.setPurchaseReturnMultipleRelateReceive(vo.getPurchaseReturnMultipleRelateReceive());

    purchaseConfigMapper.updateById(config);

    OpLogUtil.setExtra(vo);

    IPurchaseConfigService thisService = getThis(this.getClass());
    thisService.cleanCacheByKey(config.getId());
  }

  @CacheEvict(value = PurchaseConfigDto.CACHE_NAME, key = "'config'")
  @Override
  public void cleanCacheByKey(String key) {

  }
}

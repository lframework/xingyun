package com.lframework.xingyun.sc.impl.sale;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.web.impl.BaseMpServiceImpl;
import com.lframework.xingyun.sc.entity.SaleConfig;
import com.lframework.xingyun.sc.enums.ScOpLogType;
import com.lframework.xingyun.sc.mappers.SaleConfigMapper;
import com.lframework.xingyun.sc.service.sale.SaleConfigService;
import com.lframework.xingyun.sc.vo.sale.config.UpdateSaleConfigVo;
import com.lframework.xingyun.core.annotations.OpLog;
import com.lframework.xingyun.core.utils.OpLogUtil;
import java.io.Serializable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaleConfigServiceImpl extends BaseMpServiceImpl<SaleConfigMapper, SaleConfig>
    implements SaleConfigService {

  @Cacheable(value = SaleConfig.CACHE_NAME, key = "@cacheVariables.tenantId() + 'config'", unless = "#result == null")
  @Override
  public SaleConfig get() {

    SaleConfig config = getBaseMapper().selectOne(Wrappers.query());

    return config;
  }

  @OpLog(type = ScOpLogType.SALE, name = "修改销售参数设置")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateSaleConfigVo vo) {

    SaleConfig config = getBaseMapper().selectOne(Wrappers.query());
    config.setOutStockRequireSale(vo.getOutStockRequireSale());
    config.setOutStockMultipleRelateSale(vo.getOutStockMultipleRelateSale());
    config.setSaleReturnRequireOutStock(vo.getSaleReturnRequireOutStock());
    config.setSaleReturnMultipleRelateOutStock(vo.getSaleReturnMultipleRelateOutStock());
    config.setOutStockRequireLogistics(vo.getOutStockRequireLogistics());

    getBaseMapper().updateById(config);

    OpLogUtil.setExtra(vo);
  }

  @CacheEvict(value = SaleConfig.CACHE_NAME, key = "@cacheVariables.tenantId() + 'config'")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}

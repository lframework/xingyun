package com.lframework.xingyun.sc.biz.impl.sale;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.enums.OpLogType;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.mybatis.utils.OpLogUtil;
import com.lframework.xingyun.sc.biz.mappers.SaleConfigMapper;
import com.lframework.xingyun.sc.biz.service.sale.ISaleConfigService;
import com.lframework.xingyun.sc.facade.entity.SaleConfig;
import com.lframework.xingyun.sc.facade.vo.sale.config.UpdateSaleConfigVo;
import java.io.Serializable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaleConfigServiceImpl extends BaseMpServiceImpl<SaleConfigMapper, SaleConfig>
    implements ISaleConfigService {

  @Cacheable(value = SaleConfig.CACHE_NAME, key = "'config'", unless = "#result == null")
  @Override
  public SaleConfig get() {

    SaleConfig config = getBaseMapper().selectOne(Wrappers.query());

    return config;
  }

  @OpLog(type = OpLogType.OTHER, name = "修改销售参数设置")
  @Transactional
  @Override
  public void update(UpdateSaleConfigVo vo) {

    SaleConfig config = getBaseMapper().selectOne(Wrappers.query());
    config.setOutStockRequireSale(vo.getOutStockRequireSale());
    config.setOutStockMultipleRelateSale(vo.getOutStockMultipleRelateSale());
    config.setSaleReturnRequireOutStock(vo.getSaleReturnRequireOutStock());
    config.setSaleReturnMultipleRelateOutStock(vo.getSaleReturnMultipleRelateOutStock());

    getBaseMapper().updateById(config);

    OpLogUtil.setExtra(vo);
  }

  @CacheEvict(value = SaleConfig.CACHE_NAME, key = "'config'")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}

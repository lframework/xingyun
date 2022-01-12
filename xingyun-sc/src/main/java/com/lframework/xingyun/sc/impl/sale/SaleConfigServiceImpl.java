package com.lframework.xingyun.sc.impl.sale;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.enums.OpLogType;
import com.lframework.starter.mybatis.utils.OpLogUtil;
import com.lframework.xingyun.sc.dto.sale.config.SaleConfigDto;
import com.lframework.xingyun.sc.entity.SaleConfig;
import com.lframework.xingyun.sc.mappers.SaleConfigMapper;
import com.lframework.xingyun.sc.service.sale.ISaleConfigService;
import com.lframework.xingyun.sc.vo.sale.config.UpdateSaleConfigVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaleConfigServiceImpl implements ISaleConfigService {

    @Autowired
    private SaleConfigMapper saleConfigMapper;

    @Cacheable(value = SaleConfigDto.CACHE_NAME, key = "'config'", unless = "#result == null")
    @Override
    public SaleConfigDto get() {

        SaleConfig config = saleConfigMapper.selectOne(Wrappers.query());

        SaleConfigDto result = new SaleConfigDto();

        result.setId(config.getId());
        result.setOutStockRequireSale(config.getOutStockRequireSale());
        result.setOutStockMultipleRelateSale(config.getOutStockMultipleRelateSale());
        result.setSaleReturnRequireOutStock(config.getSaleReturnRequireOutStock());
        result.setSaleReturnMultipleRelateOutStock(config.getSaleReturnMultipleRelateOutStock());

        return result;
    }

    @OpLog(type = OpLogType.OTHER, name = "修改销售参数设置")
    @Transactional
    @Override
    public void update(UpdateSaleConfigVo vo) {

        SaleConfig config = saleConfigMapper.selectOne(Wrappers.query());
        config.setOutStockRequireSale(vo.getOutStockRequireSale());
        config.setOutStockMultipleRelateSale(vo.getOutStockMultipleRelateSale());
        config.setSaleReturnRequireOutStock(vo.getSaleReturnRequireOutStock());
        config.setSaleReturnMultipleRelateOutStock(vo.getSaleReturnMultipleRelateOutStock());

        saleConfigMapper.updateById(config);

        OpLogUtil.setExtra(vo);

        ISaleConfigService thisService = getThis(this.getClass());
        thisService.cleanCacheByKey(config.getId());
    }

    @CacheEvict(value = SaleConfigDto.CACHE_NAME, key = "'config'")
    @Override
    public void cleanCacheByKey(String key) {

    }
}

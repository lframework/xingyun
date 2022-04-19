package com.lframework.xingyun.sc.impl.stock;

import com.github.pagehelper.PageInfo;
import com.lframework.common.utils.Assert;
import com.lframework.common.utils.IdUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageHelperUtil;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.web.utils.EnumUtil;
import com.lframework.xingyun.sc.dto.stock.ProductLotDto;
import com.lframework.xingyun.sc.dto.stock.ProductLotWithStockDto;
import com.lframework.xingyun.sc.entity.ProductLot;
import com.lframework.xingyun.sc.enums.ProductStockBizType;
import com.lframework.xingyun.sc.mappers.ProductLotMapper;
import com.lframework.xingyun.sc.service.stock.IProductLotService;
import com.lframework.xingyun.sc.vo.stock.lot.CreateProductLotVo;
import com.lframework.xingyun.sc.vo.stock.lot.QueryProductLotVo;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductLotServiceImpl extends
    BaseMpServiceImpl<ProductLotMapper, ProductLot> implements IProductLotService {

  @Override
  public PageResult<ProductLotWithStockDto> query(Integer pageIndex, Integer pageSize,
      QueryProductLotVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<ProductLotWithStockDto> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<ProductLotWithStockDto> query(QueryProductLotVo vo) {

    return getBaseMapper().query(vo);
  }

  @Cacheable(value = ProductLotDto.CACHE_NAME, key = "#id", unless = "#result == null")
  @Override
  public ProductLotDto getById(String id) {

    return getBaseMapper().getById(id);
  }

  @Transactional
  @Override
  public String create(CreateProductLotVo vo) {

    ProductLot record = new ProductLot();
    record.setId(IdUtil.getId());
    record.setLotCode(vo.getLotCode());
    record.setProductId(vo.getProductId());
    record.setSupplierId(vo.getSupplierId());
    record.setTaxRate(vo.getTaxRate());
    record.setCreateTime(vo.getCreateTime());
    if (!StringUtil.isBlank(vo.getBizId())) {
      record.setBizId(vo.getBizId());
    }
    if (!StringUtil.isBlank(vo.getBizDetailId())) {
      record.setBizDetailId(vo.getBizDetailId());
    }
    if (!StringUtil.isBlank(vo.getBizCode())) {
      record.setBizCode(vo.getBizCode());
    }
    record.setBizType(EnumUtil.getByCode(ProductStockBizType.class, vo.getBizType()));

    getBaseMapper().insert(record);

    return record.getId();
  }

  @Override
  public ProductLotWithStockDto getLastPurchaseLot(String productId, String scId,
      String supplierId) {
    return getBaseMapper().getLastPurchaseLot(productId, scId, supplierId);
  }
}

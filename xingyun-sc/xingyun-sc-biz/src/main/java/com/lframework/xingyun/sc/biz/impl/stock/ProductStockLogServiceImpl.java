package com.lframework.xingyun.sc.biz.impl.stock;

import com.github.pagehelper.PageInfo;
import com.lframework.common.utils.Assert;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageHelperUtil;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.web.utils.EnumUtil;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.xingyun.sc.biz.mappers.ProductStockLogMapper;
import com.lframework.xingyun.sc.biz.service.stock.IProductStockLogService;
import com.lframework.xingyun.sc.facade.entity.ProductStockLog;
import com.lframework.xingyun.sc.facade.enums.ProductStockBizType;
import com.lframework.xingyun.sc.facade.vo.stock.log.AddLogWithAddStockVo;
import com.lframework.xingyun.sc.facade.vo.stock.log.AddLogWithStockCostAdjustVo;
import com.lframework.xingyun.sc.facade.vo.stock.log.AddLogWithSubStockVo;
import com.lframework.xingyun.sc.facade.vo.stock.log.QueryProductStockLogVo;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductStockLogServiceImpl extends
    BaseMpServiceImpl<ProductStockLogMapper, ProductStockLog>
    implements IProductStockLogService {

  @Override
  public PageResult<ProductStockLog> query(Integer pageIndex, Integer pageSize,
      QueryProductStockLogVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<ProductStockLog> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<ProductStockLog> query(QueryProductStockLogVo vo) {

    return getBaseMapper().query(vo);
  }

  @Transactional
  @Override
  public void addLogWithAddStock(AddLogWithAddStockVo vo) {

    ProductStockLog record = new ProductStockLog();
    record.setId(IdUtil.getId());
    record.setScId(vo.getScId());
    record.setProductId(vo.getProductId());
    record.setLotId(vo.getLotId());
    record.setOriStockNum(vo.getOriStockNum());
    record.setCurStockNum(vo.getCurStockNum());
    record.setOriTaxPrice(vo.getOriTaxPrice());
    record.setCurTaxPrice(vo.getCurTaxPrice());
    record.setOriUnTaxPrice(vo.getOriUnTaxPrice());
    record.setCurUnTaxPrice(vo.getCurUnTaxPrice());
    record.setStockNum(vo.getStockNum());
    record.setTaxAmount(vo.getTaxAmount());
    record.setUnTaxAmount(vo.getUnTaxAmount());
    if (!StringUtil.isBlank(vo.getCreateBy())) {
      record.setCreateBy(vo.getCreateBy());
    }
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
  }

  @Transactional
  @Override
  public void addLogWithSubStock(AddLogWithSubStockVo vo) {

    ProductStockLog record = new ProductStockLog();
    record.setId(IdUtil.getId());
    record.setScId(vo.getScId());
    record.setProductId(vo.getProductId());
    record.setLotId(vo.getLotId());
    record.setOriStockNum(vo.getOriStockNum());
    record.setCurStockNum(vo.getCurStockNum());
    record.setOriTaxPrice(vo.getOriTaxPrice());
    record.setCurTaxPrice(vo.getCurTaxPrice());
    record.setOriUnTaxPrice(vo.getOriUnTaxPrice());
    record.setCurUnTaxPrice(vo.getCurUnTaxPrice());
    record.setStockNum(-Math.abs(vo.getStockNum()));
    record.setTaxAmount(vo.getTaxAmount().abs().negate());
    record.setUnTaxAmount(vo.getUnTaxAmount().abs().negate());
    if (!StringUtil.isBlank(vo.getCreateBy())) {
      record.setCreateBy(vo.getCreateBy());
    }
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
  }

  @Transactional
  @Override
  public void addLogWithStockCostAdjust(AddLogWithStockCostAdjustVo vo) {

    ProductStockLog record = new ProductStockLog();
    record.setId(IdUtil.getId());
    record.setScId(vo.getScId());
    record.setProductId(vo.getProductId());
    record.setLotId(vo.getLotId());
    record.setOriStockNum(vo.getOriStockNum());
    record.setCurStockNum(vo.getOriStockNum());
    record.setOriTaxPrice(vo.getOriTaxPrice());
    record.setCurTaxPrice(vo.getCurTaxPrice());
    record.setOriUnTaxPrice(vo.getOriUnTaxPrice());
    record.setCurUnTaxPrice(vo.getCurUnTaxPrice());
    record.setStockNum(0);
    record.setTaxAmount(vo.getTaxAmount());
    record.setUnTaxAmount(vo.getUnTaxAmount());
    if (!StringUtil.isBlank(vo.getCreateBy())) {
      record.setCreateBy(vo.getCreateBy());
    }
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
    record.setBizType(ProductStockBizType.STOCK_COST_ADJUST);

    getBaseMapper().insert(record);
  }
}

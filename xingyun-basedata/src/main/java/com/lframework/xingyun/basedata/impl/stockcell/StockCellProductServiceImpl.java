package com.lframework.xingyun.basedata.impl.stockcell;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.starter.web.core.utils.IdUtil;
import com.lframework.starter.web.core.utils.PageHelperUtil;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.xingyun.basedata.dto.stockcell.product.StockCellProductDto;
import com.lframework.xingyun.basedata.entity.StockCell;
import com.lframework.xingyun.basedata.entity.StockCellProduct;
import com.lframework.xingyun.basedata.mappers.StockCellProductMapper;
import com.lframework.xingyun.basedata.service.stockcell.StockCellProductService;
import com.lframework.xingyun.basedata.service.stockcell.StockCellService;
import com.lframework.xingyun.basedata.vo.stockcell.product.CreateStockCellProductVo;
import com.lframework.xingyun.basedata.vo.stockcell.product.QueryStockCellProductVo;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockCellProductServiceImpl extends
    BaseMpServiceImpl<StockCellProductMapper, StockCellProduct>
    implements StockCellProductService {

  @Autowired
  private StockCellService stockCellService;

  @Override
  public PageResult<StockCellProductDto> query(Integer pageIndex, Integer pageSize,
      QueryStockCellProductVo vo) {
    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<StockCellProductDto> datas = getBaseMapper().query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void create(CreateStockCellProductVo vo) {
    List<StockCellProduct> stockCellProductList = this.list(
        Wrappers.lambdaQuery(StockCellProduct.class)
            .eq(StockCellProduct::getStockCellId, vo.getStockCellId())
            .in(StockCellProduct::getProductId, vo.getProductIds()));
    if (CollectionUtil.isNotEmpty(stockCellProductList)) {
      this.removeByIds(
          stockCellProductList.stream().map(StockCellProduct::getId).collect(Collectors.toList()));
    }

    StockCell stockCell = stockCellService.getById(vo.getStockCellId());

    List<StockCellProduct> recordList = vo.getProductIds().stream().map(productId -> {
      StockCellProduct stockCellProduct = new StockCellProduct();
      stockCellProduct.setId(IdUtil.getId());
      stockCellProduct.setScId(stockCell.getScId());
      stockCellProduct.setStockCellId(vo.getStockCellId());
      stockCellProduct.setProductId(productId);
      return stockCellProduct;
    }).collect(Collectors.toList());

    this.saveBatch(recordList);
  }
}

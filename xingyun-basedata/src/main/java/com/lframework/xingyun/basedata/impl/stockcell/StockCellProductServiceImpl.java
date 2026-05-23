package com.lframework.xingyun.basedata.impl.stockcell;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.starter.web.core.utils.IdUtil;
import com.lframework.starter.web.core.utils.PageHelperUtil;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.xingyun.basedata.dto.stockcell.product.StockCellProductDto;
import com.lframework.xingyun.basedata.entity.Product;
import com.lframework.xingyun.basedata.entity.ProductSku;
import com.lframework.xingyun.basedata.entity.StockCell;
import com.lframework.xingyun.basedata.entity.StockCellProduct;
import com.lframework.xingyun.basedata.mappers.StockCellProductMapper;
import com.lframework.xingyun.basedata.service.product.ProductService;
import com.lframework.xingyun.basedata.service.product.ProductSkuService;
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

  @Autowired
  private ProductSkuService productSkuService;

  @Autowired
  private ProductService productService;

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
    if (CollectionUtil.isEmpty(vo.getSkuIds())) {
      throw new DefaultClientException("请选择商品！");
    }

    List<StockCellProduct> stockCellProductList = this.list(
        Wrappers.lambdaQuery(StockCellProduct.class)
            .eq(StockCellProduct::getStockCellId, vo.getStockCellId())
            .in(StockCellProduct::getSkuId, vo.getSkuIds()));
    if (CollectionUtil.isNotEmpty(stockCellProductList)) {
      this.removeByIds(
          stockCellProductList.stream().map(StockCellProduct::getId).collect(Collectors.toList()));
    }

    StockCell stockCell = stockCellService.getById(vo.getStockCellId());

    List<StockCellProduct> recordList = vo.getSkuIds().stream().map(skuId -> {
      ProductSku sku = productSkuService.findById(skuId);
      if (sku == null || !sku.getAvailable()) {
        throw new DefaultClientException("SKU不存在！");
      }
      Product product = productService.findById(sku.getProductId());
      if (product == null || !product.getAvailable()) {
        throw new DefaultClientException("商品不存在！");
      }

      StockCellProduct stockCellProduct = new StockCellProduct();
      stockCellProduct.setId(IdUtil.getId());
      stockCellProduct.setScId(stockCell.getScId());
      stockCellProduct.setStockCellId(vo.getStockCellId());
      stockCellProduct.setProductId(sku.getProductId());
      stockCellProduct.setSkuId(sku.getId());
      return stockCellProduct;
    }).collect(Collectors.toList());

    this.saveBatch(recordList);
  }
}

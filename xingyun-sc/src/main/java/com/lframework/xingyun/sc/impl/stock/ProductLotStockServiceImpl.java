package com.lframework.xingyun.sc.impl.stock;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.IdUtil;
import com.lframework.common.utils.NumberUtil;
import com.lframework.xingyun.basedata.dto.product.info.ProductDto;
import com.lframework.xingyun.basedata.service.product.IProductService;
import com.lframework.xingyun.sc.dto.stock.ProductLotDto;
import com.lframework.xingyun.sc.dto.stock.ProductLotStockDto;
import com.lframework.xingyun.sc.entity.ProductLotStock;
import com.lframework.xingyun.sc.mappers.ProductLotStockMapper;
import com.lframework.xingyun.sc.service.stock.IProductLotService;
import com.lframework.xingyun.sc.service.stock.IProductLotStockService;
import com.lframework.xingyun.sc.vo.stock.lot.AddProductLotStockVo;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductLotStockServiceImpl implements IProductLotStockService {

  @Autowired
  private ProductLotStockMapper productLotStockMapper;

  @Autowired
  private IProductService productService;

  @Autowired
  private IProductLotService productLotService;

  @Override
  public List<ProductLotStockDto> getWithSubStock(String productId, String scId, String supplierId,
      Integer num) {

    List<ProductLotStockDto> datas = productLotStockMapper.getFifoList(productId, scId, supplierId);
    int totalNum = 0;

    List<ProductLotStockDto> results = new ArrayList<>();
    for (ProductLotStockDto data : datas) {
      totalNum += data.getStockNum();
      results.add(data);
      if (totalNum >= num) {
        break;
      }
    }

    if (totalNum < num) {
      //库存不足
      return null;
    }

    return results;
  }

  @Transactional
  @Override
  public void subStockById(String id, Integer num) {

    ProductLotStock lotStock = productLotStockMapper.selectById(id);
    ProductLotDto lot = productLotService.getById(lotStock.getLotId());

    if (NumberUtil.lt(lotStock.getStockNum(), num)) {
      ProductDto product = productService.getById(lot.getProductId());
      throw new DefaultClientException(
          "商品（" + product.getCode() + "）" + product.getName() + "当前库存不足，无法出库！");
    }

    int count = productLotStockMapper.subStockById(id, num);
    if (count != 1) {
      ProductDto product = productService.getById(lot.getProductId());
      throw new DefaultClientException(
          "商品（" + product.getCode() + "）" + product.getName() + "出库失败，请稍后重试！");
    }
  }

  @Override
  public ProductLotStockDto getById(String id) {

    return productLotStockMapper.getById(id);
  }

  @Override
  public ProductLotStockDto getByScIdAndLotId(String scId, String lotId) {

    return productLotStockMapper.getByScIdAndLotId(scId, lotId);
  }

  @Transactional
  @Override
  public String addStock(AddProductLotStockVo vo) {

    ProductLotStock record = new ProductLotStock();
    record.setId(IdUtil.getId());
    record.setLotId(vo.getLotId());
    record.setScId(vo.getScId());
    record.setStockNum(vo.getStockNum());

    productLotStockMapper.insert(record);

    return record.getId();
  }

  @Override
  public List<ProductLotStockDto> getAllHasStockLots(String productId, String scId) {
    return productLotStockMapper.getAllHasStockLots(productId, scId);
  }
}

package com.lframework.xingyun.sc.biz.impl.stock;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.NumberUtil;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.xingyun.basedata.facade.ProductFeignClient;
import com.lframework.xingyun.basedata.facade.dto.product.info.ProductDto;
import com.lframework.xingyun.sc.biz.mappers.ProductLotStockMapper;
import com.lframework.xingyun.sc.biz.service.stock.IProductLotService;
import com.lframework.xingyun.sc.biz.service.stock.IProductLotStockService;
import com.lframework.xingyun.sc.facade.entity.ProductLot;
import com.lframework.xingyun.sc.facade.entity.ProductLotStock;
import com.lframework.xingyun.sc.facade.vo.stock.lot.AddProductLotStockVo;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductLotStockServiceImpl extends
    BaseMpServiceImpl<ProductLotStockMapper, ProductLotStock>
    implements IProductLotStockService {

  @Autowired
  private ProductFeignClient productFeignClient;

  @Autowired
  private IProductLotService productLotService;

  @Override
  public List<ProductLotStock> getWithSubStock(String productId, String scId, String supplierId,
      Integer num) {

    List<ProductLotStock> datas = getBaseMapper().getFifoList(productId, scId, supplierId);
    int totalNum = 0;

    List<ProductLotStock> results = new ArrayList<>();
    for (ProductLotStock data : datas) {
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

    ProductLotStock lotStock = getBaseMapper().selectById(id);
    ProductLot lot = productLotService.findById(lotStock.getLotId());

    if (NumberUtil.lt(lotStock.getStockNum(), num)) {
      ProductDto product = productFeignClient.findById(lot.getProductId()).getData();
      throw new DefaultClientException(
          "商品（" + product.getCode() + "）" + product.getName() + "当前库存不足，无法出库！");
    }

    int count = getBaseMapper().subStockById(id, num);
    if (count != 1) {
      ProductDto product = productFeignClient.findById(lot.getProductId()).getData();
      throw new DefaultClientException(
          "商品（" + product.getCode() + "）" + product.getName() + "出库失败，请稍后重试！");
    }
  }

  @Override
  public ProductLotStock getByScIdAndLotId(String scId, String lotId) {

    return getBaseMapper().getByScIdAndLotId(scId, lotId);
  }

  @Transactional
  @Override
  public String addStock(AddProductLotStockVo vo) {

    ProductLotStock record = new ProductLotStock();
    record.setId(IdUtil.getId());
    record.setLotId(vo.getLotId());
    record.setScId(vo.getScId());
    record.setStockNum(vo.getStockNum());

    getBaseMapper().insert(record);

    return record.getId();
  }

  @Override
  public List<ProductLotStock> getAllHasStockLots(String productId, String scId) {

    return getBaseMapper().getAllHasStockLots(productId, scId);
  }
}

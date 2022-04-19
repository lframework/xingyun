package com.lframework.xingyun.sc.service.stock;

import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.xingyun.sc.dto.stock.ProductLotStockDto;
import com.lframework.xingyun.sc.entity.ProductLotStock;
import com.lframework.xingyun.sc.vo.stock.lot.AddProductLotStockVo;
import java.util.List;

public interface IProductLotStockService extends BaseMpService<ProductLotStock> {

  /**
   * 查询列表（出库业务） 出库规则：先进先出
   *
   * @param productId
   * @param scId
   * @param supplierId
   * @param num
   * @return 如果返回结果为空，则代表库存不足
   */
  List<ProductLotStockDto> getWithSubStock(String productId, String scId, String supplierId,
      Integer num);

  /**
   * 根据ID出库
   *
   * @param id
   * @param num
   */
  void subStockById(String id, Integer num);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  ProductLotStockDto getById(String id);

  /**
   * 根据仓库ID、批次ID查询
   *
   * @param scId
   * @param lotId
   * @return
   */
  ProductLotStockDto getByScIdAndLotId(String scId, String lotId);

  /**
   * 入库
   *
   * @param vo
   * @return
   */
  String addStock(AddProductLotStockVo vo);

  /**
   * 查询所有有库存的批次库存
   *
   * @param productId
   * @param scId
   * @return
   */
  List<ProductLotStockDto> getAllHasStockLots(String productId, String scId);
}

package com.lframework.xingyun.sc.biz.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.sc.facade.entity.ProductLotStock;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductLotStockMapper extends BaseMapper<ProductLotStock> {

  /**
   * 查询列表（先进先出）
   *
   * @param productId
   * @param scId
   * @return
   */
  List<ProductLotStock> getFifoList(@Param("productId") String productId,
      @Param("scId") String scId,
      @Param("supplierId") String supplierId);

  /**
   * 根据ID出库
   *
   * @param id
   * @param num
   */
  int subStockById(@Param("id") String id, @Param("num") Integer num);

  /**
   * 根据仓库ID、批次ID查询
   *
   * @param scId
   * @param lotId
   * @return
   */
  ProductLotStock getByScIdAndLotId(@Param("scId") String scId, @Param("lotId") String lotId);

  /**
   * 查询所有有库存的批次库存
   *
   * @param productId
   * @param scId
   * @return
   */
  List<ProductLotStock> getAllHasStockLots(@Param("productId") String productId,
      @Param("scId") String scId);
}

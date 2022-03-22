package com.lframework.xingyun.sc.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.sc.dto.stock.ProductLotDto;
import com.lframework.xingyun.sc.dto.stock.ProductLotWithStockDto;
import com.lframework.xingyun.sc.entity.ProductLot;
import com.lframework.xingyun.sc.vo.stock.lot.QueryProductLotVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-10-12
 */
public interface ProductLotMapper extends BaseMapper<ProductLot> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<ProductLotWithStockDto> query(@Param("vo") QueryProductLotVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  ProductLotDto getById(String id);

  /**
   * 查询末次采购入库的批次信息
   *
   * @param productId
   * @param scId
   * @param supplierId
   * @return
   */
  ProductLotWithStockDto getLastPurchaseLot(@Param("productId") String productId,
      @Param("scId") String scId, @Param("supplierId") String supplierId);
}

package com.lframework.xingyun.sc.biz.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.sc.facade.dto.stock.ProductLotWithStockDto;
import com.lframework.xingyun.sc.facade.entity.ProductLot;
import com.lframework.xingyun.sc.facade.vo.stock.lot.QueryProductLotVo;
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
   * 查询末次采购入库的批次信息
   *
   * @param productId
   * @param scId
   * @param supplierId
   * @return
   */
  ProductLotWithStockDto getLastPurchaseLot(@Param("productId") String productId,
      @Param("scId") String scId,
      @Param("supplierId") String supplierId);
}

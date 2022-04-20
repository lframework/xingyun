package com.lframework.xingyun.sc.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.sc.entity.ProductStock;
import com.lframework.xingyun.sc.vo.stock.QueryProductStockVo;
import java.math.BigDecimal;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-09-12
 */
public interface ProductStockMapper extends BaseMapper<ProductStock> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<ProductStock> query(@Param("vo") QueryProductStockVo vo);

  /**
   * 根据商品ID、仓库ID查询
   *
   * @param productId
   * @param scId
   * @return
   */
  ProductStock getByProductIdAndScId(@Param("productId") String productId,
      @Param("scId") String scId);

  /**
   * 根据商品ID、仓库ID查询
   *
   * @param productIds
   * @param scId
   * @return
   */
  List<ProductStock> getByProductIdsAndScId(@Param("productIds") List<String> productIds,
      @Param("scId") String scId);

  /**
   * 入库
   *
   * @param productId
   * @param scId
   * @param stockNum
   * @param taxAmount
   * @param oriStockNum
   * @param oriTaxAmount
   * @return
   */
  int addStock(@Param("productId") String productId, @Param("scId") String scId,
      @Param("stockNum") Integer stockNum,
      @Param("taxAmount") BigDecimal taxAmount, @Param("unTaxAmount") BigDecimal unTaxAmount,
      @Param("oriStockNum") Integer oriStockNum, @Param("oriTaxAmount") BigDecimal oriTaxAmount,
      @Param("reCalcCostPrice") boolean reCalcCostPrice);

  /**
   * 出库
   *
   * @param productId
   * @param scId
   * @param stockNum
   * @param taxAmount
   * @param oriStockNum
   * @param oriTaxAmount
   * @return
   */
  int subStock(@Param("productId") String productId, @Param("scId") String scId,
      @Param("stockNum") Integer stockNum,
      @Param("taxAmount") BigDecimal taxAmount, @Param("unTaxAmount") BigDecimal unTaxAmount,
      @Param("oriStockNum") Integer oriStockNum, @Param("oriTaxAmount") BigDecimal oriTaxAmount,
      @Param("reCalcCostPrice") boolean reCalcCostPrice);

  /**
   * 库存成本调整
   *
   * @param productId
   * @param scId
   * @param taxPrice
   * @param unTaxPrice
   */
  void stockCostAdjust(@Param("productId") String productId, @Param("scId") String scId,
      BigDecimal taxPrice,
      BigDecimal unTaxPrice);
}

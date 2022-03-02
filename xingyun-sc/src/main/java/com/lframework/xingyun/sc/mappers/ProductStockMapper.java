package com.lframework.xingyun.sc.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.sc.dto.stock.ProductStockDto;
import com.lframework.xingyun.sc.entity.ProductStock;
import com.lframework.xingyun.sc.vo.stock.QueryProductStockVo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-09-12
 */
public interface ProductStockMapper extends BaseMapper<ProductStock> {

    /**
     * 查询列表
     * @param vo
     * @return
     */
    List<ProductStockDto> query(@Param("vo") QueryProductStockVo vo);

    /**
     * 根据商品ID、仓库ID查询
     * @param productId
     * @param scId
     * @return
     */
    ProductStockDto getByProductIdAndScId(@Param("productId") String productId, @Param("scId") String scId);

    /**
     * 根据商品ID、仓库ID查询
     * @param productIds
     * @param scId
     * @return
     */
    List<ProductStockDto> getByProductIdsAndScId(@Param("productIds") List<String> productIds, @Param("scId") String scId);

    /**
     * 入库
     * @param productId
     * @param scId
     * @param stockNum
     * @param taxAmount
     * @param oriStockNum
     * @param oriTaxAmount
     * @return
     */
    int addStock(@Param("productId") String productId, @Param("scId") String scId, @Param("stockNum") Integer stockNum,
            @Param("taxAmount") BigDecimal taxAmount, @Param("unTaxAmount") BigDecimal unTaxAmount,
            @Param("oriStockNum") Integer oriStockNum, @Param("oriTaxAmount") BigDecimal oriTaxAmount,
            @Param("reCalcCostPrice") boolean reCalcCostPrice);

    /**
     * 出库
     * @param productId
     * @param scId
     * @param stockNum
     * @param taxAmount
     * @param oriStockNum
     * @param oriTaxAmount
     * @return
     */
    int subStock(@Param("productId") String productId, @Param("scId") String scId, @Param("stockNum") Integer stockNum,
            @Param("taxAmount") BigDecimal taxAmount, @Param("unTaxAmount") BigDecimal unTaxAmount,
            @Param("oriStockNum") Integer oriStockNum, @Param("oriTaxAmount") BigDecimal oriTaxAmount,
            @Param("reCalcCostPrice") boolean reCalcCostPrice);
}

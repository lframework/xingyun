package com.lframework.xingyun.basedata.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.basedata.dto.product.info.*;
import com.lframework.xingyun.basedata.entity.Product;
import com.lframework.xingyun.basedata.vo.product.info.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-07-11
 */
public interface ProductMapper extends BaseMapper<Product> {

    /**
     * 查询列表
     * @param vo
     * @return
     */
    List<ProductDto> query(@Param("vo") QueryProductVo vo);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    ProductDto getById(String id);

    /**
     * 根据ID查询详情
     * @param id
     * @return
     */
    GetProductDto getDetailById(String id);

    /**
     * 根据关键字查询采购商品信息
     * @param condition
     * @return
     */
    List<PurchaseProductDto> queryPurchaseByCondition(String condition);

    /**
     * 查询可采购商品信息
     * @param vo
     * @return
     */
    List<PurchaseProductDto> queryPurchaseList(@Param("vo") QueryPurchaseProductVo vo);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    PurchaseProductDto getPurchaseById(String id);

    /**
     * 根据关键字销售采购商品信息
     * @param condition
     * @return
     */
    List<SaleProductDto> querySaleByCondition(String condition);

    /**
     * 查询可销售商品信息
     * @param vo
     * @return
     */
    List<SaleProductDto> querySaleList(@Param("vo") QuerySaleProductVo vo);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    SaleProductDto getSaleById(String id);

    /**
     * 根据关键字零售采购商品信息
     * @param condition
     * @return
     */
    List<RetailProductDto> queryRetailByCondition(String condition);

    /**
     * 查询可零售商品信息
     * @param vo
     * @return
     */
    List<RetailProductDto> queryRetailList(@Param("vo") QueryRetailProductVo vo);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    RetailProductDto getRetailById(String id);

    /**
     * 根据关键字查询预先盘点单商品信息
     * @param condition
     * @return
     */
    List<PreTakeStockProductDto> queryPreTakeStockByCondition(String condition);

    /**
     * 查询预先盘点单商品信息
     * @param vo
     * @return
     */
    List<PreTakeStockProductDto> queryPreTakeStockList(@Param("vo") QueryPreTakeStockProductVo vo);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    PreTakeStockProductDto getPreTakeStockById(String id);
}

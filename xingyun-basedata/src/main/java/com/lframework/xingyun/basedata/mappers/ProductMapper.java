package com.lframework.xingyun.basedata.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.basedata.dto.product.info.GetProductDto;
import com.lframework.xingyun.basedata.dto.product.info.PreTakeStockProductDto;
import com.lframework.xingyun.basedata.dto.product.info.ProductDto;
import com.lframework.xingyun.basedata.dto.product.info.PurchaseProductDto;
import com.lframework.xingyun.basedata.dto.product.info.RetailProductDto;
import com.lframework.xingyun.basedata.dto.product.info.SaleProductDto;
import com.lframework.xingyun.basedata.dto.product.info.StockCostAdjustProductDto;
import com.lframework.xingyun.basedata.dto.product.info.TakeStockSheetProductDto;
import com.lframework.xingyun.basedata.entity.Product;
import com.lframework.xingyun.basedata.vo.product.info.QueryPreTakeStockProductVo;
import com.lframework.xingyun.basedata.vo.product.info.QueryProductVo;
import com.lframework.xingyun.basedata.vo.product.info.QueryPurchaseProductVo;
import com.lframework.xingyun.basedata.vo.product.info.QueryRetailProductVo;
import com.lframework.xingyun.basedata.vo.product.info.QuerySaleProductVo;
import com.lframework.xingyun.basedata.vo.product.info.QueryStockCostAdjustProductVo;
import com.lframework.xingyun.basedata.vo.product.info.QueryTakeStockSheetProductVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-07-11
 */
public interface ProductMapper extends BaseMapper<Product> {

    /**
     * 查询列表
     *
     * @param vo
     * @return
     */
    List<ProductDto> query(@Param("vo") QueryProductVo vo);

    /**
     * 查询商品品种数
     *
     * @param vo
     * @return
     */
    Integer queryCount(@Param("vo") QueryProductVo vo);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    ProductDto findById(String id);

    /**
     * 根据ID查询详情
     *
     * @param id
     * @return
     */
    GetProductDto getDetailById(String id);

    /**
     * 根据关键字查询采购商品信息
     *
     * @param condition
     * @return
     */
    List<PurchaseProductDto> queryPurchaseByCondition(String condition);

    /**
     * 查询可采购商品信息
     *
     * @param vo
     * @return
     */
    List<PurchaseProductDto> queryPurchaseList(@Param("vo") QueryPurchaseProductVo vo);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    PurchaseProductDto getPurchaseById(String id);

    /**
     * 根据关键字销售采购商品信息
     *
     * @param condition
     * @return
     */
    List<SaleProductDto> querySaleByCondition(String condition);

    /**
     * 查询可销售商品信息
     *
     * @param vo
     * @return
     */
    List<SaleProductDto> querySaleList(@Param("vo") QuerySaleProductVo vo);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    SaleProductDto getSaleById(String id);

    /**
     * 根据关键字零售采购商品信息
     *
     * @param condition
     * @return
     */
    List<RetailProductDto> queryRetailByCondition(String condition);

    /**
     * 查询可零售商品信息
     *
     * @param vo
     * @return
     */
    List<RetailProductDto> queryRetailList(@Param("vo") QueryRetailProductVo vo);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    RetailProductDto getRetailById(String id);

    /**
     * 根据关键字查询预先盘点单商品信息
     *
     * @param condition
     * @return
     */
    List<PreTakeStockProductDto> queryPreTakeStockByCondition(String condition);

    /**
     * 查询预先盘点单商品信息
     *
     * @param vo
     * @return
     */
    List<PreTakeStockProductDto> queryPreTakeStockList(@Param("vo") QueryPreTakeStockProductVo vo);

    /**
     * 根据类目ID查询
     *
     * @param categoryIds
     * @return
     */
    List<ProductDto> getByCategoryIds(@Param("categoryIds") List<String> categoryIds);

    /**
     * 根据品牌ID查询
     *
     * @param brandIds
     * @return
     */
    List<ProductDto> getByBrandIds(@Param("brandIds") List<String> brandIds);

    /**
     * 根据关键字查询盘点单商品信息
     *
     * @param condition
     * @return
     */
    List<TakeStockSheetProductDto> queryTakeStockByCondition(@Param("planId") String planId,
            @Param("condition") String condition);

    /**
     * 查询盘点单商品信息
     *
     * @param vo
     * @return
     */
    List<TakeStockSheetProductDto> queryTakeStockList(@Param("vo") QueryTakeStockSheetProductVo vo);

    /**
     * 根据关键字查询库存成本调整单商品信息
     *
     * @param scId
     * @param condition
     * @return
     */
    List<StockCostAdjustProductDto> queryStockCostAdjustByCondition(String scId, String condition);

    /**
     * 查询库存成本调整单商品信息
     *
     * @param vo
     * @return
     */
    List<StockCostAdjustProductDto> queryStockCostAdjustList(@Param("vo") QueryStockCostAdjustProductVo vo);
}

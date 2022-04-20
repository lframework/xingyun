package com.lframework.xingyun.basedata.service.product;

import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.xingyun.basedata.dto.product.info.GetProductDto;
import com.lframework.xingyun.basedata.dto.product.info.PreTakeStockProductDto;
import com.lframework.xingyun.basedata.dto.product.info.ProductDto;
import com.lframework.xingyun.basedata.dto.product.info.PurchaseProductDto;
import com.lframework.xingyun.basedata.dto.product.info.RetailProductDto;
import com.lframework.xingyun.basedata.dto.product.info.SaleProductDto;
import com.lframework.xingyun.basedata.dto.product.info.StockCostAdjustProductDto;
import com.lframework.xingyun.basedata.dto.product.info.TakeStockSheetProductDto;
import com.lframework.xingyun.basedata.entity.Product;
import com.lframework.xingyun.basedata.vo.product.info.CreateProductVo;
import com.lframework.xingyun.basedata.vo.product.info.QueryPreTakeStockProductVo;
import com.lframework.xingyun.basedata.vo.product.info.QueryProductVo;
import com.lframework.xingyun.basedata.vo.product.info.QueryPurchaseProductVo;
import com.lframework.xingyun.basedata.vo.product.info.QueryRetailProductVo;
import com.lframework.xingyun.basedata.vo.product.info.QuerySaleProductVo;
import com.lframework.xingyun.basedata.vo.product.info.QueryStockCostAdjustProductVo;
import com.lframework.xingyun.basedata.vo.product.info.QueryTakeStockSheetProductVo;
import com.lframework.xingyun.basedata.vo.product.info.UpdateProductVo;
import java.util.Collection;
import java.util.List;

public interface IProductService extends BaseMpService<Product> {

    /**
     * 查询列表
     *
     * @return
     */
    PageResult<ProductDto> query(Integer pageIndex, Integer pageSize, QueryProductVo vo);

    /**
     * 查询列表
     *
     * @param vo
     * @return
     */
    List<ProductDto> query(QueryProductVo vo);

    /**
     * 查询商品品种数
     *
     * @param vo
     * @return
     */
    Integer queryCount(QueryProductVo vo);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    ProductDto findById(String id);

    /**
     * 根据ID查询明细
     *
     * @param id
     * @return
     */
    GetProductDto getDetailById(String id);

    /**
     * 根据ID停用
     *
     * @param ids
     */
    void batchUnable(Collection<String> ids);

    /**
     * 根据ID启用
     *
     * @param ids
     */
    void batchEnable(Collection<String> ids);

    /**
     * 创建
     *
     * @param vo
     * @return
     */
    String create(CreateProductVo vo);

    /**
     * 修改
     *
     * @param vo
     */
    void update(UpdateProductVo vo);

    /**
     * 根据关键字查询采购商品信息
     *
     * @param pageIndex
     * @param pageSize
     * @param condition
     * @return
     */
    PageResult<PurchaseProductDto> queryPurchaseByCondition(Integer pageIndex, Integer pageSize, String condition);

    /**
     * 查询可采购商品信息
     *
     * @param pageIndex
     * @param pageSize
     * @param vo
     * @return
     */
    PageResult<PurchaseProductDto> queryPurchaseList(Integer pageIndex, Integer pageSize, QueryPurchaseProductVo vo);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    PurchaseProductDto getPurchaseById(String id);

    /**
     * 根据关键字查询销售商品信息
     *
     * @param pageIndex
     * @param pageSize
     * @param condition
     * @return
     */
    PageResult<SaleProductDto> querySaleByCondition(Integer pageIndex, Integer pageSize, String condition);

    /**
     * 查询可销售商品信息
     *
     * @param pageIndex
     * @param pageSize
     * @param vo
     * @return
     */
    PageResult<SaleProductDto> querySaleList(Integer pageIndex, Integer pageSize, QuerySaleProductVo vo);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    SaleProductDto getSaleById(String id);

    /**
     * 根据关键字查询零售商品信息
     *
     * @param pageIndex
     * @param pageSize
     * @param condition
     * @return
     */
    PageResult<RetailProductDto> queryRetailByCondition(Integer pageIndex, Integer pageSize, String condition);

    /**
     * 查询可零售商品信息
     *
     * @param pageIndex
     * @param pageSize
     * @param vo
     * @return
     */
    PageResult<RetailProductDto> queryRetailList(Integer pageIndex, Integer pageSize, QueryRetailProductVo vo);

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
     * @param pageIndex
     * @param pageSize
     * @param condition
     * @return
     */
    PageResult<PreTakeStockProductDto> queryPreTakeStockByCondition(Integer pageIndex, Integer pageSize,
            String condition);

    /**
     * 查询预先盘点单商品信息
     *
     * @param pageIndex
     * @param pageSize
     * @param vo
     * @return
     */
    PageResult<PreTakeStockProductDto> queryPreTakeStockList(Integer pageIndex, Integer pageSize,
            QueryPreTakeStockProductVo vo);

    /**
     * 根据类目ID查询
     *
     * @param categoryIds
     * @return
     */
    List<ProductDto> getByCategoryIds(List<String> categoryIds);

    /**
     * 根据品牌ID查询
     *
     * @param brandIds
     * @return
     */
    List<ProductDto> getByBrandIds(List<String> brandIds);

    /**
     * 根据关键字查询盘点单商品信息
     *
     * @param pageIndex
     * @param pageSize
     * @param condition
     * @return
     */
    PageResult<TakeStockSheetProductDto> queryTakeStockByCondition(Integer pageIndex, Integer pageSize, String planId,
            String condition);

    /**
     * 查询盘点单商品信息
     *
     * @param pageIndex
     * @param pageSize
     * @param vo
     * @return
     */
    PageResult<TakeStockSheetProductDto> queryTakeStockList(Integer pageIndex, Integer pageSize,
            QueryTakeStockSheetProductVo vo);

    /**
     * 根据关键字查询库存成本调整单商品信息
     *
     * @param pageIndex
     * @param pageSize
     * @param scId
     * @param condition
     * @return
     */
    PageResult<StockCostAdjustProductDto> queryStockCostAdjustByCondition(Integer pageIndex, Integer pageSize,
            String scId, String condition);

    /**
     * 查询库存成本调整单商品信息
     *
     * @param pageIndex
     * @param pageSize
     * @param vo
     * @return
     */
    PageResult<StockCostAdjustProductDto> queryStockCostAdjustList(Integer pageIndex, Integer pageSize,
            QueryStockCostAdjustProductVo vo);
}

package com.lframework.xingyun.sc.service.stock;

import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.web.service.BaseService;
import com.lframework.xingyun.sc.dto.stock.ProductStockChangeDto;
import com.lframework.xingyun.sc.dto.stock.ProductStockDto;
import com.lframework.xingyun.sc.vo.stock.AddProductStockVo;
import com.lframework.xingyun.sc.vo.stock.QueryProductStockVo;
import com.lframework.xingyun.sc.vo.stock.SubProductStockVo;

import java.util.List;

public interface IProductStockService extends BaseService {

    /**
     * 查询列表
     * @param pageIndex
     * @param pageSize
     * @param vo
     * @return
     */
    PageResult<ProductStockDto> query(Integer pageIndex, Integer pageSize, QueryProductStockVo vo);

    /**
     * 查询列表
     * @param vo
     * @return
     */
    List<ProductStockDto> query(QueryProductStockVo vo);

    /**
     * 根据商品ID、仓库ID查询
     * @param productId
     * @param scId
     * @return
     */
    ProductStockDto getByProductIdAndScId(String productId, String scId);

    /**
     * 根据商品ID、仓库ID查询
     * @param productIds
     * @param scId
     * @return
     */
    List<ProductStockDto> getByProductIdsAndScId(List<String> productIds, String scId);

    /**
     * 入库
     * @param vo
     */
    ProductStockChangeDto addStock(AddProductStockVo vo);

    /**
     * 出库
     * @param vo
     */
    ProductStockChangeDto subStock(SubProductStockVo vo);
}

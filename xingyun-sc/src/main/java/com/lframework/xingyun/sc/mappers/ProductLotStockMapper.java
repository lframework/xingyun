package com.lframework.xingyun.sc.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.sc.dto.stock.ProductLotStockDto;
import com.lframework.xingyun.sc.entity.ProductLotStock;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductLotStockMapper extends BaseMapper<ProductLotStock> {

    /**
     * 查询列表（先进先出）
     * @param productId
     * @param scId
     * @return
     */
    List<ProductLotStockDto> getFifoList(@Param("productId") String productId, @Param("scId") String scId,
            @Param("supplierId") String supplierId);

    /**
     * 根据ID出库
     * @param id
     * @param num
     */
    int subStockById(@Param("id") String id, @Param("num") Integer num);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    ProductLotStockDto getById(String id);

    /**
     * 根据仓库ID、批次ID查询
     * @param scId
     * @param lotId
     * @return
     */
    ProductLotStockDto getByScIdAndLotId(@Param("scId") String scId, @Param("lotId") String lotId);

    /**
     * 查询所有有库存的批次库存
     * @param productId
     * @param scId
     * @return
     */
    List<ProductLotStockDto> getAllHasStockLots(@Param("productId") String productId, @Param("scId") String scId);
}

package com.lframework.xingyun.sc.service.stock;

import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.web.service.BaseService;
import com.lframework.xingyun.sc.dto.stock.ProductStockLogDto;
import com.lframework.xingyun.sc.vo.stock.log.AddLogWithAddStockVo;
import com.lframework.xingyun.sc.vo.stock.log.AddLogWithSubStockVo;
import com.lframework.xingyun.sc.vo.stock.log.QueryProductStockLogVo;

import java.util.List;

public interface IProductStockLogService extends BaseService {

    /**
     * 查询列表
     * @param pageIndex
     * @param pageSize
     * @param vo
     * @return
     */
    PageResult<ProductStockLogDto> query(Integer pageIndex, Integer pageSize, QueryProductStockLogVo vo);

    /**
     * 查询列表
     * @param vo
     * @return
     */
    List<ProductStockLogDto> query(QueryProductStockLogVo vo);

    /**
     * 添加入库记录
     */
    void addLogWithAddStock(AddLogWithAddStockVo vo);

    /**
     * 添加出库记录
     */
    void addLogWithSubStock(AddLogWithSubStockVo vo);
}

package com.lframework.xingyun.basedata.service.stockcell;

import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.xingyun.basedata.dto.stockcell.product.StockCellProductDto;
import com.lframework.xingyun.basedata.entity.StockCellProduct;
import com.lframework.xingyun.basedata.vo.stockcell.product.CreateStockCellProductVo;
import com.lframework.xingyun.basedata.vo.stockcell.product.QueryStockCellProductVo;

public interface StockCellProductService extends BaseMpService<StockCellProduct> {

  /**
   * 查询列表
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<StockCellProductDto> query(Integer pageIndex, Integer pageSize,
      QueryStockCellProductVo vo);

  /**
   * 新增
   *
   * @param vo
   */
  void create(CreateStockCellProductVo vo);
}

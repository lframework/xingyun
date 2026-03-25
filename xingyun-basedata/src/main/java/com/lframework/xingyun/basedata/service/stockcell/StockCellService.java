package com.lframework.xingyun.basedata.service.stockcell;

import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.xingyun.basedata.dto.stockcell.StockCellDto;
import com.lframework.xingyun.basedata.entity.StockCell;
import com.lframework.xingyun.basedata.vo.stockcell.CreateStockCellVo;
import com.lframework.xingyun.basedata.vo.stockcell.QueryStockCellSelectorVo;
import com.lframework.xingyun.basedata.vo.stockcell.QueryStockCellVo;
import com.lframework.xingyun.basedata.vo.stockcell.UpdateStockCellVo;

public interface StockCellService extends BaseMpService<StockCell> {

  /**
   * 查询列表
   *
   * @return
   */
  PageResult<StockCellDto> query(Integer pageIndex, Integer pageSize, QueryStockCellVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  StockCellDto findById(String id);

  /**
   * 根据ID删除(逻辑删除)
   *
   * @param id
   */
  void deleteById(String id);

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  String create(CreateStockCellVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateStockCellVo vo);

  /**
   * 选择器
   *
   * @return
   */
  PageResult<StockCellDto> selector(Integer pageIndex, Integer pageSize,
      QueryStockCellSelectorVo vo);
}

package com.lframework.xingyun.sc.biz.service.stock;

import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.xingyun.sc.facade.entity.ProductStockLog;
import com.lframework.xingyun.sc.facade.vo.stock.log.AddLogWithAddStockVo;
import com.lframework.xingyun.sc.facade.vo.stock.log.AddLogWithStockCostAdjustVo;
import com.lframework.xingyun.sc.facade.vo.stock.log.AddLogWithSubStockVo;
import com.lframework.xingyun.sc.facade.vo.stock.log.QueryProductStockLogVo;
import java.util.List;

public interface IProductStockLogService extends BaseMpService<ProductStockLog> {

  /**
   * 查询列表
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<ProductStockLog> query(Integer pageIndex, Integer pageSize, QueryProductStockLogVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<ProductStockLog> query(QueryProductStockLogVo vo);

  /**
   * 添加入库记录
   */
  void addLogWithAddStock(AddLogWithAddStockVo vo);

  /**
   * 添加出库记录
   */
  void addLogWithSubStock(AddLogWithSubStockVo vo);

  /**
   * 添加库存成本调整记录
   *
   * @param vo
   */
  void addLogWithStockCostAdjust(AddLogWithStockCostAdjustVo vo);
}

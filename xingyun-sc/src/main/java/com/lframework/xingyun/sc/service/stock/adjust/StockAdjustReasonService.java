package com.lframework.xingyun.sc.service.stock.adjust;

import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.service.BaseMpService;
import com.lframework.xingyun.sc.entity.StockAdjustReason;
import com.lframework.xingyun.sc.vo.stock.adjust.stock.reason.CreateStockAdjustReasonVo;
import com.lframework.xingyun.sc.vo.stock.adjust.stock.reason.QueryStockAdjustReasonVo;
import com.lframework.xingyun.sc.vo.stock.adjust.stock.reason.StockAdjustReasonSelectorVo;
import com.lframework.xingyun.sc.vo.stock.adjust.stock.reason.UpdateStockAdjustReasonVo;
import java.util.Collection;
import java.util.List;

public interface StockAdjustReasonService extends BaseMpService<StockAdjustReason> {

  /**
   * 查询列表
   *
   * @return
   */
  PageResult<StockAdjustReason> query(Integer pageIndex, Integer pageSize,
      QueryStockAdjustReasonVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<StockAdjustReason> query(QueryStockAdjustReasonVo vo);

  /**
   * 选择器
   *
   * @return
   */
  PageResult<StockAdjustReason> selector(Integer pageIndex, Integer pageSize,
      StockAdjustReasonSelectorVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  StockAdjustReason findById(String id);

  /**
   * 根据ID停用
   *
   * @param id
   */
  void unable(String id);

  /**
   * 根据ID启用
   *
   * @param id
   */
  void enable(String id);

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  String create(CreateStockAdjustReasonVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateStockAdjustReasonVo vo);
}

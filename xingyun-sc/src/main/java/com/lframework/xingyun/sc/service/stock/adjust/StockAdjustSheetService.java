package com.lframework.xingyun.sc.service.stock.adjust;

import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.service.BaseMpService;
import com.lframework.xingyun.sc.dto.stock.adjust.stock.StockAdjustProductDto;
import com.lframework.xingyun.sc.dto.stock.adjust.stock.StockAdjustSheetFullDto;
import com.lframework.xingyun.sc.entity.StockAdjustSheet;
import com.lframework.xingyun.sc.vo.stock.adjust.stock.ApprovePassStockAdjustSheetVo;
import com.lframework.xingyun.sc.vo.stock.adjust.stock.ApproveRefuseStockAdjustSheetVo;
import com.lframework.xingyun.sc.vo.stock.adjust.stock.CreateStockAdjustSheetVo;
import com.lframework.xingyun.sc.vo.stock.adjust.stock.QueryStockAdjustProductVo;
import com.lframework.xingyun.sc.vo.stock.adjust.stock.QueryStockAdjustSheetVo;
import com.lframework.xingyun.sc.vo.stock.adjust.stock.UpdateStockAdjustSheetVo;
import java.util.List;

/**
 * 库存调整单 Service
 *
 * @author zmj
 */
public interface StockAdjustSheetService extends BaseMpService<StockAdjustSheet> {

  /**
   * 查询列表
   *
   * @return
   */
  PageResult<StockAdjustSheet> query(Integer pageIndex, Integer pageSize,
      QueryStockAdjustSheetVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<StockAdjustSheet> query(QueryStockAdjustSheetVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  StockAdjustSheetFullDto getDetail(String id);

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  String create(CreateStockAdjustSheetVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateStockAdjustSheetVo vo);

  /**
   * 根据ID删除
   *
   * @param id
   * @return
   */
  void deleteById(String id);

  /**
   * 审核通过
   *
   * @param vo
   */
  void approvePass(ApprovePassStockAdjustSheetVo vo);

  /**
   * 直接审核通过
   *
   * @param vo
   */
  String directApprovePass(CreateStockAdjustSheetVo vo);

  /**
   * 审核拒绝
   *
   * @param vo
   */
  void approveRefuse(ApproveRefuseStockAdjustSheetVo vo);

  /**
   * 根据关键字查询库存调整单商品信息
   *
   * @param pageIndex
   * @param pageSize
   * @param scId
   * @param condition
   * @return
   */
  PageResult<StockAdjustProductDto> queryStockAdjustByCondition(Integer pageIndex, Integer pageSize,
      String scId, String condition);

  /**
   * 查询库存调整单商品信息
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<StockAdjustProductDto> queryStockAdjustList(Integer pageIndex, Integer pageSize,
      QueryStockAdjustProductVo vo);
}

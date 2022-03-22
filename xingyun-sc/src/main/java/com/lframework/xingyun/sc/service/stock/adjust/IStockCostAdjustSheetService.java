package com.lframework.xingyun.sc.service.stock.adjust;

import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.web.service.BaseService;
import com.lframework.xingyun.sc.dto.stock.adjust.StockCostAdjustSheetDto;
import com.lframework.xingyun.sc.dto.stock.adjust.StockCostAdjustSheetFullDto;
import com.lframework.xingyun.sc.vo.stock.adjust.ApprovePassStockCostAdjustSheetVo;
import com.lframework.xingyun.sc.vo.stock.adjust.ApproveRefuseStockCostAdjustSheetVo;
import com.lframework.xingyun.sc.vo.stock.adjust.BatchApprovePassStockCostAdjustSheetVo;
import com.lframework.xingyun.sc.vo.stock.adjust.BatchApproveRefuseStockCostAdjustSheetVo;
import com.lframework.xingyun.sc.vo.stock.adjust.CreateStockCostAdjustSheetVo;
import com.lframework.xingyun.sc.vo.stock.adjust.QueryStockCostAdjustSheetVo;
import com.lframework.xingyun.sc.vo.stock.adjust.UpdateStockCostAdjustSheetVo;
import java.util.List;

/**
 * 库存成本调整单 Service
 *
 * @author zmj
 */
public interface IStockCostAdjustSheetService extends BaseService {

  /**
   * 查询列表
   *
   * @return
   */
  PageResult<StockCostAdjustSheetDto> query(Integer pageIndex, Integer pageSize,
      QueryStockCostAdjustSheetVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<StockCostAdjustSheetDto> query(QueryStockCostAdjustSheetVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  StockCostAdjustSheetDto getById(String id);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  StockCostAdjustSheetFullDto getDetail(String id);

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  String create(CreateStockCostAdjustSheetVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateStockCostAdjustSheetVo vo);

  /**
   * 根据ID删除
   *
   * @param id
   * @return
   */
  void deleteById(String id);

  /**
   * 根据IDs删除
   *
   * @param ids
   */
  void deleteByIds(List<String> ids);

  /**
   * 审核通过
   *
   * @param vo
   */
  void approvePass(ApprovePassStockCostAdjustSheetVo vo);

  /**
   * 批量审核通过
   *
   * @param vo
   */
  void batchApprovePass(BatchApprovePassStockCostAdjustSheetVo vo);

  /**
   * 直接审核通过
   *
   * @param vo
   */
  void directApprovePass(CreateStockCostAdjustSheetVo vo);

  /**
   * 审核拒绝
   *
   * @param vo
   */
  void approveRefuse(ApproveRefuseStockCostAdjustSheetVo vo);

  /**
   * 批量审核拒绝
   *
   * @param vo
   */
  void batchApproveRefuse(BatchApproveRefuseStockCostAdjustSheetVo vo);
}

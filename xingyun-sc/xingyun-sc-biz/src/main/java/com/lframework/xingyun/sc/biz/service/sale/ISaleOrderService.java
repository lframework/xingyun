package com.lframework.xingyun.sc.biz.service.sale;

import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.xingyun.sc.facade.dto.sale.SaleOrderFullDto;
import com.lframework.xingyun.sc.facade.dto.sale.SaleOrderWithOutDto;
import com.lframework.xingyun.sc.facade.entity.SaleOrder;
import com.lframework.xingyun.sc.facade.vo.sale.ApprovePassSaleOrderVo;
import com.lframework.xingyun.sc.facade.vo.sale.ApproveRefuseSaleOrderVo;
import com.lframework.xingyun.sc.facade.vo.sale.BatchApprovePassSaleOrderVo;
import com.lframework.xingyun.sc.facade.vo.sale.BatchApproveRefuseSaleOrderVo;
import com.lframework.xingyun.sc.facade.vo.sale.CreateSaleOrderVo;
import com.lframework.xingyun.sc.facade.vo.sale.QuerySaleOrderVo;
import com.lframework.xingyun.sc.facade.vo.sale.QuerySaleOrderWithOutVo;
import com.lframework.xingyun.sc.facade.vo.sale.SaleOrderSelectorVo;
import com.lframework.xingyun.sc.facade.vo.sale.UpdateSaleOrderVo;
import java.util.List;

public interface ISaleOrderService extends BaseMpService<SaleOrder> {

  /**
   * 查询列表
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<SaleOrder> query(Integer pageIndex, Integer pageSize, QuerySaleOrderVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<SaleOrder> query(QuerySaleOrderVo vo);

  /**
   * 选择器
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<SaleOrder> selector(Integer pageIndex, Integer pageSize, SaleOrderSelectorVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  SaleOrderFullDto getDetail(String id);

  /**
   * 根据ID查询（发货业务）
   *
   * @param id
   * @return
   */
  SaleOrderWithOutDto getWithOut(String id);

  /**
   * 查询列表（出库业务）
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<SaleOrder> queryWithOut(Integer pageIndex, Integer pageSize,
      QuerySaleOrderWithOutVo vo);

  /**
   * 创建订单
   *
   * @param vo
   * @return
   */
  String create(CreateSaleOrderVo vo);

  /**
   * 修改订单
   *
   * @param vo
   */
  void update(UpdateSaleOrderVo vo);

  /**
   * 审核通过
   *
   * @param vo
   */
  void approvePass(ApprovePassSaleOrderVo vo);

  /**
   * 批量审核通过
   *
   * @param vo
   */
  void batchApprovePass(BatchApprovePassSaleOrderVo vo);

  /**
   * 直接审核通过
   *
   * @param vo
   */
  String directApprovePass(CreateSaleOrderVo vo);

  /**
   * 审核拒绝
   *
   * @param vo
   */
  void approveRefuse(ApproveRefuseSaleOrderVo vo);

  /**
   * 批量审核拒绝
   *
   * @param vo
   */
  void batchApproveRefuse(BatchApproveRefuseSaleOrderVo vo);

  /**
   * 根据ID删除
   *
   * @param id
   */
  void deleteById(String id);

  /**
   * 根据IDs删除
   *
   * @param ids
   */
  void deleteByIds(List<String> ids);
}

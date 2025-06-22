package com.lframework.xingyun.sc.service.sale;

import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.xingyun.sc.dto.sale.SaleOrderFullDto;
import com.lframework.xingyun.sc.dto.sale.SaleOrderWithOutDto;
import com.lframework.xingyun.sc.dto.sale.SaleProductDto;
import com.lframework.xingyun.sc.entity.SaleOrder;
import com.lframework.xingyun.sc.vo.sale.ApprovePassSaleOrderVo;
import com.lframework.xingyun.sc.vo.sale.ApproveRefuseSaleOrderVo;
import com.lframework.xingyun.sc.vo.sale.CreateSaleOrderVo;
import com.lframework.xingyun.sc.vo.sale.QuerySaleOrderVo;
import com.lframework.xingyun.sc.vo.sale.QuerySaleOrderWithOutVo;
import com.lframework.xingyun.sc.vo.sale.QuerySaleProductVo;
import com.lframework.xingyun.sc.vo.sale.SaleOrderSelectorVo;
import com.lframework.xingyun.sc.vo.sale.UpdateSaleOrderVo;
import java.util.List;

public interface SaleOrderService extends BaseMpService<SaleOrder> {

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
   * 根据ID删除
   *
   * @param id
   */
  void deleteById(String id);

  /**
   * 根据关键字查询销售商品信息
   *
   * @param pageIndex
   * @param pageSize
   * @param condition
   * @return
   */
  PageResult<SaleProductDto> querySaleByCondition(Integer pageIndex, Integer pageSize, String condition, Boolean isReturn);

  /**
   * 查询可销售商品信息
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<SaleProductDto> querySaleList(Integer pageIndex, Integer pageSize, QuerySaleProductVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  SaleProductDto getSaleById(String id);
}

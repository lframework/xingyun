package com.lframework.xingyun.sc.service.sale;

import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.xingyun.sc.dto.purchase.receive.GetPaymentDateDto;
import com.lframework.xingyun.sc.dto.sale.out.SaleOutSheetFullDto;
import com.lframework.xingyun.sc.dto.sale.out.SaleOutSheetWithReturnDto;
import com.lframework.xingyun.sc.entity.SaleOutSheet;
import com.lframework.xingyun.sc.enums.SettleStatus;
import com.lframework.xingyun.sc.vo.sale.out.ApprovePassSaleOutSheetVo;
import com.lframework.xingyun.sc.vo.sale.out.ApproveRefuseSaleOutSheetVo;
import com.lframework.xingyun.sc.vo.sale.out.CreateSaleOutSheetVo;
import com.lframework.xingyun.sc.vo.sale.out.QuerySaleOutSheetVo;
import com.lframework.xingyun.sc.vo.sale.out.QuerySaleOutSheetWithReturnVo;
import com.lframework.xingyun.sc.vo.sale.out.SaleOutSheetSelectorVo;
import com.lframework.xingyun.sc.vo.sale.out.UpdateSaleOutSheetVo;
import java.time.LocalDateTime;
import java.util.List;

public interface SaleOutSheetService extends BaseMpService<SaleOutSheet> {

  /**
   * 查询列表
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<SaleOutSheet> query(Integer pageIndex, Integer pageSize, QuerySaleOutSheetVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<SaleOutSheet> query(QuerySaleOutSheetVo vo);

  /**
   * 选择器
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<SaleOutSheet> selector(Integer pageIndex, Integer pageSize, SaleOutSheetSelectorVo vo);

  /**
   * 根据客户ID查询默认付款日期
   *
   * @param customerId
   */
  GetPaymentDateDto getPaymentDate(String customerId);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  SaleOutSheetFullDto getDetail(String id);

  /**
   * 根据ID查询（销售退货业务）
   *
   * @param id
   * @return
   */
  SaleOutSheetWithReturnDto getWithReturn(String id);

  /**
   * 查询列表（销售退货业务）
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<SaleOutSheet> queryWithReturn(Integer pageIndex, Integer pageSize,
      QuerySaleOutSheetWithReturnVo vo);

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  String create(CreateSaleOutSheetVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateSaleOutSheetVo vo);

  /**
   * 审核通过
   *
   * @param vo
   */
  void approvePass(ApprovePassSaleOutSheetVo vo);

  /**
   * 直接审核通过
   *
   * @param vo
   */
  String directApprovePass(CreateSaleOutSheetVo vo);

  /**
   * 审核拒绝
   *
   * @param vo
   */
  void approveRefuse(ApproveRefuseSaleOutSheetVo vo);

  /**
   * 根据ID删除
   *
   * @param id
   */
  void deleteById(String id);

  /**
   * 设置成未结算
   *
   * @param id
   * @return
   */
  int setUnSettle(String id);

  /**
   * 设置成结算中
   *
   * @param id
   * @return
   */
  int setPartSettle(String id);

  /**
   * 设置成已结算
   *
   * @param id
   * @return
   */
  int setSettled(String id);

  /**
   * 查询已审核列表
   *
   * @param customerId
   * @param startTime
   * @param endTime
   * @return
   */
  List<SaleOutSheet> getApprovedList(String customerId, LocalDateTime startTime,
      LocalDateTime endTime,
      SettleStatus settleStatus);
}

package com.lframework.xingyun.sc.biz.service.purchase;

import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.xingyun.core.enums.SettleStatus;
import com.lframework.xingyun.sc.facade.dto.purchase.receive.GetPaymentDateDto;
import com.lframework.xingyun.sc.facade.dto.purchase.receive.ReceiveSheetFullDto;
import com.lframework.xingyun.sc.facade.dto.purchase.receive.ReceiveSheetWithReturnDto;
import com.lframework.xingyun.sc.facade.entity.ReceiveSheet;
import com.lframework.xingyun.sc.facade.vo.purchase.receive.ApprovePassReceiveSheetVo;
import com.lframework.xingyun.sc.facade.vo.purchase.receive.ApproveRefuseReceiveSheetVo;
import com.lframework.xingyun.sc.facade.vo.purchase.receive.BatchApprovePassReceiveSheetVo;
import com.lframework.xingyun.sc.facade.vo.purchase.receive.BatchApproveRefuseReceiveSheetVo;
import com.lframework.xingyun.sc.facade.vo.purchase.receive.CreateReceiveSheetVo;
import com.lframework.xingyun.sc.facade.vo.purchase.receive.QueryReceiveSheetVo;
import com.lframework.xingyun.sc.facade.vo.purchase.receive.QueryReceiveSheetWithReturnVo;
import com.lframework.xingyun.sc.facade.vo.purchase.receive.ReceiveSheetSelectorVo;
import com.lframework.xingyun.sc.facade.vo.purchase.receive.UpdateReceiveSheetVo;
import java.time.LocalDateTime;
import java.util.List;

public interface IReceiveSheetService extends BaseMpService<ReceiveSheet> {

  /**
   * 查询列表
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<ReceiveSheet> query(Integer pageIndex, Integer pageSize, QueryReceiveSheetVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<ReceiveSheet> query(QueryReceiveSheetVo vo);

  /**
   * 选择器
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<ReceiveSheet> selector(Integer pageIndex, Integer pageSize, ReceiveSheetSelectorVo vo);

  /**
   * 根据供应商ID查询默认付款日期
   *
   * @param supplierId
   */
  GetPaymentDateDto getPaymentDate(String supplierId);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  ReceiveSheetFullDto getDetail(String id);

  /**
   * 根据ID查询（采购退货业务）
   *
   * @param id
   * @return
   */
  ReceiveSheetWithReturnDto getWithReturn(String id);

  /**
   * 查询列表（采购退货业务）
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<ReceiveSheet> queryWithReturn(Integer pageIndex, Integer pageSize,
      QueryReceiveSheetWithReturnVo vo);

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  String create(CreateReceiveSheetVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateReceiveSheetVo vo);

  /**
   * 审核通过
   *
   * @param vo
   */
  void approvePass(ApprovePassReceiveSheetVo vo);

  /**
   * 批量审核通过
   *
   * @param vo
   */
  void batchApprovePass(BatchApprovePassReceiveSheetVo vo);

  /**
   * 直接审核通过
   *
   * @param vo
   */
  String directApprovePass(CreateReceiveSheetVo vo);

  /**
   * 审核拒绝
   *
   * @param vo
   */
  void approveRefuse(ApproveRefuseReceiveSheetVo vo);

  /**
   * 批量审核拒绝
   *
   * @param vo
   */
  void batchApproveRefuse(BatchApproveRefuseReceiveSheetVo vo);

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
   * @param supplierId
   * @param startTime
   * @param endTime
   * @return
   */
  List<ReceiveSheet> getApprovedList(String supplierId, LocalDateTime startTime,
      LocalDateTime endTime,
      SettleStatus settleStatus);
}

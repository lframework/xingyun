package com.lframework.xingyun.sc.biz.service.purchase;

import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.xingyun.core.enums.SettleStatus;
import com.lframework.xingyun.sc.facade.dto.purchase.returned.PurchaseReturnFullDto;
import com.lframework.xingyun.sc.facade.entity.PurchaseReturn;
import com.lframework.xingyun.sc.facade.vo.purchase.returned.ApprovePassPurchaseReturnVo;
import com.lframework.xingyun.sc.facade.vo.purchase.returned.ApproveRefusePurchaseReturnVo;
import com.lframework.xingyun.sc.facade.vo.purchase.returned.BatchApprovePassPurchaseReturnVo;
import com.lframework.xingyun.sc.facade.vo.purchase.returned.BatchApproveRefusePurchaseReturnVo;
import com.lframework.xingyun.sc.facade.vo.purchase.returned.CreatePurchaseReturnVo;
import com.lframework.xingyun.sc.facade.vo.purchase.returned.QueryPurchaseReturnVo;
import com.lframework.xingyun.sc.facade.vo.purchase.returned.UpdatePurchaseReturnVo;
import java.time.LocalDateTime;
import java.util.List;

public interface IPurchaseReturnService extends BaseMpService<PurchaseReturn> {

  /**
   * 查询列表
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<PurchaseReturn> query(Integer pageIndex, Integer pageSize, QueryPurchaseReturnVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<PurchaseReturn> query(QueryPurchaseReturnVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  PurchaseReturnFullDto getDetail(String id);

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  String create(CreatePurchaseReturnVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdatePurchaseReturnVo vo);

  /**
   * 审核通过
   *
   * @param vo
   */
  void approvePass(ApprovePassPurchaseReturnVo vo);

  /**
   * 批量审核通过
   *
   * @param vo
   */
  void batchApprovePass(BatchApprovePassPurchaseReturnVo vo);

  /**
   * 直接审核通过
   *
   * @param vo
   */
  String directApprovePass(CreatePurchaseReturnVo vo);

  /**
   * 审核拒绝
   *
   * @param vo
   */
  void approveRefuse(ApproveRefusePurchaseReturnVo vo);

  /**
   * 批量审核拒绝
   *
   * @param vo
   */
  void batchApproveRefuse(BatchApproveRefusePurchaseReturnVo vo);

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
  List<PurchaseReturn> getApprovedList(String supplierId, LocalDateTime startTime,
      LocalDateTime endTime,
      SettleStatus settleStatus);
}

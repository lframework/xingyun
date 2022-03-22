package com.lframework.xingyun.settle.service;

import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.web.service.BaseService;
import com.lframework.xingyun.settle.dto.check.SettleCheckBizItemDto;
import com.lframework.xingyun.settle.dto.check.SettleCheckSheetDto;
import com.lframework.xingyun.settle.dto.check.SettleCheckSheetFullDto;
import com.lframework.xingyun.settle.enums.SettleCheckSheetBizType;
import com.lframework.xingyun.settle.vo.check.ApprovePassSettleCheckSheetVo;
import com.lframework.xingyun.settle.vo.check.ApproveRefuseSettleCheckSheetVo;
import com.lframework.xingyun.settle.vo.check.BatchApprovePassSettleCheckSheetVo;
import com.lframework.xingyun.settle.vo.check.BatchApproveRefuseSettleCheckSheetVo;
import com.lframework.xingyun.settle.vo.check.CreateSettleCheckSheetVo;
import com.lframework.xingyun.settle.vo.check.QuerySettleCheckSheetVo;
import com.lframework.xingyun.settle.vo.check.QueryUnCheckBizItemVo;
import com.lframework.xingyun.settle.vo.check.UpdateSettleCheckSheetVo;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface ISettleCheckSheetService extends BaseService {

  /**
   * 查询列表
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<SettleCheckSheetDto> query(Integer pageIndex, Integer pageSize,
      QuerySettleCheckSheetVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<SettleCheckSheetDto> query(QuerySettleCheckSheetVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  SettleCheckSheetDto getById(String id);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  SettleCheckSheetFullDto getDetail(String id);

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  String create(CreateSettleCheckSheetVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateSettleCheckSheetVo vo);

  /**
   * 审核通过
   *
   * @param vo
   */
  void approvePass(ApprovePassSettleCheckSheetVo vo);

  /**
   * 直接审核通过
   *
   * @param vo
   */
  void directApprovePass(CreateSettleCheckSheetVo vo);

  /**
   * 审核拒绝
   *
   * @param vo
   */
  void approveRefuse(ApproveRefuseSettleCheckSheetVo vo);

  /**
   * 批量审核通过
   *
   * @param vo
   */
  void batchApprovePass(BatchApprovePassSettleCheckSheetVo vo);

  /**
   * 批量审核拒绝
   *
   * @param vo
   */
  void batchApproveRefuse(BatchApproveRefuseSettleCheckSheetVo vo);

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
   * 查询业务单据
   *
   * @param id
   * @param bizType
   * @return
   */
  SettleCheckBizItemDto getBizItem(String id, SettleCheckSheetBizType bizType);

  /**
   * 更新业务单据未结算
   *
   * @param id
   * @param bizType
   */
  void setBizItemUnSettle(String id, SettleCheckSheetBizType bizType);

  /**
   * 更新业务单据结算中
   *
   * @param id
   * @param bizType
   */
  void setBizItemPartSettle(String id, SettleCheckSheetBizType bizType);

  /**
   * 更新业务单据已结算
   *
   * @param id
   * @param bizType
   */
  void setBizItemSettled(String id, SettleCheckSheetBizType bizType);

  /**
   * 查询未对账单据
   *
   * @param vo
   * @return
   */
  List<SettleCheckBizItemDto> getUnCheckBizItems(QueryUnCheckBizItemVo vo);

  /**
   * 更新结算状态-未结算
   *
   * @param id
   * @return
   */
  int setUnSettle(String id);

  /**
   * 更新结算状态-结算中
   *
   * @param id
   * @return
   */
  int setPartSettle(String id);

  /**
   * 更新结算状态-已结算
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
  List<SettleCheckSheetDto> getApprovedList(String supplierId, LocalDateTime startTime,
      LocalDateTime endTime);

  /**
   * 设置结算金额
   *
   * @param id
   * @param totalPayedAmount
   * @param totalDiscountAmount
   */
  void setSettleAmount(String id, BigDecimal totalPayedAmount, BigDecimal totalDiscountAmount);
}

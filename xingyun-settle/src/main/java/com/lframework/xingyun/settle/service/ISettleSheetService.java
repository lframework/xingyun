package com.lframework.xingyun.settle.service;

import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.web.service.BaseService;
import com.lframework.xingyun.settle.dto.sheet.SettleBizItemDto;
import com.lframework.xingyun.settle.dto.sheet.SettleSheetDto;
import com.lframework.xingyun.settle.dto.sheet.SettleSheetFullDto;
import com.lframework.xingyun.settle.vo.sheet.ApprovePassSettleSheetVo;
import com.lframework.xingyun.settle.vo.sheet.ApproveRefuseSettleSheetVo;
import com.lframework.xingyun.settle.vo.sheet.BatchApprovePassSettleSheetVo;
import com.lframework.xingyun.settle.vo.sheet.BatchApproveRefuseSettleSheetVo;
import com.lframework.xingyun.settle.vo.sheet.CreateSettleSheetVo;
import com.lframework.xingyun.settle.vo.sheet.QuerySettleSheetVo;
import com.lframework.xingyun.settle.vo.sheet.QueryUnSettleBizItemVo;
import com.lframework.xingyun.settle.vo.sheet.UpdateSettleSheetVo;
import java.util.List;

public interface ISettleSheetService extends BaseService {

  /**
   * 查询列表
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<SettleSheetDto> query(Integer pageIndex, Integer pageSize, QuerySettleSheetVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<SettleSheetDto> query(QuerySettleSheetVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  SettleSheetDto getById(String id);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  SettleSheetFullDto getDetail(String id);

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  String create(CreateSettleSheetVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateSettleSheetVo vo);

  /**
   * 审核通过
   *
   * @param vo
   */
  void approvePass(ApprovePassSettleSheetVo vo);

  /**
   * 直接审核通过
   *
   * @param vo
   */
  void directApprovePass(CreateSettleSheetVo vo);

  /**
   * 审核拒绝
   *
   * @param vo
   */
  void approveRefuse(ApproveRefuseSettleSheetVo vo);

  /**
   * 批量审核通过
   *
   * @param vo
   */
  void batchApprovePass(BatchApprovePassSettleSheetVo vo);

  /**
   * 批量审核拒绝
   *
   * @param vo
   */
  void batchApproveRefuse(BatchApproveRefuseSettleSheetVo vo);

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
   * @return
   */
  SettleBizItemDto getBizItem(String id);

  /**
   * 更新业务单据未结算
   *
   * @param id
   */
  void setBizItemUnSettle(String id);

  /**
   * 更新业务单据结算中
   *
   * @param id
   */
  void setBizItemPartSettle(String id);

  /**
   * 更新业务单据已结算
   *
   * @param id
   */
  void setBizItemSettled(String id);

  /**
   * 查询未结算单据
   *
   * @param vo
   * @return
   */
  List<SettleBizItemDto> getUnSettleBizItems(QueryUnSettleBizItemVo vo);
}

package com.lframework.xingyun.sc.service.retail;

import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.xingyun.sc.dto.purchase.receive.GetPaymentDateDto;
import com.lframework.xingyun.sc.dto.retail.out.RetailOutSheetFullDto;
import com.lframework.xingyun.sc.dto.retail.out.RetailOutSheetWithReturnDto;
import com.lframework.xingyun.sc.entity.RetailOutSheet;
import com.lframework.xingyun.sc.vo.retail.out.ApprovePassRetailOutSheetVo;
import com.lframework.xingyun.sc.vo.retail.out.ApproveRefuseRetailOutSheetVo;
import com.lframework.xingyun.sc.vo.retail.out.BatchApprovePassRetailOutSheetVo;
import com.lframework.xingyun.sc.vo.retail.out.BatchApproveRefuseRetailOutSheetVo;
import com.lframework.xingyun.sc.vo.retail.out.CreateRetailOutSheetVo;
import com.lframework.xingyun.sc.vo.retail.out.QueryRetailOutSheetVo;
import com.lframework.xingyun.sc.vo.retail.out.QueryRetailOutSheetWithReturnVo;
import com.lframework.xingyun.sc.vo.retail.out.RetailOutSheetSelectorVo;
import com.lframework.xingyun.sc.vo.retail.out.UpdateRetailOutSheetVo;
import java.util.List;

public interface IRetailOutSheetService extends BaseMpService<RetailOutSheet> {

  /**
   * 查询列表
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<RetailOutSheet> query(Integer pageIndex, Integer pageSize, QueryRetailOutSheetVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<RetailOutSheet> query(QueryRetailOutSheetVo vo);

  /**
   * 选择器
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<RetailOutSheet> selector(Integer pageIndex, Integer pageSize,
      RetailOutSheetSelectorVo vo);

  /**
   * 根据会员ID查询默认付款日期
   *
   * @param memberId
   */
  GetPaymentDateDto getPaymentDate(String memberId);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  RetailOutSheetFullDto getDetail(String id);

  /**
   * 根据ID查询（零售退货业务）
   *
   * @param id
   * @return
   */
  RetailOutSheetWithReturnDto getWithReturn(String id);

  /**
   * 查询列表（零售退货业务）
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<RetailOutSheet> queryWithReturn(Integer pageIndex, Integer pageSize,
      QueryRetailOutSheetWithReturnVo vo);

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  String create(CreateRetailOutSheetVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateRetailOutSheetVo vo);

  /**
   * 审核通过
   *
   * @param vo
   */
  void approvePass(ApprovePassRetailOutSheetVo vo);

  /**
   * 批量审核通过
   *
   * @param vo
   */
  void batchApprovePass(BatchApprovePassRetailOutSheetVo vo);

  /**
   * 直接审核通过
   *
   * @param vo
   */
  void directApprovePass(CreateRetailOutSheetVo vo);

  /**
   * 审核拒绝
   *
   * @param vo
   */
  void approveRefuse(ApproveRefuseRetailOutSheetVo vo);

  /**
   * 批量审核拒绝
   *
   * @param vo
   */
  void batchApproveRefuse(BatchApproveRefuseRetailOutSheetVo vo);

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

package com.lframework.xingyun.sc.biz.service.retail;

import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.xingyun.sc.facade.dto.retail.returned.RetailReturnFullDto;
import com.lframework.xingyun.sc.facade.entity.RetailReturn;
import com.lframework.xingyun.sc.facade.vo.retail.returned.ApprovePassRetailReturnVo;
import com.lframework.xingyun.sc.facade.vo.retail.returned.ApproveRefuseRetailReturnVo;
import com.lframework.xingyun.sc.facade.vo.retail.returned.BatchApprovePassRetailReturnVo;
import com.lframework.xingyun.sc.facade.vo.retail.returned.BatchApproveRefuseRetailReturnVo;
import com.lframework.xingyun.sc.facade.vo.retail.returned.CreateRetailReturnVo;
import com.lframework.xingyun.sc.facade.vo.retail.returned.QueryRetailReturnVo;
import com.lframework.xingyun.sc.facade.vo.retail.returned.UpdateRetailReturnVo;
import java.util.List;

public interface IRetailReturnService extends BaseMpService<RetailReturn> {

  /**
   * 查询列表
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<RetailReturn> query(Integer pageIndex, Integer pageSize, QueryRetailReturnVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<RetailReturn> query(QueryRetailReturnVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  RetailReturnFullDto getDetail(String id);

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  String create(CreateRetailReturnVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateRetailReturnVo vo);

  /**
   * 审核通过
   *
   * @param vo
   */
  void approvePass(ApprovePassRetailReturnVo vo);

  /**
   * 批量审核通过
   *
   * @param vo
   */
  void batchApprovePass(BatchApprovePassRetailReturnVo vo);

  /**
   * 直接审核通过
   *
   * @param vo
   */
  String directApprovePass(CreateRetailReturnVo vo);

  /**
   * 审核拒绝
   *
   * @param vo
   */
  void approveRefuse(ApproveRefuseRetailReturnVo vo);

  /**
   * 批量审核拒绝
   *
   * @param vo
   */
  void batchApproveRefuse(BatchApproveRefuseRetailReturnVo vo);

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

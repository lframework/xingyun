package com.lframework.xingyun.sc.service.retail;

import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.web.service.BaseService;
import com.lframework.xingyun.sc.dto.retail.returned.RetailReturnDto;
import com.lframework.xingyun.sc.dto.retail.returned.RetailReturnFullDto;
import com.lframework.xingyun.sc.vo.retail.returned.ApprovePassRetailReturnVo;
import com.lframework.xingyun.sc.vo.retail.returned.ApproveRefuseRetailReturnVo;
import com.lframework.xingyun.sc.vo.retail.returned.BatchApprovePassRetailReturnVo;
import com.lframework.xingyun.sc.vo.retail.returned.BatchApproveRefuseRetailReturnVo;
import com.lframework.xingyun.sc.vo.retail.returned.CreateRetailReturnVo;
import com.lframework.xingyun.sc.vo.retail.returned.QueryRetailReturnVo;
import com.lframework.xingyun.sc.vo.retail.returned.UpdateRetailReturnVo;
import java.util.List;

public interface IRetailReturnService extends BaseService {

  /**
   * 查询列表
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<RetailReturnDto> query(Integer pageIndex, Integer pageSize, QueryRetailReturnVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<RetailReturnDto> query(QueryRetailReturnVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  RetailReturnDto getById(String id);

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
  void directApprovePass(CreateRetailReturnVo vo);

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

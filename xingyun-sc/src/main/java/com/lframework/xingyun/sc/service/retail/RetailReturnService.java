package com.lframework.xingyun.sc.service.retail;

import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.service.BaseMpService;
import com.lframework.xingyun.sc.dto.retail.returned.RetailReturnFullDto;
import com.lframework.xingyun.sc.entity.RetailReturn;
import com.lframework.xingyun.sc.vo.retail.returned.ApprovePassRetailReturnVo;
import com.lframework.xingyun.sc.vo.retail.returned.ApproveRefuseRetailReturnVo;
import com.lframework.xingyun.sc.vo.retail.returned.CreateRetailReturnVo;
import com.lframework.xingyun.sc.vo.retail.returned.QueryRetailReturnVo;
import com.lframework.xingyun.sc.vo.retail.returned.UpdateRetailReturnVo;
import java.util.List;

public interface RetailReturnService extends BaseMpService<RetailReturn> {

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
   * 根据ID删除
   *
   * @param id
   */
  void deleteById(String id);
}

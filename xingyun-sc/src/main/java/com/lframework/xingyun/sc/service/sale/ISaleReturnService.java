package com.lframework.xingyun.sc.service.sale;

import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.xingyun.sc.dto.sale.returned.SaleReturnDto;
import com.lframework.xingyun.sc.dto.sale.returned.SaleReturnFullDto;
import com.lframework.xingyun.sc.entity.SaleReturn;
import com.lframework.xingyun.sc.vo.sale.returned.ApprovePassSaleReturnVo;
import com.lframework.xingyun.sc.vo.sale.returned.ApproveRefuseSaleReturnVo;
import com.lframework.xingyun.sc.vo.sale.returned.BatchApprovePassSaleReturnVo;
import com.lframework.xingyun.sc.vo.sale.returned.BatchApproveRefuseSaleReturnVo;
import com.lframework.xingyun.sc.vo.sale.returned.CreateSaleReturnVo;
import com.lframework.xingyun.sc.vo.sale.returned.QuerySaleReturnVo;
import com.lframework.xingyun.sc.vo.sale.returned.UpdateSaleReturnVo;
import java.util.List;

public interface ISaleReturnService extends BaseMpService<SaleReturn> {

  /**
   * 查询列表
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<SaleReturnDto> query(Integer pageIndex, Integer pageSize, QuerySaleReturnVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<SaleReturnDto> query(QuerySaleReturnVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  SaleReturnDto getById(String id);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  SaleReturnFullDto getDetail(String id);

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  String create(CreateSaleReturnVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateSaleReturnVo vo);

  /**
   * 审核通过
   *
   * @param vo
   */
  void approvePass(ApprovePassSaleReturnVo vo);

  /**
   * 批量审核通过
   *
   * @param vo
   */
  void batchApprovePass(BatchApprovePassSaleReturnVo vo);

  /**
   * 直接审核通过
   *
   * @param vo
   */
  void directApprovePass(CreateSaleReturnVo vo);

  /**
   * 审核拒绝
   *
   * @param vo
   */
  void approveRefuse(ApproveRefuseSaleReturnVo vo);

  /**
   * 批量审核拒绝
   *
   * @param vo
   */
  void batchApproveRefuse(BatchApproveRefuseSaleReturnVo vo);

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

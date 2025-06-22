package com.lframework.xingyun.sc.service.stock.transfer;

import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.xingyun.sc.dto.stock.transfer.ScTransferOrderFullDto;
import com.lframework.xingyun.sc.dto.stock.transfer.ScTransferProductDto;
import com.lframework.xingyun.sc.entity.ScTransferOrder;
import com.lframework.xingyun.sc.vo.stock.transfer.ApprovePassScTransferOrderVo;
import com.lframework.xingyun.sc.vo.stock.transfer.ApproveRefuseScTransferOrderVo;
import com.lframework.xingyun.sc.vo.stock.transfer.CreateScTransferOrderVo;
import com.lframework.xingyun.sc.vo.stock.transfer.QueryScTransferOrderVo;
import com.lframework.xingyun.sc.vo.stock.transfer.QueryScTransferProductVo;
import com.lframework.xingyun.sc.vo.stock.transfer.ReceiveScTransferOrderVo;
import com.lframework.xingyun.sc.vo.stock.transfer.UpdateScTransferOrderVo;
import java.util.List;

public interface ScTransferOrderService extends BaseMpService<ScTransferOrder> {

  /**
   * 查询列表
   *
   * @return
   */
  PageResult<ScTransferOrder> query(Integer pageIndex, Integer pageSize,
      QueryScTransferOrderVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<ScTransferOrder> query(QueryScTransferOrderVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  ScTransferOrderFullDto getDetail(String id);

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  String create(CreateScTransferOrderVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateScTransferOrderVo vo);

  /**
   * 根据ID删除
   *
   * @param id
   * @return
   */
  void deleteById(String id);

  /**
   * 审核通过
   *
   * @param vo
   */
  void approvePass(ApprovePassScTransferOrderVo vo);

  /**
   * 直接审核通过
   *
   * @param vo
   */
  String directApprovePass(CreateScTransferOrderVo vo);

  /**
   * 审核拒绝
   *
   * @param vo
   */
  void approveRefuse(ApproveRefuseScTransferOrderVo vo);

  /**
   * 收货
   * @param vo
   */
  void receive(ReceiveScTransferOrderVo vo);

  /**
   * 根据关键字查询仓库调拨单商品信息
   *
   * @param pageIndex
   * @param pageSize
   * @param scId
   * @param condition
   * @return
   */
  PageResult<ScTransferProductDto> queryScTransferByCondition(Integer pageIndex, Integer pageSize,
      String scId, String condition);

  /**
   * 查询仓库调拨单商品信息
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<ScTransferProductDto> queryScTransferList(Integer pageIndex, Integer pageSize,
      QueryScTransferProductVo vo);
}

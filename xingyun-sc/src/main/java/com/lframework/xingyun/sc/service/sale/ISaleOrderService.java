package com.lframework.xingyun.sc.service.sale;

import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.web.service.BaseService;
import com.lframework.xingyun.sc.dto.sale.SaleOrderDto;
import com.lframework.xingyun.sc.dto.sale.SaleOrderFullDto;
import com.lframework.xingyun.sc.dto.sale.SaleOrderWithOutDto;
import com.lframework.xingyun.sc.vo.sale.*;

import java.util.List;

public interface ISaleOrderService extends BaseService {

    /**
     * 查询列表
     * @param pageIndex
     * @param pageSize
     * @param vo
     * @return
     */
    PageResult<SaleOrderDto> query(Integer pageIndex, Integer pageSize, QuerySaleOrderVo vo);

    /**
     * 查询列表
     * @param vo
     * @return
     */
    List<SaleOrderDto> query(QuerySaleOrderVo vo);

    /**
     * 选择器
     * @param pageIndex
     * @param pageSize
     * @param vo
     * @return
     */
    PageResult<SaleOrderDto> selector(Integer pageIndex, Integer pageSize, SaleOrderSelectorVo vo);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    SaleOrderDto getById(String id);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    SaleOrderFullDto getDetail(String id);

    /**
     * 根据ID查询（发货业务）
     * @param id
     * @return
     */
    SaleOrderWithOutDto getWithOut(String id);

    /**
     * 查询列表（出库业务）
     * @param pageIndex
     * @param pageSize
     * @param vo
     * @return
     */
    PageResult<SaleOrderDto> queryWithOut(Integer pageIndex, Integer pageSize, QuerySaleOrderWithOutVo vo);

    /**
     * 创建订单
     * @param vo
     * @return
     */
    String create(CreateSaleOrderVo vo);

    /**
     * 修改订单
     * @param vo
     */
    void update(UpdateSaleOrderVo vo);

    /**
     * 审核通过
     * @param vo
     */
    void approvePass(ApprovePassSaleOrderVo vo);

    /**
     * 批量审核通过
     * @param vo
     */
    void batchApprovePass(BatchApprovePassSaleOrderVo vo);

    /**
     * 直接审核通过
     * @param vo
     */
    void directApprovePass(CreateSaleOrderVo vo);

    /**
     * 审核拒绝
     * @param vo
     */
    void approveRefuse(ApproveRefuseSaleOrderVo vo);

    /**
     * 批量审核拒绝
     * @param vo
     */
    void batchApproveRefuse(BatchApproveRefuseSaleOrderVo vo);

    /**
     * 根据ID删除
     * @param id
     */
    void deleteById(String id);

    /**
     * 根据IDs删除
     * @param ids
     */
    void deleteByIds(List<String> ids);
}

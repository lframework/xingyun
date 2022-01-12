package com.lframework.xingyun.sc.service.purchase;

import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.web.service.BaseService;
import com.lframework.xingyun.sc.dto.purchase.returned.PurchaseReturnDto;
import com.lframework.xingyun.sc.dto.purchase.returned.PurchaseReturnFullDto;
import com.lframework.xingyun.sc.enums.SettleStatus;
import com.lframework.xingyun.sc.vo.purchase.returned.*;

import java.time.LocalDateTime;
import java.util.List;

public interface IPurchaseReturnService extends BaseService {

    /**
     * 查询列表
     * @param pageIndex
     * @param pageSize
     * @param vo
     * @return
     */
    PageResult<PurchaseReturnDto> query(Integer pageIndex, Integer pageSize, QueryPurchaseReturnVo vo);

    /**
     * 查询列表
     * @param vo
     * @return
     */
    List<PurchaseReturnDto> query(QueryPurchaseReturnVo vo);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    PurchaseReturnDto getById(String id);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    PurchaseReturnFullDto getDetail(String id);

    /**
     * 创建
     * @param vo
     * @return
     */
    String create(CreatePurchaseReturnVo vo);

    /**
     * 修改
     * @param vo
     */
    void update(UpdatePurchaseReturnVo vo);

    /**
     * 审核通过
     * @param vo
     */
    void approvePass(ApprovePassPurchaseReturnVo vo);

    /**
     * 批量审核通过
     * @param vo
     */
    void batchApprovePass(BatchApprovePassPurchaseReturnVo vo);

    /**
     * 直接审核通过
     * @param vo
     */
    void redirectApprovePass(CreatePurchaseReturnVo vo);

    /**
     * 审核拒绝
     * @param vo
     */
    void approveRefuse(ApproveRefusePurchaseReturnVo vo);

    /**
     * 批量审核拒绝
     * @param vo
     */
    void batchApproveRefuse(BatchApproveRefusePurchaseReturnVo vo);

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

    /**
     * 设置成未结算
     * @param id
     * @return
     */
    int setUnSettle(String id);

    /**
     * 设置成结算中
     * @param id
     * @return
     */
    int setPartSettle(String id);

    /**
     * 设置成已结算
     * @param id
     * @return
     */
    int setSettled(String id);

    /**
     * 查询已审核列表
     * @param supplierId
     * @param startTime
     * @param endTime
     * @return
     */
    List<PurchaseReturnDto> getApprovedList(String supplierId, LocalDateTime startTime, LocalDateTime endTime,
            SettleStatus settleStatus);
}

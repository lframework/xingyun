package com.lframework.xingyun.settle.service;

import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.xingyun.sc.enums.SettleStatus;
import com.lframework.xingyun.settle.dto.pre.customer.CustomerSettlePreSheetFullDto;
import com.lframework.xingyun.settle.entity.CustomerSettlePreSheet;
import com.lframework.xingyun.settle.vo.pre.customer.ApprovePassCustomerSettlePreSheetVo;
import com.lframework.xingyun.settle.vo.pre.customer.ApproveRefuseCustomerSettlePreSheetVo;
import com.lframework.xingyun.settle.vo.pre.customer.BatchApprovePassCustomerSettlePreSheetVo;
import com.lframework.xingyun.settle.vo.pre.customer.BatchApproveRefuseCustomerSettlePreSheetVo;
import com.lframework.xingyun.settle.vo.pre.customer.CreateCustomerSettlePreSheetVo;
import com.lframework.xingyun.settle.vo.pre.customer.QueryCustomerSettlePreSheetVo;
import com.lframework.xingyun.settle.vo.pre.customer.UpdateCustomerSettlePreSheetVo;
import java.time.LocalDateTime;
import java.util.List;

public interface CustomerSettlePreSheetService extends BaseMpService<CustomerSettlePreSheet> {

    /**
     * 查询列表
     *
     * @param pageIndex
     * @param pageSize
     * @param vo
     * @return
     */
    PageResult<CustomerSettlePreSheet> query(Integer pageIndex, Integer pageSize,
        QueryCustomerSettlePreSheetVo vo);

    /**
     * 查询列表
     *
     * @param vo
     * @return
     */
    List<CustomerSettlePreSheet> query(QueryCustomerSettlePreSheetVo vo);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    CustomerSettlePreSheetFullDto getDetail(String id);

    /**
     * 创建
     *
     * @param vo
     * @return
     */
    String create(CreateCustomerSettlePreSheetVo vo);

    /**
     * 修改
     *
     * @param vo
     */
    void update(UpdateCustomerSettlePreSheetVo vo);

    /**
     * 审核通过
     *
     * @param vo
     */
    void approvePass(ApprovePassCustomerSettlePreSheetVo vo);

    /**
     * 直接审核通过
     *
     * @param vo
     */
    String directApprovePass(CreateCustomerSettlePreSheetVo vo);

    /**
     * 审核拒绝
     *
     * @param vo
     */
    void approveRefuse(ApproveRefuseCustomerSettlePreSheetVo vo);

    /**
     * 批量审核通过
     *
     * @param vo
     */
    void batchApprovePass(BatchApprovePassCustomerSettlePreSheetVo vo);

    /**
     * 批量审核拒绝
     *
     * @param vo
     */
    void batchApproveRefuse(BatchApproveRefuseCustomerSettlePreSheetVo vo);

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
     * 设置成未结算
     *
     * @param id
     * @return
     */
    int setUnSettle(String id);

    /**
     * 设置成结算中
     *
     * @param id
     * @return
     */
    int setPartSettle(String id);

    /**
     * 设置成已结算
     *
     * @param id
     * @return
     */
    int setSettled(String id);

    /**
     * 查询已审核列表
     *
     * @param customerId
     * @param startTime
     * @param endTime
     * @return
     */
    List<CustomerSettlePreSheet> getApprovedList(String customerId, LocalDateTime startTime,
        LocalDateTime endTime,
        SettleStatus settleStatus);
}

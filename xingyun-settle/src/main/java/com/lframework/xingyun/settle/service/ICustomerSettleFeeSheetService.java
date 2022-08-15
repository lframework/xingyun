package com.lframework.xingyun.settle.service;

import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.xingyun.sc.enums.SettleStatus;
import com.lframework.xingyun.settle.dto.fee.customer.CustomerSettleFeeSheetFullDto;
import com.lframework.xingyun.settle.entity.CustomerSettleFeeSheet;
import com.lframework.xingyun.settle.vo.fee.customer.ApprovePassCustomerSettleFeeSheetVo;
import com.lframework.xingyun.settle.vo.fee.customer.ApproveRefuseCustomerSettleFeeSheetVo;
import com.lframework.xingyun.settle.vo.fee.customer.BatchApprovePassCustomerSettleFeeSheetVo;
import com.lframework.xingyun.settle.vo.fee.customer.BatchApproveRefuseCustomerSettleFeeSheetVo;
import com.lframework.xingyun.settle.vo.fee.customer.CreateCustomerSettleFeeSheetVo;
import com.lframework.xingyun.settle.vo.fee.customer.QueryCustomerSettleFeeSheetVo;
import com.lframework.xingyun.settle.vo.fee.customer.UpdateCustomerSettleFeeSheetVo;
import java.time.LocalDateTime;
import java.util.List;

public interface ICustomerSettleFeeSheetService extends BaseMpService<CustomerSettleFeeSheet> {

    /**
     * 查询列表
     *
     * @param pageIndex
     * @param pageSize
     * @param vo
     * @return
     */
    PageResult<CustomerSettleFeeSheet> query(Integer pageIndex, Integer pageSize,
        QueryCustomerSettleFeeSheetVo vo);

    /**
     * 查询列表
     *
     * @param vo
     * @return
     */
    List<CustomerSettleFeeSheet> query(QueryCustomerSettleFeeSheetVo vo);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    CustomerSettleFeeSheetFullDto getDetail(String id);

    /**
     * 创建
     *
     * @param vo
     * @return
     */
    String create(CreateCustomerSettleFeeSheetVo vo);

    /**
     * 修改
     *
     * @param vo
     */
    void update(UpdateCustomerSettleFeeSheetVo vo);

    /**
     * 审核通过
     *
     * @param vo
     */
    void approvePass(ApprovePassCustomerSettleFeeSheetVo vo);

    /**
     * 直接审核通过
     *
     * @param vo
     */
    String directApprovePass(CreateCustomerSettleFeeSheetVo vo);

    /**
     * 审核拒绝
     *
     * @param vo
     */
    void approveRefuse(ApproveRefuseCustomerSettleFeeSheetVo vo);

    /**
     * 批量审核通过
     *
     * @param vo
     */
    void batchApprovePass(BatchApprovePassCustomerSettleFeeSheetVo vo);

    /**
     * 批量审核拒绝
     *
     * @param vo
     */
    void batchApproveRefuse(BatchApproveRefuseCustomerSettleFeeSheetVo vo);

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
    List<CustomerSettleFeeSheet> getApprovedList(String customerId, LocalDateTime startTime,
        LocalDateTime endTime,
        SettleStatus settleStatus);
}

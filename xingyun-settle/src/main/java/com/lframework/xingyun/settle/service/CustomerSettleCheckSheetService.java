package com.lframework.xingyun.settle.service;

import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.service.BaseMpService;
import com.lframework.xingyun.settle.dto.check.customer.CustomerSettleCheckBizItemDto;
import com.lframework.xingyun.settle.dto.check.customer.CustomerSettleCheckSheetFullDto;
import com.lframework.xingyun.settle.entity.CustomerSettleCheckSheet;
import com.lframework.xingyun.settle.enums.CustomerSettleCheckSheetBizType;
import com.lframework.xingyun.settle.vo.check.customer.ApprovePassCustomerSettleCheckSheetVo;
import com.lframework.xingyun.settle.vo.check.customer.ApproveRefuseCustomerSettleCheckSheetVo;
import com.lframework.xingyun.settle.vo.check.customer.BatchApprovePassCustomerSettleCheckSheetVo;
import com.lframework.xingyun.settle.vo.check.customer.BatchApproveRefuseCustomerSettleCheckSheetVo;
import com.lframework.xingyun.settle.vo.check.customer.CreateCustomerSettleCheckSheetVo;
import com.lframework.xingyun.settle.vo.check.customer.QueryCustomerSettleCheckSheetVo;
import com.lframework.xingyun.settle.vo.check.customer.QueryCustomerUnCheckBizItemVo;
import com.lframework.xingyun.settle.vo.check.customer.UpdateCustomerSettleCheckSheetVo;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface CustomerSettleCheckSheetService extends BaseMpService<CustomerSettleCheckSheet> {

    /**
     * 查询列表
     *
     * @param pageIndex
     * @param pageSize
     * @param vo
     * @return
     */
    PageResult<CustomerSettleCheckSheet> query(Integer pageIndex, Integer pageSize,
        QueryCustomerSettleCheckSheetVo vo);

    /**
     * 查询列表
     *
     * @param vo
     * @return
     */
    List<CustomerSettleCheckSheet> query(QueryCustomerSettleCheckSheetVo vo);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    CustomerSettleCheckSheetFullDto getDetail(String id);

    /**
     * 创建
     *
     * @param vo
     * @return
     */
    String create(CreateCustomerSettleCheckSheetVo vo);

    /**
     * 修改
     *
     * @param vo
     */
    void update(UpdateCustomerSettleCheckSheetVo vo);

    /**
     * 审核通过
     *
     * @param vo
     */
    void approvePass(ApprovePassCustomerSettleCheckSheetVo vo);

    /**
     * 直接审核通过
     *
     * @param vo
     */
    String directApprovePass(CreateCustomerSettleCheckSheetVo vo);

    /**
     * 审核拒绝
     *
     * @param vo
     */
    void approveRefuse(ApproveRefuseCustomerSettleCheckSheetVo vo);

    /**
     * 批量审核通过
     *
     * @param vo
     */
    void batchApprovePass(BatchApprovePassCustomerSettleCheckSheetVo vo);

    /**
     * 批量审核拒绝
     *
     * @param vo
     */
    void batchApproveRefuse(BatchApproveRefuseCustomerSettleCheckSheetVo vo);

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
     * 查询业务单据
     *
     * @param id
     * @param bizType
     * @return
     */
    CustomerSettleCheckBizItemDto getBizItem(String id, CustomerSettleCheckSheetBizType bizType);

    /**
     * 更新业务单据未结算
     *
     * @param id
     * @param bizType
     */
    void setBizItemUnSettle(String id, CustomerSettleCheckSheetBizType bizType);

    /**
     * 更新业务单据结算中
     *
     * @param id
     * @param bizType
     */
    void setBizItemPartSettle(String id, CustomerSettleCheckSheetBizType bizType);

    /**
     * 更新业务单据已结算
     *
     * @param id
     * @param bizType
     */
    void setBizItemSettled(String id, CustomerSettleCheckSheetBizType bizType);

    /**
     * 查询未对账单据
     *
     * @param vo
     * @return
     */
    List<CustomerSettleCheckBizItemDto> getUnCheckBizItems(QueryCustomerUnCheckBizItemVo vo);

    /**
     * 更新结算状态-未结算
     *
     * @param id
     * @return
     */
    int setUnSettle(String id);

    /**
     * 更新结算状态-结算中
     *
     * @param id
     * @return
     */
    int setPartSettle(String id);

    /**
     * 更新结算状态-已结算
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
    List<CustomerSettleCheckSheet> getApprovedList(String customerId, LocalDateTime startTime,
        LocalDateTime endTime);

    /**
     * 设置结算金额
     *
     * @param id
     * @param totalPayedAmount
     * @param totalDiscountAmount
     */
    void setSettleAmount(String id, BigDecimal totalPayedAmount, BigDecimal totalDiscountAmount);
}

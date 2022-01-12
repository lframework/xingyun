package com.lframework.xingyun.settle.service;

import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.web.service.BaseService;
import com.lframework.xingyun.sc.enums.SettleStatus;
import com.lframework.xingyun.settle.dto.fee.SettleFeeSheetDto;
import com.lframework.xingyun.settle.dto.fee.SettleFeeSheetFullDto;
import com.lframework.xingyun.settle.vo.fee.*;

import java.time.LocalDateTime;
import java.util.List;

public interface ISettleFeeSheetService extends BaseService {

    /**
     * 查询列表
     * @param pageIndex
     * @param pageSize
     * @param vo
     * @return
     */
    PageResult<SettleFeeSheetDto> query(Integer pageIndex, Integer pageSize, QuerySettleFeeSheetVo vo);

    /**
     * 查询列表
     * @param vo
     * @return
     */
    List<SettleFeeSheetDto> query(QuerySettleFeeSheetVo vo);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    SettleFeeSheetDto getById(String id);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    SettleFeeSheetFullDto getDetail(String id);

    /**
     * 创建
     * @param vo
     * @return
     */
    String create(CreateSettleFeeSheetVo vo);

    /**
     * 修改
     * @param vo
     */
    void update(UpdateSettleFeeSheetVo vo);

    /**
     * 审核通过
     * @param vo
     */
    void approvePass(ApprovePassSettleFeeSheetVo vo);

    /**
     * 直接审核通过
     * @param vo
     */
    void redirectApprovePass(CreateSettleFeeSheetVo vo);

    /**
     * 审核拒绝
     * @param vo
     */
    void approveRefuse(ApproveRefuseSettleFeeSheetVo vo);

    /**
     * 批量审核通过
     * @param vo
     */
    void batchApprovePass(BatchApprovePassSettleFeeSheetVo vo);

    /**
     * 批量审核拒绝
     * @param vo
     */
    void batchApproveRefuse(BatchApproveRefuseSettleFeeSheetVo vo);

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
    List<SettleFeeSheetDto> getApprovedList(String supplierId, LocalDateTime startTime, LocalDateTime endTime,
            SettleStatus settleStatus);
}

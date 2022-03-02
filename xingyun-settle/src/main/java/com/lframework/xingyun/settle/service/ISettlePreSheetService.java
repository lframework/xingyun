package com.lframework.xingyun.settle.service;

import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.web.service.BaseService;
import com.lframework.xingyun.sc.enums.SettleStatus;
import com.lframework.xingyun.settle.dto.pre.SettlePreSheetDto;
import com.lframework.xingyun.settle.dto.pre.SettlePreSheetFullDto;
import com.lframework.xingyun.settle.vo.pre.*;

import java.time.LocalDateTime;
import java.util.List;

public interface ISettlePreSheetService extends BaseService {

    /**
     * 查询列表
     * @param pageIndex
     * @param pageSize
     * @param vo
     * @return
     */
    PageResult<SettlePreSheetDto> query(Integer pageIndex, Integer pageSize, QuerySettlePreSheetVo vo);

    /**
     * 查询列表
     * @param vo
     * @return
     */
    List<SettlePreSheetDto> query(QuerySettlePreSheetVo vo);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    SettlePreSheetDto getById(String id);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    SettlePreSheetFullDto getDetail(String id);

    /**
     * 创建
     * @param vo
     * @return
     */
    String create(CreateSettlePreSheetVo vo);

    /**
     * 修改
     * @param vo
     */
    void update(UpdateSettlePreSheetVo vo);

    /**
     * 审核通过
     * @param vo
     */
    void approvePass(ApprovePassSettlePreSheetVo vo);

    /**
     * 直接审核通过
     * @param vo
     */
    void directApprovePass(CreateSettlePreSheetVo vo);

    /**
     * 审核拒绝
     * @param vo
     */
    void approveRefuse(ApproveRefuseSettlePreSheetVo vo);

    /**
     * 批量审核通过
     * @param vo
     */
    void batchApprovePass(BatchApprovePassSettlePreSheetVo vo);

    /**
     * 批量审核拒绝
     * @param vo
     */
    void batchApproveRefuse(BatchApproveRefuseSettlePreSheetVo vo);

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
    List<SettlePreSheetDto> getApprovedList(String supplierId, LocalDateTime startTime, LocalDateTime endTime,
            SettleStatus settleStatus);
}

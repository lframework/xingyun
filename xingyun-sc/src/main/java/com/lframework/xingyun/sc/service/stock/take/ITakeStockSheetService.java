package com.lframework.xingyun.sc.service.stock.take;

import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.web.service.BaseService;
import com.lframework.xingyun.sc.dto.stock.take.sheet.TakeStockSheetDto;
import com.lframework.xingyun.sc.dto.stock.take.sheet.TakeStockSheetFullDto;
import com.lframework.xingyun.sc.vo.stock.take.sheet.*;

import java.util.List;

/**
 * 盘点单 Service
 * @author zmj
 */
public interface ITakeStockSheetService extends BaseService {

    /**
     * 查询列表
     * @return
     */
    PageResult<TakeStockSheetDto> query(Integer pageIndex, Integer pageSize, QueryTakeStockSheetVo vo);

    /**
     * 查询列表
     * @param vo
     * @return
     */
    List<TakeStockSheetDto> query(QueryTakeStockSheetVo vo);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    TakeStockSheetDto getById(String id);

    /**
     * 根据ID查询详情
     * @param id
     * @return
     */
    TakeStockSheetFullDto getDetail(String id);

    /**
     * 创建
     * @param vo
     * @return
     */
    String create(CreateTakeStockSheetVo vo);

    /**
     * 修改
     * @param vo
     */
    void update(UpdateTakeStockSheetVo vo);

    /**
     * 审核通过
     * @param vo
     */
    void approvePass(ApprovePassTakeStockSheetVo vo);

    /**
     * 批量审核通过
     * @param vo
     */
    void batchApprovePass(BatchApprovePassTakeStockSheetVo vo);

    /**
     * 直接审核通过
     * @param vo
     */
    void directApprovePass(CreateTakeStockSheetVo vo);

    /**
     * 审核拒绝
     * @param vo
     */
    void approveRefuse(ApproveRefuseTakeStockSheetVo vo);

    /**
     * 批量审核拒绝
     * @param vo
     */
    void batchApproveRefuse(BatchApproveRefuseTakeStockSheetVo vo);

    /**
     * 取消审核
     * @param id
     */
    void cancelApprovePass(String id);

    /**
     * 根据ID删除
     * @param id
     */
    void deleteById(String id);

    /**
     * 根据ID删除
     * @param ids
     */
    void batchDelete(List<String> ids);

    /**
     * 根据预先盘点单ID判断是否有盘点单关联
     * @param preSheetId
     * @return
     */
    Boolean hasRelatePreTakeStockSheet(String preSheetId);

    /**
     * 根据盘点任务ID查询是否有未审核通过的盘点单
     * @param planId
     * @return
     */
    Boolean hasUnApprove(String planId);
}
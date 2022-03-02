package com.lframework.xingyun.sc.service.sale;

import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.web.service.BaseService;
import com.lframework.xingyun.sc.dto.purchase.receive.GetPaymentDateDto;
import com.lframework.xingyun.sc.dto.sale.out.SaleOutSheetDto;
import com.lframework.xingyun.sc.dto.sale.out.SaleOutSheetFullDto;
import com.lframework.xingyun.sc.dto.sale.out.SaleOutSheetWithReturnDto;
import com.lframework.xingyun.sc.vo.sale.out.*;

import java.util.List;

public interface ISaleOutSheetService extends BaseService {

    /**
     * 查询列表
     * @param pageIndex
     * @param pageSize
     * @param vo
     * @return
     */
    PageResult<SaleOutSheetDto> query(Integer pageIndex, Integer pageSize, QuerySaleOutSheetVo vo);

    /**
     * 查询列表
     * @param vo
     * @return
     */
    List<SaleOutSheetDto> query(QuerySaleOutSheetVo vo);

    /**
     * 选择器
     * @param pageIndex
     * @param pageSize
     * @param vo
     * @return
     */
    PageResult<SaleOutSheetDto> selector(Integer pageIndex, Integer pageSize, SaleOutSheetSelectorVo vo);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    SaleOutSheetDto getById(String id);

    /**
     * 根据客户ID查询默认付款日期
     * @param customerId
     */
    GetPaymentDateDto getPaymentDate(String customerId);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    SaleOutSheetFullDto getDetail(String id);

    /**
     * 根据ID查询（销售退货业务）
     * @param id
     * @return
     */
    SaleOutSheetWithReturnDto getWithReturn(String id);

    /**
     * 查询列表（销售退货业务）
     * @param pageIndex
     * @param pageSize
     * @param vo
     * @return
     */
    PageResult<SaleOutSheetDto> queryWithReturn(Integer pageIndex, Integer pageSize, QuerySaleOutSheetWithReturnVo vo);

    /**
     * 创建
     * @param vo
     * @return
     */
    String create(CreateSaleOutSheetVo vo);

    /**
     * 修改
     * @param vo
     */
    void update(UpdateSaleOutSheetVo vo);

    /**
     * 审核通过
     * @param vo
     */
    void approvePass(ApprovePassSaleOutSheetVo vo);

    /**
     * 批量审核通过
     * @param vo
     */
    void batchApprovePass(BatchApprovePassSaleOutSheetVo vo);

    /**
     * 直接审核通过
     * @param vo
     */
    void directApprovePass(CreateSaleOutSheetVo vo);

    /**
     * 审核拒绝
     * @param vo
     */
    void approveRefuse(ApproveRefuseSaleOutSheetVo vo);

    /**
     * 批量审核拒绝
     * @param vo
     */
    void batchApproveRefuse(BatchApproveRefuseSaleOutSheetVo vo);

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

package com.lframework.xingyun.settle.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.common.constants.StringPool;
import com.lframework.common.exceptions.ClientException;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.exceptions.impl.DefaultSysException;
import com.lframework.common.exceptions.impl.InputErrorException;
import com.lframework.common.utils.Assert;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.NumberUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.enums.OpLogType;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.OpLogUtil;
import com.lframework.starter.mybatis.utils.PageHelperUtil;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.web.service.IGenerateCodeService;
import com.lframework.starter.web.utils.EnumUtil;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.web.common.security.AbstractUserDetails;
import com.lframework.web.common.security.SecurityUtil;
import com.lframework.xingyun.core.annations.OrderTimeLineLog;
import com.lframework.xingyun.core.enums.OrderTimeLineBizType;
import com.lframework.xingyun.sc.entity.SaleOutSheet;
import com.lframework.xingyun.sc.entity.SaleReturn;
import com.lframework.xingyun.sc.enums.SettleStatus;
import com.lframework.xingyun.sc.service.sale.ISaleOutSheetService;
import com.lframework.xingyun.sc.service.sale.ISaleReturnService;
import com.lframework.xingyun.settle.components.code.GenerateCodeTypePool;
import com.lframework.xingyun.settle.dto.check.customer.CustomerSettleCheckBizItemDto;
import com.lframework.xingyun.settle.dto.check.customer.CustomerSettleCheckSheetFullDto;
import com.lframework.xingyun.settle.entity.CustomerSettleCheckSheet;
import com.lframework.xingyun.settle.entity.CustomerSettleCheckSheetDetail;
import com.lframework.xingyun.settle.entity.CustomerSettleFeeSheet;
import com.lframework.xingyun.settle.entity.CustomerSettlePreSheet;
import com.lframework.xingyun.settle.enums.CustomerSettleCheckSheetBizType;
import com.lframework.xingyun.settle.enums.CustomerSettleCheckSheetCalcType;
import com.lframework.xingyun.settle.enums.CustomerSettleCheckSheetStatus;
import com.lframework.xingyun.settle.enums.CustomerSettleFeeSheetType;
import com.lframework.xingyun.settle.mappers.CustomerSettleCheckSheetMapper;
import com.lframework.xingyun.settle.service.ICustomerSettleCheckSheetDetailService;
import com.lframework.xingyun.settle.service.ICustomerSettleCheckSheetService;
import com.lframework.xingyun.settle.service.ICustomerSettleFeeSheetService;
import com.lframework.xingyun.settle.service.ICustomerSettlePreSheetService;
import com.lframework.xingyun.settle.vo.check.customer.ApprovePassCustomerSettleCheckSheetVo;
import com.lframework.xingyun.settle.vo.check.customer.ApproveRefuseCustomerSettleCheckSheetVo;
import com.lframework.xingyun.settle.vo.check.customer.BatchApprovePassCustomerSettleCheckSheetVo;
import com.lframework.xingyun.settle.vo.check.customer.BatchApproveRefuseCustomerSettleCheckSheetVo;
import com.lframework.xingyun.settle.vo.check.customer.CreateCustomerSettleCheckSheetVo;
import com.lframework.xingyun.settle.vo.check.customer.CustomerSettleCheckSheetItemVo;
import com.lframework.xingyun.settle.vo.check.customer.QueryCustomerSettleCheckSheetVo;
import com.lframework.xingyun.settle.vo.check.customer.QueryCustomerUnCheckBizItemVo;
import com.lframework.xingyun.settle.vo.check.customer.UpdateCustomerSettleCheckSheetVo;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerSettleCheckSheetServiceImpl extends
        BaseMpServiceImpl<CustomerSettleCheckSheetMapper, CustomerSettleCheckSheet>
        implements ICustomerSettleCheckSheetService {

    @Autowired
    private ICustomerSettleCheckSheetDetailService customerSettleCheckSheetDetailService;

    @Autowired
    private IGenerateCodeService generateCodeService;

    @Autowired
    private ISaleOutSheetService saleOutSheetService;

    @Autowired
    private ISaleReturnService saleReturnService;

    @Autowired
    private ICustomerSettleFeeSheetService customerSettleFeeSheetService;

    @Autowired
    private ICustomerSettlePreSheetService customerSettlePreSheetService;

    @Override
    public PageResult<CustomerSettleCheckSheet> query(Integer pageIndex, Integer pageSize,
            QueryCustomerSettleCheckSheetVo vo) {

        Assert.greaterThanZero(pageIndex);
        Assert.greaterThanZero(pageSize);

        PageHelperUtil.startPage(pageIndex, pageSize);
        List<CustomerSettleCheckSheet> datas = this.query(vo);

        return PageResultUtil.convert(new PageInfo<>(datas));
    }

    @Override
    public List<CustomerSettleCheckSheet> query(QueryCustomerSettleCheckSheetVo vo) {

        return getBaseMapper().query(vo);
    }

    @Override
    public CustomerSettleCheckSheetFullDto getDetail(String id) {

        return getBaseMapper().getDetail(id);
    }

    @OpLog(type = OpLogType.OTHER, name = "创建客户对账单，单号：{}", params = "#code")
    @OrderTimeLineLog(type = OrderTimeLineBizType.CREATE, orderId = "#_result", name = "创建对账单")
    @Transactional
    @Override
    public String create(CreateCustomerSettleCheckSheetVo vo) {

        CustomerSettleCheckSheet sheet = new CustomerSettleCheckSheet();

        sheet.setId(IdUtil.getId());
        sheet.setCode(
                generateCodeService.generate(GenerateCodeTypePool.CUSTOMER_SETTLE_CHECK_SHEET));

        this.create(sheet, vo);

        sheet.setStatus(CustomerSettleCheckSheetStatus.CREATED);

        OpLogUtil.setVariable("code", sheet.getCode());
        OpLogUtil.setExtra(vo);

        getBaseMapper().insert(sheet);

        return sheet.getId();
    }

    @OpLog(type = OpLogType.OTHER, name = "修改客户对账单，单号：{}", params = "#code")
    @OrderTimeLineLog(type = OrderTimeLineBizType.UPDATE, orderId = "#vo.id", name = "修改对账单")
    @Transactional
    @Override
    public void update(UpdateCustomerSettleCheckSheetVo vo) {

        CustomerSettleCheckSheet sheet = getBaseMapper().selectById(vo.getId());
        if (sheet == null) {
            throw new DefaultClientException("客户对账单不存在！");
        }

        if (sheet.getStatus() != CustomerSettleCheckSheetStatus.CREATED
                && sheet.getStatus() != CustomerSettleCheckSheetStatus.APPROVE_REFUSE) {
            if (sheet.getStatus() == CustomerSettleCheckSheetStatus.APPROVE_PASS) {
                throw new DefaultClientException("客户对账单已审核通过，无法修改！");
            } else {
                throw new DefaultClientException("客户对账单无法修改！");
            }
        }

        //将所有的单据的结算状态更新
        Wrapper<CustomerSettleCheckSheetDetail> queryDetailWrapper = Wrappers.lambdaQuery(
                        CustomerSettleCheckSheetDetail.class)
                .eq(CustomerSettleCheckSheetDetail::getSheetId, sheet.getId())
                .orderByAsc(CustomerSettleCheckSheetDetail::getOrderNo);
        List<CustomerSettleCheckSheetDetail> sheetDetails = customerSettleCheckSheetDetailService.list(
                queryDetailWrapper);
        for (CustomerSettleCheckSheetDetail sheetDetail : sheetDetails) {
            this.setBizItemUnSettle(sheetDetail.getBizId(), sheetDetail.getBizType());
        }

        // 删除明细
        Wrapper<CustomerSettleCheckSheetDetail> deleteDetailWrapper = Wrappers.lambdaQuery(
                        CustomerSettleCheckSheetDetail.class)
                .eq(CustomerSettleCheckSheetDetail::getSheetId, sheet.getId());
        customerSettleCheckSheetDetailService.remove(deleteDetailWrapper);

        this.create(sheet, vo);

        sheet.setStatus(CustomerSettleCheckSheetStatus.CREATED);

        List<CustomerSettleCheckSheetStatus> statusList = new ArrayList<>();
        statusList.add(CustomerSettleCheckSheetStatus.CREATED);
        statusList.add(CustomerSettleCheckSheetStatus.APPROVE_REFUSE);

        Wrapper<CustomerSettleCheckSheet> updateWrapper = Wrappers.lambdaUpdate(
                        CustomerSettleCheckSheet.class)
                .set(CustomerSettleCheckSheet::getApproveBy, null)
                .set(CustomerSettleCheckSheet::getApproveTime, null)
                .set(CustomerSettleCheckSheet::getRefuseReason, StringPool.EMPTY_STR)
                .eq(CustomerSettleCheckSheet::getId, sheet.getId())
                .in(CustomerSettleCheckSheet::getStatus, statusList);
        if (getBaseMapper().update(sheet, updateWrapper) != 1) {
            throw new DefaultClientException("客户对账单信息已过期，请刷新重试！");
        }

        OpLogUtil.setVariable("code", sheet.getCode());
        OpLogUtil.setExtra(vo);
    }

    @OpLog(type = OpLogType.OTHER, name = "审核通过客户对账单，单号：{}", params = "#code")
    @OrderTimeLineLog(type = OrderTimeLineBizType.APPROVE_PASS, orderId = "#vo.id", name = "审核通过")
    @Transactional
    @Override
    public void approvePass(ApprovePassCustomerSettleCheckSheetVo vo) {

        CustomerSettleCheckSheet sheet = getBaseMapper().selectById(vo.getId());
        if (sheet == null) {
            throw new DefaultClientException("客户对账单不存在！");
        }

        if (sheet.getStatus() != CustomerSettleCheckSheetStatus.CREATED
                && sheet.getStatus() != CustomerSettleCheckSheetStatus.APPROVE_REFUSE) {
            if (sheet.getStatus() == CustomerSettleCheckSheetStatus.APPROVE_PASS) {
                throw new DefaultClientException("客户对账单已审核通过，不允许继续执行审核！");
            }
            throw new DefaultClientException("客户对账单无法审核通过！");
        }

        sheet.setStatus(CustomerSettleCheckSheetStatus.APPROVE_PASS);
        sheet.setApproveBy(SecurityUtil.getCurrentUser().getId());
        sheet.setApproveTime(LocalDateTime.now());
        if (!StringUtil.isBlank(vo.getDescription())) {
            sheet.setDescription(vo.getDescription());
        }

        List<CustomerSettleCheckSheetStatus> statusList = new ArrayList<>();
        statusList.add(CustomerSettleCheckSheetStatus.CREATED);
        statusList.add(CustomerSettleCheckSheetStatus.APPROVE_REFUSE);

        Wrapper<CustomerSettleCheckSheet> updateWrapper = Wrappers.lambdaUpdate(
                        CustomerSettleCheckSheet.class)
                .eq(CustomerSettleCheckSheet::getId, sheet.getId())
                .in(CustomerSettleCheckSheet::getStatus, statusList);
        if (getBaseMapper().update(sheet, updateWrapper) != 1) {
            throw new DefaultClientException("客户对账单信息已过期，请刷新重试！");
        }

        OpLogUtil.setVariable("code", sheet.getCode());
        OpLogUtil.setExtra(vo);
    }

    @OrderTimeLineLog(type = OrderTimeLineBizType.APPROVE_PASS, orderId = "#_result", name = "直接审核通过")
    @Transactional
    @Override
    public String directApprovePass(CreateCustomerSettleCheckSheetVo vo) {

        ICustomerSettleCheckSheetService thisService = getThis(this.getClass());

        String id = thisService.create(vo);

        ApprovePassCustomerSettleCheckSheetVo approveVo = new ApprovePassCustomerSettleCheckSheetVo();
        approveVo.setId(id);

        thisService.approvePass(approveVo);

        return id;
    }

    @OpLog(type = OpLogType.OTHER, name = "审核拒绝客户对账单，单号：{}", params = "#code")
    @OrderTimeLineLog(type = OrderTimeLineBizType.APPROVE_RETURN, orderId = "#vo.id", name = "审核拒绝，拒绝理由：{}", params = "#vo.refuseReason")
    @Transactional
    @Override
    public void approveRefuse(ApproveRefuseCustomerSettleCheckSheetVo vo) {

        CustomerSettleCheckSheet sheet = getBaseMapper().selectById(vo.getId());
        if (sheet == null) {
            throw new DefaultClientException("客户对账单不存在！");
        }

        if (sheet.getStatus() != CustomerSettleCheckSheetStatus.CREATED) {
            if (sheet.getStatus() == CustomerSettleCheckSheetStatus.APPROVE_PASS) {
                throw new DefaultClientException("客户对账单已审核通过，不允许继续执行审核！");
            }
            if (sheet.getStatus() == CustomerSettleCheckSheetStatus.APPROVE_REFUSE) {
                throw new DefaultClientException("客户对账单已审核拒绝，不允许继续执行审核！");
            }
            throw new DefaultClientException("客户对账单无法审核拒绝！");
        }

        sheet.setStatus(CustomerSettleCheckSheetStatus.APPROVE_REFUSE);
        sheet.setApproveBy(SecurityUtil.getCurrentUser().getId());
        sheet.setApproveTime(LocalDateTime.now());
        sheet.setRefuseReason(vo.getRefuseReason());

        List<CustomerSettleCheckSheetStatus> statusList = new ArrayList<>();
        statusList.add(CustomerSettleCheckSheetStatus.CREATED);
        statusList.add(CustomerSettleCheckSheetStatus.APPROVE_REFUSE);

        Wrapper<CustomerSettleCheckSheet> updateWrapper = Wrappers.lambdaUpdate(
                        CustomerSettleCheckSheet.class)
                .eq(CustomerSettleCheckSheet::getId, sheet.getId())
                .in(CustomerSettleCheckSheet::getStatus, statusList);
        if (getBaseMapper().update(sheet, updateWrapper) != 1) {
            throw new DefaultClientException("客户对账单信息已过期，请刷新重试！");
        }

        OpLogUtil.setVariable("code", sheet.getCode());
        OpLogUtil.setExtra(vo);
    }

    @OrderTimeLineLog(type = OrderTimeLineBizType.APPROVE_PASS, orderId = "#vo.ids", name = "审核通过")
    @Transactional
    @Override
    public void batchApprovePass(BatchApprovePassCustomerSettleCheckSheetVo vo) {

        ICustomerSettleCheckSheetService thisService = getThis(this.getClass());
        int orderNo = 1;
        for (String id : vo.getIds()) {
            ApprovePassCustomerSettleCheckSheetVo approveVo = new ApprovePassCustomerSettleCheckSheetVo();
            approveVo.setId(id);
            try {
                thisService.approvePass(approveVo);
            } catch (ClientException e) {
                throw new DefaultClientException("第" + orderNo + "个客户对账单审核通过失败，失败原因：" + e.getMsg());
            }
            orderNo++;
        }
    }

    @OrderTimeLineLog(type = OrderTimeLineBizType.APPROVE_RETURN, orderId = "#vo.ids", name = "审核拒绝，拒绝理由：{}", params = "#vo.refuseReason")
    @Transactional
    @Override
    public void batchApproveRefuse(BatchApproveRefuseCustomerSettleCheckSheetVo vo) {

        ICustomerSettleCheckSheetService thisService = getThis(this.getClass());
        int orderNo = 1;
        for (String id : vo.getIds()) {
            ApproveRefuseCustomerSettleCheckSheetVo approveVo = new ApproveRefuseCustomerSettleCheckSheetVo();
            approveVo.setId(id);
            approveVo.setRefuseReason(vo.getRefuseReason());

            try {
                thisService.approveRefuse(approveVo);
            } catch (ClientException e) {
                throw new DefaultClientException("第" + orderNo + "个客户对账单审核拒绝失败，失败原因：" + e.getMsg());
            }
            orderNo++;
        }
    }

    @OpLog(type = OpLogType.OTHER, name = "删除客户对账单，单号：{}", params = "#code")
    @OrderTimeLineLog(orderId = "#id", delete = true)
    @Transactional
    @Override
    public void deleteById(String id) {

        Assert.notBlank(id);
        CustomerSettleCheckSheet sheet = getBaseMapper().selectById(id);
        if (sheet == null) {
            throw new InputErrorException("客户对账单不存在！");
        }

        if (sheet.getStatus() != CustomerSettleCheckSheetStatus.CREATED
                && sheet.getStatus() != CustomerSettleCheckSheetStatus.APPROVE_REFUSE) {

            if (sheet.getStatus() == CustomerSettleCheckSheetStatus.APPROVE_PASS) {
                throw new DefaultClientException("“审核通过”的客户对账单不允许执行删除操作！");
            }

            throw new DefaultClientException("客户对账单无法删除！");
        }

        //将所有的单据的结算状态更新
        Wrapper<CustomerSettleCheckSheetDetail> queryDetailWrapper = Wrappers.lambdaQuery(
                        CustomerSettleCheckSheetDetail.class)
                .eq(CustomerSettleCheckSheetDetail::getSheetId, sheet.getId())
                .orderByAsc(CustomerSettleCheckSheetDetail::getOrderNo);
        List<CustomerSettleCheckSheetDetail> sheetDetails = customerSettleCheckSheetDetailService.list(
                queryDetailWrapper);
        for (CustomerSettleCheckSheetDetail sheetDetail : sheetDetails) {
            this.setBizItemUnSettle(sheetDetail.getBizId(), sheetDetail.getBizType());
        }

        // 删除明细
        Wrapper<CustomerSettleCheckSheetDetail> deleteDetailWrapper = Wrappers.lambdaQuery(
                        CustomerSettleCheckSheetDetail.class)
                .eq(CustomerSettleCheckSheetDetail::getSheetId, sheet.getId());
        customerSettleCheckSheetDetailService.remove(deleteDetailWrapper);

        // 删除单据
        getBaseMapper().deleteById(id);

        OpLogUtil.setVariable("code", sheet.getCode());
    }

    @OrderTimeLineLog(orderId = "#ids", delete = true)
    @Transactional
    @Override
    public void deleteByIds(List<String> ids) {

        if (!CollectionUtil.isEmpty(ids)) {
            int orderNo = 1;
            for (String id : ids) {

                try {
                    ICustomerSettleCheckSheetService thisService = getThis(this.getClass());
                    thisService.deleteById(id);
                } catch (ClientException e) {
                    throw new DefaultClientException(
                            "第" + orderNo + "个客户对账单删除失败，失败原因：" + e.getMsg());
                }

                orderNo++;
            }
        }
    }

    @Override
    public CustomerSettleCheckBizItemDto getBizItem(String id,
            CustomerSettleCheckSheetBizType bizType) {

        CustomerSettleCheckBizItemDto result = new CustomerSettleCheckBizItemDto();

        switch (bizType) {
            case OUT_SHEET: {
                SaleOutSheet outSheet = saleOutSheetService.getById(id);

                result.setId(outSheet.getId());
                result.setCode(outSheet.getCode());
                result.setTotalAmount(outSheet.getTotalAmount());
                result.setApproveTime(outSheet.getApproveTime());
                result.setCalcType(CustomerSettleCheckSheetCalcType.ADD);
                break;
            }
            case SALE_RETURN: {
                SaleReturn saleReturn = saleReturnService.getById(id);

                result.setId(saleReturn.getId());
                result.setCode(saleReturn.getCode());
                result.setTotalAmount(saleReturn.getTotalAmount());
                result.setApproveTime(saleReturn.getApproveTime());
                result.setCalcType(CustomerSettleCheckSheetCalcType.SUB);
                break;
            }
            case SETTLE_FEE_SHEET: {
                CustomerSettleFeeSheet feeSheet = customerSettleFeeSheetService.getById(id);

                result.setId(feeSheet.getId());
                result.setCode(feeSheet.getCode());
                result.setTotalAmount(feeSheet.getTotalAmount());
                result.setApproveTime(feeSheet.getApproveTime());
                result.setCalcType(feeSheet.getSheetType() == CustomerSettleFeeSheetType.RECEIVE ?
                        CustomerSettleCheckSheetCalcType.ADD :
                        CustomerSettleCheckSheetCalcType.SUB);
                break;
            }
            case SETTLE_PRE_SHEET: {
                CustomerSettlePreSheet preSheet = customerSettlePreSheetService.getById(id);

                result.setId(preSheet.getId());
                result.setCode(preSheet.getCode());
                result.setTotalAmount(preSheet.getTotalAmount());
                result.setApproveTime(preSheet.getApproveTime());
                result.setCalcType(CustomerSettleCheckSheetCalcType.ADD);
                break;
            }
            default: {
                throw new DefaultSysException("未知的CustomerSettleCheckSheetBizType");
            }
        }

        result.setBizType(bizType);
        if (result.getCalcType() == CustomerSettleCheckSheetCalcType.SUB) {
            result.setTotalAmount(result.getTotalAmount().negate());
        }

        return result;
    }

    @Transactional
    @Override
    public void setBizItemUnSettle(String id, CustomerSettleCheckSheetBizType bizType) {

        CustomerSettleCheckBizItemDto item = this.getBizItem(id, bizType);

        switch (bizType) {
            case OUT_SHEET: {
                int count = saleOutSheetService.setUnSettle(id);
                if (count != 1) {
                    throw new DefaultClientException(
                            "单号：" + item.getCode() + "，" + bizType.getDesc() + "已结算，业务无法进行！");
                }
                break;
            }
            case SALE_RETURN: {
                int count = saleReturnService.setUnSettle(id);
                if (count != 1) {
                    throw new DefaultClientException(
                            "单号：" + item.getCode() + "，" + bizType.getDesc() + "已结算，业务无法进行！");
                }
                break;
            }
            case SETTLE_FEE_SHEET: {
                int count = customerSettleFeeSheetService.setUnSettle(id);
                if (count != 1) {
                    throw new DefaultClientException(
                            "单号：" + item.getCode() + "，" + bizType.getDesc() + "已结算，业务无法进行！");
                }
                break;
            }
            case SETTLE_PRE_SHEET: {
                int count = customerSettlePreSheetService.setUnSettle(id);
                if (count != 1) {
                    throw new DefaultClientException(
                            "单号：" + item.getCode() + "，" + bizType.getDesc() + "已结算，业务无法进行！");
                }
                break;
            }
            default: {
                throw new DefaultSysException("未知的CustomerSettleCheckSheetBizType");
            }
        }
    }

    @Transactional
    @Override
    public void setBizItemPartSettle(String id, CustomerSettleCheckSheetBizType bizType) {

        CustomerSettleCheckBizItemDto item = this.getBizItem(id, bizType);

        switch (bizType) {
            case OUT_SHEET: {
                int count = saleOutSheetService.setPartSettle(id);
                if (count != 1) {
                    throw new DefaultClientException(
                            "单号：" + item.getCode() + "，" + bizType.getDesc() + "已结算，业务无法进行！");
                }
                break;
            }
            case SALE_RETURN: {
                int count = saleReturnService.setPartSettle(id);
                if (count != 1) {
                    throw new DefaultClientException(
                            "单号：" + item.getCode() + "，" + bizType.getDesc() + "已结算，业务无法进行！");
                }
                break;
            }
            case SETTLE_FEE_SHEET: {
                int count = customerSettleFeeSheetService.setPartSettle(id);
                if (count != 1) {
                    throw new DefaultClientException(
                            "单号：" + item.getCode() + "，" + bizType.getDesc() + "已结算，业务无法进行！");
                }
                break;
            }
            case SETTLE_PRE_SHEET: {
                int count = customerSettlePreSheetService.setPartSettle(id);
                if (count != 1) {
                    throw new DefaultClientException(
                            "单号：" + item.getCode() + "，" + bizType.getDesc() + "已结算，业务无法进行！");
                }
                break;
            }
            default: {
                throw new DefaultSysException("未知的CustomerSettleCheckSheetBizType");
            }
        }
    }

    @Transactional
    @Override
    public void setBizItemSettled(String id, CustomerSettleCheckSheetBizType bizType) {

        CustomerSettleCheckBizItemDto item = this.getBizItem(id, bizType);

        switch (bizType) {
            case OUT_SHEET: {
                int count = saleOutSheetService.setSettled(id);
                if (count != 1) {
                    throw new DefaultClientException(
                            "单号：" + item.getCode() + "，" + bizType.getDesc() + "已结算，无法重复结算！");
                }
                break;
            }
            case SALE_RETURN: {
                int count = saleReturnService.setSettled(id);
                if (count != 1) {
                    throw new DefaultClientException(
                            "单号：" + item.getCode() + "，" + bizType.getDesc() + "已结算，无法重复结算！");
                }
                break;
            }
            case SETTLE_FEE_SHEET: {
                int count = customerSettleFeeSheetService.setSettled(id);
                if (count != 1) {
                    throw new DefaultClientException(
                            "单号：" + item.getCode() + "，" + bizType.getDesc() + "已结算，无法重复结算！");
                }
                break;
            }
            case SETTLE_PRE_SHEET: {
                int count = customerSettlePreSheetService.setSettled(id);
                if (count != 1) {
                    throw new DefaultClientException(
                            "单号：" + item.getCode() + "，" + bizType.getDesc() + "已结算，无法重复结算！");
                }
                break;
            }
            default: {
                throw new DefaultSysException("未知的CustomerSettleCheckSheetBizType");
            }
        }
    }

    @Override
    public List<CustomerSettleCheckBizItemDto> getUnCheckBizItems(
            QueryCustomerUnCheckBizItemVo vo) {

        List<CustomerSettleCheckBizItemDto> results = new ArrayList<>();

        List<SaleOutSheet> outSheetList = saleOutSheetService.getApprovedList(vo.getCustomerId(),
                vo.getStartTime(),
                vo.getEndTime(), SettleStatus.UN_SETTLE);

        List<SaleReturn> saleReturnList = saleReturnService.getApprovedList(vo.getCustomerId(),
                vo.getStartTime(), vo.getEndTime(), SettleStatus.UN_SETTLE);

        List<CustomerSettleFeeSheet> feeSheetList = customerSettleFeeSheetService.getApprovedList(
                vo.getCustomerId(), vo.getStartTime(),
                vo.getEndTime(), SettleStatus.UN_SETTLE);

        List<CustomerSettlePreSheet> preSheetList = customerSettlePreSheetService.getApprovedList(
                vo.getCustomerId(), vo.getStartTime(),
                vo.getEndTime(), SettleStatus.UN_SETTLE);

        if (!CollectionUtil.isEmpty(outSheetList)) {
            for (SaleOutSheet item : outSheetList) {
                CustomerSettleCheckBizItemDto result = new CustomerSettleCheckBizItemDto();
                result.setId(item.getId());
                result.setCode(item.getCode());
                result.setTotalAmount(item.getTotalAmount());
                result.setApproveTime(item.getApproveTime());
                result.setBizType(CustomerSettleCheckSheetBizType.OUT_SHEET);
                result.setCalcType(CustomerSettleCheckSheetCalcType.ADD);

                results.add(result);
            }
        }

        if (!CollectionUtil.isEmpty(saleReturnList)) {
            for (SaleReturn item : saleReturnList) {
                CustomerSettleCheckBizItemDto result = new CustomerSettleCheckBizItemDto();
                result.setId(item.getId());
                result.setCode(item.getCode());
                result.setTotalAmount(item.getTotalAmount());
                result.setApproveTime(item.getApproveTime());
                result.setBizType(CustomerSettleCheckSheetBizType.SALE_RETURN);
                result.setCalcType(CustomerSettleCheckSheetCalcType.SUB);

                results.add(result);
            }
        }

        if (!CollectionUtil.isEmpty(feeSheetList)) {
            for (CustomerSettleFeeSheet item : feeSheetList) {
                CustomerSettleCheckBizItemDto result = new CustomerSettleCheckBizItemDto();
                result.setId(item.getId());
                result.setCode(item.getCode());
                result.setTotalAmount(item.getTotalAmount());
                result.setApproveTime(item.getApproveTime());
                result.setBizType(CustomerSettleCheckSheetBizType.SETTLE_FEE_SHEET);
                result.setCalcType(item.getSheetType() == CustomerSettleFeeSheetType.RECEIVE ?
                        CustomerSettleCheckSheetCalcType.ADD :
                        CustomerSettleCheckSheetCalcType.SUB);

                results.add(result);
            }
        }

        if (!CollectionUtil.isEmpty(preSheetList)) {
            for (CustomerSettlePreSheet item : preSheetList) {
                CustomerSettleCheckBizItemDto result = new CustomerSettleCheckBizItemDto();
                result.setId(item.getId());
                result.setCode(item.getCode());
                result.setTotalAmount(item.getTotalAmount());
                result.setApproveTime(item.getApproveTime());
                result.setBizType(CustomerSettleCheckSheetBizType.SETTLE_PRE_SHEET);
                result.setCalcType(CustomerSettleCheckSheetCalcType.ADD);

                results.add(result);
            }
        }

        results.stream().filter(t -> t.getCalcType() == CustomerSettleCheckSheetCalcType.SUB)
                .forEach(t -> t.setTotalAmount(t.getTotalAmount().negate()));

        return results;
    }

    @Transactional
    @Override
    public int setUnSettle(String id) {

        Wrapper<CustomerSettleCheckSheet> updateWrapper = Wrappers.lambdaUpdate(
                        CustomerSettleCheckSheet.class)
                .set(CustomerSettleCheckSheet::getSettleStatus, SettleStatus.UN_SETTLE)
                .eq(CustomerSettleCheckSheet::getId, id)
                .eq(CustomerSettleCheckSheet::getSettleStatus, SettleStatus.PART_SETTLE);
        int count = getBaseMapper().update(updateWrapper);

        return count;
    }

    @Transactional
    @Override
    public int setPartSettle(String id) {

        Wrapper<CustomerSettleCheckSheet> updateWrapper = Wrappers.lambdaUpdate(
                        CustomerSettleCheckSheet.class)
                .set(CustomerSettleCheckSheet::getSettleStatus, SettleStatus.PART_SETTLE)
                .eq(CustomerSettleCheckSheet::getId, id)
                .in(CustomerSettleCheckSheet::getSettleStatus, SettleStatus.UN_SETTLE,
                        SettleStatus.PART_SETTLE);
        int count = getBaseMapper().update(updateWrapper);

        return count;
    }

    @Transactional
    @Override
    public int setSettled(String id) {

        Wrapper<CustomerSettleCheckSheet> updateWrapper = Wrappers.lambdaUpdate(
                        CustomerSettleCheckSheet.class)
                .set(CustomerSettleCheckSheet::getSettleStatus, SettleStatus.SETTLED)
                .eq(CustomerSettleCheckSheet::getId, id)
                .in(CustomerSettleCheckSheet::getSettleStatus, SettleStatus.UN_SETTLE,
                        SettleStatus.PART_SETTLE);
        int count = getBaseMapper().update(updateWrapper);

        //将所有的单据的结算状态更新
        Wrapper<CustomerSettleCheckSheetDetail> queryDetailWrapper = Wrappers.lambdaQuery(
                        CustomerSettleCheckSheetDetail.class)
                .eq(CustomerSettleCheckSheetDetail::getSheetId, id)
                .orderByAsc(CustomerSettleCheckSheetDetail::getOrderNo);
        List<CustomerSettleCheckSheetDetail> sheetDetails = customerSettleCheckSheetDetailService.list(
                queryDetailWrapper);
        for (CustomerSettleCheckSheetDetail sheetDetail : sheetDetails) {
            this.setBizItemSettled(sheetDetail.getBizId(), sheetDetail.getBizType());
        }

        return count;
    }

    @Override
    public List<CustomerSettleCheckSheet> getApprovedList(String customerId,
            LocalDateTime startTime, LocalDateTime endTime) {

        return getBaseMapper().getApprovedList(customerId, startTime, endTime);
    }

    @Transactional
    @Override
    public void setSettleAmount(String id, BigDecimal totalPayedAmount,
            BigDecimal totalDiscountAmount) {

        CustomerSettleCheckSheet checkSheet = getBaseMapper().selectById(id);
        BigDecimal remainTotalPayAmount = NumberUtil.sub(checkSheet.getTotalPayAmount(),
                checkSheet.getTotalPayedAmount(), checkSheet.getTotalDiscountAmount(),
                totalPayedAmount,
                totalDiscountAmount);
        BigDecimal totalPayAmount = NumberUtil.sub(checkSheet.getTotalPayAmount(),
                checkSheet.getTotalPayedAmount(),
                checkSheet.getTotalDiscountAmount());
        if (NumberUtil.lt(checkSheet.getTotalPayAmount(), 0)) {
            if (NumberUtil.gt(remainTotalPayAmount, 0)) {
                throw new DefaultClientException(
                        "对账单：" + checkSheet.getCode() + "，剩余收款金额为" + totalPayAmount + "元，本次收款金额为"
                                + NumberUtil.add(
                                totalPayedAmount, totalDiscountAmount) + "元，无法结算！");
            }
        }
        if (NumberUtil.gt(checkSheet.getTotalPayAmount(), 0)) {
            if (NumberUtil.lt(remainTotalPayAmount, 0)) {
                throw new DefaultClientException(
                        "对账单：" + checkSheet.getCode() + "，剩余收款金额为" + totalPayAmount + "元，本次收款金额为"
                                + NumberUtil.add(
                                totalPayedAmount, totalDiscountAmount) + "元，无法结算！");
            }
        }
        Wrapper<CustomerSettleCheckSheet> updateWrapper = Wrappers.lambdaUpdate(
                        CustomerSettleCheckSheet.class)
                .set(CustomerSettleCheckSheet::getTotalPayedAmount,
                        NumberUtil.add(totalPayedAmount, checkSheet.getTotalPayedAmount()))
                .set(CustomerSettleCheckSheet::getTotalDiscountAmount,
                        NumberUtil.add(totalDiscountAmount, checkSheet.getTotalDiscountAmount()))
                .eq(CustomerSettleCheckSheet::getId, id)
                .eq(CustomerSettleCheckSheet::getTotalPayedAmount, checkSheet.getTotalPayedAmount())
                .eq(CustomerSettleCheckSheet::getTotalDiscountAmount,
                        checkSheet.getTotalDiscountAmount());
        if (getBaseMapper().update(updateWrapper) != 1) {
            throw new DefaultClientException("结账单：" + checkSheet.getCode() + "，信息已过期，请刷新重试！");
        }

        if (NumberUtil.equal(remainTotalPayAmount, 0)) {
            this.setSettled(id);
        }
    }

    private void create(CustomerSettleCheckSheet sheet, CreateCustomerSettleCheckSheetVo vo) {

        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalPayAmount = BigDecimal.ZERO;

        int orderNo = 0;
        for (CustomerSettleCheckSheetItemVo itemVo : vo.getItems()) {
            orderNo++;
            CustomerSettleCheckBizItemDto item = this.getBizItem(itemVo.getId(),
                    EnumUtil.getByCode(CustomerSettleCheckSheetBizType.class, itemVo.getBizType()));
            if (item == null) {
                throw new DefaultClientException("第" + orderNo + "行业务单据不存在！");
            }
            CustomerSettleCheckSheetDetail detail = new CustomerSettleCheckSheetDetail();

            detail.setId(IdUtil.getId());
            detail.setSheetId(sheet.getId());
            detail.setBizId(itemVo.getId());
            detail.setBizType(
                    EnumUtil.getByCode(CustomerSettleCheckSheetBizType.class, itemVo.getBizType()));
            detail.setCalcType(item.getCalcType());
            if (item.getCalcType() == CustomerSettleCheckSheetCalcType.ADD) {
                if (NumberUtil.lt(itemVo.getPayAmount(), BigDecimal.ZERO)) {
                    throw new DefaultClientException("第" + orderNo + "行业务单据应收金额不允许小于0！");
                }
            } else {
                if (NumberUtil.gt(itemVo.getPayAmount(), BigDecimal.ZERO)) {
                    throw new DefaultClientException("第" + orderNo + "行业务单据应收金额不允许大于0！");
                }
            }
            detail.setPayAmount(itemVo.getPayAmount());
            detail.setDescription(itemVo.getDescription());
            detail.setOrderNo(orderNo);

            customerSettleCheckSheetDetailService.save(detail);

            totalAmount = NumberUtil.add(totalAmount, item.getTotalAmount());
            totalPayAmount = NumberUtil.add(totalPayAmount, itemVo.getPayAmount());

            //将所有的单据的结算状态更新
            this.setBizItemPartSettle(detail.getBizId(), detail.getBizType());
        }

        AbstractUserDetails currentUser = SecurityUtil.getCurrentUser();

        sheet.setCustomerId(vo.getCustomerId());
        sheet.setTotalAmount(totalAmount);
        sheet.setTotalPayAmount(totalPayAmount);
        sheet.setTotalPayedAmount(BigDecimal.ZERO);
        sheet.setTotalDiscountAmount(BigDecimal.ZERO);
        sheet.setDescription(StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR
                : vo.getDescription());
        sheet.setCreateBy(currentUser.getId());
        sheet.setRefuseReason(StringPool.EMPTY_STR);
        sheet.setSettleStatus(SettleStatus.UN_SETTLE);
        sheet.setStartDate(vo.getStartDate());
        sheet.setEndDate(vo.getEndDate());
    }
}

package com.lframework.xingyun.settle.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.common.constants.StringPool;
import com.lframework.common.exceptions.ClientException;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.exceptions.impl.DefaultSysException;
import com.lframework.common.exceptions.impl.InputErrorException;
import com.lframework.common.utils.*;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.enums.OpLogType;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.OpLogUtil;
import com.lframework.starter.mybatis.utils.PageHelperUtil;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.web.components.security.AbstractUserDetails;
import com.lframework.starter.web.service.IGenerateCodeService;
import com.lframework.starter.web.utils.EnumUtil;
import com.lframework.starter.web.utils.SecurityUtil;
import com.lframework.xingyun.sc.dto.purchase.receive.ReceiveSheetDto;
import com.lframework.xingyun.sc.dto.purchase.returned.PurchaseReturnDto;
import com.lframework.xingyun.sc.enums.SettleStatus;
import com.lframework.xingyun.sc.service.purchase.IPurchaseReturnService;
import com.lframework.xingyun.sc.service.purchase.IReceiveSheetService;
import com.lframework.xingyun.settle.components.code.GenerateCodeTypePool;
import com.lframework.xingyun.settle.dto.check.SettleCheckBizItemDto;
import com.lframework.xingyun.settle.dto.check.SettleCheckSheetDto;
import com.lframework.xingyun.settle.dto.check.SettleCheckSheetFullDto;
import com.lframework.xingyun.settle.dto.fee.SettleFeeSheetDto;
import com.lframework.xingyun.settle.dto.pre.SettlePreSheetDto;
import com.lframework.xingyun.settle.entity.SettleCheckSheet;
import com.lframework.xingyun.settle.entity.SettleCheckSheetDetail;
import com.lframework.xingyun.settle.enums.SettleCheckSheetBizType;
import com.lframework.xingyun.settle.enums.SettleCheckSheetStatus;
import com.lframework.xingyun.settle.mappers.SettleCheckSheetDetailMapper;
import com.lframework.xingyun.settle.mappers.SettleCheckSheetMapper;
import com.lframework.xingyun.settle.service.ISettleCheckSheetService;
import com.lframework.xingyun.settle.service.ISettleFeeSheetService;
import com.lframework.xingyun.settle.service.ISettlePreSheetService;
import com.lframework.xingyun.settle.vo.check.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SettleCheckSheetServiceImpl implements ISettleCheckSheetService {

    @Autowired
    private SettleCheckSheetMapper settleCheckSheetMapper;

    @Autowired
    private SettleCheckSheetDetailMapper settleCheckSheetDetailMapper;

    @Autowired
    private IGenerateCodeService generateCodeService;

    @Autowired
    private IReceiveSheetService receiveSheetService;

    @Autowired
    private IPurchaseReturnService purchaseReturnService;

    @Autowired
    private ISettleFeeSheetService settleFeeSheetService;

    @Autowired
    private ISettlePreSheetService settlePreSheetService;

    @Override
    public PageResult<SettleCheckSheetDto> query(Integer pageIndex, Integer pageSize, QuerySettleCheckSheetVo vo) {

        Assert.greaterThanZero(pageIndex);
        Assert.greaterThanZero(pageSize);

        PageHelperUtil.startPage(pageIndex, pageSize);
        List<SettleCheckSheetDto> datas = this.query(vo);

        return PageResultUtil.convert(new PageInfo<>(datas));
    }

    @Override
    public List<SettleCheckSheetDto> query(QuerySettleCheckSheetVo vo) {

        return settleCheckSheetMapper.query(vo);
    }

    @Cacheable(value = SettleCheckSheetDto.CACHE_NAME, key = "#id")
    @Override
    public SettleCheckSheetDto getById(String id) {

        return settleCheckSheetMapper.getById(id);
    }

    @Override
    public SettleCheckSheetFullDto getDetail(String id) {

        return settleCheckSheetMapper.getDetail(id);
    }

    @OpLog(type = OpLogType.OTHER, name = "创建供应商对账单，单号：{}", params = "#code")
    @Transactional
    @Override
    public String create(CreateSettleCheckSheetVo vo) {

        SettleCheckSheet sheet = new SettleCheckSheet();

        sheet.setId(IdUtil.getId());
        sheet.setCode(generateCodeService.generate(GenerateCodeTypePool.SETTLE_CHECK_SHEET));

        this.create(sheet, vo);

        sheet.setStatus(SettleCheckSheetStatus.CREATED);

        OpLogUtil.setVariable("code", sheet.getCode());
        OpLogUtil.setExtra(vo);

        settleCheckSheetMapper.insert(sheet);

        return sheet.getId();
    }

    @OpLog(type = OpLogType.OTHER, name = "修改供应商对账单，单号：{}", params = "#code")
    @Transactional
    @Override
    public void update(UpdateSettleCheckSheetVo vo) {

        SettleCheckSheet sheet = settleCheckSheetMapper.selectById(vo.getId());
        if (sheet == null) {
            throw new DefaultClientException("供应商对账单不存在！");
        }

        if (sheet.getStatus() != SettleCheckSheetStatus.CREATED
                && sheet.getStatus() != SettleCheckSheetStatus.APPROVE_REFUSE) {
            if (sheet.getStatus() == SettleCheckSheetStatus.APPROVE_PASS) {
                throw new DefaultClientException("供应商对账单已审核通过，无法修改！");
            } else {
                throw new DefaultClientException("供应商对账单无法修改！");
            }
        }

        //将所有的单据的结算状态更新
        Wrapper<SettleCheckSheetDetail> queryDetailWrapper = Wrappers.lambdaQuery(SettleCheckSheetDetail.class)
                .eq(SettleCheckSheetDetail::getSheetId, sheet.getId()).orderByAsc(SettleCheckSheetDetail::getOrderNo);
        List<SettleCheckSheetDetail> sheetDetails = settleCheckSheetDetailMapper.selectList(queryDetailWrapper);
        for (SettleCheckSheetDetail sheetDetail : sheetDetails) {
            this.setBizItemUnSettle(sheetDetail.getBizId(), sheetDetail.getBizType());
        }

        // 删除明细
        Wrapper<SettleCheckSheetDetail> deleteDetailWrapper = Wrappers.lambdaQuery(SettleCheckSheetDetail.class)
                .eq(SettleCheckSheetDetail::getSheetId, sheet.getId());
        settleCheckSheetDetailMapper.delete(deleteDetailWrapper);

        this.create(sheet, vo);

        sheet.setStatus(SettleCheckSheetStatus.CREATED);

        List<SettleCheckSheetStatus> statusList = new ArrayList<>();
        statusList.add(SettleCheckSheetStatus.CREATED);
        statusList.add(SettleCheckSheetStatus.APPROVE_REFUSE);

        Wrapper<SettleCheckSheet> updateWrapper = Wrappers.lambdaUpdate(SettleCheckSheet.class)
                .set(SettleCheckSheet::getApproveBy, null).set(SettleCheckSheet::getApproveTime, null)
                .set(SettleCheckSheet::getRefuseReason, StringPool.EMPTY_STR).eq(SettleCheckSheet::getId, sheet.getId())
                .in(SettleCheckSheet::getStatus, statusList);
        if (settleCheckSheetMapper.update(sheet, updateWrapper) != 1) {
            throw new DefaultClientException("供应商对账单信息已过期，请刷新重试！");
        }

        OpLogUtil.setVariable("code", sheet.getCode());
        OpLogUtil.setExtra(vo);

        ISettleCheckSheetService thisService = getThis(this.getClass());
        thisService.cleanCacheByKey(sheet.getId());
    }

    @OpLog(type = OpLogType.OTHER, name = "审核通过供应商对账单，单号：{}", params = "#code")
    @Transactional
    @Override
    public void approvePass(ApprovePassSettleCheckSheetVo vo) {

        SettleCheckSheet sheet = settleCheckSheetMapper.selectById(vo.getId());
        if (sheet == null) {
            throw new DefaultClientException("供应商对账单不存在！");
        }

        if (sheet.getStatus() != SettleCheckSheetStatus.CREATED
                && sheet.getStatus() != SettleCheckSheetStatus.APPROVE_REFUSE) {
            if (sheet.getStatus() == SettleCheckSheetStatus.APPROVE_PASS) {
                throw new DefaultClientException("供应商对账单已审核通过，不允许继续执行审核！");
            }
            throw new DefaultClientException("供应商对账单无法审核通过！");
        }

        sheet.setStatus(SettleCheckSheetStatus.APPROVE_PASS);
        sheet.setApproveBy(SecurityUtil.getCurrentUser().getId());
        sheet.setApproveTime(LocalDateTime.now());
        if (!StringUtil.isBlank(vo.getDescription())) {
            sheet.setDescription(vo.getDescription());
        }

        List<SettleCheckSheetStatus> statusList = new ArrayList<>();
        statusList.add(SettleCheckSheetStatus.CREATED);
        statusList.add(SettleCheckSheetStatus.APPROVE_REFUSE);

        Wrapper<SettleCheckSheet> updateWrapper = Wrappers.lambdaUpdate(SettleCheckSheet.class)
                .eq(SettleCheckSheet::getId, sheet.getId()).in(SettleCheckSheet::getStatus, statusList);
        if (settleCheckSheetMapper.update(sheet, updateWrapper) != 1) {
            throw new DefaultClientException("供应商对账单信息已过期，请刷新重试！");
        }

        OpLogUtil.setVariable("code", sheet.getCode());
        OpLogUtil.setExtra(vo);

        ISettleCheckSheetService thisService = getThis(this.getClass());
        thisService.cleanCacheByKey(sheet.getId());
    }

    @Transactional
    @Override
    public void redirectApprovePass(CreateSettleCheckSheetVo vo) {

        ISettleCheckSheetService thisService = getThis(this.getClass());

        String id = thisService.create(vo);

        ApprovePassSettleCheckSheetVo approveVo = new ApprovePassSettleCheckSheetVo();
        approveVo.setId(id);

        thisService.approvePass(approveVo);
    }

    @OpLog(type = OpLogType.OTHER, name = "审核拒绝供应商对账单，单号：{}", params = "#code")
    @Transactional
    @Override
    public void approveRefuse(ApproveRefuseSettleCheckSheetVo vo) {

        SettleCheckSheet sheet = settleCheckSheetMapper.selectById(vo.getId());
        if (sheet == null) {
            throw new DefaultClientException("供应商对账单不存在！");
        }

        if (sheet.getStatus() != SettleCheckSheetStatus.CREATED) {
            if (sheet.getStatus() == SettleCheckSheetStatus.APPROVE_PASS) {
                throw new DefaultClientException("供应商对账单已审核通过，不允许继续执行审核！");
            }
            if (sheet.getStatus() == SettleCheckSheetStatus.APPROVE_REFUSE) {
                throw new DefaultClientException("供应商对账单已审核拒绝，不允许继续执行审核！");
            }
            throw new DefaultClientException("供应商对账单无法审核拒绝！");
        }

        sheet.setStatus(SettleCheckSheetStatus.APPROVE_REFUSE);
        sheet.setApproveBy(SecurityUtil.getCurrentUser().getId());
        sheet.setApproveTime(LocalDateTime.now());
        sheet.setRefuseReason(vo.getRefuseReason());

        List<SettleCheckSheetStatus> statusList = new ArrayList<>();
        statusList.add(SettleCheckSheetStatus.CREATED);
        statusList.add(SettleCheckSheetStatus.APPROVE_REFUSE);

        Wrapper<SettleCheckSheet> updateWrapper = Wrappers.lambdaUpdate(SettleCheckSheet.class)
                .in(SettleCheckSheet::getStatus, statusList);
        if (settleCheckSheetMapper.update(sheet, updateWrapper) != 1) {
            throw new DefaultClientException("供应商对账单信息已过期，请刷新重试！");
        }

        OpLogUtil.setVariable("code", sheet.getCode());
        OpLogUtil.setExtra(vo);

        ISettleCheckSheetService thisService = getThis(this.getClass());
        thisService.cleanCacheByKey(sheet.getId());
    }

    @Transactional
    @Override
    public void batchApprovePass(BatchApprovePassSettleCheckSheetVo vo) {

        ISettleCheckSheetService thisService = getThis(this.getClass());
        int orderNo = 1;
        for (String id : vo.getIds()) {
            ApprovePassSettleCheckSheetVo approveVo = new ApprovePassSettleCheckSheetVo();
            approveVo.setId(id);
            try {
                thisService.approvePass(approveVo);
            } catch (ClientException e) {
                throw new DefaultClientException("第" + orderNo + "个供应商对账单审核通过失败，失败原因：" + e.getMsg());
            }
            orderNo++;
        }
    }

    @Transactional
    @Override
    public void batchApproveRefuse(BatchApproveRefuseSettleCheckSheetVo vo) {

        ISettleCheckSheetService thisService = getThis(this.getClass());
        int orderNo = 1;
        for (String id : vo.getIds()) {
            ApproveRefuseSettleCheckSheetVo approveVo = new ApproveRefuseSettleCheckSheetVo();
            approveVo.setId(id);
            approveVo.setRefuseReason(vo.getRefuseReason());

            try {
                thisService.approveRefuse(approveVo);
            } catch (ClientException e) {
                throw new DefaultClientException("第" + orderNo + "个供应商对账单审核拒绝失败，失败原因：" + e.getMsg());
            }
            orderNo++;
        }
    }

    @OpLog(type = OpLogType.OTHER, name = "删除供应商对账单，单号：{}", params = "#code")
    @Transactional
    @Override
    public void deleteById(String id) {

        Assert.notBlank(id);
        SettleCheckSheet sheet = settleCheckSheetMapper.selectById(id);
        if (sheet == null) {
            throw new InputErrorException("供应商对账单不存在！");
        }

        if (sheet.getStatus() != SettleCheckSheetStatus.CREATED
                && sheet.getStatus() != SettleCheckSheetStatus.APPROVE_REFUSE) {

            if (sheet.getStatus() == SettleCheckSheetStatus.APPROVE_PASS) {
                throw new DefaultClientException("“审核通过”的供应商对账单不允许执行删除操作！");
            }

            throw new DefaultClientException("供应商对账单无法删除！");
        }

        //将所有的单据的结算状态更新
        Wrapper<SettleCheckSheetDetail> queryDetailWrapper = Wrappers.lambdaQuery(SettleCheckSheetDetail.class)
                .eq(SettleCheckSheetDetail::getSheetId, sheet.getId()).orderByAsc(SettleCheckSheetDetail::getOrderNo);
        List<SettleCheckSheetDetail> sheetDetails = settleCheckSheetDetailMapper.selectList(queryDetailWrapper);
        for (SettleCheckSheetDetail sheetDetail : sheetDetails) {
            this.setBizItemUnSettle(sheetDetail.getBizId(), sheetDetail.getBizType());
        }

        // 删除明细
        Wrapper<SettleCheckSheetDetail> deleteDetailWrapper = Wrappers.lambdaQuery(SettleCheckSheetDetail.class)
                .eq(SettleCheckSheetDetail::getSheetId, sheet.getId());
        settleCheckSheetDetailMapper.delete(deleteDetailWrapper);

        // 删除单据
        settleCheckSheetMapper.deleteById(id);

        OpLogUtil.setVariable("code", sheet.getCode());

        ISettleCheckSheetService thisService = getThis(this.getClass());
        thisService.cleanCacheByKey(sheet.getId());
    }

    @Transactional
    @Override
    public void deleteByIds(List<String> ids) {

        if (!CollectionUtil.isEmpty(ids)) {
            int orderNo = 1;
            for (String id : ids) {

                try {
                    ISettleCheckSheetService thisService = getThis(this.getClass());
                    thisService.deleteById(id);
                } catch (ClientException e) {
                    throw new DefaultClientException("第" + orderNo + "个供应商对账单删除失败，失败原因：" + e.getMsg());
                }

                orderNo++;
            }
        }
    }

    @Override
    public SettleCheckBizItemDto getBizItem(String id, SettleCheckSheetBizType bizType) {

        SettleCheckBizItemDto result = new SettleCheckBizItemDto();

        switch (bizType) {
            case RECEIVE_SHEET: {
                ReceiveSheetDto receiveSheet = receiveSheetService.getById(id);

                result.setId(receiveSheet.getId());
                result.setCode(receiveSheet.getCode());
                result.setTotalAmount(receiveSheet.getTotalAmount());
                result.setApproveTime(receiveSheet.getApproveTime());
                break;
            }
            case PURCHASE_RETURN: {
                PurchaseReturnDto purchaseReturn = purchaseReturnService.getById(id);

                result.setId(purchaseReturn.getId());
                result.setCode(purchaseReturn.getCode());
                result.setTotalAmount(purchaseReturn.getTotalAmount().negate());
                result.setApproveTime(purchaseReturn.getApproveTime());
                break;
            }
            case SETTLE_FEE_SHEET: {
                SettleFeeSheetDto feeSheet = settleFeeSheetService.getById(id);

                result.setId(feeSheet.getId());
                result.setCode(feeSheet.getCode());
                result.setTotalAmount(feeSheet.getTotalAmount());
                result.setApproveTime(feeSheet.getApproveTime());
                break;
            }
            case SETTLE_PRE_SHEET: {
                SettlePreSheetDto preSheet = settlePreSheetService.getById(id);

                result.setId(preSheet.getId());
                result.setCode(preSheet.getCode());
                result.setTotalAmount(preSheet.getTotalAmount().negate());
                result.setApproveTime(preSheet.getApproveTime());
                break;
            }
            default: {
                throw new DefaultSysException("未知的SettleCheckSheetBizType");
            }
        }

        result.setBizType(bizType);

        return result;
    }

    @Transactional
    @Override
    public void setBizItemUnSettle(String id, SettleCheckSheetBizType bizType) {

        SettleCheckBizItemDto item = this.getBizItem(id, bizType);

        switch (bizType) {
            case RECEIVE_SHEET: {
                int count = receiveSheetService.setUnSettle(id);
                if (count != 1) {
                    throw new DefaultClientException("单号：" + item.getCode() + "，" + bizType.getDesc() + "已结算，业务无法进行！");
                }
                break;
            }
            case PURCHASE_RETURN: {
                int count = purchaseReturnService.setUnSettle(id);
                if (count != 1) {
                    throw new DefaultClientException("单号：" + item.getCode() + "，" + bizType.getDesc() + "已结算，业务无法进行！");
                }
                break;
            }
            case SETTLE_FEE_SHEET: {
                int count = settleFeeSheetService.setUnSettle(id);
                if (count != 1) {
                    throw new DefaultClientException("单号：" + item.getCode() + "，" + bizType.getDesc() + "已结算，业务无法进行！");
                }
                break;
            }
            case SETTLE_PRE_SHEET: {
                int count = settlePreSheetService.setUnSettle(id);
                if (count != 1) {
                    throw new DefaultClientException("单号：" + item.getCode() + "，" + bizType.getDesc() + "已结算，业务无法进行！");
                }
                break;
            }
            default: {
                throw new DefaultSysException("未知的SettleCheckSheetBizType");
            }
        }
    }

    @Transactional
    @Override
    public void setBizItemPartSettle(String id, SettleCheckSheetBizType bizType) {

        SettleCheckBizItemDto item = this.getBizItem(id, bizType);

        switch (bizType) {
            case RECEIVE_SHEET: {
                int count = receiveSheetService.setPartSettle(id);
                if (count != 1) {
                    throw new DefaultClientException("单号：" + item.getCode() + "，" + bizType.getDesc() + "已结算，业务无法进行！");
                }
                break;
            }
            case PURCHASE_RETURN: {
                int count = purchaseReturnService.setPartSettle(id);
                if (count != 1) {
                    throw new DefaultClientException("单号：" + item.getCode() + "，" + bizType.getDesc() + "已结算，业务无法进行！");
                }
                break;
            }
            case SETTLE_FEE_SHEET: {
                int count = settleFeeSheetService.setPartSettle(id);
                if (count != 1) {
                    throw new DefaultClientException("单号：" + item.getCode() + "，" + bizType.getDesc() + "已结算，业务无法进行！");
                }
                break;
            }
            case SETTLE_PRE_SHEET: {
                int count = settlePreSheetService.setPartSettle(id);
                if (count != 1) {
                    throw new DefaultClientException("单号：" + item.getCode() + "，" + bizType.getDesc() + "已结算，业务无法进行！");
                }
                break;
            }
            default: {
                throw new DefaultSysException("未知的SettleCheckSheetBizType");
            }
        }
    }

    @Transactional
    @Override
    public void setBizItemSettled(String id, SettleCheckSheetBizType bizType) {

        SettleCheckBizItemDto item = this.getBizItem(id, bizType);

        switch (bizType) {
            case RECEIVE_SHEET: {
                int count = receiveSheetService.setSettled(id);
                if (count != 1) {
                    throw new DefaultClientException("单号：" + item.getCode() + "，" + bizType.getDesc() + "已结算，无法重复结算！");
                }
                break;
            }
            case PURCHASE_RETURN: {
                int count = purchaseReturnService.setSettled(id);
                if (count != 1) {
                    throw new DefaultClientException("单号：" + item.getCode() + "，" + bizType.getDesc() + "已结算，无法重复结算！");
                }
                break;
            }
            case SETTLE_FEE_SHEET: {
                int count = settleFeeSheetService.setSettled(id);
                if (count != 1) {
                    throw new DefaultClientException("单号：" + item.getCode() + "，" + bizType.getDesc() + "已结算，无法重复结算！");
                }
                break;
            }
            case SETTLE_PRE_SHEET: {
                int count = settlePreSheetService.setSettled(id);
                if (count != 1) {
                    throw new DefaultClientException("单号：" + item.getCode() + "，" + bizType.getDesc() + "已结算，无法重复结算！");
                }
                break;
            }
            default: {
                throw new DefaultSysException("未知的SettleCheckSheetBizType");
            }
        }
    }

    @Override
    public List<SettleCheckBizItemDto> getUnCheckBizItems(QueryUnCheckBizItemVo vo) {

        List<SettleCheckBizItemDto> results = new ArrayList<>();

        List<ReceiveSheetDto> receiveSheetList = receiveSheetService
                .getApprovedList(vo.getSupplierId(), vo.getStartTime(), vo.getEndTime(), SettleStatus.UN_SETTLE);

        List<PurchaseReturnDto> purchaseReturnList = purchaseReturnService
                .getApprovedList(vo.getSupplierId(), vo.getStartTime(), vo.getEndTime(), SettleStatus.UN_SETTLE);

        List<SettleFeeSheetDto> feeSheetList = settleFeeSheetService
                .getApprovedList(vo.getSupplierId(), vo.getStartTime(), vo.getEndTime(), SettleStatus.UN_SETTLE);

        List<SettlePreSheetDto> preSheetList = settlePreSheetService
                .getApprovedList(vo.getSupplierId(), vo.getStartTime(), vo.getEndTime(), SettleStatus.UN_SETTLE);

        if (!CollectionUtil.isEmpty(receiveSheetList)) {
            for (ReceiveSheetDto item : receiveSheetList) {
                SettleCheckBizItemDto result = new SettleCheckBizItemDto();
                result.setId(item.getId());
                result.setCode(item.getCode());
                result.setTotalAmount(item.getTotalAmount());
                result.setApproveTime(item.getApproveTime());
                result.setBizType(SettleCheckSheetBizType.RECEIVE_SHEET);

                results.add(result);
            }
        }

        if (!CollectionUtil.isEmpty(purchaseReturnList)) {
            for (PurchaseReturnDto item : purchaseReturnList) {
                SettleCheckBizItemDto result = new SettleCheckBizItemDto();
                result.setId(item.getId());
                result.setCode(item.getCode());
                result.setTotalAmount(item.getTotalAmount());
                result.setApproveTime(item.getApproveTime());
                result.setBizType(SettleCheckSheetBizType.PURCHASE_RETURN);

                results.add(result);
            }
        }

        if (!CollectionUtil.isEmpty(feeSheetList)) {
            for (SettleFeeSheetDto item : feeSheetList) {
                SettleCheckBizItemDto result = new SettleCheckBizItemDto();
                result.setId(item.getId());
                result.setCode(item.getCode());
                result.setTotalAmount(item.getTotalAmount());
                result.setApproveTime(item.getApproveTime());
                result.setBizType(SettleCheckSheetBizType.SETTLE_FEE_SHEET);

                results.add(result);
            }
        }

        if (!CollectionUtil.isEmpty(preSheetList)) {
            for (SettlePreSheetDto item : preSheetList) {
                SettleCheckBizItemDto result = new SettleCheckBizItemDto();
                result.setId(item.getId());
                result.setCode(item.getCode());
                result.setTotalAmount(item.getTotalAmount());
                result.setApproveTime(item.getApproveTime());
                result.setBizType(SettleCheckSheetBizType.SETTLE_PRE_SHEET);

                results.add(result);
            }
        }

        return results;
    }

    @Transactional
    @Override
    public int setUnSettle(String id) {

        Wrapper<SettleCheckSheet> updateWrapper = Wrappers.lambdaUpdate(SettleCheckSheet.class)
                .set(SettleCheckSheet::getSettleStatus, SettleStatus.UN_SETTLE).eq(SettleCheckSheet::getId, id)
                .eq(SettleCheckSheet::getSettleStatus, SettleStatus.PART_SETTLE);
        return settleCheckSheetMapper.update(updateWrapper);
    }

    @Transactional
    @Override
    public int setPartSettle(String id) {

        Wrapper<SettleCheckSheet> updateWrapper = Wrappers.lambdaUpdate(SettleCheckSheet.class)
                .set(SettleCheckSheet::getSettleStatus, SettleStatus.PART_SETTLE).eq(SettleCheckSheet::getId, id)
                .eq(SettleCheckSheet::getSettleStatus, SettleStatus.UN_SETTLE);
        return settleCheckSheetMapper.update(updateWrapper);
    }

    @Transactional
    @Override
    public int setSettled(String id) {

        Wrapper<SettleCheckSheet> updateWrapper = Wrappers.lambdaUpdate(SettleCheckSheet.class)
                .set(SettleCheckSheet::getSettleStatus, SettleStatus.SETTLED).eq(SettleCheckSheet::getId, id)
                .in(SettleCheckSheet::getSettleStatus, SettleStatus.UN_SETTLE, SettleStatus.PART_SETTLE);
        return settleCheckSheetMapper.update(updateWrapper);
    }

    @Override
    public List<SettleCheckSheetDto> getApprovedList(String supplierId, LocalDateTime startTime,
            LocalDateTime endTime) {

        return settleCheckSheetMapper.getApprovedList(supplierId, startTime, endTime);
    }

    @Transactional
    @Override
    public void setSettleAmount(String id, BigDecimal totalPayedAmount, BigDecimal totalDiscountAmount) {

        SettleCheckSheet checkSheet = settleCheckSheetMapper.selectById(id);
        BigDecimal remainTotalPayAmount = NumberUtil
                .sub(checkSheet.getTotalPayAmount(), checkSheet.getTotalPayedAmount(),
                        checkSheet.getTotalDiscountAmount(), totalPayedAmount, totalDiscountAmount);
        BigDecimal totalPayAmount = NumberUtil.sub(checkSheet.getTotalPayAmount(), checkSheet.getTotalPayedAmount(),
                checkSheet.getTotalDiscountAmount());
        if (NumberUtil.lt(checkSheet.getTotalPayAmount(), 0)) {
            if (NumberUtil.gt(remainTotalPayAmount, 0)) {
                throw new DefaultClientException(
                        "结账单：" + checkSheet.getCode() + "，剩余付款金额为" + totalPayAmount + "元，本次付款金额为" + NumberUtil
                                .add(totalPayedAmount, totalDiscountAmount) + "元，无法结算！");
            }
        }
        if (NumberUtil.gt(checkSheet.getTotalPayAmount(), 0)) {
            if (NumberUtil.lt(remainTotalPayAmount, 0)) {
                throw new DefaultClientException(
                        "结账单：" + checkSheet.getCode() + "，剩余付款金额为" + totalPayAmount + "元，本次付款金额为" + NumberUtil
                                .add(totalPayedAmount, totalDiscountAmount) + "元，无法结算！");
            }
        }
        Wrapper<SettleCheckSheet> updateWrapper = Wrappers.lambdaUpdate(SettleCheckSheet.class)
                .set(SettleCheckSheet::getTotalPayedAmount,
                        NumberUtil.add(totalPayedAmount, checkSheet.getTotalPayedAmount()))
                .set(SettleCheckSheet::getTotalDiscountAmount,
                        NumberUtil.add(totalDiscountAmount, checkSheet.getTotalDiscountAmount()))
                .eq(SettleCheckSheet::getId, id)
                .eq(SettleCheckSheet::getTotalPayedAmount, checkSheet.getTotalPayedAmount())
                .eq(SettleCheckSheet::getTotalDiscountAmount, checkSheet.getTotalDiscountAmount());
        if (settleCheckSheetMapper.update(updateWrapper) != 1) {
            throw new DefaultClientException("结账单：" + checkSheet.getCode() + "，信息已过时，请刷新重试！");
        }

        if (NumberUtil.equal(remainTotalPayAmount, 0)) {
            this.setSettled(id);
        }
    }

    @CacheEvict(value = SettleCheckSheetDto.CACHE_NAME, key = "#key")
    @Override
    public void cleanCacheByKey(String key) {

    }

    private void create(SettleCheckSheet sheet, CreateSettleCheckSheetVo vo) {

        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalPayAmount = BigDecimal.ZERO;

        int orderNo = 1;
        for (SettleCheckSheetItemVo itemVo : vo.getItems()) {
            SettleCheckBizItemDto item = this
                    .getBizItem(itemVo.getId(), EnumUtil.getByCode(SettleCheckSheetBizType.class, itemVo.getBizType()));
            if (item == null) {
                throw new DefaultClientException("第" + orderNo + "行业务单据不存在！");
            }
            SettleCheckSheetDetail detail = new SettleCheckSheetDetail();

            detail.setId(IdUtil.getId());
            detail.setSheetId(sheet.getId());
            detail.setBizId(itemVo.getId());
            detail.setBizType(EnumUtil.getByCode(SettleCheckSheetBizType.class, itemVo.getBizType()));
            detail.setPayAmount(itemVo.getPayAmount());
            detail.setDescription(itemVo.getDescription());
            detail.setOrderNo(orderNo);

            settleCheckSheetDetailMapper.insert(detail);

            totalAmount = NumberUtil.add(totalAmount, item.getTotalAmount());
            totalPayAmount = NumberUtil.add(totalPayAmount, itemVo.getPayAmount());

            //将所有的单据的结算状态更新
            this.setBizItemPartSettle(detail.getBizId(), detail.getBizType());

            orderNo++;
        }

        AbstractUserDetails currentUser = SecurityUtil.getCurrentUser();

        sheet.setSupplierId(vo.getSupplierId());
        sheet.setTotalAmount(totalAmount);
        sheet.setTotalPayAmount(totalPayAmount);
        sheet.setTotalPayedAmount(BigDecimal.ZERO);
        sheet.setTotalDiscountAmount(BigDecimal.ZERO);
        sheet.setDescription(StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());
        sheet.setCreateBy(currentUser.getId());
        sheet.setRefuseReason(StringPool.EMPTY_STR);
        sheet.setSettleStatus(SettleStatus.UN_SETTLE);
        sheet.setStartDate(vo.getStartDate());
        sheet.setEndDate(vo.getEndDate());
    }
}

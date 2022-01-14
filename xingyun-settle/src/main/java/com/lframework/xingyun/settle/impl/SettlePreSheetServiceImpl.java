package com.lframework.xingyun.settle.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.common.constants.StringPool;
import com.lframework.common.exceptions.ClientException;
import com.lframework.common.exceptions.impl.DefaultClientException;
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
import com.lframework.starter.web.utils.SecurityUtil;
import com.lframework.xingyun.sc.enums.SettleStatus;
import com.lframework.xingyun.settle.components.code.GenerateCodeTypePool;
import com.lframework.xingyun.settle.dto.item.out.SettleOutItemDto;
import com.lframework.xingyun.settle.dto.pre.SettlePreSheetDto;
import com.lframework.xingyun.settle.dto.pre.SettlePreSheetFullDto;
import com.lframework.xingyun.settle.entity.SettlePreSheet;
import com.lframework.xingyun.settle.entity.SettlePreSheetDetail;
import com.lframework.xingyun.settle.enums.SettlePreSheetStatus;
import com.lframework.xingyun.settle.mappers.SettlePreSheetDetailMapper;
import com.lframework.xingyun.settle.mappers.SettlePreSheetMapper;
import com.lframework.xingyun.settle.service.ISettleOutItemService;
import com.lframework.xingyun.settle.service.ISettlePreSheetService;
import com.lframework.xingyun.settle.vo.pre.*;
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
public class SettlePreSheetServiceImpl implements ISettlePreSheetService {

    @Autowired
    private SettlePreSheetMapper settlePreSheetMapper;

    @Autowired
    private SettlePreSheetDetailMapper settlePreSheetDetailMapper;

    @Autowired
    private ISettleOutItemService settleOutItemService;

    @Autowired
    private IGenerateCodeService generateCodeService;

    @Override
    public PageResult<SettlePreSheetDto> query(Integer pageIndex, Integer pageSize, QuerySettlePreSheetVo vo) {

        Assert.greaterThanZero(pageIndex);
        Assert.greaterThanZero(pageSize);

        PageHelperUtil.startPage(pageIndex, pageSize);
        List<SettlePreSheetDto> datas = this.query(vo);

        return PageResultUtil.convert(new PageInfo<>(datas));
    }

    @Override
    public List<SettlePreSheetDto> query(QuerySettlePreSheetVo vo) {

        return settlePreSheetMapper.query(vo);
    }

    @Override
    public SettlePreSheetDto getById(String id) {

        return settlePreSheetMapper.getById(id);
    }

    @Override
    public SettlePreSheetFullDto getDetail(String id) {

        return settlePreSheetMapper.getDetail(id);
    }

    @OpLog(type = OpLogType.OTHER, name = "创建供应商预付款单，单号：{}", params = "#code")
    @Transactional
    @Override
    public String create(CreateSettlePreSheetVo vo) {

        SettlePreSheet sheet = new SettlePreSheet();

        sheet.setId(IdUtil.getId());
        sheet.setCode(generateCodeService.generate(GenerateCodeTypePool.SETTLE_PRE_SHEET));

        this.create(sheet, vo);

        sheet.setStatus(SettlePreSheetStatus.CREATED);

        OpLogUtil.setVariable("code", sheet.getCode());
        OpLogUtil.setExtra(vo);

        settlePreSheetMapper.insert(sheet);

        return sheet.getId();
    }

    @OpLog(type = OpLogType.OTHER, name = "修改供应商预付款单，单号：{}", params = "#code")
    @Transactional
    @Override
    public void update(UpdateSettlePreSheetVo vo) {

        SettlePreSheet sheet = settlePreSheetMapper.selectById(vo.getId());
        if (sheet == null) {
            throw new DefaultClientException("供应商预付款单不存在！");
        }

        if (sheet.getStatus() != SettlePreSheetStatus.CREATED
                && sheet.getStatus() != SettlePreSheetStatus.APPROVE_REFUSE) {
            if (sheet.getStatus() == SettlePreSheetStatus.APPROVE_PASS) {
                throw new DefaultClientException("供应商预付款单已审核通过，无法修改！");
            } else {
                throw new DefaultClientException("供应商预付款单无法修改！");
            }
        }

        // 删除明细
        Wrapper<SettlePreSheetDetail> deleteDetailWrapper = Wrappers.lambdaQuery(SettlePreSheetDetail.class)
                .eq(SettlePreSheetDetail::getSheetId, sheet.getId());
        settlePreSheetDetailMapper.delete(deleteDetailWrapper);

        this.create(sheet, vo);

        sheet.setStatus(SettlePreSheetStatus.CREATED);

        List<SettlePreSheetStatus> statusList = new ArrayList<>();
        statusList.add(SettlePreSheetStatus.CREATED);
        statusList.add(SettlePreSheetStatus.APPROVE_REFUSE);

        Wrapper<SettlePreSheet> updateWrapper = Wrappers.lambdaUpdate(SettlePreSheet.class)
                .set(SettlePreSheet::getApproveBy, null).set(SettlePreSheet::getApproveTime, null)
                .set(SettlePreSheet::getRefuseReason, StringPool.EMPTY_STR).eq(SettlePreSheet::getId, sheet.getId())
                .in(SettlePreSheet::getStatus, statusList);
        if (settlePreSheetMapper.update(sheet, updateWrapper) != 1) {
            throw new DefaultClientException("供应商预付款单信息已过期，请刷新重试！");
        }

        OpLogUtil.setVariable("code", sheet.getCode());
        OpLogUtil.setExtra(vo);
    }

    @OpLog(type = OpLogType.OTHER, name = "审核通过供应商预付款单，单号：{}", params = "#code")
    @Transactional
    @Override
    public void approvePass(ApprovePassSettlePreSheetVo vo) {

        SettlePreSheet sheet = settlePreSheetMapper.selectById(vo.getId());
        if (sheet == null) {
            throw new DefaultClientException("供应商预付款单不存在！");
        }

        if (sheet.getStatus() != SettlePreSheetStatus.CREATED
                && sheet.getStatus() != SettlePreSheetStatus.APPROVE_REFUSE) {
            if (sheet.getStatus() == SettlePreSheetStatus.APPROVE_PASS) {
                throw new DefaultClientException("供应商预付款单已审核通过，不允许继续执行审核！");
            }
            throw new DefaultClientException("供应商预付款单无法审核通过！");
        }

        sheet.setStatus(SettlePreSheetStatus.APPROVE_PASS);
        sheet.setApproveBy(SecurityUtil.getCurrentUser().getId());
        sheet.setApproveTime(LocalDateTime.now());
        if (!StringUtil.isBlank(vo.getDescription())) {
            sheet.setDescription(vo.getDescription());
        }

        List<SettlePreSheetStatus> statusList = new ArrayList<>();
        statusList.add(SettlePreSheetStatus.CREATED);
        statusList.add(SettlePreSheetStatus.APPROVE_REFUSE);

        Wrapper<SettlePreSheet> updateWrapper = Wrappers.lambdaUpdate(SettlePreSheet.class)
                .eq(SettlePreSheet::getId, sheet.getId()).in(SettlePreSheet::getStatus, statusList);
        if (settlePreSheetMapper.update(sheet, updateWrapper) != 1) {
            throw new DefaultClientException("供应商预付款单信息已过期，请刷新重试！");
        }

        OpLogUtil.setVariable("code", sheet.getCode());
        OpLogUtil.setExtra(vo);
    }

    @Transactional
    @Override
    public void redirectApprovePass(CreateSettlePreSheetVo vo) {

        ISettlePreSheetService thisService = getThis(this.getClass());

        String id = thisService.create(vo);

        ApprovePassSettlePreSheetVo approveVo = new ApprovePassSettlePreSheetVo();
        approveVo.setId(id);

        thisService.approvePass(approveVo);
    }

    @OpLog(type = OpLogType.OTHER, name = "审核拒绝供应商预付款单，单号：{}", params = "#code")
    @Transactional
    @Override
    public void approveRefuse(ApproveRefuseSettlePreSheetVo vo) {

        SettlePreSheet sheet = settlePreSheetMapper.selectById(vo.getId());
        if (sheet == null) {
            throw new DefaultClientException("供应商预付款单不存在！");
        }

        if (sheet.getStatus() != SettlePreSheetStatus.CREATED) {
            if (sheet.getStatus() == SettlePreSheetStatus.APPROVE_PASS) {
                throw new DefaultClientException("供应商预付款单已审核通过，不允许继续执行审核！");
            }
            if (sheet.getStatus() == SettlePreSheetStatus.APPROVE_REFUSE) {
                throw new DefaultClientException("供应商预付款单已审核拒绝，不允许继续执行审核！");
            }
            throw new DefaultClientException("供应商预付款单无法审核拒绝！");
        }

        sheet.setStatus(SettlePreSheetStatus.APPROVE_REFUSE);
        sheet.setApproveBy(SecurityUtil.getCurrentUser().getId());
        sheet.setApproveTime(LocalDateTime.now());
        sheet.setRefuseReason(vo.getRefuseReason());

        List<SettlePreSheetStatus> statusList = new ArrayList<>();
        statusList.add(SettlePreSheetStatus.CREATED);
        statusList.add(SettlePreSheetStatus.APPROVE_REFUSE);

        Wrapper<SettlePreSheet> updateWrapper = Wrappers.lambdaUpdate(SettlePreSheet.class)
                .in(SettlePreSheet::getStatus, statusList);
        if (settlePreSheetMapper.update(sheet, updateWrapper) != 1) {
            throw new DefaultClientException("供应商预付款单信息已过期，请刷新重试！");
        }

        OpLogUtil.setVariable("code", sheet.getCode());
        OpLogUtil.setExtra(vo);
    }

    @Transactional
    @Override
    public void batchApprovePass(BatchApprovePassSettlePreSheetVo vo) {

        ISettlePreSheetService thisService = getThis(this.getClass());
        int orderNo = 1;
        for (String id : vo.getIds()) {
            ApprovePassSettlePreSheetVo approveVo = new ApprovePassSettlePreSheetVo();
            approveVo.setId(id);
            try {
                thisService.approvePass(approveVo);
            } catch (ClientException e) {
                throw new DefaultClientException("第" + orderNo + "个供应商预付款单审核通过失败，失败原因：" + e.getMsg());
            }
            orderNo++;
        }
    }

    @Transactional
    @Override
    public void batchApproveRefuse(BatchApproveRefuseSettlePreSheetVo vo) {

        ISettlePreSheetService thisService = getThis(this.getClass());
        int orderNo = 1;
        for (String id : vo.getIds()) {
            ApproveRefuseSettlePreSheetVo approveVo = new ApproveRefuseSettlePreSheetVo();
            approveVo.setId(id);
            approveVo.setRefuseReason(vo.getRefuseReason());

            try {
                thisService.approveRefuse(approveVo);
            } catch (ClientException e) {
                throw new DefaultClientException("第" + orderNo + "个供应商预付款单审核拒绝失败，失败原因：" + e.getMsg());
            }
            orderNo++;
        }
    }

    @OpLog(type = OpLogType.OTHER, name = "删除供应商预付款单，单号：{}", params = "#code")
    @Transactional
    @Override
    public void deleteById(String id) {

        Assert.notBlank(id);
        SettlePreSheet sheet = settlePreSheetMapper.selectById(id);
        if (sheet == null) {
            throw new InputErrorException("供应商预付款单不存在！");
        }

        if (sheet.getStatus() != SettlePreSheetStatus.CREATED
                && sheet.getStatus() != SettlePreSheetStatus.APPROVE_REFUSE) {

            if (sheet.getStatus() == SettlePreSheetStatus.APPROVE_PASS) {
                throw new DefaultClientException("“审核通过”的供应商预付款单不允许执行删除操作！");
            }

            throw new DefaultClientException("供应商预付款单无法删除！");
        }

        // 删除明细
        Wrapper<SettlePreSheetDetail> deleteDetailWrapper = Wrappers.lambdaQuery(SettlePreSheetDetail.class)
                .eq(SettlePreSheetDetail::getSheetId, sheet.getId());
        settlePreSheetDetailMapper.delete(deleteDetailWrapper);

        // 删除单据
        settlePreSheetMapper.deleteById(id);

        OpLogUtil.setVariable("code", sheet.getCode());
    }

    @Transactional
    @Override
    public void deleteByIds(List<String> ids) {

        if (!CollectionUtil.isEmpty(ids)) {
            int orderNo = 1;
            for (String id : ids) {

                try {
                    ISettlePreSheetService thisService = getThis(this.getClass());
                    thisService.deleteById(id);
                } catch (ClientException e) {
                    throw new DefaultClientException("第" + orderNo + "个供应商预付款单删除失败，失败原因：" + e.getMsg());
                }

                orderNo++;
            }
        }
    }

    @Transactional
    @Override
    public int setUnSettle(String id) {

        Wrapper<SettlePreSheet> updateWrapper = Wrappers.lambdaUpdate(SettlePreSheet.class)
                .set(SettlePreSheet::getSettleStatus, SettleStatus.UN_SETTLE).eq(SettlePreSheet::getId, id)
                .eq(SettlePreSheet::getSettleStatus, SettleStatus.PART_SETTLE);
        int count = settlePreSheetMapper.update(updateWrapper);

        return count;
    }

    @Transactional
    @Override
    public int setPartSettle(String id) {

        Wrapper<SettlePreSheet> updateWrapper = Wrappers.lambdaUpdate(SettlePreSheet.class)
                .set(SettlePreSheet::getSettleStatus, SettleStatus.PART_SETTLE).eq(SettlePreSheet::getId, id)
                .in(SettlePreSheet::getSettleStatus, SettleStatus.UN_SETTLE, SettleStatus.PART_SETTLE);
        int count = settlePreSheetMapper.update(updateWrapper);

        return count;
    }

    @Transactional
    @Override
    public int setSettled(String id) {

        Wrapper<SettlePreSheet> updateWrapper = Wrappers.lambdaUpdate(SettlePreSheet.class)
                .set(SettlePreSheet::getSettleStatus, SettleStatus.SETTLED).eq(SettlePreSheet::getId, id)
                .in(SettlePreSheet::getSettleStatus, SettleStatus.UN_SETTLE, SettleStatus.PART_SETTLE);
        int count = settlePreSheetMapper.update(updateWrapper);

        return count;
    }

    @Override
    public List<SettlePreSheetDto> getApprovedList(String supplierId, LocalDateTime startTime, LocalDateTime endTime,
            SettleStatus settleStatus) {

        return settlePreSheetMapper.getApprovedList(supplierId, startTime, endTime, settleStatus);
    }

    private void create(SettlePreSheet sheet, CreateSettlePreSheetVo vo) {

        BigDecimal totalAmount = BigDecimal.ZERO;

        int orderNo = 1;
        for (SettlePreSheetItemVo itemVo : vo.getItems()) {
            SettleOutItemDto item = settleOutItemService.getById(itemVo.getId());
            if (item == null) {
                throw new DefaultClientException("第" + orderNo + "行项目不存在！");
            }
            SettlePreSheetDetail detail = new SettlePreSheetDetail();
            detail.setId(IdUtil.getId());
            detail.setSheetId(sheet.getId());
            detail.setItemId(itemVo.getId());
            detail.setAmount(itemVo.getAmount());
            detail.setOrderNo(orderNo);

            settlePreSheetDetailMapper.insert(detail);

            totalAmount = NumberUtil.add(totalAmount, detail.getAmount());

            orderNo++;
        }

        AbstractUserDetails currentUser = SecurityUtil.getCurrentUser();

        sheet.setSupplierId(vo.getSupplierId());
        sheet.setTotalAmount(totalAmount);
        sheet.setDescription(StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());
        sheet.setCreateBy(currentUser.getId());
        sheet.setRefuseReason(StringPool.EMPTY_STR);
        sheet.setSettleStatus(SettleStatus.UN_SETTLE);
    }
}

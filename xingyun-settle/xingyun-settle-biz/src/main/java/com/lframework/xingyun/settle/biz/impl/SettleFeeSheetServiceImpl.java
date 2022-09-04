package com.lframework.xingyun.settle.biz.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.common.constants.StringPool;
import com.lframework.common.exceptions.ClientException;
import com.lframework.common.exceptions.impl.DefaultClientException;
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
import com.lframework.xingyun.core.enums.SettleStatus;
import com.lframework.xingyun.settle.biz.components.code.GenerateCodeTypePool;
import com.lframework.xingyun.settle.biz.mappers.SettleFeeSheetMapper;
import com.lframework.xingyun.settle.biz.service.ISettleFeeSheetDetailService;
import com.lframework.xingyun.settle.biz.service.ISettleFeeSheetService;
import com.lframework.xingyun.settle.biz.service.ISettleInItemService;
import com.lframework.xingyun.settle.biz.service.ISettleOutItemService;
import com.lframework.xingyun.settle.facade.dto.fee.SettleFeeSheetFullDto;
import com.lframework.xingyun.settle.facade.entity.SettleFeeSheet;
import com.lframework.xingyun.settle.facade.entity.SettleFeeSheetDetail;
import com.lframework.xingyun.settle.facade.entity.SettleInItem;
import com.lframework.xingyun.settle.facade.entity.SettleOutItem;
import com.lframework.xingyun.settle.facade.enums.SettleFeeSheetStatus;
import com.lframework.xingyun.settle.facade.enums.SettleFeeSheetType;
import com.lframework.xingyun.settle.facade.vo.fee.ApprovePassSettleFeeSheetVo;
import com.lframework.xingyun.settle.facade.vo.fee.ApproveRefuseSettleFeeSheetVo;
import com.lframework.xingyun.settle.facade.vo.fee.BatchApprovePassSettleFeeSheetVo;
import com.lframework.xingyun.settle.facade.vo.fee.BatchApproveRefuseSettleFeeSheetVo;
import com.lframework.xingyun.settle.facade.vo.fee.CreateSettleFeeSheetVo;
import com.lframework.xingyun.settle.facade.vo.fee.QuerySettleFeeSheetVo;
import com.lframework.xingyun.settle.facade.vo.fee.SettleFeeSheetItemVo;
import com.lframework.xingyun.settle.facade.vo.fee.UpdateSettleFeeSheetVo;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SettleFeeSheetServiceImpl extends BaseMpServiceImpl<SettleFeeSheetMapper, SettleFeeSheet>
        implements ISettleFeeSheetService {

    @Autowired
    private ISettleFeeSheetDetailService settleFeeSheetDetailService;

    @Autowired
    private ISettleOutItemService settleOutItemService;

    @Autowired
    private ISettleInItemService settleInItemService;

    @Autowired
    private IGenerateCodeService generateCodeService;

    @Override
    public PageResult<SettleFeeSheet> query(Integer pageIndex, Integer pageSize, QuerySettleFeeSheetVo vo) {

        Assert.greaterThanZero(pageIndex);
        Assert.greaterThanZero(pageSize);

        PageHelperUtil.startPage(pageIndex, pageSize);
        List<SettleFeeSheet> datas = this.query(vo);

        return PageResultUtil.convert(new PageInfo<>(datas));
    }

    @Override
    public List<SettleFeeSheet> query(QuerySettleFeeSheetVo vo) {

        return getBaseMapper().query(vo);
    }

    @Override
    public SettleFeeSheetFullDto getDetail(String id) {

        return getBaseMapper().getDetail(id);
    }

    @OpLog(type = OpLogType.OTHER, name = "创建供应商费用单，单号：{}", params = "#code")
    @OrderTimeLineLog(type = OrderTimeLineBizType.CREATE, orderId = "#_result", name = "创建费用单")
    @Transactional
    @Override
    public String create(CreateSettleFeeSheetVo vo) {

        SettleFeeSheet sheet = new SettleFeeSheet();

        sheet.setId(IdUtil.getId());
        sheet.setCode(generateCodeService.generate(GenerateCodeTypePool.SETTLE_FEE_SHEET));

        this.create(sheet, vo);

        sheet.setStatus(SettleFeeSheetStatus.CREATED);

        OpLogUtil.setVariable("code", sheet.getCode());
        OpLogUtil.setExtra(vo);

        getBaseMapper().insert(sheet);

        return sheet.getId();
    }

    @OpLog(type = OpLogType.OTHER, name = "修改供应商费用单，单号：{}", params = "#code")
    @OrderTimeLineLog(type = OrderTimeLineBizType.UPDATE, orderId = "#vo.id", name = "修改费用单")
    @Transactional
    @Override
    public void update(UpdateSettleFeeSheetVo vo) {

        SettleFeeSheet sheet = getBaseMapper().selectById(vo.getId());
        if (sheet == null) {
            throw new DefaultClientException("供应商费用单不存在！");
        }

        if (sheet.getStatus() != SettleFeeSheetStatus.CREATED
                && sheet.getStatus() != SettleFeeSheetStatus.APPROVE_REFUSE) {
            if (sheet.getStatus() == SettleFeeSheetStatus.APPROVE_PASS) {
                throw new DefaultClientException("供应商费用单已审核通过，无法修改！");
            } else {
                throw new DefaultClientException("供应商费用单无法修改！");
            }
        }

        // 删除明细
        Wrapper<SettleFeeSheetDetail> deleteDetailWrapper = Wrappers.lambdaQuery(SettleFeeSheetDetail.class)
                .eq(SettleFeeSheetDetail::getSheetId, sheet.getId());
        settleFeeSheetDetailService.remove(deleteDetailWrapper);

        this.create(sheet, vo);

        sheet.setStatus(SettleFeeSheetStatus.CREATED);

        List<SettleFeeSheetStatus> statusList = new ArrayList<>();
        statusList.add(SettleFeeSheetStatus.CREATED);
        statusList.add(SettleFeeSheetStatus.APPROVE_REFUSE);

        Wrapper<SettleFeeSheet> updateWrapper = Wrappers.lambdaUpdate(SettleFeeSheet.class)
                .set(SettleFeeSheet::getApproveBy, null).set(SettleFeeSheet::getApproveTime, null)
                .set(SettleFeeSheet::getRefuseReason, StringPool.EMPTY_STR).eq(SettleFeeSheet::getId, sheet.getId())
                .in(SettleFeeSheet::getStatus, statusList);
        if (getBaseMapper().update(sheet, updateWrapper) != 1) {
            throw new DefaultClientException("供应商费用单信息已过期，请刷新重试！");
        }

        OpLogUtil.setVariable("code", sheet.getCode());
        OpLogUtil.setExtra(vo);
    }

    @OpLog(type = OpLogType.OTHER, name = "审核通过供应商费用单，单号：{}", params = "#code")
    @OrderTimeLineLog(type = OrderTimeLineBizType.APPROVE_PASS, orderId = "#vo.id", name = "审核通过")
    @Transactional
    @Override
    public void approvePass(ApprovePassSettleFeeSheetVo vo) {

        SettleFeeSheet sheet = getBaseMapper().selectById(vo.getId());
        if (sheet == null) {
            throw new DefaultClientException("供应商费用单不存在！");
        }

        if (sheet.getStatus() != SettleFeeSheetStatus.CREATED
                && sheet.getStatus() != SettleFeeSheetStatus.APPROVE_REFUSE) {
            if (sheet.getStatus() == SettleFeeSheetStatus.APPROVE_PASS) {
                throw new DefaultClientException("供应商费用单已审核通过，不允许继续执行审核！");
            }
            throw new DefaultClientException("供应商费用单无法审核通过！");
        }

        sheet.setStatus(SettleFeeSheetStatus.APPROVE_PASS);
        sheet.setApproveBy(SecurityUtil.getCurrentUser().getId());
        sheet.setApproveTime(LocalDateTime.now());
        if (!StringUtil.isBlank(vo.getDescription())) {
            sheet.setDescription(vo.getDescription());
        }

        List<SettleFeeSheetStatus> statusList = new ArrayList<>();
        statusList.add(SettleFeeSheetStatus.CREATED);
        statusList.add(SettleFeeSheetStatus.APPROVE_REFUSE);

        Wrapper<SettleFeeSheet> updateWrapper = Wrappers.lambdaUpdate(SettleFeeSheet.class)
                .eq(SettleFeeSheet::getId, sheet.getId()).in(SettleFeeSheet::getStatus, statusList);
        if (getBaseMapper().update(sheet, updateWrapper) != 1) {
            throw new DefaultClientException("供应商费用单信息已过期，请刷新重试！");
        }

        OpLogUtil.setVariable("code", sheet.getCode());
        OpLogUtil.setExtra(vo);
    }

    @OrderTimeLineLog(type = OrderTimeLineBizType.APPROVE_PASS, orderId = "#_result", name = "直接审核通过")
    @Transactional
    @Override
    public String directApprovePass(CreateSettleFeeSheetVo vo) {

        ISettleFeeSheetService thisService = getThis(this.getClass());

        String id = thisService.create(vo);

        ApprovePassSettleFeeSheetVo approveVo = new ApprovePassSettleFeeSheetVo();
        approveVo.setId(id);

        thisService.approvePass(approveVo);

        return id;
    }

    @OpLog(type = OpLogType.OTHER, name = "审核拒绝供应商费用单，单号：{}", params = "#code")
    @OrderTimeLineLog(type = OrderTimeLineBizType.APPROVE_RETURN, orderId = "#vo.id", name = "审核拒绝，拒绝理由：{}", params = "#vo.refuseReason")
    @Transactional
    @Override
    public void approveRefuse(ApproveRefuseSettleFeeSheetVo vo) {

        SettleFeeSheet sheet = getBaseMapper().selectById(vo.getId());
        if (sheet == null) {
            throw new DefaultClientException("供应商费用单不存在！");
        }

        if (sheet.getStatus() != SettleFeeSheetStatus.CREATED) {
            if (sheet.getStatus() == SettleFeeSheetStatus.APPROVE_PASS) {
                throw new DefaultClientException("供应商费用单已审核通过，不允许继续执行审核！");
            }
            if (sheet.getStatus() == SettleFeeSheetStatus.APPROVE_REFUSE) {
                throw new DefaultClientException("供应商费用单已审核拒绝，不允许继续执行审核！");
            }
            throw new DefaultClientException("供应商费用单无法审核拒绝！");
        }

        sheet.setStatus(SettleFeeSheetStatus.APPROVE_REFUSE);
        sheet.setApproveBy(SecurityUtil.getCurrentUser().getId());
        sheet.setApproveTime(LocalDateTime.now());
        sheet.setRefuseReason(vo.getRefuseReason());

        List<SettleFeeSheetStatus> statusList = new ArrayList<>();
        statusList.add(SettleFeeSheetStatus.CREATED);
        statusList.add(SettleFeeSheetStatus.APPROVE_REFUSE);

        Wrapper<SettleFeeSheet> updateWrapper = Wrappers.lambdaUpdate(SettleFeeSheet.class)
                .eq(SettleFeeSheet::getId, sheet.getId()).in(SettleFeeSheet::getStatus, statusList);
        if (getBaseMapper().update(sheet, updateWrapper) != 1) {
            throw new DefaultClientException("供应商费用单信息已过期，请刷新重试！");
        }

        OpLogUtil.setVariable("code", sheet.getCode());
        OpLogUtil.setExtra(vo);
    }

    @OrderTimeLineLog(type = OrderTimeLineBizType.APPROVE_PASS, orderId = "#vo.ids", name = "审核通过")
    @Transactional
    @Override
    public void batchApprovePass(BatchApprovePassSettleFeeSheetVo vo) {

        ISettleFeeSheetService thisService = getThis(this.getClass());
        int orderNo = 1;
        for (String id : vo.getIds()) {
            ApprovePassSettleFeeSheetVo approveVo = new ApprovePassSettleFeeSheetVo();
            approveVo.setId(id);
            try {
                thisService.approvePass(approveVo);
            } catch (ClientException e) {
                throw new DefaultClientException("第" + orderNo + "个供应商费用单审核通过失败，失败原因：" + e.getMsg());
            }
            orderNo++;
        }
    }

    @OrderTimeLineLog(type = OrderTimeLineBizType.APPROVE_RETURN, orderId = "#vo.ids", name = "审核拒绝，拒绝理由：{}", params = "#vo.refuseReason")
    @Transactional
    @Override
    public void batchApproveRefuse(BatchApproveRefuseSettleFeeSheetVo vo) {

        ISettleFeeSheetService thisService = getThis(this.getClass());
        int orderNo = 1;
        for (String id : vo.getIds()) {
            ApproveRefuseSettleFeeSheetVo approveVo = new ApproveRefuseSettleFeeSheetVo();
            approveVo.setId(id);
            approveVo.setRefuseReason(vo.getRefuseReason());

            try {
                thisService.approveRefuse(approveVo);
            } catch (ClientException e) {
                throw new DefaultClientException("第" + orderNo + "个供应商费用单审核拒绝失败，失败原因：" + e.getMsg());
            }
            orderNo++;
        }
    }

    @OpLog(type = OpLogType.OTHER, name = "删除供应商费用单，单号：{}", params = "#code")
    @OrderTimeLineLog(orderId = "#id", delete = true)
    @Transactional
    @Override
    public void deleteById(String id) {

        Assert.notBlank(id);
        SettleFeeSheet sheet = getBaseMapper().selectById(id);
        if (sheet == null) {
            throw new InputErrorException("供应商费用单不存在！");
        }

        if (sheet.getStatus() != SettleFeeSheetStatus.CREATED
                && sheet.getStatus() != SettleFeeSheetStatus.APPROVE_REFUSE) {

            if (sheet.getStatus() == SettleFeeSheetStatus.APPROVE_PASS) {
                throw new DefaultClientException("“审核通过”的供应商费用单不允许执行删除操作！");
            }

            throw new DefaultClientException("供应商费用单无法删除！");
        }

        // 删除明细
        Wrapper<SettleFeeSheetDetail> deleteDetailWrapper = Wrappers.lambdaQuery(SettleFeeSheetDetail.class)
                .eq(SettleFeeSheetDetail::getSheetId, sheet.getId());
        settleFeeSheetDetailService.remove(deleteDetailWrapper);

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
                    ISettleFeeSheetService thisService = getThis(this.getClass());
                    thisService.deleteById(id);
                } catch (ClientException e) {
                    throw new DefaultClientException("第" + orderNo + "个供应商费用单删除失败，失败原因：" + e.getMsg());
                }

                orderNo++;
            }
        }
    }

    @Transactional
    @Override
    public int setUnSettle(String id) {

        Wrapper<SettleFeeSheet> updateWrapper = Wrappers.lambdaUpdate(SettleFeeSheet.class)
                .set(SettleFeeSheet::getSettleStatus, SettleStatus.UN_SETTLE).eq(SettleFeeSheet::getId, id)
                .eq(SettleFeeSheet::getSettleStatus, SettleStatus.PART_SETTLE);
        int count = getBaseMapper().update(updateWrapper);

        return count;
    }

    @Transactional
    @Override
    public int setPartSettle(String id) {

        Wrapper<SettleFeeSheet> updateWrapper = Wrappers.lambdaUpdate(SettleFeeSheet.class)
                .set(SettleFeeSheet::getSettleStatus, SettleStatus.PART_SETTLE).eq(SettleFeeSheet::getId, id)
                .in(SettleFeeSheet::getSettleStatus, SettleStatus.UN_SETTLE, SettleStatus.PART_SETTLE);
        int count = getBaseMapper().update(updateWrapper);

        return count;
    }

    @Transactional
    @Override
    public int setSettled(String id) {

        Wrapper<SettleFeeSheet> updateWrapper = Wrappers.lambdaUpdate(SettleFeeSheet.class)
                .set(SettleFeeSheet::getSettleStatus, SettleStatus.SETTLED).eq(SettleFeeSheet::getId, id)
                .in(SettleFeeSheet::getSettleStatus, SettleStatus.UN_SETTLE, SettleStatus.PART_SETTLE);
        int count = getBaseMapper().update(updateWrapper);

        return count;
    }

    @Override
    public List<SettleFeeSheet> getApprovedList(String supplierId, LocalDateTime startTime, LocalDateTime endTime,
            SettleStatus settleStatus) {

        return getBaseMapper().getApprovedList(supplierId, startTime, endTime, settleStatus);
    }

    private void create(SettleFeeSheet sheet, CreateSettleFeeSheetVo vo) {

        BigDecimal totalAmount = BigDecimal.ZERO;

        int orderNo = 1;
        for (SettleFeeSheetItemVo itemVo : vo.getItems()) {
            if (vo.getSheetType() == SettleFeeSheetType.RECEIVE.getCode().intValue()) {
                SettleInItem item = settleInItemService.findById(itemVo.getId());
                if (item == null) {
                    throw new DefaultClientException("第" + orderNo + "行项目不存在！");
                }
            } else {
                SettleOutItem item = settleOutItemService.findById(itemVo.getId());
                if (item == null) {
                    throw new DefaultClientException("第" + orderNo + "行项目不存在！");
                }
            }
            SettleFeeSheetDetail detail = new SettleFeeSheetDetail();
            detail.setId(IdUtil.getId());
            detail.setSheetId(sheet.getId());
            detail.setItemId(itemVo.getId());
            detail.setAmount(itemVo.getAmount());
            detail.setOrderNo(orderNo);

            settleFeeSheetDetailService.save(detail);

            totalAmount = NumberUtil.add(totalAmount, detail.getAmount());

            orderNo++;
        }

        AbstractUserDetails currentUser = SecurityUtil.getCurrentUser();

        sheet.setSupplierId(vo.getSupplierId());
        sheet.setSheetType(EnumUtil.getByCode(SettleFeeSheetType.class, vo.getSheetType()));
        sheet.setTotalAmount(totalAmount);
        sheet.setDescription(StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());
        sheet.setCreateBy(currentUser.getId());
        sheet.setRefuseReason(StringPool.EMPTY_STR);
        sheet.setSettleStatus(SettleStatus.UN_SETTLE);
    }
}
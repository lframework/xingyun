package com.lframework.xingyun.settle.impl;

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
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.web.common.security.AbstractUserDetails;
import com.lframework.web.common.security.SecurityUtil;
import com.lframework.xingyun.settle.components.code.GenerateCodeTypePool;
import com.lframework.xingyun.settle.dto.sheet.SettleBizItemDto;
import com.lframework.xingyun.settle.dto.sheet.SettleSheetFullDto;
import com.lframework.xingyun.settle.entity.SettleCheckSheet;
import com.lframework.xingyun.settle.entity.SettleSheet;
import com.lframework.xingyun.settle.entity.SettleSheetDetail;
import com.lframework.xingyun.settle.enums.SettleSheetStatus;
import com.lframework.xingyun.settle.mappers.SettleSheetMapper;
import com.lframework.xingyun.settle.service.ISettleCheckSheetService;
import com.lframework.xingyun.settle.service.ISettleSheetDetailService;
import com.lframework.xingyun.settle.service.ISettleSheetService;
import com.lframework.xingyun.settle.vo.sheet.ApprovePassSettleSheetVo;
import com.lframework.xingyun.settle.vo.sheet.ApproveRefuseSettleSheetVo;
import com.lframework.xingyun.settle.vo.sheet.BatchApprovePassSettleSheetVo;
import com.lframework.xingyun.settle.vo.sheet.BatchApproveRefuseSettleSheetVo;
import com.lframework.xingyun.settle.vo.sheet.CreateSettleSheetVo;
import com.lframework.xingyun.settle.vo.sheet.QuerySettleSheetVo;
import com.lframework.xingyun.settle.vo.sheet.QueryUnSettleBizItemVo;
import com.lframework.xingyun.settle.vo.sheet.SettleSheetItemVo;
import com.lframework.xingyun.settle.vo.sheet.UpdateSettleSheetVo;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SettleSheetServiceImpl extends BaseMpServiceImpl<SettleSheetMapper, SettleSheet>
    implements ISettleSheetService {

  @Autowired
  private ISettleSheetDetailService settleSheetDetailService;

  @Autowired
  private IGenerateCodeService generateCodeService;

  @Autowired
  private ISettleCheckSheetService settleCheckSheetService;

  @Override
  public PageResult<SettleSheet> query(Integer pageIndex, Integer pageSize, QuerySettleSheetVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<SettleSheet> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<SettleSheet> query(QuerySettleSheetVo vo) {

    return getBaseMapper().query(vo);
  }

  @Override
  public SettleSheetFullDto getDetail(String id) {

    return getBaseMapper().getDetail(id);
  }

  @OpLog(type = OpLogType.OTHER, name = "????????????????????????????????????{}", params = "#code")
  @Transactional
  @Override
  public String create(CreateSettleSheetVo vo) {

    SettleSheet sheet = new SettleSheet();

    sheet.setId(IdUtil.getId());
    sheet.setCode(generateCodeService.generate(GenerateCodeTypePool.SETTLE_SHEET));

    this.create(sheet, vo);

    sheet.setStatus(SettleSheetStatus.CREATED);

    OpLogUtil.setVariable("code", sheet.getCode());
    OpLogUtil.setExtra(vo);

    getBaseMapper().insert(sheet);

    return sheet.getId();
  }

  @OpLog(type = OpLogType.OTHER, name = "????????????????????????????????????{}", params = "#code")
  @Transactional
  @Override
  public void update(UpdateSettleSheetVo vo) {

    SettleSheet sheet = getBaseMapper().selectById(vo.getId());
    if (sheet == null) {
      throw new DefaultClientException("??????????????????????????????");
    }

    if (sheet.getStatus() != SettleSheetStatus.CREATED
        && sheet.getStatus() != SettleSheetStatus.APPROVE_REFUSE) {
      if (sheet.getStatus() == SettleSheetStatus.APPROVE_PASS) {
        throw new DefaultClientException("???????????????????????????????????????????????????");
      } else {
        throw new DefaultClientException("?????????????????????????????????");
      }
    }

    //???????????????????????????????????????
    Wrapper<SettleSheetDetail> queryDetailWrapper = Wrappers.lambdaQuery(SettleSheetDetail.class)
        .eq(SettleSheetDetail::getSheetId, sheet.getId()).orderByAsc(SettleSheetDetail::getOrderNo);
    List<SettleSheetDetail> sheetDetails = settleSheetDetailService.list(queryDetailWrapper);
    for (SettleSheetDetail sheetDetail : sheetDetails) {
      this.setBizItemUnSettle(sheetDetail.getBizId());
    }

    // ????????????
    Wrapper<SettleSheetDetail> deleteDetailWrapper = Wrappers.lambdaQuery(SettleSheetDetail.class)
        .eq(SettleSheetDetail::getSheetId, sheet.getId());
    settleSheetDetailService.remove(deleteDetailWrapper);

    this.create(sheet, vo);

    sheet.setStatus(SettleSheetStatus.CREATED);

    List<SettleSheetStatus> statusList = new ArrayList<>();
    statusList.add(SettleSheetStatus.CREATED);
    statusList.add(SettleSheetStatus.APPROVE_REFUSE);

    Wrapper<SettleSheet> updateWrapper = Wrappers.lambdaUpdate(SettleSheet.class)
        .set(SettleSheet::getApproveBy, null).set(SettleSheet::getApproveTime, null)
        .set(SettleSheet::getRefuseReason, StringPool.EMPTY_STR)
        .eq(SettleSheet::getId, sheet.getId())
        .in(SettleSheet::getStatus, statusList);
    if (getBaseMapper().update(sheet, updateWrapper) != 1) {
      throw new DefaultClientException("??????????????????????????????????????????????????????");
    }

    OpLogUtil.setVariable("code", sheet.getCode());
    OpLogUtil.setExtra(vo);
  }

  @OpLog(type = OpLogType.OTHER, name = "??????????????????????????????????????????{}", params = "#code")
  @Transactional
  @Override
  public void approvePass(ApprovePassSettleSheetVo vo) {

    SettleSheet sheet = getBaseMapper().selectById(vo.getId());
    if (sheet == null) {
      throw new DefaultClientException("??????????????????????????????");
    }

    if (sheet.getStatus() != SettleSheetStatus.CREATED
        && sheet.getStatus() != SettleSheetStatus.APPROVE_REFUSE) {
      if (sheet.getStatus() == SettleSheetStatus.APPROVE_PASS) {
        throw new DefaultClientException("??????????????????????????????????????????????????????????????????");
      }
      throw new DefaultClientException("???????????????????????????????????????");
    }

    sheet.setStatus(SettleSheetStatus.APPROVE_PASS);
    sheet.setApproveBy(SecurityUtil.getCurrentUser().getId());
    sheet.setApproveTime(LocalDateTime.now());
    if (!StringUtil.isBlank(vo.getDescription())) {
      sheet.setDescription(vo.getDescription());
    }

    List<SettleSheetStatus> statusList = new ArrayList<>();
    statusList.add(SettleSheetStatus.CREATED);
    statusList.add(SettleSheetStatus.APPROVE_REFUSE);

    Wrapper<SettleSheet> updateWrapper = Wrappers.lambdaUpdate(SettleSheet.class)
        .eq(SettleSheet::getId, sheet.getId()).in(SettleSheet::getStatus, statusList);
    if (getBaseMapper().update(sheet, updateWrapper) != 1) {
      throw new DefaultClientException("??????????????????????????????????????????????????????");
    }

    Wrapper<SettleSheetDetail> queryDetailWrapper = Wrappers.lambdaQuery(SettleSheetDetail.class)
        .eq(SettleSheetDetail::getSheetId, sheet.getId()).orderByAsc(SettleSheetDetail::getOrderNo);
    List<SettleSheetDetail> details = settleSheetDetailService.list(queryDetailWrapper);
    for (SettleSheetDetail detail : details) {
      settleCheckSheetService.setSettleAmount(detail.getBizId(), detail.getPayAmount(),
          detail.getDiscountAmount());
    }

    OpLogUtil.setVariable("code", sheet.getCode());
    OpLogUtil.setExtra(vo);
  }

  @Transactional
  @Override
  public void directApprovePass(CreateSettleSheetVo vo) {

    ISettleSheetService thisService = getThis(this.getClass());

    String id = thisService.create(vo);

    ApprovePassSettleSheetVo approveVo = new ApprovePassSettleSheetVo();
    approveVo.setId(id);

    thisService.approvePass(approveVo);
  }

  @OpLog(type = OpLogType.OTHER, name = "??????????????????????????????????????????{}", params = "#code")
  @Transactional
  @Override
  public void approveRefuse(ApproveRefuseSettleSheetVo vo) {

    SettleSheet sheet = getBaseMapper().selectById(vo.getId());
    if (sheet == null) {
      throw new DefaultClientException("??????????????????????????????");
    }

    if (sheet.getStatus() != SettleSheetStatus.CREATED) {
      if (sheet.getStatus() == SettleSheetStatus.APPROVE_PASS) {
        throw new DefaultClientException("??????????????????????????????????????????????????????????????????");
      }
      if (sheet.getStatus() == SettleSheetStatus.APPROVE_REFUSE) {
        throw new DefaultClientException("??????????????????????????????????????????????????????????????????");
      }
      throw new DefaultClientException("???????????????????????????????????????");
    }

    sheet.setStatus(SettleSheetStatus.APPROVE_REFUSE);
    sheet.setApproveBy(SecurityUtil.getCurrentUser().getId());
    sheet.setApproveTime(LocalDateTime.now());
    sheet.setRefuseReason(vo.getRefuseReason());

    List<SettleSheetStatus> statusList = new ArrayList<>();
    statusList.add(SettleSheetStatus.CREATED);
    statusList.add(SettleSheetStatus.APPROVE_REFUSE);

    Wrapper<SettleSheet> updateWrapper = Wrappers.lambdaUpdate(SettleSheet.class)
        .eq(SettleSheet::getId, sheet.getId()).in(SettleSheet::getStatus, statusList);
    if (getBaseMapper().update(sheet, updateWrapper) != 1) {
      throw new DefaultClientException("??????????????????????????????????????????????????????");
    }

    OpLogUtil.setVariable("code", sheet.getCode());
    OpLogUtil.setExtra(vo);
  }

  @Transactional
  @Override
  public void batchApprovePass(BatchApprovePassSettleSheetVo vo) {

    ISettleSheetService thisService = getThis(this.getClass());
    int orderNo = 1;
    for (String id : vo.getIds()) {
      ApprovePassSettleSheetVo approveVo = new ApprovePassSettleSheetVo();
      approveVo.setId(id);
      try {
        thisService.approvePass(approveVo);
      } catch (ClientException e) {
        throw new DefaultClientException("???" + orderNo + "?????????????????????????????????????????????????????????" + e.getMsg());
      }
      orderNo++;
    }
  }

  @Transactional
  @Override
  public void batchApproveRefuse(BatchApproveRefuseSettleSheetVo vo) {

    ISettleSheetService thisService = getThis(this.getClass());
    int orderNo = 1;
    for (String id : vo.getIds()) {
      ApproveRefuseSettleSheetVo approveVo = new ApproveRefuseSettleSheetVo();
      approveVo.setId(id);
      approveVo.setRefuseReason(vo.getRefuseReason());

      try {
        thisService.approveRefuse(approveVo);
      } catch (ClientException e) {
        throw new DefaultClientException("???" + orderNo + "?????????????????????????????????????????????????????????" + e.getMsg());
      }
      orderNo++;
    }
  }

  @OpLog(type = OpLogType.OTHER, name = "????????????????????????????????????{}", params = "#code")
  @Transactional
  @Override
  public void deleteById(String id) {

    Assert.notBlank(id);
    SettleSheet sheet = getBaseMapper().selectById(id);
    if (sheet == null) {
      throw new InputErrorException("??????????????????????????????");
    }

    if (sheet.getStatus() != SettleSheetStatus.CREATED
        && sheet.getStatus() != SettleSheetStatus.APPROVE_REFUSE) {

      if (sheet.getStatus() == SettleSheetStatus.APPROVE_PASS) {
        throw new DefaultClientException("?????????????????????????????????????????????????????????????????????");
      }

      throw new DefaultClientException("?????????????????????????????????");
    }

    //???????????????????????????????????????
    Wrapper<SettleSheetDetail> queryDetailWrapper = Wrappers.lambdaQuery(SettleSheetDetail.class)
        .eq(SettleSheetDetail::getSheetId, sheet.getId()).orderByAsc(SettleSheetDetail::getOrderNo);
    List<SettleSheetDetail> sheetDetails = settleSheetDetailService.list(queryDetailWrapper);
    for (SettleSheetDetail sheetDetail : sheetDetails) {
      this.setBizItemUnSettle(sheetDetail.getBizId());
    }

    // ????????????
    Wrapper<SettleSheetDetail> deleteDetailWrapper = Wrappers.lambdaQuery(SettleSheetDetail.class)
        .eq(SettleSheetDetail::getSheetId, sheet.getId());
    settleSheetDetailService.remove(deleteDetailWrapper);

    // ????????????
    getBaseMapper().deleteById(id);

    OpLogUtil.setVariable("code", sheet.getCode());
  }

  @Transactional
  @Override
  public void deleteByIds(List<String> ids) {

    if (!CollectionUtil.isEmpty(ids)) {
      int orderNo = 1;
      for (String id : ids) {

        try {
          ISettleSheetService thisService = getThis(this.getClass());
          thisService.deleteById(id);
        } catch (ClientException e) {
          throw new DefaultClientException("???" + orderNo + "???????????????????????????????????????????????????" + e.getMsg());
        }

        orderNo++;
      }
    }
  }

  @Override
  public SettleBizItemDto getBizItem(String id) {

    SettleCheckSheet checkSheet = settleCheckSheetService.getById(id);

    SettleBizItemDto result = new SettleBizItemDto();
    result.setId(checkSheet.getId());
    result.setCode(checkSheet.getCode());
    result.setTotalPayAmount(checkSheet.getTotalPayAmount());
    result.setTotalPayedAmount(checkSheet.getTotalPayedAmount());
    result.setTotalDiscountAmount(checkSheet.getTotalDiscountAmount());
    result.setTotalUnPayAmount(
        NumberUtil.sub(checkSheet.getTotalPayAmount(), checkSheet.getTotalPayedAmount(),
            checkSheet.getTotalDiscountAmount()));
    result.setApproveTime(checkSheet.getApproveTime());

    return result;
  }

  @Transactional
  @Override
  public void setBizItemUnSettle(String id) {

    SettleCheckSheet item = settleCheckSheetService.getById(id);
    int count = settleCheckSheetService.setUnSettle(id);
    if (count != 1) {
      throw new DefaultClientException("?????????" + item.getCode() + "?????????????????????????????????");
    }
  }

  @Transactional
  @Override
  public void setBizItemPartSettle(String id) {

    SettleCheckSheet item = settleCheckSheetService.getById(id);
    int count = settleCheckSheetService.setPartSettle(id);
    if (count != 1) {
      throw new DefaultClientException("?????????" + item.getCode() + "?????????????????????????????????");
    }
  }

  @Transactional
  @Override
  public void setBizItemSettled(String id) {

    SettleCheckSheet item = settleCheckSheetService.getById(id);
    int count = settleCheckSheetService.setSettled(id);
    if (count != 1) {
      throw new DefaultClientException("?????????" + item.getCode() + "?????????????????????????????????");
    }
  }

  @Override
  public List<SettleBizItemDto> getUnSettleBizItems(QueryUnSettleBizItemVo vo) {

    List<SettleBizItemDto> results = new ArrayList<>();

    List<SettleCheckSheet> sheetList = settleCheckSheetService.getApprovedList(vo.getSupplierId(),
        vo.getStartTime(), vo.getEndTime());

    if (!CollectionUtil.isEmpty(sheetList)) {
      for (SettleCheckSheet item : sheetList) {
        SettleBizItemDto result = new SettleBizItemDto();
        result.setId(item.getId());
        result.setCode(item.getCode());
        result.setTotalPayAmount(item.getTotalPayAmount());
        result.setTotalPayedAmount(item.getTotalPayedAmount());
        result.setTotalDiscountAmount(item.getTotalDiscountAmount());
        result.setTotalUnPayAmount(
            NumberUtil.sub(item.getTotalPayAmount(), item.getTotalPayedAmount(),
                item.getTotalDiscountAmount()));
        result.setApproveTime(item.getApproveTime());

        results.add(result);
      }
    }

    return results;
  }

  private void create(SettleSheet sheet, CreateSettleSheetVo vo) {

    BigDecimal totalAmount = BigDecimal.ZERO;
    BigDecimal totalDiscountAmount = BigDecimal.ZERO;

    int orderNo = 1;
    for (SettleSheetItemVo itemVo : vo.getItems()) {
      SettleBizItemDto item = this.getBizItem(itemVo.getId());
      if (item == null) {
        throw new DefaultClientException("???" + orderNo + "???????????????????????????");
      }

      if (NumberUtil.lt(item.getTotalPayAmount(), 0)) {
        if (NumberUtil.gt(itemVo.getPayAmount(), 0)) {
          throw new DefaultClientException("???" + orderNo + "??????????????????????????????0???");
        }

        if (NumberUtil.gt(itemVo.getDiscountAmount(), 0)) {
          throw new DefaultClientException("???" + orderNo + "??????????????????????????????0???");
        }

        if (NumberUtil.equal(itemVo.getPayAmount(), 0) && NumberUtil.equal(
            itemVo.getDiscountAmount(), 0)) {
          throw new DefaultClientException("???" + orderNo + "????????????????????????????????????????????????0???");
        }

        if (NumberUtil.gt(item.getTotalUnPayAmount(),
            NumberUtil.add(itemVo.getPayAmount(), itemVo.getDiscountAmount()))) {
          throw new DefaultClientException("???" + orderNo + "?????????????????????????????????????????????????????????????????????");
        }
      } else if (NumberUtil.gt(item.getTotalPayAmount(), 0)) {
        if (NumberUtil.lt(itemVo.getPayAmount(), 0)) {
          throw new DefaultClientException("???" + orderNo + "??????????????????????????????0???");
        }

        if (NumberUtil.lt(itemVo.getDiscountAmount(), 0)) {
          throw new DefaultClientException("???" + orderNo + "??????????????????????????????0???");
        }

        if (NumberUtil.equal(itemVo.getPayAmount(), 0) && NumberUtil.equal(
            itemVo.getDiscountAmount(), 0)) {
          throw new DefaultClientException("???" + orderNo + "????????????????????????????????????????????????0???");
        }
        if (NumberUtil.lt(item.getTotalUnPayAmount(),
            NumberUtil.add(itemVo.getPayAmount(), itemVo.getDiscountAmount()))) {
          throw new DefaultClientException("???" + orderNo + "?????????????????????????????????????????????????????????????????????");
        }
      } else {
        // ?????????????????????0
        if (!NumberUtil.equal(itemVo.getPayAmount(), 0) || !NumberUtil.equal(
            itemVo.getDiscountAmount(), 0)) {
          throw new DefaultClientException("???" + orderNo + "????????????????????????????????????????????????0???");
        }
      }

      SettleSheetDetail detail = new SettleSheetDetail();
      detail.setId(IdUtil.getId());
      detail.setSheetId(sheet.getId());
      detail.setBizId(itemVo.getId());
      detail.setPayAmount(itemVo.getPayAmount());
      detail.setDiscountAmount(itemVo.getDiscountAmount());
      detail.setDescription(itemVo.getDescription());
      detail.setOrderNo(orderNo);

      settleSheetDetailService.save(detail);

      totalAmount = NumberUtil.add(totalAmount, itemVo.getPayAmount());
      totalDiscountAmount = NumberUtil.add(totalDiscountAmount, itemVo.getDiscountAmount());

      //???????????????????????????????????????
      this.setBizItemPartSettle(detail.getBizId());

      orderNo++;
    }

    AbstractUserDetails currentUser = SecurityUtil.getCurrentUser();

    sheet.setSupplierId(vo.getSupplierId());
    sheet.setTotalAmount(totalAmount);
    sheet.setTotalDiscountAmount(totalDiscountAmount);
    sheet.setDescription(
        StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());
    sheet.setCreateBy(currentUser.getId());
    sheet.setRefuseReason(StringPool.EMPTY_STR);
    sheet.setStartDate(vo.getStartDate());
    sheet.setEndDate(vo.getEndDate());
  }
}

package com.lframework.xingyun.sc.impl.retail;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
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
import com.lframework.starter.mybatis.service.IUserService;
import com.lframework.starter.mybatis.utils.OpLogUtil;
import com.lframework.starter.mybatis.utils.PageHelperUtil;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.web.dto.UserDto;
import com.lframework.starter.web.service.IGenerateCodeService;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.web.common.security.SecurityUtil;
import com.lframework.xingyun.basedata.dto.product.info.ProductDto;
import com.lframework.xingyun.basedata.entity.Member;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.service.member.IMemberService;
import com.lframework.xingyun.basedata.service.product.IProductService;
import com.lframework.xingyun.basedata.service.storecenter.IStoreCenterService;
import com.lframework.xingyun.core.dto.stock.ProductLotChangeDto;
import com.lframework.xingyun.core.dto.stock.ProductStockChangeDto;
import com.lframework.xingyun.core.events.order.impl.ApprovePassRetailOutSheetEvent;
import com.lframework.xingyun.sc.components.code.GenerateCodeTypePool;
import com.lframework.xingyun.sc.dto.purchase.receive.GetPaymentDateDto;
import com.lframework.xingyun.sc.dto.retail.out.RetailOutSheetFullDto;
import com.lframework.xingyun.sc.dto.retail.out.RetailOutSheetWithReturnDto;
import com.lframework.xingyun.sc.entity.RetailConfig;
import com.lframework.xingyun.sc.entity.RetailOutSheet;
import com.lframework.xingyun.sc.entity.RetailOutSheetDetail;
import com.lframework.xingyun.sc.entity.RetailOutSheetDetailLot;
import com.lframework.xingyun.sc.enums.ProductStockBizType;
import com.lframework.xingyun.sc.enums.RetailOutSheetStatus;
import com.lframework.xingyun.sc.enums.SettleStatus;
import com.lframework.xingyun.sc.mappers.RetailOutSheetMapper;
import com.lframework.xingyun.sc.service.retail.IRetailConfigService;
import com.lframework.xingyun.sc.service.retail.IRetailOutSheetDetailLotService;
import com.lframework.xingyun.sc.service.retail.IRetailOutSheetDetailService;
import com.lframework.xingyun.sc.service.retail.IRetailOutSheetService;
import com.lframework.xingyun.sc.service.stock.IProductStockService;
import com.lframework.xingyun.sc.vo.retail.out.ApprovePassRetailOutSheetVo;
import com.lframework.xingyun.sc.vo.retail.out.ApproveRefuseRetailOutSheetVo;
import com.lframework.xingyun.sc.vo.retail.out.BatchApprovePassRetailOutSheetVo;
import com.lframework.xingyun.sc.vo.retail.out.BatchApproveRefuseRetailOutSheetVo;
import com.lframework.xingyun.sc.vo.retail.out.CreateRetailOutSheetVo;
import com.lframework.xingyun.sc.vo.retail.out.QueryRetailOutSheetVo;
import com.lframework.xingyun.sc.vo.retail.out.QueryRetailOutSheetWithReturnVo;
import com.lframework.xingyun.sc.vo.retail.out.RetailOutProductVo;
import com.lframework.xingyun.sc.vo.retail.out.RetailOutSheetSelectorVo;
import com.lframework.xingyun.sc.vo.retail.out.UpdateRetailOutSheetVo;
import com.lframework.xingyun.sc.vo.stock.SubProductStockVo;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RetailOutSheetServiceImpl extends
    BaseMpServiceImpl<RetailOutSheetMapper, RetailOutSheet>
    implements IRetailOutSheetService {

  @Autowired
  private IRetailOutSheetDetailService retailOutSheetDetailService;

  @Autowired
  private IRetailOutSheetDetailLotService retailOutSheetDetailLotService;

  @Autowired
  private IStoreCenterService storeCenterService;

  @Autowired
  private IMemberService memberService;

  @Autowired
  private IUserService userService;

  @Autowired
  private IProductService productService;

  @Autowired
  private IGenerateCodeService generateCodeService;

  @Autowired
  private IRetailConfigService retailConfigService;

  @Autowired
  private IProductStockService productStockService;

  @Override
  public PageResult<RetailOutSheet> query(Integer pageIndex, Integer pageSize,
      QueryRetailOutSheetVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<RetailOutSheet> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<RetailOutSheet> query(QueryRetailOutSheetVo vo) {

    return getBaseMapper().query(vo);
  }

  @Override
  public PageResult<RetailOutSheet> selector(Integer pageIndex, Integer pageSize,
      RetailOutSheetSelectorVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<RetailOutSheet> datas = getBaseMapper().selector(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public GetPaymentDateDto getPaymentDate(String memberId) {

    GetPaymentDateDto result = new GetPaymentDateDto();
    result.setAllowModify(Boolean.TRUE);
    result.setPaymentDate(LocalDate.now());

    return result;
  }

  @Override
  public RetailOutSheetFullDto getDetail(String id) {

    return getBaseMapper().getDetail(id);
  }

  @Override
  public RetailOutSheetWithReturnDto getWithReturn(String id) {

    RetailConfig retailConfig = retailConfigService.get();

    RetailOutSheetWithReturnDto sheet = getBaseMapper().getWithReturn(id,
        retailConfig.getRetailReturnRequireOutStock());
    if (sheet == null) {
      throw new InputErrorException("销售出库单不存在！");
    }

    return sheet;
  }

  @Override
  public PageResult<RetailOutSheet> queryWithReturn(Integer pageIndex, Integer pageSize,
      QueryRetailOutSheetWithReturnVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    RetailConfig retailConfig = retailConfigService.get();

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<RetailOutSheet> datas = getBaseMapper().queryWithReturn(vo,
        retailConfig.getRetailReturnMultipleRelateOutStock());

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @OpLog(type = OpLogType.OTHER, name = "创建销售出库单，单号：{}", params = "#code")
  @Transactional
  @Override
  public String create(CreateRetailOutSheetVo vo) {

    RetailOutSheet sheet = new RetailOutSheet();
    sheet.setId(IdUtil.getId());
    sheet.setCode(generateCodeService.generate(GenerateCodeTypePool.RETAIL_OUT_SHEET));

    this.create(sheet, vo);

    sheet.setStatus(RetailOutSheetStatus.CREATED);

    getBaseMapper().insert(sheet);

    OpLogUtil.setVariable("code", sheet.getCode());
    OpLogUtil.setExtra(vo);

    return sheet.getId();
  }

  @OpLog(type = OpLogType.OTHER, name = "修改销售出库单，单号：{}", params = "#code")
  @Transactional
  @Override
  public void update(UpdateRetailOutSheetVo vo) {

    RetailOutSheet sheet = getBaseMapper().selectById(vo.getId());
    if (sheet == null) {
      throw new InputErrorException("销售出库单不存在！");
    }

    if (sheet.getStatus() != RetailOutSheetStatus.CREATED
        && sheet.getStatus() != RetailOutSheetStatus.APPROVE_REFUSE) {

      if (sheet.getStatus() == RetailOutSheetStatus.APPROVE_PASS) {
        throw new DefaultClientException("销售出库单已审核通过，无法修改！");
      }

      throw new DefaultClientException("销售出库单无法修改！");
    }

    // 删除出库单明细
    Wrapper<RetailOutSheetDetail> deleteDetailWrapper = Wrappers.lambdaQuery(
            RetailOutSheetDetail.class)
        .eq(RetailOutSheetDetail::getSheetId, sheet.getId());
    retailOutSheetDetailService.remove(deleteDetailWrapper);

    this.create(sheet, vo);

    sheet.setStatus(RetailOutSheetStatus.CREATED);

    List<RetailOutSheetStatus> statusList = new ArrayList<>();
    statusList.add(RetailOutSheetStatus.CREATED);
    statusList.add(RetailOutSheetStatus.APPROVE_REFUSE);

    Wrapper<RetailOutSheet> updateOrderWrapper = Wrappers.lambdaUpdate(RetailOutSheet.class)
        .set(RetailOutSheet::getApproveBy, null).set(RetailOutSheet::getApproveTime, null)
        .set(RetailOutSheet::getRefuseReason, StringPool.EMPTY_STR)
        .set(RetailOutSheet::getMemberId, sheet.getMemberId())
        .eq(RetailOutSheet::getId, sheet.getId())
        .in(RetailOutSheet::getStatus, statusList);
    if (getBaseMapper().update(sheet, updateOrderWrapper) != 1) {
      throw new DefaultClientException("销售出库单信息已过期，请刷新重试！");
    }

    OpLogUtil.setVariable("code", sheet.getCode());
    OpLogUtil.setExtra(vo);
  }

  @OpLog(type = OpLogType.OTHER, name = "审核通过销售出库单，单号：{}", params = "#code")
  @Transactional
  @Override
  public void approvePass(ApprovePassRetailOutSheetVo vo) {

    RetailOutSheet sheet = getBaseMapper().selectById(vo.getId());
    if (sheet == null) {
      throw new InputErrorException("销售出库单不存在！");
    }

    if (sheet.getStatus() != RetailOutSheetStatus.CREATED
        && sheet.getStatus() != RetailOutSheetStatus.APPROVE_REFUSE) {

      if (sheet.getStatus() == RetailOutSheetStatus.APPROVE_PASS) {
        throw new DefaultClientException("销售出库单已审核通过，不允许继续执行审核！");
      }

      throw new DefaultClientException("销售出库单无法审核通过！");
    }

    sheet.setStatus(RetailOutSheetStatus.APPROVE_PASS);

    List<RetailOutSheetStatus> statusList = new ArrayList<>();
    statusList.add(RetailOutSheetStatus.CREATED);
    statusList.add(RetailOutSheetStatus.APPROVE_REFUSE);

    LambdaUpdateWrapper<RetailOutSheet> updateOrderWrapper = Wrappers.lambdaUpdate(
            RetailOutSheet.class)
        .set(RetailOutSheet::getApproveBy, SecurityUtil.getCurrentUser().getId())
        .set(RetailOutSheet::getApproveTime, LocalDateTime.now())
        .eq(RetailOutSheet::getId, sheet.getId())
        .in(RetailOutSheet::getStatus, statusList);
    if (!StringUtil.isBlank(vo.getDescription())) {
      updateOrderWrapper.set(RetailOutSheet::getDescription, vo.getDescription());
    }
    if (getBaseMapper().update(sheet, updateOrderWrapper) != 1) {
      throw new DefaultClientException("销售出库单信息已过期，请刷新重试！");
    }

    Wrapper<RetailOutSheetDetail> queryDetailWrapper = Wrappers.lambdaQuery(
            RetailOutSheetDetail.class)
        .eq(RetailOutSheetDetail::getSheetId, sheet.getId())
        .orderByAsc(RetailOutSheetDetail::getOrderNo);
    List<RetailOutSheetDetail> details = retailOutSheetDetailService.list(queryDetailWrapper);
    for (RetailOutSheetDetail detail : details) {
      SubProductStockVo subProductStockVo = new SubProductStockVo();
      subProductStockVo.setProductId(detail.getProductId());
      subProductStockVo.setScId(sheet.getScId());
      subProductStockVo.setStockNum(detail.getOrderNum());
      subProductStockVo.setBizId(sheet.getId());
      subProductStockVo.setBizDetailId(detail.getId());
      subProductStockVo.setBizCode(sheet.getCode());
      subProductStockVo.setBizType(ProductStockBizType.RETAIL.getCode());

      ProductStockChangeDto stockChange = productStockService.subStock(subProductStockVo);
      int orderNo = 1;

      for (ProductLotChangeDto lotChange : stockChange.getLotChangeList()) {
        RetailOutSheetDetailLot detailLot = new RetailOutSheetDetailLot();

        detailLot.setId(IdUtil.getId());
        detailLot.setDetailId(detail.getId());
        detailLot.setLotId(lotChange.getLotId());
        detailLot.setOrderNum(lotChange.getNum());
        detailLot.setCostTaxAmount(lotChange.getTaxAmount());
        detailLot.setCostUnTaxAmount(lotChange.getUnTaxAmount());
        detailLot.setSettleStatus(detail.getSettleStatus());
        detailLot.setOrderNo(orderNo);
        retailOutSheetDetailLotService.save(detailLot);

        orderNo++;
      }

      retailOutSheetDetailService.updateById(detail);
    }

    OpLogUtil.setVariable("code", sheet.getCode());
    OpLogUtil.setExtra(vo);
  }

  @Transactional
  @Override
  public void batchApprovePass(BatchApprovePassRetailOutSheetVo vo) {

    int orderNo = 1;
    for (String id : vo.getIds()) {
      ApprovePassRetailOutSheetVo approvePassVo = new ApprovePassRetailOutSheetVo();
      approvePassVo.setId(id);

      try {
        IRetailOutSheetService thisService = getThis(this.getClass());
        thisService.approvePass(approvePassVo);
      } catch (ClientException e) {
        throw new DefaultClientException("第" + orderNo + "个销售出库单审核通过失败，失败原因：" + e.getMsg());
      }

      orderNo++;
    }
  }

  @Transactional
  @Override
  public void directApprovePass(CreateRetailOutSheetVo vo) {

    IRetailOutSheetService thisService = getThis(this.getClass());

    String sheetId = thisService.create(vo);

    ApprovePassRetailOutSheetVo approvePassVo = new ApprovePassRetailOutSheetVo();
    approvePassVo.setId(sheetId);
    approvePassVo.setDescription(vo.getDescription());

    thisService.approvePass(approvePassVo);
  }

  @OpLog(type = OpLogType.OTHER, name = "审核拒绝销售出库单，单号：{}", params = "#code")
  @Transactional
  @Override
  public void approveRefuse(ApproveRefuseRetailOutSheetVo vo) {

    RetailOutSheet sheet = getBaseMapper().selectById(vo.getId());
    if (sheet == null) {
      throw new InputErrorException("销售出库单不存在！");
    }

    if (sheet.getStatus() != RetailOutSheetStatus.CREATED) {

      if (sheet.getStatus() == RetailOutSheetStatus.APPROVE_PASS) {
        throw new DefaultClientException("销售出库单已审核通过，不允许继续执行审核！");
      }

      if (sheet.getStatus() == RetailOutSheetStatus.APPROVE_REFUSE) {
        throw new DefaultClientException("销售出库单已审核拒绝，不允许继续执行审核！");
      }

      throw new DefaultClientException("销售出库单无法审核拒绝！");
    }

    sheet.setStatus(RetailOutSheetStatus.APPROVE_REFUSE);

    LambdaUpdateWrapper<RetailOutSheet> updateOrderWrapper = Wrappers.lambdaUpdate(
            RetailOutSheet.class)
        .set(RetailOutSheet::getApproveBy, SecurityUtil.getCurrentUser().getId())
        .set(RetailOutSheet::getApproveTime, LocalDateTime.now())
        .set(RetailOutSheet::getRefuseReason, vo.getRefuseReason())
        .eq(RetailOutSheet::getId, sheet.getId())
        .eq(RetailOutSheet::getStatus, RetailOutSheetStatus.CREATED);
    if (getBaseMapper().update(sheet, updateOrderWrapper) != 1) {
      throw new DefaultClientException("销售出库单信息已过期，请刷新重试！");
    }

    OpLogUtil.setVariable("code", sheet.getCode());
    OpLogUtil.setExtra(vo);
  }

  @Transactional
  @Override
  public void batchApproveRefuse(BatchApproveRefuseRetailOutSheetVo vo) {

    int orderNo = 1;
    for (String id : vo.getIds()) {
      ApproveRefuseRetailOutSheetVo approveRefuseVo = new ApproveRefuseRetailOutSheetVo();
      approveRefuseVo.setId(id);
      approveRefuseVo.setRefuseReason(vo.getRefuseReason());

      try {
        IRetailOutSheetService thisService = getThis(this.getClass());
        thisService.approveRefuse(approveRefuseVo);
      } catch (ClientException e) {
        throw new DefaultClientException("第" + orderNo + "个销售出库单审核拒绝失败，失败原因：" + e.getMsg());
      }

      orderNo++;
    }
  }

  @OpLog(type = OpLogType.OTHER, name = "删除销售出库单，单号：{}", params = "#code")
  @Transactional
  @Override
  public void deleteById(String id) {

    Assert.notBlank(id);
    RetailOutSheet sheet = getBaseMapper().selectById(id);
    if (sheet == null) {
      throw new InputErrorException("销售出库单不存在！");
    }

    if (sheet.getStatus() != RetailOutSheetStatus.CREATED
        && sheet.getStatus() != RetailOutSheetStatus.APPROVE_REFUSE) {

      if (sheet.getStatus() == RetailOutSheetStatus.APPROVE_PASS) {
        throw new DefaultClientException("“审核通过”的销售出库单不允许执行删除操作！");
      }

      throw new DefaultClientException("销售出库单无法删除！");
    }

    // 删除订单明细
    Wrapper<RetailOutSheetDetail> deleteDetailWrapper = Wrappers.lambdaQuery(
            RetailOutSheetDetail.class)
        .eq(RetailOutSheetDetail::getSheetId, sheet.getId());
    retailOutSheetDetailService.remove(deleteDetailWrapper);

    // 删除订单
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
          IRetailOutSheetService thisService = getThis(this.getClass());
          thisService.deleteById(id);
        } catch (ClientException e) {
          throw new DefaultClientException("第" + orderNo + "个销售出库单删除失败，失败原因：" + e.getMsg());
        }

        orderNo++;
      }
    }
  }

  private void create(RetailOutSheet sheet, CreateRetailOutSheetVo vo) {

    StoreCenter sc = storeCenterService.findById(vo.getScId());
    if (sc == null) {
      throw new InputErrorException("仓库不存在！");
    }

    sheet.setScId(vo.getScId());

    Member member = null;
    if (!StringUtil.isBlank(vo.getMemberId())) {
      member = memberService.findById(vo.getMemberId());
      if (member == null) {
        throw new InputErrorException("会员不存在！");
      }
      sheet.setMemberId(vo.getMemberId());
    } else {
      sheet.setMemberId(null);
    }

    if (!StringUtil.isBlank(vo.getSalerId())) {
      UserDto saler = userService.findById(vo.getSalerId());
      if (saler == null) {
        throw new InputErrorException("销售员不存在！");
      }

      sheet.setSalerId(vo.getSalerId());
    }

    GetPaymentDateDto paymentDate = this.getPaymentDate(member == null ? null : member.getId());

    sheet.setPaymentDate(
        vo.getAllowModifyPaymentDate() || paymentDate.getAllowModify() ? vo.getPaymentDate()
            : paymentDate.getPaymentDate());

    int purchaseNum = 0;
    int giftNum = 0;
    BigDecimal totalAmount = BigDecimal.ZERO;
    int orderNo = 1;
    for (RetailOutProductVo productVo : vo.getProducts()) {
      boolean isGift = productVo.getTaxPrice().doubleValue() == 0D;

      if (isGift) {
        giftNum += productVo.getOrderNum();
      } else {
        purchaseNum += productVo.getOrderNum();
      }

      totalAmount = NumberUtil.add(totalAmount,
          NumberUtil.mul(productVo.getTaxPrice(), productVo.getOrderNum()));

      RetailOutSheetDetail detail = new RetailOutSheetDetail();
      detail.setId(IdUtil.getId());
      detail.setSheetId(sheet.getId());

      ProductDto product = productService.findById(productVo.getProductId());
      if (product == null) {
        throw new InputErrorException("第" + orderNo + "行商品不存在！");
      }

      if (!NumberUtil.isNumberPrecision(productVo.getTaxPrice(), 2)) {
        throw new InputErrorException("第" + orderNo + "行商品价格最多允许2位小数！");
      }

      detail.setProductId(productVo.getProductId());
      detail.setOrderNum(productVo.getOrderNum());
      detail.setOriPrice(productVo.getOriPrice());
      detail.setTaxPrice(productVo.getTaxPrice());
      detail.setDiscountRate(productVo.getDiscountRate());
      detail.setIsGift(isGift);
      detail.setTaxRate(product.getPoly().getTaxRate());
      detail.setDescription(
          StringUtil.isBlank(productVo.getDescription()) ? StringPool.EMPTY_STR
              : productVo.getDescription());
      detail.setOrderNo(orderNo);
      detail.setSettleStatus(this.getInitSettleStatus(member));

      retailOutSheetDetailService.save(detail);
      orderNo++;
    }
    sheet.setTotalNum(purchaseNum);
    sheet.setTotalGiftNum(giftNum);
    sheet.setTotalAmount(totalAmount);
    sheet.setDescription(
        StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());
    sheet.setSettleStatus(this.getInitSettleStatus(member));
  }

  /**
   * 根据会员获取初始结算状态
   *
   * @param member
   * @return
   */
  private SettleStatus getInitSettleStatus(Member member) {

    return SettleStatus.UN_SETTLE;
  }

  private void sendApprovePassEvent(RetailOutSheet sheet) {

    ApprovePassRetailOutSheetEvent event = new ApprovePassRetailOutSheetEvent(this);
    event.setId(sheet.getId());
    event.setTotalAmount(sheet.getTotalAmount());
    event.setApproveTime(sheet.getApproveTime());

    ApplicationUtil.publishEvent(event);
  }
}

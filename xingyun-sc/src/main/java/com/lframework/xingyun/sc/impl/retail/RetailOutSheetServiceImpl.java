package com.lframework.xingyun.sc.impl.retail;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.exceptions.impl.InputErrorException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.NumberUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.annotations.oplog.OpLog;
import com.lframework.starter.web.core.annotations.timeline.OrderTimeLineLog;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.components.security.SecurityUtil;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.starter.web.core.utils.IdUtil;
import com.lframework.starter.web.core.utils.OpLogUtil;
import com.lframework.starter.web.core.utils.PageHelperUtil;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.starter.web.inner.components.timeline.ApprovePassOrderTimeLineBizType;
import com.lframework.starter.web.inner.components.timeline.ApproveReturnOrderTimeLineBizType;
import com.lframework.starter.web.inner.components.timeline.CreateOrderTimeLineBizType;
import com.lframework.starter.web.inner.components.timeline.UpdateOrderTimeLineBizType;
import com.lframework.starter.web.inner.dto.order.ApprovePassOrderDto;
import com.lframework.starter.web.inner.entity.SysUser;
import com.lframework.starter.web.inner.service.GenerateCodeService;
import com.lframework.starter.web.inner.service.system.SysUserService;
import com.lframework.xingyun.basedata.entity.Member;
import com.lframework.xingyun.basedata.entity.Product;
import com.lframework.xingyun.basedata.entity.ProductBundle;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.enums.ProductType;
import com.lframework.xingyun.basedata.service.member.MemberService;
import com.lframework.xingyun.basedata.service.product.ProductBundleService;
import com.lframework.xingyun.basedata.service.product.ProductService;
import com.lframework.xingyun.basedata.service.storecenter.StoreCenterService;
import com.lframework.xingyun.core.utils.SplitNumberUtil;
import com.lframework.xingyun.sc.components.code.GenerateCodeTypePool;
import com.lframework.xingyun.sc.dto.purchase.receive.GetPaymentDateDto;
import com.lframework.xingyun.sc.dto.retail.RetailProductDto;
import com.lframework.xingyun.sc.dto.retail.out.RetailOutSheetFullDto;
import com.lframework.xingyun.sc.dto.retail.out.RetailOutSheetWithReturnDto;
import com.lframework.xingyun.sc.dto.stock.ProductStockChangeDto;
import com.lframework.xingyun.sc.entity.LogisticsSheetDetail;
import com.lframework.xingyun.sc.entity.OrderPayType;
import com.lframework.xingyun.sc.entity.RetailConfig;
import com.lframework.xingyun.sc.entity.RetailOutSheet;
import com.lframework.xingyun.sc.entity.RetailOutSheetDetail;
import com.lframework.xingyun.sc.entity.RetailOutSheetDetailBundle;
import com.lframework.xingyun.sc.entity.RetailOutSheetDetailLot;
import com.lframework.xingyun.sc.enums.LogisticsSheetDetailBizType;
import com.lframework.xingyun.sc.enums.ProductStockBizType;
import com.lframework.xingyun.sc.enums.RetailOpLogType;
import com.lframework.xingyun.sc.enums.RetailOutSheetStatus;
import com.lframework.xingyun.sc.enums.SettleStatus;
import com.lframework.xingyun.sc.events.order.impl.ApprovePassRetailOutSheetEvent;
import com.lframework.xingyun.sc.mappers.RetailOutSheetMapper;
import com.lframework.xingyun.sc.service.logistics.LogisticsSheetDetailService;
import com.lframework.xingyun.sc.service.paytype.OrderPayTypeService;
import com.lframework.xingyun.sc.service.retail.RetailConfigService;
import com.lframework.xingyun.sc.service.retail.RetailOutSheetDetailBundleService;
import com.lframework.xingyun.sc.service.retail.RetailOutSheetDetailLotService;
import com.lframework.xingyun.sc.service.retail.RetailOutSheetDetailService;
import com.lframework.xingyun.sc.service.retail.RetailOutSheetService;
import com.lframework.xingyun.sc.service.stock.ProductStockService;
import com.lframework.xingyun.sc.vo.retail.out.ApprovePassRetailOutSheetVo;
import com.lframework.xingyun.sc.vo.retail.out.ApproveRefuseRetailOutSheetVo;
import com.lframework.xingyun.sc.vo.retail.out.CreateRetailOutSheetVo;
import com.lframework.xingyun.sc.vo.retail.out.QueryRetailOutSheetVo;
import com.lframework.xingyun.sc.vo.retail.out.QueryRetailOutSheetWithReturnVo;
import com.lframework.xingyun.sc.vo.retail.out.QueryRetailProductVo;
import com.lframework.xingyun.sc.vo.retail.out.RetailOutProductVo;
import com.lframework.xingyun.sc.vo.retail.out.RetailOutSheetSelectorVo;
import com.lframework.xingyun.sc.vo.retail.out.UpdateRetailOutSheetVo;
import com.lframework.xingyun.sc.vo.stock.SubProductStockVo;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RetailOutSheetServiceImpl extends
    BaseMpServiceImpl<RetailOutSheetMapper, RetailOutSheet>
    implements RetailOutSheetService {

  @Autowired
  private RetailOutSheetDetailService retailOutSheetDetailService;

  @Autowired
  private RetailOutSheetDetailLotService retailOutSheetDetailLotService;

  @Autowired
  private RetailOutSheetDetailBundleService retailOutSheetDetailBundleService;

  @Autowired
  private ProductBundleService productBundleService;

  @Autowired
  private StoreCenterService storeCenterService;

  @Autowired
  private MemberService memberService;

  @Autowired
  private SysUserService userService;

  @Autowired
  private ProductService productService;

  @Autowired
  private GenerateCodeService generateCodeService;

  @Autowired
  private RetailConfigService retailConfigService;

  @Autowired
  private ProductStockService productStockService;

  @Autowired
  private OrderPayTypeService orderPayTypeService;

  @Autowired
  private LogisticsSheetDetailService logisticsSheetDetailService;

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
      throw new InputErrorException("零售出库单不存在！");
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

  @OpLog(type = RetailOpLogType.class, name = "创建零售出库单，单号：{}", params = "#code")
  @OrderTimeLineLog(type = CreateOrderTimeLineBizType.class, orderId = "#_result", name = "创建出库单")
  @Transactional(rollbackFor = Exception.class)
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

  @OpLog(type = RetailOpLogType.class, name = "修改零售出库单，单号：{}", params = "#code")
  @OrderTimeLineLog(type = UpdateOrderTimeLineBizType.class, orderId = "#vo.id", name = "修改出库单")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateRetailOutSheetVo vo) {

    RetailOutSheet sheet = getBaseMapper().selectById(vo.getId());
    if (sheet == null) {
      throw new InputErrorException("零售出库单不存在！");
    }

    if (sheet.getStatus() != RetailOutSheetStatus.CREATED
        && sheet.getStatus() != RetailOutSheetStatus.APPROVE_REFUSE) {

      if (sheet.getStatus() == RetailOutSheetStatus.APPROVE_PASS) {
        throw new DefaultClientException("零售出库单已审核通过，无法修改！");
      }

      throw new DefaultClientException("零售出库单无法修改！");
    }

    // 删除出库单明细
    Wrapper<RetailOutSheetDetail> deleteDetailWrapper = Wrappers.lambdaQuery(
            RetailOutSheetDetail.class)
        .eq(RetailOutSheetDetail::getSheetId, sheet.getId());
    retailOutSheetDetailService.remove(deleteDetailWrapper);

    // 删除组合商品信息
    Wrapper<RetailOutSheetDetailBundle> deleteDetailBundleWrapper = Wrappers.lambdaQuery(
        RetailOutSheetDetailBundle.class).eq(RetailOutSheetDetailBundle::getSheetId, sheet.getId());
    retailOutSheetDetailBundleService.remove(deleteDetailBundleWrapper);

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
    if (getBaseMapper().updateAllColumn(sheet, updateOrderWrapper) != 1) {
      throw new DefaultClientException("零售出库单信息已过期，请刷新重试！");
    }

    OpLogUtil.setVariable("code", sheet.getCode());
    OpLogUtil.setExtra(vo);
  }

  @OpLog(type = RetailOpLogType.class, name = "审核通过零售出库单，单号：{}", params = "#code")
  @OrderTimeLineLog(type = ApprovePassOrderTimeLineBizType.class, orderId = "#vo.id", name = "审核通过")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void approvePass(ApprovePassRetailOutSheetVo vo) {

    RetailOutSheet sheet = getBaseMapper().selectById(vo.getId());
    if (sheet == null) {
      throw new InputErrorException("零售出库单不存在！");
    }

    if (sheet.getStatus() != RetailOutSheetStatus.CREATED
        && sheet.getStatus() != RetailOutSheetStatus.APPROVE_REFUSE) {

      if (sheet.getStatus() == RetailOutSheetStatus.APPROVE_PASS) {
        throw new DefaultClientException("零售出库单已审核通过，不允许继续执行审核！");
      }

      throw new DefaultClientException("零售出库单无法审核通过！");
    }

    RetailConfig retailConfig = retailConfigService.get();
    if (retailConfig.getRetailOutSheetRequireLogistics()) {
      // 关联物流单
      LogisticsSheetDetail logisticsSheetDetail = logisticsSheetDetailService.getByBizId(
          sheet.getId(), LogisticsSheetDetailBizType.RETAIL_OUT_SHEET);
      if (logisticsSheetDetail == null) {
        throw new DefaultClientException("零售出库单尚未发货，无法审核通过！");
      }
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
    if (getBaseMapper().updateAllColumn(sheet, updateOrderWrapper) != 1) {
      throw new DefaultClientException("零售出库单信息已过期，请刷新重试！");
    }

    if (NumberUtil.gt(sheet.getTotalAmount(), BigDecimal.ZERO)) {
      List<OrderPayType> orderPayTypes = orderPayTypeService.findByOrderId(sheet.getId());
      if (CollectionUtil.isEmpty(orderPayTypes)) {
        throw new DefaultClientException("单据没有支付方式，请检查！");
      }
    }

    Wrapper<RetailOutSheetDetail> queryDetailWrapper = Wrappers.lambdaQuery(
            RetailOutSheetDetail.class)
        .eq(RetailOutSheetDetail::getSheetId, sheet.getId())
        .orderByAsc(RetailOutSheetDetail::getOrderNo);
    List<RetailOutSheetDetail> details = retailOutSheetDetailService.list(queryDetailWrapper);

    BigDecimal totalNum = BigDecimal.ZERO;
    BigDecimal giftNum = BigDecimal.ZERO;
    BigDecimal totalAmount = BigDecimal.ZERO;

    int orderNo = 1;
    for (RetailOutSheetDetail detail : details) {
      boolean isGift = detail.getIsGift();
      totalAmount = NumberUtil.add(totalAmount,
          NumberUtil.getNumber(NumberUtil.mul(detail.getTaxPrice(), detail.getOrderNum()), 2));

      Product product = productService.findById(detail.getProductId());
      if (product.getProductType() == ProductType.NORMAL) {
        SubProductStockVo subProductStockVo = new SubProductStockVo();
        subProductStockVo.setProductId(detail.getProductId());
        subProductStockVo.setScId(sheet.getScId());
        subProductStockVo.setStockNum(detail.getOrderNum());
        subProductStockVo.setBizId(sheet.getId());
        subProductStockVo.setBizDetailId(detail.getId());
        subProductStockVo.setBizCode(sheet.getCode());
        subProductStockVo.setBizType(ProductStockBizType.RETAIL.getCode());

        ProductStockChangeDto stockChange = productStockService.subStock(subProductStockVo);

        RetailOutSheetDetailLot detailLot = new RetailOutSheetDetailLot();

        detailLot.setId(IdUtil.getId());
        detailLot.setDetailId(detail.getId());
        detailLot.setOrderNum(detail.getOrderNum());
        detailLot.setCostTaxAmount(stockChange.getTaxAmount());
        detailLot.setSettleStatus(detail.getSettleStatus());
        detailLot.setOrderNo(orderNo);
        retailOutSheetDetailLotService.save(detailLot);

        if (isGift) {
          giftNum = NumberUtil.add(giftNum, detail.getOrderNum());
        } else {
          totalNum = NumberUtil.add(totalNum, detail.getOrderNum());
        }
      } else {
        Wrapper<RetailOutSheetDetailBundle> queryBundleWrapper = Wrappers.lambdaQuery(
                RetailOutSheetDetailBundle.class)
            .eq(RetailOutSheetDetailBundle::getSheetId, sheet.getId())
            .eq(RetailOutSheetDetailBundle::getDetailId, detail.getId());
        List<RetailOutSheetDetailBundle> retailOutSheetDetailBundles = retailOutSheetDetailBundleService.list(
            queryBundleWrapper);
        Assert.notEmpty(retailOutSheetDetailBundles);

        for (RetailOutSheetDetailBundle retailOutSheetDetailBundle : retailOutSheetDetailBundles) {
          RetailOutSheetDetail newDetail = new RetailOutSheetDetail();
          newDetail.setId(IdUtil.getId());
          newDetail.setSheetId(sheet.getId());
          newDetail.setProductId(retailOutSheetDetailBundle.getProductId());
          newDetail.setOrderNum(retailOutSheetDetailBundle.getProductOrderNum());
          newDetail.setOriPrice(retailOutSheetDetailBundle.getProductOriPrice());
          newDetail.setTaxPrice(retailOutSheetDetailBundle.getProductTaxPrice());
          newDetail.setDiscountRate(detail.getDiscountRate());
          newDetail.setIsGift(detail.getIsGift());
          newDetail.setTaxRate(retailOutSheetDetailBundle.getProductTaxRate());
          newDetail.setDescription(detail.getDescription());
          newDetail.setOrderNo(orderNo++);
          newDetail.setSettleStatus(detail.getSettleStatus());
          newDetail.setOriBundleDetailId(detail.getId());

          SubProductStockVo subProductStockVo = new SubProductStockVo();
          subProductStockVo.setProductId(newDetail.getProductId());
          subProductStockVo.setScId(sheet.getScId());
          subProductStockVo.setStockNum(newDetail.getOrderNum());
          subProductStockVo.setBizId(sheet.getId());
          subProductStockVo.setBizDetailId(newDetail.getId());
          subProductStockVo.setBizCode(sheet.getCode());
          subProductStockVo.setBizType(ProductStockBizType.RETAIL.getCode());

          ProductStockChangeDto stockChange = productStockService.subStock(subProductStockVo);

          RetailOutSheetDetailLot detailLot = new RetailOutSheetDetailLot();

          detailLot.setId(IdUtil.getId());
          detailLot.setDetailId(newDetail.getId());
          detailLot.setOrderNum(newDetail.getOrderNum());
          detailLot.setCostTaxAmount(stockChange.getTaxAmount());
          detailLot.setSettleStatus(newDetail.getSettleStatus());
          detailLot.setOrderNo(orderNo);
          retailOutSheetDetailLotService.save(detailLot);

          retailOutSheetDetailService.save(newDetail);
          retailOutSheetDetailService.removeById(detail.getId());

          retailOutSheetDetailBundle.setProductDetailId(newDetail.getId());
          retailOutSheetDetailBundleService.updateById(retailOutSheetDetailBundle);

          if (isGift) {
            giftNum = NumberUtil.add(giftNum, newDetail.getOrderNum());
          } else {
            totalNum = NumberUtil.add(totalNum, newDetail.getOrderNum());
          }
        }
      }
      orderNo++;
    }

    // 这里需要重新统计明细信息，因为明细发生变动了
    Wrapper<RetailOutSheet> updateWrapper = Wrappers.lambdaUpdate(RetailOutSheet.class)
        .set(RetailOutSheet::getTotalNum, totalNum).set(RetailOutSheet::getTotalGiftNum, giftNum)
        .set(RetailOutSheet::getTotalAmount, totalAmount).eq(RetailOutSheet::getId, sheet.getId());
    this.update(updateWrapper);

    OpLogUtil.setVariable("code", sheet.getCode());
    OpLogUtil.setExtra(vo);

    this.sendApprovePassEvent(sheet);
  }

  @OrderTimeLineLog(type = ApprovePassOrderTimeLineBizType.class, orderId = "#_result", name = "直接审核通过")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public String directApprovePass(CreateRetailOutSheetVo vo) {

    RetailOutSheetService thisService = getThis(this.getClass());

    String sheetId = thisService.create(vo);

    ApprovePassRetailOutSheetVo approvePassVo = new ApprovePassRetailOutSheetVo();
    approvePassVo.setId(sheetId);
    approvePassVo.setDescription(vo.getDescription());

    thisService.approvePass(approvePassVo);

    return sheetId;
  }

  @OpLog(type = RetailOpLogType.class, name = "审核拒绝零售出库单，单号：{}", params = "#code")
  @OrderTimeLineLog(type = ApproveReturnOrderTimeLineBizType.class, orderId = "#vo.id", name = "审核拒绝，拒绝理由：{}", params = "#vo.refuseReason")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void approveRefuse(ApproveRefuseRetailOutSheetVo vo) {

    RetailOutSheet sheet = getBaseMapper().selectById(vo.getId());
    if (sheet == null) {
      throw new InputErrorException("零售出库单不存在！");
    }

    if (sheet.getStatus() != RetailOutSheetStatus.CREATED) {

      if (sheet.getStatus() == RetailOutSheetStatus.APPROVE_PASS) {
        throw new DefaultClientException("零售出库单已审核通过，不允许继续执行审核！");
      }

      if (sheet.getStatus() == RetailOutSheetStatus.APPROVE_REFUSE) {
        throw new DefaultClientException("零售出库单已审核拒绝，不允许继续执行审核！");
      }

      throw new DefaultClientException("零售出库单无法审核拒绝！");
    }

    sheet.setStatus(RetailOutSheetStatus.APPROVE_REFUSE);

    LambdaUpdateWrapper<RetailOutSheet> updateOrderWrapper = Wrappers.lambdaUpdate(
            RetailOutSheet.class)
        .set(RetailOutSheet::getApproveBy, SecurityUtil.getCurrentUser().getId())
        .set(RetailOutSheet::getApproveTime, LocalDateTime.now())
        .set(RetailOutSheet::getRefuseReason, vo.getRefuseReason())
        .eq(RetailOutSheet::getId, sheet.getId())
        .eq(RetailOutSheet::getStatus, RetailOutSheetStatus.CREATED);
    if (getBaseMapper().updateAllColumn(sheet, updateOrderWrapper) != 1) {
      throw new DefaultClientException("零售出库单信息已过期，请刷新重试！");
    }

    OpLogUtil.setVariable("code", sheet.getCode());
    OpLogUtil.setExtra(vo);
  }

  @OpLog(type = RetailOpLogType.class, name = "删除零售出库单，单号：{}", params = "#code")
  @OrderTimeLineLog(orderId = "#id", delete = true)
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteById(String id) {

    Assert.notBlank(id);
    RetailOutSheet sheet = getBaseMapper().selectById(id);
    if (sheet == null) {
      throw new InputErrorException("零售出库单不存在！");
    }

    if (sheet.getStatus() != RetailOutSheetStatus.CREATED
        && sheet.getStatus() != RetailOutSheetStatus.APPROVE_REFUSE) {

      if (sheet.getStatus() == RetailOutSheetStatus.APPROVE_PASS) {
        throw new DefaultClientException("“审核通过”的零售出库单不允许执行删除操作！");
      }

      throw new DefaultClientException("零售出库单无法删除！");
    }

    if (logisticsSheetDetailService.getByBizId(sheet.getId(),
        LogisticsSheetDetailBizType.RETAIL_OUT_SHEET) != null) {
      throw new DefaultClientException("零售出库单已关联物流单，请先删除物流单！");
    }

    // 删除订单明细
    Wrapper<RetailOutSheetDetail> queryDetailWrapper = Wrappers.lambdaQuery(
            RetailOutSheetDetail.class)
        .eq(RetailOutSheetDetail::getSheetId, sheet.getId());
    List<RetailOutSheetDetail> details = retailOutSheetDetailService.list(queryDetailWrapper);
    retailOutSheetDetailService.remove(queryDetailWrapper);

    // 删除组合商品信息
    Wrapper<RetailOutSheetDetailBundle> deleteDetailBundleWrapper = Wrappers.lambdaQuery(
        RetailOutSheetDetailBundle.class).eq(RetailOutSheetDetailBundle::getSheetId, sheet.getId());
    retailOutSheetDetailBundleService.remove(deleteDetailBundleWrapper);

    Wrapper<RetailOutSheetDetailLot> deleteDetailLotWrapper = Wrappers.lambdaQuery(
        RetailOutSheetDetailLot.class).in(RetailOutSheetDetailLot::getDetailId,
        details.stream().map(RetailOutSheetDetail::getId).collect(
            Collectors.toList()));
    retailOutSheetDetailLotService.remove(deleteDetailLotWrapper);

    // 删除订单
    Wrapper<RetailOutSheet> deleteWrapper = Wrappers.lambdaQuery(RetailOutSheet.class)
        .eq(RetailOutSheet::getId, id).in(RetailOutSheet::getStatus, RetailOutSheetStatus.CREATED,
            RetailOutSheetStatus.APPROVE_REFUSE);
    if (!remove(deleteWrapper)) {
      throw new DefaultClientException("零售出库单信息已过期，请刷新重试！");
    }

    orderPayTypeService.deleteByOrderId(id);

    OpLogUtil.setVariable("code", sheet.getCode());
  }

  @Override
  public PageResult<RetailProductDto> queryRetailByCondition(Integer pageIndex, Integer pageSize,
      String condition, Boolean isReturn) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);

    List<RetailProductDto> datas = getBaseMapper().queryRetailByCondition(condition, isReturn);
    PageResult<RetailProductDto> pageResult = PageResultUtil.convert(new PageInfo<>(datas));

    return pageResult;
  }

  @Override
  public PageResult<RetailProductDto> queryRetailList(Integer pageIndex, Integer pageSize,
      QueryRetailProductVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);

    List<RetailProductDto> datas = getBaseMapper().queryRetailList(vo);
    PageResult<RetailProductDto> pageResult = PageResultUtil.convert(new PageInfo<>(datas));

    return pageResult;
  }

  @Override
  public RetailProductDto getRetailById(String id) {

    RetailProductDto data = getBaseMapper().getRetailById(id);
    return data;
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
      SysUser saler = userService.findById(vo.getSalerId());
      if (saler == null) {
        throw new InputErrorException("销售员不存在！");
      }

      sheet.setSalerId(vo.getSalerId());
    }

    GetPaymentDateDto paymentDate = this.getPaymentDate(member == null ? null : member.getId());

    sheet.setPaymentDate(
        vo.getAllowModifyPaymentDate() || paymentDate.getAllowModify() ? vo.getPaymentDate()
            : paymentDate.getPaymentDate());

    BigDecimal purchaseNum = BigDecimal.ZERO;
    BigDecimal giftNum = BigDecimal.ZERO;
    BigDecimal totalAmount = BigDecimal.ZERO;
    int orderNo = 1;
    for (RetailOutProductVo productVo : vo.getProducts()) {
      boolean isGift = productVo.getTaxPrice().doubleValue() == 0D;

      if (isGift) {
        giftNum = NumberUtil.add(giftNum, productVo.getOrderNum());
      } else {
        purchaseNum = NumberUtil.add(purchaseNum, productVo.getOrderNum());
      }

      totalAmount = NumberUtil.add(totalAmount,
          NumberUtil.getNumber(NumberUtil.mul(productVo.getTaxPrice(), productVo.getOrderNum()),
              2));

      RetailOutSheetDetail detail = new RetailOutSheetDetail();
      detail.setId(IdUtil.getId());
      detail.setSheetId(sheet.getId());

      Product product = productService.findById(productVo.getProductId());
      if (product == null) {
        throw new InputErrorException("第" + orderNo + "行商品不存在！");
      }

      detail.setProductId(productVo.getProductId());
      detail.setOrderNum(productVo.getOrderNum());
      detail.setOriPrice(productVo.getOriPrice());
      detail.setTaxPrice(productVo.getTaxPrice());
      detail.setDiscountRate(productVo.getDiscountRate());
      detail.setIsGift(isGift);
      detail.setTaxRate(product.getSaleTaxRate());
      detail.setDescription(
          StringUtil.isBlank(productVo.getDescription()) ? StringPool.EMPTY_STR
              : productVo.getDescription());
      detail.setOrderNo(orderNo);
      detail.setSettleStatus(this.getInitSettleStatus(member));

      retailOutSheetDetailService.save(detail);

      // 这里处理组合商品
      if (product.getProductType() == ProductType.BUNDLE) {
        if (!NumberUtil.isInteger(productVo.getOrderNum())) {
          throw new InputErrorException("第" + orderNo + "行商品出库数量必须是整数！");
        }
        List<ProductBundle> productBundles = productBundleService.getByMainProductId(
            product.getId());
        // 构建指标项
        Map<Object, Number> bundleWeight = new HashMap<>(productBundles.size());
        for (ProductBundle productBundle : productBundles) {
          bundleWeight.put(productBundle.getProductId(),
              NumberUtil.mul(productBundle.getRetailPrice(), productBundle.getBundleNum()));
        }
        Map<Object, Number> splitPriceMap = SplitNumberUtil.split(detail.getTaxPrice(),
            bundleWeight, 2);
        List<RetailOutSheetDetailBundle> retailOutSheetDetailBundles = productBundles.stream()
            .map(productBundle -> {
              Product bundle = productService.findById(productBundle.getProductId());
              RetailOutSheetDetailBundle retailOutSheetDetailBundle = new RetailOutSheetDetailBundle();
              retailOutSheetDetailBundle.setId(IdUtil.getId());
              retailOutSheetDetailBundle.setSheetId(sheet.getId());
              retailOutSheetDetailBundle.setDetailId(detail.getId());
              retailOutSheetDetailBundle.setMainProductId(product.getId());
              retailOutSheetDetailBundle.setOrderNum(detail.getOrderNum());
              retailOutSheetDetailBundle.setProductId(productBundle.getProductId());
              retailOutSheetDetailBundle.setProductOrderNum(
                  NumberUtil.mul(detail.getOrderNum(), productBundle.getBundleNum()));
              retailOutSheetDetailBundle.setProductOriPrice(productBundle.getRetailPrice());
              // 这里会有尾差
              retailOutSheetDetailBundle.setProductTaxPrice(
                  NumberUtil.getNumber(NumberUtil.div(BigDecimal.valueOf(
                          splitPriceMap.get(productBundle.getProductId()).doubleValue()),
                      productBundle.getBundleNum()), 6));
              retailOutSheetDetailBundle.setProductTaxRate(bundle.getSaleTaxRate());

              return retailOutSheetDetailBundle;
            }).collect(Collectors.toList());

        retailOutSheetDetailBundleService.saveBatch(retailOutSheetDetailBundles);
      }
      orderNo++;
    }
    sheet.setTotalNum(purchaseNum);
    sheet.setTotalGiftNum(giftNum);
    sheet.setTotalAmount(totalAmount);
    sheet.setDescription(
        StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());
    sheet.setSettleStatus(this.getInitSettleStatus(member));

    orderPayTypeService.create(sheet.getId(), vo.getPayTypes());
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

    ApprovePassOrderDto dto = new ApprovePassOrderDto();
    dto.setId(sheet.getId());
    dto.setTotalAmount(sheet.getTotalAmount());
    dto.setApproveTime(sheet.getApproveTime());

    ApprovePassRetailOutSheetEvent event = new ApprovePassRetailOutSheetEvent(this, dto);

    ApplicationUtil.publishEvent(event);
  }
}

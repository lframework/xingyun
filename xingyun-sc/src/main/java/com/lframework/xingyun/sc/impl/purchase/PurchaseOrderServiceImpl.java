package com.lframework.xingyun.sc.impl.purchase;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.bpm.dto.FlowInstanceExtDto;
import com.lframework.starter.bpm.enums.FlowDefinitionExtBizType;
import com.lframework.starter.bpm.listeners.BpmBizListener;
import com.lframework.starter.bpm.service.FlowInstanceWrapperService;
import com.lframework.starter.bpm.transfers.FlowCuInstanceTransfer;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.exceptions.impl.InputErrorException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.BeanUtil;
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
import com.lframework.starter.web.core.utils.JsonUtil;
import com.lframework.starter.web.core.utils.OpLogUtil;
import com.lframework.starter.web.core.utils.PageHelperUtil;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.starter.web.inner.components.timeline.ApprovePassOrderTimeLineBizType;
import com.lframework.starter.web.inner.components.timeline.ApproveReturnOrderTimeLineBizType;
import com.lframework.starter.web.inner.components.timeline.CancelApproveOrderTimeLineBizType;
import com.lframework.starter.web.inner.components.timeline.CreateOrderTimeLineBizType;
import com.lframework.starter.web.inner.components.timeline.UpdateOrderTimeLineBizType;
import com.lframework.starter.web.inner.dto.order.ApprovePassOrderDto;
import com.lframework.starter.web.inner.entity.SysUser;
import com.lframework.starter.web.inner.service.GenerateCodeService;
import com.lframework.starter.web.inner.service.system.SysUserService;
import com.lframework.xingyun.basedata.entity.Product;
import com.lframework.xingyun.basedata.entity.ProductBundle;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.entity.Supplier;
import com.lframework.xingyun.basedata.enums.ProductType;
import com.lframework.xingyun.basedata.service.product.ProductBundleService;
import com.lframework.xingyun.basedata.service.product.ProductService;
import com.lframework.xingyun.basedata.service.storecenter.StoreCenterService;
import com.lframework.xingyun.basedata.service.supplier.SupplierService;
import com.lframework.xingyun.core.utils.SplitNumberUtil;
import com.lframework.xingyun.sc.bo.purchase.GetPurchaseOrderBo;
import com.lframework.xingyun.sc.components.code.GenerateCodeTypePool;
import com.lframework.xingyun.sc.dto.purchase.PurchaseOrderFullDto;
import com.lframework.xingyun.sc.dto.purchase.PurchaseOrderWithReceiveDto;
import com.lframework.xingyun.sc.dto.purchase.PurchaseProductDto;
import com.lframework.xingyun.sc.entity.OrderPayType;
import com.lframework.xingyun.sc.entity.PurchaseConfig;
import com.lframework.xingyun.sc.entity.PurchaseOrder;
import com.lframework.xingyun.sc.entity.PurchaseOrderDetail;
import com.lframework.xingyun.sc.entity.PurchaseOrderDetailBundle;
import com.lframework.xingyun.sc.entity.PurchaseOrderDetailBundleForm;
import com.lframework.xingyun.sc.entity.PurchaseOrderDetailForm;
import com.lframework.xingyun.sc.entity.PurchaseOrderForm;
import com.lframework.xingyun.sc.enums.PurchaseOpLogType;
import com.lframework.xingyun.sc.enums.PurchaseOrderStatus;
import com.lframework.xingyun.sc.events.order.impl.ApprovePassPurchaseOrderEvent;
import com.lframework.xingyun.sc.mappers.PurchaseOrderMapper;
import com.lframework.xingyun.sc.service.paytype.OrderPayTypeService;
import com.lframework.xingyun.sc.service.purchase.PurchaseConfigService;
import com.lframework.xingyun.sc.service.purchase.PurchaseOrderDetailBundleFormService;
import com.lframework.xingyun.sc.service.purchase.PurchaseOrderDetailBundleService;
import com.lframework.xingyun.sc.service.purchase.PurchaseOrderDetailFormService;
import com.lframework.xingyun.sc.service.purchase.PurchaseOrderDetailService;
import com.lframework.xingyun.sc.service.purchase.PurchaseOrderFormService;
import com.lframework.xingyun.sc.service.purchase.PurchaseOrderService;
import com.lframework.xingyun.sc.vo.purchase.ApprovePassPurchaseOrderVo;
import com.lframework.xingyun.sc.vo.purchase.ApproveRefusePurchaseOrderVo;
import com.lframework.xingyun.sc.vo.purchase.CreatePurchaseOrderVo;
import com.lframework.xingyun.sc.vo.purchase.PurchaseOrderSelectorVo;
import com.lframework.xingyun.sc.vo.purchase.PurchaseProductVo;
import com.lframework.xingyun.sc.vo.purchase.QueryPurchaseOrderVo;
import com.lframework.xingyun.sc.vo.purchase.QueryPurchaseOrderWithReceiveVo;
import com.lframework.xingyun.sc.vo.purchase.QueryPurchaseProductVo;
import com.lframework.xingyun.sc.vo.purchase.UpdatePurchaseOrderVo;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.dromara.warm.flow.core.dto.FlowParams;
import org.dromara.warm.flow.core.entity.Instance;
import org.dromara.warm.flow.core.listener.ListenerVariable;
import org.dromara.warm.flow.core.service.InsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PurchaseOrderServiceImpl extends
    BaseMpServiceImpl<PurchaseOrderMapper, PurchaseOrder> implements PurchaseOrderService {

  private static final String BPM_FLAG = "PurchaseOrder";

  @Autowired
  private PurchaseOrderDetailService purchaseOrderDetailService;

  @Autowired
  private GenerateCodeService generateCodeService;

  @Autowired
  private StoreCenterService storeCenterService;

  @Autowired
  private SupplierService supplierService;

  @Autowired
  private SysUserService userService;

  @Autowired
  private ProductService productService;

  @Autowired
  private PurchaseConfigService purchaseConfigService;

  @Autowired
  private OrderPayTypeService orderPayTypeService;

  @Autowired
  private InsService insService;

  @Autowired
  private PurchaseOrderFormService purchaseOrderFormService;

  @Autowired
  private PurchaseOrderDetailFormService purchaseOrderDetailFormService;

  @Autowired
  private FlowInstanceWrapperService flowInstanceWrapperService;

  @Autowired
  private ProductBundleService productBundleService;

  @Autowired
  private PurchaseOrderDetailBundleService purchaseOrderDetailBundleService;

  @Autowired
  private PurchaseOrderDetailBundleFormService purchaseOrderDetailBundleFormService;

  @Override
  public PageResult<PurchaseOrder> query(Integer pageIndex, Integer pageSize,
      QueryPurchaseOrderVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<PurchaseOrder> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<PurchaseOrder> query(QueryPurchaseOrderVo vo) {

    return getBaseMapper().query(vo);
  }

  @Override
  public PageResult<PurchaseOrder> selector(Integer pageIndex, Integer pageSize,
      PurchaseOrderSelectorVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<PurchaseOrder> datas = getBaseMapper().selector(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public PurchaseOrderFullDto getDetail(String id, Boolean isForm) {
    if (isForm == null) {
      isForm = false;
    }

    PurchaseOrderFullDto order = getBaseMapper().getDetail(id, isForm);
    if (order == null) {
      throw new InputErrorException("订单不存在！");
    }

    return order;
  }

  @Override
  public PurchaseOrderWithReceiveDto getWithReceive(String id) {

    PurchaseConfig purchaseConfig = purchaseConfigService.get();

    PurchaseOrderWithReceiveDto order = getBaseMapper().getWithReceive(id,
        purchaseConfig.getReceiveRequirePurchase());
    if (order == null) {
      throw new InputErrorException("订单不存在！");
    }
    return order;
  }

  @Override
  public PageResult<PurchaseOrder> queryWithReceive(Integer pageIndex, Integer pageSize,
      QueryPurchaseOrderWithReceiveVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PurchaseConfig purchaseConfig = purchaseConfigService.get();

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<PurchaseOrder> datas = getBaseMapper().queryWithReceive(vo,
        purchaseConfig.getReceiveMultipleRelatePurchase());

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @OpLog(type = PurchaseOpLogType.class, name = "创建订单，单号：{}", params = "#code")
  @OrderTimeLineLog(type = CreateOrderTimeLineBizType.class, orderId = "#_result", name = "创建订单")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(CreatePurchaseOrderVo vo) {

    PurchaseConfig config = purchaseConfigService.get();

    PurchaseOrder order = newOrder(config.getPurchaseRequireBpm());
    order.setId(IdUtil.getId());
    order.setCode(generateCodeService.generate(GenerateCodeTypePool.PURCHASE_ORDER));

    this.create(order, vo, config.getPurchaseRequireBpm());

    order.setStatus(PurchaseOrderStatus.CREATED);

    if (config.getPurchaseRequireBpm()) {
      purchaseOrderFormService.save((PurchaseOrderForm) order);
      Instance instance = this.startBpmInstance(config.getPurchaseBpmProcessCode(), order.getId());
      order.setFlowInstanceId(instance.getId());
    } else {
      getBaseMapper().insert(order);
    }

    OpLogUtil.setVariable("code", order.getCode());
    OpLogUtil.setExtra(vo);

    return order.getId();
  }

  @OpLog(type = PurchaseOpLogType.class, name = "修改订单，单号：{}", params = "#code")
  @OrderTimeLineLog(type = UpdateOrderTimeLineBizType.class, orderId = "#vo.id", name = "修改订单")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdatePurchaseOrderVo vo) {

    PurchaseOrder order = vo.getIsForm() ? purchaseOrderFormService.getById(vo.getId())
        : getBaseMapper().selectById(vo.getId());
    if (order == null) {
      throw new InputErrorException("订单不存在！");
    }

    if (order.getStatus() != PurchaseOrderStatus.CREATED
        && order.getStatus() != PurchaseOrderStatus.APPROVE_REFUSE) {

      if (order.getStatus() == PurchaseOrderStatus.APPROVE_PASS) {
        throw new DefaultClientException("订单已审核通过，无法修改！");
      }

      throw new DefaultClientException("订单无法修改！");
    }

    // 删除订单明细
    if (vo.getIsForm()) {
      Wrapper<PurchaseOrderDetailForm> deleteDetailWrapper = Wrappers.lambdaQuery(
          PurchaseOrderDetailForm.class).eq(PurchaseOrderDetailForm::getOrderId, order.getId());
      purchaseOrderDetailFormService.remove(deleteDetailWrapper);
    } else {
      Wrapper<PurchaseOrderDetail> deleteDetailWrapper = Wrappers.lambdaQuery(
          PurchaseOrderDetail.class).eq(PurchaseOrderDetail::getOrderId, order.getId());
      purchaseOrderDetailService.remove(deleteDetailWrapper);
    }

    this.create(order, vo, vo.getIsForm());

    order.setStatus(PurchaseOrderStatus.CREATED);

    List<PurchaseOrderStatus> statusList = new ArrayList<>();
    statusList.add(PurchaseOrderStatus.CREATED);
    statusList.add(PurchaseOrderStatus.APPROVE_REFUSE);

    if (vo.getIsForm()) {
      PurchaseConfig config = purchaseConfigService.get();
      if (!config.getPurchaseRequireBpm()) {
        throw new DefaultClientException("已关闭审批流程，无法重新发起该订单！");
      }
      if (!flowInstanceWrapperService.canRestart(order.getId())) {
        throw new DefaultClientException("订单不允许重新发起！");
      }
      LambdaUpdateWrapper<PurchaseOrderForm> updateOrderWrapper = Wrappers.lambdaUpdate(
              PurchaseOrderForm.class).set(PurchaseOrderForm::getApproveBy, null)
          .set(PurchaseOrderForm::getApproveTime, null)
          .set(PurchaseOrderForm::getRefuseReason, StringPool.EMPTY_STR)
          .eq(PurchaseOrderForm::getId, order.getId()).in(PurchaseOrderForm::getStatus, statusList);

      if (purchaseOrderFormService.updateAllColumn((PurchaseOrderForm) order, updateOrderWrapper)) {
        throw new DefaultClientException("订单信息已过期，请刷新重试！");
      }

      Instance instance = this.startBpmInstance(config.getPurchaseBpmProcessCode(), order.getId());
      updateOrderWrapper = Wrappers.lambdaUpdate(PurchaseOrderForm.class)
          .set(PurchaseOrderForm::getFlowInstanceId, instance.getId())
          .eq(PurchaseOrderForm::getId, order.getId());
      purchaseOrderFormService.update(updateOrderWrapper);
    } else {
      LambdaUpdateWrapper<PurchaseOrder> updateOrderWrapper = Wrappers.lambdaUpdate(
              PurchaseOrder.class).set(PurchaseOrder::getApproveBy, null)
          .set(PurchaseOrder::getApproveTime, null)
          .set(PurchaseOrder::getRefuseReason, StringPool.EMPTY_STR)
          .eq(PurchaseOrder::getId, order.getId()).in(PurchaseOrder::getStatus, statusList);

      if (getBaseMapper().updateAllColumn(order, updateOrderWrapper) != 1) {
        throw new DefaultClientException("订单信息已过期，请刷新重试！");
      }
    }

    OpLogUtil.setVariable("code", order.getCode());
    OpLogUtil.setExtra(vo);
  }

  @OpLog(type = PurchaseOpLogType.class, name = "审核通过订单，单号：{}", params = "#code")
  @OrderTimeLineLog(type = ApprovePassOrderTimeLineBizType.class, orderId = "#vo.id", name = "审核通过")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void approvePass(ApprovePassPurchaseOrderVo vo) {

    PurchaseOrder order = getBaseMapper().selectById(vo.getId());
    if (order == null) {
      throw new InputErrorException("订单不存在！");
    }

    if (order.getStatus() != PurchaseOrderStatus.CREATED
        && order.getStatus() != PurchaseOrderStatus.APPROVE_REFUSE) {

      if (order.getStatus() == PurchaseOrderStatus.APPROVE_PASS) {
        throw new DefaultClientException("订单已审核通过，不允许继续执行审核！");
      }

      throw new DefaultClientException("订单无法审核通过！");
    }

    order.setStatus(PurchaseOrderStatus.APPROVE_PASS);

    List<PurchaseOrderStatus> statusList = new ArrayList<>();
    statusList.add(PurchaseOrderStatus.CREATED);
    statusList.add(PurchaseOrderStatus.APPROVE_REFUSE);

    LambdaUpdateWrapper<PurchaseOrder> updateOrderWrapper = Wrappers.lambdaUpdate(
            PurchaseOrder.class).set(PurchaseOrder::getApproveBy, SecurityUtil.getCurrentUser().getId())
        .set(PurchaseOrder::getApproveTime, LocalDateTime.now())
        .eq(PurchaseOrder::getId, order.getId()).in(PurchaseOrder::getStatus, statusList);

    if (getBaseMapper().updateAllColumn(order, updateOrderWrapper) != 1) {
      throw new DefaultClientException("订单信息已过期，请刷新重试！");
    }

    if (NumberUtil.gt(order.getTotalAmount(), BigDecimal.ZERO)) {
      List<OrderPayType> orderPayTypes = orderPayTypeService.findByOrderId(order.getId());
      if (CollectionUtil.isEmpty(orderPayTypes)) {
        throw new DefaultClientException("单据没有约定支付，请检查！");
      }
    }

    Wrapper<PurchaseOrderDetail> queryDetailWrapper = Wrappers.lambdaQuery(
            PurchaseOrderDetail.class).eq(PurchaseOrderDetail::getOrderId, order.getId())
        .orderByAsc(PurchaseOrderDetail::getOrderNo);
    List<PurchaseOrderDetail> details = purchaseOrderDetailService.list(queryDetailWrapper);

    BigDecimal totalNum = BigDecimal.ZERO;
    BigDecimal giftNum = BigDecimal.ZERO;
    BigDecimal totalAmount = BigDecimal.ZERO;

    for (PurchaseOrderDetail detail : details) {
      boolean isGift = detail.getIsGift();
      totalAmount = NumberUtil.add(totalAmount,
          NumberUtil.getNumber(NumberUtil.mul(detail.getTaxPrice(), detail.getOrderNum()), 2));

      Product product = productService.findById(detail.getProductId());
      if (product.getProductType() == ProductType.NORMAL) {
        if (isGift) {
          giftNum = NumberUtil.add(giftNum, detail.getOrderNum());
        } else {
          totalNum = NumberUtil.add(totalNum, detail.getOrderNum());
        }
      } else {
        Wrapper<PurchaseOrderDetailBundle> queryBundleWrapper = Wrappers.lambdaQuery(
                PurchaseOrderDetailBundle.class)
            .eq(PurchaseOrderDetailBundle::getOrderId, order.getId())
            .eq(PurchaseOrderDetailBundle::getDetailId, detail.getId());
        List<PurchaseOrderDetailBundle> purchaseOrderDetailBundles = purchaseOrderDetailBundleService.list(
            queryBundleWrapper);
        Assert.notEmpty(purchaseOrderDetailBundles);

        for (PurchaseOrderDetailBundle purchaseOrderDetailBundle : purchaseOrderDetailBundles) {
          PurchaseOrderDetail newDetail = new PurchaseOrderDetail();
          newDetail.setId(IdUtil.getId());
          newDetail.setOrderId(order.getId());
          newDetail.setProductId(purchaseOrderDetailBundle.getProductId());
          newDetail.setOrderNum(purchaseOrderDetailBundle.getProductOrderNum());
          newDetail.setTaxPrice(purchaseOrderDetailBundle.getProductTaxPrice());
          newDetail.setIsGift(detail.getIsGift());
          newDetail.setTaxRate(purchaseOrderDetailBundle.getProductTaxRate());
          newDetail.setDescription(detail.getDescription());
          newDetail.setOrderNo(detail.getOrderNo());
          newDetail.setTaxAmount(purchaseOrderDetailBundle.getProductTaxAmount());

          purchaseOrderDetailService.save(newDetail);
          purchaseOrderDetailService.removeById(detail.getId());

          purchaseOrderDetailBundle.setProductDetailId(newDetail.getId());
          purchaseOrderDetailBundleService.updateById(purchaseOrderDetailBundle);

          if (isGift) {
            giftNum = NumberUtil.add(giftNum, newDetail.getOrderNum());
          } else {
            totalNum = NumberUtil.add(totalNum, newDetail.getOrderNum());
          }
        }
      }
    }

    // 这里需要重新统计明细信息，因为明细发生变动了
    Wrapper<PurchaseOrder> updateWrapper = Wrappers.lambdaUpdate(PurchaseOrder.class)
        .set(PurchaseOrder::getTotalNum, totalNum).set(PurchaseOrder::getTotalGiftNum, giftNum)
        .set(PurchaseOrder::getTotalAmount, totalAmount).eq(PurchaseOrder::getId, order.getId());
    this.update(updateWrapper);

    OpLogUtil.setVariable("code", order.getCode());
    OpLogUtil.setExtra(vo);

    this.sendApprovePassEvent(order);
  }

  @Transactional(rollbackFor = Exception.class)
  @OrderTimeLineLog(type = ApprovePassOrderTimeLineBizType.class, orderId = "#_result", name = "直接审核通过")
  @Override
  public String directApprovePass(CreatePurchaseOrderVo vo) {

    PurchaseOrderService thisService = getThis(this.getClass());

    String orderId = thisService.create(vo);

    ApprovePassPurchaseOrderVo approvePassPurchaseOrderVo = new ApprovePassPurchaseOrderVo();
    approvePassPurchaseOrderVo.setId(orderId);

    thisService.approvePass(approvePassPurchaseOrderVo);

    return orderId;
  }

  @OpLog(type = PurchaseOpLogType.class, name = "审核拒绝订单，单号：{}", params = "#code")
  @OrderTimeLineLog(type = ApproveReturnOrderTimeLineBizType.class, orderId = "#vo.id", name = "审核拒绝，拒绝理由：{}", params = "#vo.refuseReason")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void approveRefuse(ApproveRefusePurchaseOrderVo vo) {

    PurchaseOrder order = getBaseMapper().selectById(vo.getId());
    if (order == null) {
      throw new InputErrorException("订单不存在！");
    }

    if (order.getStatus() != PurchaseOrderStatus.CREATED) {

      if (order.getStatus() == PurchaseOrderStatus.APPROVE_PASS) {
        throw new DefaultClientException("订单已审核通过，不允许继续执行审核！");
      }

      if (order.getStatus() == PurchaseOrderStatus.APPROVE_REFUSE) {
        throw new DefaultClientException("订单已审核拒绝，不允许继续执行审核！");
      }

      throw new DefaultClientException("订单无法审核拒绝！");
    }

    order.setStatus(PurchaseOrderStatus.APPROVE_REFUSE);

    LambdaUpdateWrapper<PurchaseOrder> updateOrderWrapper = Wrappers.lambdaUpdate(
            PurchaseOrder.class).set(PurchaseOrder::getApproveBy, SecurityUtil.getCurrentUser().getId())
        .set(PurchaseOrder::getApproveTime, LocalDateTime.now())
        .set(PurchaseOrder::getRefuseReason, vo.getRefuseReason())
        .eq(PurchaseOrder::getId, order.getId())
        .eq(PurchaseOrder::getStatus, PurchaseOrderStatus.CREATED);
    if (getBaseMapper().updateAllColumn(order, updateOrderWrapper) != 1) {
      throw new DefaultClientException("订单信息已过期，请刷新重试！");
    }

    OpLogUtil.setVariable("code", order.getCode());
    OpLogUtil.setExtra(vo);
  }

  @OpLog(type = PurchaseOpLogType.class, name = "删除订单，单号：{}", params = "#code")
  @OrderTimeLineLog(orderId = "#id", delete = true)
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteById(String id) {

    Assert.notBlank(id);
    PurchaseOrder order = getBaseMapper().selectById(id);
    if (order == null) {
      throw new InputErrorException("订单不存在！");
    }

    if (order.getStatus() != PurchaseOrderStatus.CREATED
        && order.getStatus() != PurchaseOrderStatus.APPROVE_REFUSE) {

      if (order.getStatus() == PurchaseOrderStatus.APPROVE_PASS) {
        throw new DefaultClientException("“审核通过”的采购单据不允许执行删除操作！");
      }

      throw new DefaultClientException("订单无法删除！");
    }

    // 删除订单明细
    Wrapper<PurchaseOrderDetail> deleteDetailWrapper = Wrappers.lambdaQuery(
        PurchaseOrderDetail.class).eq(PurchaseOrderDetail::getOrderId, order.getId());
    purchaseOrderDetailService.remove(deleteDetailWrapper);

    // 删除组合商品明细
    Wrapper<PurchaseOrderDetailBundle> deleteBundleWrapper = Wrappers.lambdaQuery(
        PurchaseOrderDetailBundle.class).eq(PurchaseOrderDetailBundle::getOrderId, order.getId());
    purchaseOrderDetailBundleService.remove(deleteBundleWrapper);

    // 删除订单
    Wrapper<PurchaseOrder> deleteWrapper = Wrappers.lambdaQuery(PurchaseOrder.class)
        .eq(PurchaseOrder::getId, order.getId())
        .in(PurchaseOrder::getStatus, PurchaseOrderStatus.CREATED,
            PurchaseOrderStatus.APPROVE_REFUSE);
    if (!remove(deleteWrapper)) {
      throw new DefaultClientException("订单信息已过期，请刷新重试！");
    }

    orderPayTypeService.deleteByOrderId(id);

    OpLogUtil.setVariable("code", order.getCode());
  }

  @OpLog(type = PurchaseOpLogType.class, name = "取消审核订单，单号：{}", params = "#code")
  @OrderTimeLineLog(type = CancelApproveOrderTimeLineBizType.class, orderId = "#id", name = "取消审核")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void cancelApprovePass(String id) {

    Assert.notBlank(id);

    PurchaseOrder order = getBaseMapper().selectById(id);
    if (order == null) {
      throw new InputErrorException("订单不存在！");
    }

    if (order.getStatus() != PurchaseOrderStatus.APPROVE_PASS) {

      throw new DefaultClientException("订单尚未审核通过，无需取消审核！");
    }

    order.setStatus(PurchaseOrderStatus.CREATED);

    Wrapper<PurchaseOrder> updateOrderWrapper = Wrappers.lambdaUpdate(PurchaseOrder.class)
        .set(PurchaseOrder::getApproveBy, null).set(PurchaseOrder::getApproveTime, null)
        .set(PurchaseOrder::getRefuseReason, StringPool.EMPTY_STR)
        .eq(PurchaseOrder::getId, order.getId())
        .eq(PurchaseOrder::getStatus, PurchaseOrderStatus.APPROVE_PASS);
    if (getBaseMapper().updateAllColumn(order, updateOrderWrapper) != 1) {
      throw new DefaultClientException("订单信息已过期，请刷新重试！");
    }

    OpLogUtil.setVariable("code", order.getCode());
  }

  private void create(PurchaseOrder order, CreatePurchaseOrderVo vo, boolean isForm) {

    StoreCenter sc = storeCenterService.findById(vo.getScId());
    if (sc == null) {
      throw new InputErrorException("仓库不存在！");
    }

    order.setScId(vo.getScId());

    Supplier supplier = supplierService.findById(vo.getSupplierId());
    if (supplier == null) {
      throw new InputErrorException("供应商不存在！");
    }
    order.setSupplierId(vo.getSupplierId());

    if (!StringUtil.isBlank(vo.getPurchaserId())) {
      SysUser purchaser = userService.findById(vo.getPurchaserId());
      if (purchaser == null) {
        throw new InputErrorException("采购员不存在！");
      }

      order.setPurchaserId(vo.getPurchaserId());
    }

    order.setExpectArriveDate(vo.getExpectArriveDate());

    BigDecimal purchaseNum = BigDecimal.ZERO;
    BigDecimal giftNum = BigDecimal.ZERO;
    BigDecimal totalAmount = BigDecimal.ZERO;
    int orderNo = 1;
    for (PurchaseProductVo productVo : vo.getProducts()) {

      boolean isGift = productVo.getPurchasePrice().doubleValue() == 0D;

      if (isGift) {
        giftNum = NumberUtil.add(giftNum, productVo.getPurchaseNum());
      } else {
        purchaseNum = NumberUtil.add(purchaseNum, productVo.getPurchaseNum());
      }

      // 计算含税总金额：采购数量 × 采购价（采购价本身就是含税价）
      BigDecimal taxAmount = NumberUtil.getNumber(
          NumberUtil.mul(productVo.getPurchasePrice(), productVo.getPurchaseNum()), 2);
      totalAmount = NumberUtil.add(totalAmount, taxAmount);

      PurchaseOrderDetail orderDetail = newOrderDetail(isForm);
      orderDetail.setId(IdUtil.getId());
      orderDetail.setOrderId(order.getId());

      Product product = productService.findById(productVo.getProductId());
      if (product == null) {
        throw new InputErrorException("第" + orderNo + "行商品不存在！");
      }

      orderDetail.setProductId(productVo.getProductId());
      orderDetail.setOrderNum(productVo.getPurchaseNum());
      orderDetail.setTaxPrice(productVo.getPurchasePrice());
      orderDetail.setIsGift(isGift);
      orderDetail.setTaxRate(product.getTaxRate());
      orderDetail.setDescription(
          StringUtil.isBlank(productVo.getDescription()) ? StringPool.EMPTY_STR
              : productVo.getDescription());
      orderDetail.setOrderNo(orderNo);
      orderDetail.setTaxAmount(taxAmount);

      if (isForm) {
        purchaseOrderDetailFormService.save((PurchaseOrderDetailForm) orderDetail);
      } else {
        purchaseOrderDetailService.save(orderDetail);
      }

      // 这里处理组合商品
      if (product.getProductType() == ProductType.BUNDLE) {
        if (!NumberUtil.isInteger(productVo.getPurchaseNum())) {
          throw new InputErrorException("第" + orderNo + "行商品采购数量必须是整数！");
        }
        List<ProductBundle> productBundles = productBundleService.getByMainProductId(
            product.getId());
        // 构建指标项
        Map<Object, Number> bundleWeight = new HashMap<>(productBundles.size());
        for (ProductBundle productBundle : productBundles) {
          bundleWeight.put(productBundle.getProductId(),
              NumberUtil.mul(productBundle.getPurchasePrice(), productBundle.getBundleNum()));
        }
        Map<Object, Number> splitPriceMap = SplitNumberUtil.split(orderDetail.getTaxAmount(),
            bundleWeight, 2);
        List<PurchaseOrderDetailBundle> purchaseOrderDetailBundles = productBundles.stream()
            .map(productBundle -> {
              Product bundle = productService.findById(productBundle.getProductId());
              PurchaseOrderDetailBundle purchaseOrderDetailBundle = newOrderDetailBundle(isForm);
              purchaseOrderDetailBundle.setId(IdUtil.getId());
              purchaseOrderDetailBundle.setOrderId(order.getId());
              purchaseOrderDetailBundle.setDetailId(orderDetail.getId());
              purchaseOrderDetailBundle.setMainProductId(product.getId());
              purchaseOrderDetailBundle.setOrderNum(orderDetail.getOrderNum());
              purchaseOrderDetailBundle.setProductId(productBundle.getProductId());
              purchaseOrderDetailBundle.setProductOrderNum(
                  NumberUtil.mul(orderDetail.getOrderNum(), productBundle.getBundleNum()));
              purchaseOrderDetailBundle.setProductOriPrice(productBundle.getPurchasePrice());
              purchaseOrderDetailBundle.setProductTaxAmount(BigDecimal.valueOf(
                  splitPriceMap.get(productBundle.getProductId()).doubleValue()));
              // 这里会有尾差
              purchaseOrderDetailBundle.setProductTaxPrice(NumberUtil.getNumber(
                  NumberUtil.div(purchaseOrderDetailBundle.getProductTaxAmount(),
                      productBundle.getBundleNum()), 6));
              purchaseOrderDetailBundle.setProductTaxRate(bundle.getTaxRate());

              return purchaseOrderDetailBundle;
            }).collect(Collectors.toList());

        if (isForm) {
          purchaseOrderDetailBundleFormService.saveBatch((List) purchaseOrderDetailBundles);
        } else {
          purchaseOrderDetailBundleService.saveBatch(purchaseOrderDetailBundles);
        }
      }
      orderNo++;
    }
    order.setTotalNum(purchaseNum);
    order.setTotalGiftNum(giftNum);
    order.setTotalAmount(totalAmount);
    order.setDescription(
        StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());

    orderPayTypeService.create(order.getId(), vo.getPayTypes());
  }

  @Override
  public PageResult<PurchaseProductDto> queryPurchaseByCondition(Integer pageIndex,
      Integer pageSize, String condition, Boolean isReturn) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);

    List<PurchaseProductDto> datas = getBaseMapper().queryPurchaseByCondition(condition, isReturn);
    PageResult<PurchaseProductDto> pageResult = PageResultUtil.convert(new PageInfo<>(datas));

    return pageResult;
  }

  @Override
  public PageResult<PurchaseProductDto> queryPurchaseList(Integer pageIndex, Integer pageSize,
      QueryPurchaseProductVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);

    List<PurchaseProductDto> datas = getBaseMapper().queryPurchaseList(vo);
    PageResult<PurchaseProductDto> pageResult = PageResultUtil.convert(new PageInfo<>(datas));

    return pageResult;
  }

  @Override
  public PurchaseProductDto getPurchaseById(String id) {

    PurchaseProductDto data = getBaseMapper().getPurchaseById(id);

    return data;
  }

  private void sendApprovePassEvent(PurchaseOrder order) {

    ApprovePassOrderDto dto = new ApprovePassOrderDto();
    dto.setId(order.getId());
    dto.setTotalAmount(order.getTotalAmount());
    dto.setApproveTime(order.getApproveTime());

    ApprovePassPurchaseOrderEvent event = new ApprovePassPurchaseOrderEvent(this, dto);

    ApplicationUtil.publishEvent(event);
  }

  /**
   * 发起流程实例
   *
   * @param processCode
   * @param businessId
   * @return
   */
  private Instance startBpmInstance(String processCode, String businessId) {
    if (this.getById(businessId) != null) {
      throw new DefaultClientException("订单已办理完成，无法重新发起！");
    }
    FlowParams flowParams = new FlowParams();
    flowParams.flowCode(processCode);

    String title = this.getBpmTitle(businessId);
    FlowCuInstanceTransfer.setTitle(title);

    FlowInstanceExtDto ext = new FlowInstanceExtDto();
    ext.setBizType(FlowDefinitionExtBizType.SYSTEM.getCode());
    ext.setBizFlag(BPM_FLAG);

    flowParams.ext(JsonUtil.toJsonString(ext));

    // 需要传入表单变量
    GetPurchaseOrderBo detail = new GetPurchaseOrderBo(this.getDetail(businessId, true));
    flowParams.variable(
        JsonUtil.parseMap(JsonUtil.toJsonString(detail), String.class, Object.class));

    Instance instance = insService.start(businessId, flowParams);

    return instance;
  }

  @Slf4j
  @Component
  public static class PurchaseOrderBpmListener implements BpmBizListener {

    @Autowired
    private PurchaseOrderService purchaseOrderService;
    @Autowired
    private PurchaseOrderFormService purchaseOrderFormService;
    @Autowired
    private PurchaseOrderDetailFormService purchaseOrderDetailFormService;
    @Autowired
    private PurchaseOrderDetailBundleFormService purchaseOrderDetailBundleFormService;
    @Autowired
    private PurchaseOrderDetailBundleService purchaseOrderDetailBundleService;
    @Autowired
    private PurchaseOrderDetailService purchaseOrderDetailService;

    @Override
    public boolean isMatch(FlowInstanceExtDto ext) {
      return BPM_FLAG.equals(ext.getBizFlag());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void businessComplete(ListenerVariable listenerVariable, String businessId,
        String startById) {
      log.info("接收到业务完成事件");
      PurchaseOrderForm orderForm = purchaseOrderFormService.getById(businessId);
      Wrapper<PurchaseOrderDetailForm> queryWrapper = Wrappers.lambdaQuery(
          PurchaseOrderDetailForm.class).eq(PurchaseOrderDetailForm::getOrderId, orderForm.getId());
      List<PurchaseOrderDetailForm> detailFormList = purchaseOrderDetailFormService.list(
          queryWrapper);

      PurchaseOrder order = new PurchaseOrder();
      BeanUtil.copyProperties(orderForm, order);

      List<PurchaseOrderDetail> detailList = detailFormList.stream().map(detailForm -> {
        PurchaseOrderDetail detail = new PurchaseOrderDetail();
        BeanUtil.copyProperties(detailForm, detail);
        return detail;
      }).collect(Collectors.toList());

      purchaseOrderService.save(order);

      purchaseOrderDetailService.saveBatch(detailList);

      Wrapper<PurchaseOrderDetailBundleForm> queryDetailBundleWrapper = Wrappers.lambdaQuery(
              PurchaseOrderDetailBundleForm.class)
          .eq(PurchaseOrderDetailBundleForm::getOrderId, order.getId());
      List<PurchaseOrderDetailBundleForm> detailBundleFormList = purchaseOrderDetailBundleFormService.list(
          queryDetailBundleWrapper);
      if (!CollectionUtil.isEmpty(detailBundleFormList)) {
        List<PurchaseOrderDetailBundle> detailBundleList = detailBundleFormList.stream()
            .map(detailBundleForm -> {
              PurchaseOrderDetailBundle detailBundle = new PurchaseOrderDetailBundle();
              BeanUtil.copyProperties(detailBundleForm, detailBundle);
              return detailBundle;
            }).collect(Collectors.toList());
        purchaseOrderDetailBundleService.saveBatch(detailBundleList);
      }
      // 目前订单是已保存状态
      ApprovePassPurchaseOrderVo approveVo = new ApprovePassPurchaseOrderVo();
      approveVo.setId(order.getId());

      purchaseOrderService.approvePass(approveVo);
    }
  }

  // 扩展点，可以自定义标题
  private String getBpmTitle(String orderId) {
    return "";
  }

  private PurchaseOrder newOrder(boolean isForm) {
    if (isForm) {
      return new PurchaseOrderForm();
    } else {
      return new PurchaseOrder();
    }
  }

  private PurchaseOrderDetail newOrderDetail(boolean isForm) {
    if (isForm) {
      return new PurchaseOrderDetailForm();
    } else {
      return new PurchaseOrderDetail();
    }
  }

  private PurchaseOrderDetailBundle newOrderDetailBundle(boolean isForm) {
    if (isForm) {
      return new PurchaseOrderDetailBundleForm();
    } else {
      return new PurchaseOrderDetailBundle();
    }
  }
}

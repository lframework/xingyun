package com.lframework.xingyun.sc.biz.impl.purchase;

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
import com.lframework.starter.mq.producer.MqProducer;
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
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.web.common.security.SecurityUtil;
import com.lframework.xingyun.basedata.facade.ProductFeignClient;
import com.lframework.xingyun.basedata.facade.StoreCenterFeignClient;
import com.lframework.xingyun.basedata.facade.SupplierFeignClient;
import com.lframework.xingyun.basedata.facade.dto.product.info.ProductDto;
import com.lframework.xingyun.basedata.facade.entity.StoreCenter;
import com.lframework.xingyun.basedata.facade.entity.Supplier;
import com.lframework.xingyun.chart.facade.constants.MqConstants;
import com.lframework.xingyun.chart.facade.mq.ApprovePassOrderEvent;
import com.lframework.xingyun.chart.facade.mq.ApprovePassOrderEvent.OrderType;
import com.lframework.xingyun.core.annations.OrderTimeLineLog;
import com.lframework.xingyun.core.enums.OrderTimeLineBizType;
import com.lframework.xingyun.sc.biz.components.code.GenerateCodeTypePool;
import com.lframework.xingyun.sc.biz.mappers.PurchaseOrderMapper;
import com.lframework.xingyun.sc.biz.service.purchase.IPurchaseConfigService;
import com.lframework.xingyun.sc.biz.service.purchase.IPurchaseOrderDetailService;
import com.lframework.xingyun.sc.biz.service.purchase.IPurchaseOrderService;
import com.lframework.xingyun.sc.facade.dto.purchase.PurchaseOrderFullDto;
import com.lframework.xingyun.sc.facade.dto.purchase.PurchaseOrderWithReceiveDto;
import com.lframework.xingyun.sc.facade.entity.PurchaseConfig;
import com.lframework.xingyun.sc.facade.entity.PurchaseOrder;
import com.lframework.xingyun.sc.facade.entity.PurchaseOrderDetail;
import com.lframework.xingyun.sc.facade.enums.PurchaseOrderStatus;
import com.lframework.xingyun.sc.facade.vo.purchase.ApprovePassPurchaseOrderVo;
import com.lframework.xingyun.sc.facade.vo.purchase.ApproveRefusePurchaseOrderVo;
import com.lframework.xingyun.sc.facade.vo.purchase.BatchApprovePassPurchaseOrderVo;
import com.lframework.xingyun.sc.facade.vo.purchase.BatchApproveRefusePurchaseOrderVo;
import com.lframework.xingyun.sc.facade.vo.purchase.CreatePurchaseOrderVo;
import com.lframework.xingyun.sc.facade.vo.purchase.PurchaseOrderSelectorVo;
import com.lframework.xingyun.sc.facade.vo.purchase.PurchaseProductVo;
import com.lframework.xingyun.sc.facade.vo.purchase.QueryPurchaseOrderVo;
import com.lframework.xingyun.sc.facade.vo.purchase.QueryPurchaseOrderWithRecevieVo;
import com.lframework.xingyun.sc.facade.vo.purchase.UpdatePurchaseOrderVo;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PurchaseOrderServiceImpl extends BaseMpServiceImpl<PurchaseOrderMapper, PurchaseOrder>
    implements IPurchaseOrderService {

  @Autowired
  private IPurchaseOrderDetailService purchaseOrderDetailService;

  @Autowired
  private IGenerateCodeService generateCodeService;

  @Autowired
  private StoreCenterFeignClient storeCenterFeignClient;

  @Autowired
  private SupplierFeignClient supplierFeignClient;

  @Autowired
  private IUserService userService;

  @Autowired
  private ProductFeignClient productFeignClient;

  @Autowired
  private IPurchaseConfigService purchaseConfigService;

  @Autowired
  private MqProducer mqProducer;

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
  public PurchaseOrderFullDto getDetail(String id) {

    PurchaseOrderFullDto order = getBaseMapper().getDetail(id);
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
      QueryPurchaseOrderWithRecevieVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PurchaseConfig purchaseConfig = purchaseConfigService.get();

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<PurchaseOrder> datas = getBaseMapper().queryWithReceive(vo,
        purchaseConfig.getReceiveMultipleRelatePurchase());

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @OpLog(type = OpLogType.OTHER, name = "创建订单，单号：{}", params = "#code")
  @OrderTimeLineLog(type = OrderTimeLineBizType.CREATE, orderId = "#_result", name = "创建订单")
  @Transactional
  @Override
  public String create(CreatePurchaseOrderVo vo) {

    PurchaseOrder order = new PurchaseOrder();
    order.setId(IdUtil.getId());
    order.setCode(generateCodeService.generate(GenerateCodeTypePool.PURCHASE_ORDER));

    this.create(order, vo);

    order.setStatus(PurchaseOrderStatus.CREATED);

    getBaseMapper().insert(order);

    OpLogUtil.setVariable("code", order.getCode());
    OpLogUtil.setExtra(vo);

    return order.getId();
  }

  @OpLog(type = OpLogType.OTHER, name = "修改订单，单号：{}", params = "#code")
  @OrderTimeLineLog(type = OrderTimeLineBizType.UPDATE, orderId = "#vo.id", name = "修改订单")
  @Transactional
  @Override
  public void update(UpdatePurchaseOrderVo vo) {

    PurchaseOrder order = getBaseMapper().selectById(vo.getId());
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
    Wrapper<PurchaseOrderDetail> deleteDetailWrapper = Wrappers.lambdaQuery(
            PurchaseOrderDetail.class)
        .eq(PurchaseOrderDetail::getOrderId, order.getId());
    purchaseOrderDetailService.remove(deleteDetailWrapper);

    this.create(order, vo);

    order.setStatus(PurchaseOrderStatus.CREATED);

    List<PurchaseOrderStatus> statusList = new ArrayList<>();
    statusList.add(PurchaseOrderStatus.CREATED);
    statusList.add(PurchaseOrderStatus.APPROVE_REFUSE);

    Wrapper<PurchaseOrder> updateOrderWrapper = Wrappers.lambdaUpdate(PurchaseOrder.class)
        .set(PurchaseOrder::getApproveBy, null).set(PurchaseOrder::getApproveTime, null)
        .set(PurchaseOrder::getRefuseReason, StringPool.EMPTY_STR)
        .eq(PurchaseOrder::getId, order.getId())
        .in(PurchaseOrder::getStatus, statusList);
    if (getBaseMapper().update(order, updateOrderWrapper) != 1) {
      throw new DefaultClientException("订单信息已过期，请刷新重试！");
    }

    OpLogUtil.setVariable("code", order.getCode());
    OpLogUtil.setExtra(vo);
  }

  @OpLog(type = OpLogType.OTHER, name = "审核通过订单，单号：{}", params = "#code")
  @OrderTimeLineLog(type = OrderTimeLineBizType.APPROVE_PASS, orderId = "#vo.id", name = "审核通过")
  @Transactional
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
            PurchaseOrder.class)
        .set(PurchaseOrder::getApproveBy, SecurityUtil.getCurrentUser().getId())
        .set(PurchaseOrder::getApproveTime, LocalDateTime.now())
        .eq(PurchaseOrder::getId, order.getId())
        .in(PurchaseOrder::getStatus, statusList);
    if (!StringUtil.isBlank(vo.getDescription())) {
      updateOrderWrapper.set(PurchaseOrder::getDescription, vo.getDescription());
    }
    if (getBaseMapper().update(order, updateOrderWrapper) != 1) {
      throw new DefaultClientException("订单信息已过期，请刷新重试！");
    }

    OpLogUtil.setVariable("code", order.getCode());
    OpLogUtil.setExtra(vo);

    this.sendApprovePassEvent(order);
  }

  @Transactional
  @OrderTimeLineLog(type = OrderTimeLineBizType.APPROVE_PASS, orderId = "#vo.ids", name = "审核通过")
  @Override
  public void batchApprovePass(BatchApprovePassPurchaseOrderVo vo) {

    int orderNo = 1;
    for (String id : vo.getIds()) {
      ApprovePassPurchaseOrderVo approvePassPurchaseOrderVo = new ApprovePassPurchaseOrderVo();
      approvePassPurchaseOrderVo.setId(id);

      try {
        IPurchaseOrderService thisService = getThis(this.getClass());
        thisService.approvePass(approvePassPurchaseOrderVo);
      } catch (ClientException e) {
        throw new DefaultClientException("第" + orderNo + "个采购订单审核通过失败，失败原因：" + e.getMsg());
      }

      orderNo++;
    }
  }

  @Transactional
  @OrderTimeLineLog(type = OrderTimeLineBizType.APPROVE_PASS, orderId = "#_result", name = "直接审核通过")
  @Override
  public String directApprovePass(CreatePurchaseOrderVo vo) {

    IPurchaseOrderService thisService = getThis(this.getClass());

    String orderId = thisService.create(vo);

    ApprovePassPurchaseOrderVo approvePassPurchaseOrderVo = new ApprovePassPurchaseOrderVo();
    approvePassPurchaseOrderVo.setId(orderId);
    approvePassPurchaseOrderVo.setDescription(vo.getDescription());

    thisService.approvePass(approvePassPurchaseOrderVo);

    return orderId;
  }

  @OpLog(type = OpLogType.OTHER, name = "审核拒绝订单，单号：{}", params = "#code")
  @OrderTimeLineLog(type = OrderTimeLineBizType.APPROVE_RETURN, orderId = "#vo.id", name = "审核拒绝，拒绝理由：{}", params = "#vo.refuseReason")
  @Transactional
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
            PurchaseOrder.class)
        .set(PurchaseOrder::getApproveBy, SecurityUtil.getCurrentUser().getId())
        .set(PurchaseOrder::getApproveTime, LocalDateTime.now())
        .set(PurchaseOrder::getRefuseReason, vo.getRefuseReason())
        .eq(PurchaseOrder::getId, order.getId())
        .eq(PurchaseOrder::getStatus, PurchaseOrderStatus.CREATED);
    if (getBaseMapper().update(order, updateOrderWrapper) != 1) {
      throw new DefaultClientException("订单信息已过期，请刷新重试！");
    }

    OpLogUtil.setVariable("code", order.getCode());
    OpLogUtil.setExtra(vo);
  }

  @Transactional
  @OrderTimeLineLog(type = OrderTimeLineBizType.APPROVE_RETURN, orderId = "#vo.ids", name = "审核拒绝，拒绝理由：{}", params = "#vo.refuseReason")
  @Override
  public void batchApproveRefuse(BatchApproveRefusePurchaseOrderVo vo) {

    int orderNo = 1;
    for (String id : vo.getIds()) {
      ApproveRefusePurchaseOrderVo approveRefusePurchaseOrderVo = new ApproveRefusePurchaseOrderVo();
      approveRefusePurchaseOrderVo.setId(id);
      approveRefusePurchaseOrderVo.setRefuseReason(vo.getRefuseReason());

      try {
        IPurchaseOrderService thisService = getThis(this.getClass());
        thisService.approveRefuse(approveRefusePurchaseOrderVo);
      } catch (ClientException e) {
        throw new DefaultClientException("第" + orderNo + "个采购订单审核拒绝失败，失败原因：" + e.getMsg());
      }

      orderNo++;
    }
  }

  @OpLog(type = OpLogType.OTHER, name = "删除订单，单号：{}", params = "#code")
  @OrderTimeLineLog(orderId = "#id", delete = true)
  @Transactional
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
            PurchaseOrderDetail.class)
        .eq(PurchaseOrderDetail::getOrderId, order.getId());
    purchaseOrderDetailService.remove(deleteDetailWrapper);

    // 删除订单
    getBaseMapper().deleteById(id);

    OpLogUtil.setVariable("code", order.getCode());
  }

  @Transactional
  @OrderTimeLineLog(orderId = "#ids", delete = true)
  @Override
  public void deleteByIds(List<String> ids) {

    if (!CollectionUtil.isEmpty(ids)) {
      int orderNo = 1;
      for (String id : ids) {

        try {
          IPurchaseOrderService thisService = getThis(this.getClass());
          thisService.deleteById(id);
        } catch (ClientException e) {
          throw new DefaultClientException("第" + orderNo + "个采购订单删除失败，失败原因：" + e.getMsg());
        }

        orderNo++;
      }
    }
  }

  @OpLog(type = OpLogType.OTHER, name = "取消审核订单，单号：{}", params = "#code")
  @OrderTimeLineLog(type = OrderTimeLineBizType.CANCEL_APPROVE, orderId = "#id", name = "取消审核")
  @Transactional
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
    if (getBaseMapper().update(order, updateOrderWrapper) != 1) {
      throw new DefaultClientException("订单信息已过期，请刷新重试！");
    }

    OpLogUtil.setVariable("code", order.getCode());
  }

  private void create(PurchaseOrder order, CreatePurchaseOrderVo vo) {

    StoreCenter sc = storeCenterFeignClient.findById(vo.getScId()).getData();
    if (sc == null) {
      throw new InputErrorException("仓库不存在！");
    }

    order.setScId(vo.getScId());

    Supplier supplier = supplierFeignClient.findById(vo.getSupplierId()).getData();
    if (supplier == null) {
      throw new InputErrorException("供应商不存在！");
    }
    order.setSupplierId(vo.getSupplierId());

    if (!StringUtil.isBlank(vo.getPurchaserId())) {
      UserDto purchaser = userService.findById(vo.getPurchaserId());
      if (purchaser == null) {
        throw new InputErrorException("采购员不存在！");
      }

      order.setPurchaserId(vo.getPurchaserId());
    }

    order.setExpectArriveDate(vo.getExpectArriveDate());

    int purchaseNum = 0;
    int giftNum = 0;
    BigDecimal totalAmount = BigDecimal.ZERO;
    int orderNo = 1;
    for (PurchaseProductVo productVo : vo.getProducts()) {

      boolean isGift = productVo.getPurchasePrice().doubleValue() == 0D;

      if (isGift) {
        giftNum += productVo.getPurchaseNum();
      } else {
        purchaseNum += productVo.getPurchaseNum();
      }

      totalAmount = NumberUtil.add(totalAmount,
          NumberUtil.mul(productVo.getPurchasePrice(), productVo.getPurchaseNum()));

      PurchaseOrderDetail orderDetail = new PurchaseOrderDetail();
      orderDetail.setId(IdUtil.getId());
      orderDetail.setOrderId(order.getId());

      ProductDto product = productFeignClient.findById(productVo.getProductId()).getData();
      if (product == null) {
        throw new InputErrorException("第" + orderNo + "行商品不存在！");
      }

      if (!NumberUtil.isNumberPrecision(productVo.getPurchasePrice(), 2)) {
        throw new InputErrorException("第" + orderNo + "行商品采购价最多允许2位小数！");
      }

      orderDetail.setProductId(productVo.getProductId());
      orderDetail.setOrderNum(productVo.getPurchaseNum());
      orderDetail.setTaxPrice(productVo.getPurchasePrice());
      orderDetail.setIsGift(isGift);
      orderDetail.setTaxRate(product.getPoly().getTaxRate());
      orderDetail.setDescription(
          StringUtil.isBlank(productVo.getDescription()) ? StringPool.EMPTY_STR
              : productVo.getDescription());
      orderDetail.setOrderNo(orderNo);

      purchaseOrderDetailService.save(orderDetail);
      orderNo++;
    }
    order.setTotalNum(purchaseNum);
    order.setTotalGiftNum(giftNum);
    order.setTotalAmount(totalAmount);
    order.setDescription(
        StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());
  }

  private void sendApprovePassEvent(PurchaseOrder order) {

    ApprovePassOrderEvent event = new ApprovePassOrderEvent();
    event.setId(order.getId());
    event.setTotalAmount(order.getTotalAmount());
    event.setApproveTime(order.getApproveTime());
    event.setOrderType(OrderType.PURCHASE_ORDER);

    mqProducer.sendMessage(MqConstants.QUEUE_ORDER_CHART, event);
  }
}

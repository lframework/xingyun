package com.lframework.xingyun.sc.impl.purchase;

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
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.entity.Supplier;
import com.lframework.xingyun.basedata.service.product.IProductService;
import com.lframework.xingyun.basedata.service.storecenter.IStoreCenterService;
import com.lframework.xingyun.basedata.service.supplier.ISupplierService;
import com.lframework.xingyun.core.events.order.impl.ApprovePassPurchaseOrderEvent;
import com.lframework.xingyun.sc.components.code.GenerateCodeTypePool;
import com.lframework.xingyun.sc.dto.purchase.PurchaseOrderFullDto;
import com.lframework.xingyun.sc.dto.purchase.PurchaseOrderWithReceiveDto;
import com.lframework.xingyun.sc.entity.PurchaseConfig;
import com.lframework.xingyun.sc.entity.PurchaseOrder;
import com.lframework.xingyun.sc.entity.PurchaseOrderDetail;
import com.lframework.xingyun.sc.enums.PurchaseOrderStatus;
import com.lframework.xingyun.sc.mappers.PurchaseOrderMapper;
import com.lframework.xingyun.sc.service.purchase.IPurchaseConfigService;
import com.lframework.xingyun.sc.service.purchase.IPurchaseOrderDetailService;
import com.lframework.xingyun.sc.service.purchase.IPurchaseOrderService;
import com.lframework.xingyun.sc.vo.purchase.ApprovePassPurchaseOrderVo;
import com.lframework.xingyun.sc.vo.purchase.ApproveRefusePurchaseOrderVo;
import com.lframework.xingyun.sc.vo.purchase.BatchApprovePassPurchaseOrderVo;
import com.lframework.xingyun.sc.vo.purchase.BatchApproveRefusePurchaseOrderVo;
import com.lframework.xingyun.sc.vo.purchase.CreatePurchaseOrderVo;
import com.lframework.xingyun.sc.vo.purchase.PurchaseOrderSelectorVo;
import com.lframework.xingyun.sc.vo.purchase.PurchaseProductVo;
import com.lframework.xingyun.sc.vo.purchase.QueryPurchaseOrderVo;
import com.lframework.xingyun.sc.vo.purchase.QueryPurchaseOrderWithRecevieVo;
import com.lframework.xingyun.sc.vo.purchase.UpdatePurchaseOrderVo;
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
  private IStoreCenterService storeCenterService;

  @Autowired
  private ISupplierService supplierService;

  @Autowired
  private IUserService userService;

  @Autowired
  private IProductService productService;

  @Autowired
  private IPurchaseConfigService purchaseConfigService;

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
      throw new InputErrorException("??????????????????");
    }

    return order;
  }

  @Override
  public PurchaseOrderWithReceiveDto getWithReceive(String id) {

    PurchaseConfig purchaseConfig = purchaseConfigService.get();

    PurchaseOrderWithReceiveDto order = getBaseMapper().getWithReceive(id,
        purchaseConfig.getReceiveRequirePurchase());
    if (order == null) {
      throw new InputErrorException("??????????????????");
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

  @OpLog(type = OpLogType.OTHER, name = "????????????????????????{}", params = "#code")
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

  @OpLog(type = OpLogType.OTHER, name = "????????????????????????{}", params = "#code")
  @Transactional
  @Override
  public void update(UpdatePurchaseOrderVo vo) {

    PurchaseOrder order = getBaseMapper().selectById(vo.getId());
    if (order == null) {
      throw new InputErrorException("??????????????????");
    }

    if (order.getStatus() != PurchaseOrderStatus.CREATED
        && order.getStatus() != PurchaseOrderStatus.APPROVE_REFUSE) {

      if (order.getStatus() == PurchaseOrderStatus.APPROVE_PASS) {
        throw new DefaultClientException("???????????????????????????????????????");
      }

      throw new DefaultClientException("?????????????????????");
    }

    // ??????????????????
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
      throw new DefaultClientException("??????????????????????????????????????????");
    }

    OpLogUtil.setVariable("code", order.getCode());
    OpLogUtil.setExtra(vo);
  }

  @OpLog(type = OpLogType.OTHER, name = "??????????????????????????????{}", params = "#code")
  @Transactional
  @Override
  public void approvePass(ApprovePassPurchaseOrderVo vo) {

    PurchaseOrder order = getBaseMapper().selectById(vo.getId());
    if (order == null) {
      throw new InputErrorException("??????????????????");
    }

    if (order.getStatus() != PurchaseOrderStatus.CREATED
        && order.getStatus() != PurchaseOrderStatus.APPROVE_REFUSE) {

      if (order.getStatus() == PurchaseOrderStatus.APPROVE_PASS) {
        throw new DefaultClientException("??????????????????????????????????????????????????????");
      }

      throw new DefaultClientException("???????????????????????????");
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
      throw new DefaultClientException("??????????????????????????????????????????");
    }

    OpLogUtil.setVariable("code", order.getCode());
    OpLogUtil.setExtra(vo);

    this.sendApprovePassEvent(order);
  }

  @Transactional
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
        throw new DefaultClientException("???" + orderNo + "???????????????????????????????????????????????????" + e.getMsg());
      }

      orderNo++;
    }
  }

  @Transactional
  @Override
  public void directApprovePass(CreatePurchaseOrderVo vo) {

    IPurchaseOrderService thisService = getThis(this.getClass());

    String orderId = thisService.create(vo);

    ApprovePassPurchaseOrderVo approvePassPurchaseOrderVo = new ApprovePassPurchaseOrderVo();
    approvePassPurchaseOrderVo.setId(orderId);
    approvePassPurchaseOrderVo.setDescription(vo.getDescription());

    thisService.approvePass(approvePassPurchaseOrderVo);
  }

  @OpLog(type = OpLogType.OTHER, name = "??????????????????????????????{}", params = "#code")
  @Transactional
  @Override
  public void approveRefuse(ApproveRefusePurchaseOrderVo vo) {

    PurchaseOrder order = getBaseMapper().selectById(vo.getId());
    if (order == null) {
      throw new InputErrorException("??????????????????");
    }

    if (order.getStatus() != PurchaseOrderStatus.CREATED) {

      if (order.getStatus() == PurchaseOrderStatus.APPROVE_PASS) {
        throw new DefaultClientException("??????????????????????????????????????????????????????");
      }

      if (order.getStatus() == PurchaseOrderStatus.APPROVE_REFUSE) {
        throw new DefaultClientException("??????????????????????????????????????????????????????");
      }

      throw new DefaultClientException("???????????????????????????");
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
      throw new DefaultClientException("??????????????????????????????????????????");
    }

    OpLogUtil.setVariable("code", order.getCode());
    OpLogUtil.setExtra(vo);
  }

  @Transactional
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
        throw new DefaultClientException("???" + orderNo + "???????????????????????????????????????????????????" + e.getMsg());
      }

      orderNo++;
    }
  }

  @OpLog(type = OpLogType.OTHER, name = "????????????????????????{}", params = "#code")
  @Transactional
  @Override
  public void deleteById(String id) {

    Assert.notBlank(id);
    PurchaseOrder order = getBaseMapper().selectById(id);
    if (order == null) {
      throw new InputErrorException("??????????????????");
    }

    if (order.getStatus() != PurchaseOrderStatus.CREATED
        && order.getStatus() != PurchaseOrderStatus.APPROVE_REFUSE) {

      if (order.getStatus() == PurchaseOrderStatus.APPROVE_PASS) {
        throw new DefaultClientException("???????????????????????????????????????????????????????????????");
      }

      throw new DefaultClientException("?????????????????????");
    }

    // ??????????????????
    Wrapper<PurchaseOrderDetail> deleteDetailWrapper = Wrappers.lambdaQuery(
            PurchaseOrderDetail.class)
        .eq(PurchaseOrderDetail::getOrderId, order.getId());
    purchaseOrderDetailService.remove(deleteDetailWrapper);

    // ????????????
    getBaseMapper().deleteById(id);

    OpLogUtil.setVariable("code", order.getCode());
  }

  @Transactional
  @Override
  public void deleteByIds(List<String> ids) {

    if (!CollectionUtil.isEmpty(ids)) {
      int orderNo = 1;
      for (String id : ids) {

        try {
          IPurchaseOrderService thisService = getThis(this.getClass());
          thisService.deleteById(id);
        } catch (ClientException e) {
          throw new DefaultClientException("???" + orderNo + "?????????????????????????????????????????????" + e.getMsg());
        }

        orderNo++;
      }
    }
  }

  @OpLog(type = OpLogType.OTHER, name = "??????????????????????????????{}", params = "#code")
  @Transactional
  @Override
  public void cancelApprovePass(String id) {

    Assert.notBlank(id);

    PurchaseOrder order = getBaseMapper().selectById(id);
    if (order == null) {
      throw new InputErrorException("??????????????????");
    }

    if (order.getStatus() != PurchaseOrderStatus.APPROVE_PASS) {

      throw new DefaultClientException("????????????????????????????????????????????????");
    }

    order.setStatus(PurchaseOrderStatus.CREATED);

    Wrapper<PurchaseOrder> updateOrderWrapper = Wrappers.lambdaUpdate(PurchaseOrder.class)
        .set(PurchaseOrder::getApproveBy, null).set(PurchaseOrder::getApproveTime, null)
        .set(PurchaseOrder::getRefuseReason, StringPool.EMPTY_STR)
        .eq(PurchaseOrder::getId, order.getId())
        .eq(PurchaseOrder::getStatus, PurchaseOrderStatus.APPROVE_PASS);
    if (getBaseMapper().update(order, updateOrderWrapper) != 1) {
      throw new DefaultClientException("??????????????????????????????????????????");
    }

    OpLogUtil.setVariable("code", order.getCode());
  }

  private void create(PurchaseOrder order, CreatePurchaseOrderVo vo) {

    StoreCenter sc = storeCenterService.findById(vo.getScId());
    if (sc == null) {
      throw new InputErrorException("??????????????????");
    }

    order.setScId(vo.getScId());

    Supplier supplier = supplierService.findById(vo.getSupplierId());
    if (supplier == null) {
      throw new InputErrorException("?????????????????????");
    }
    order.setSupplierId(vo.getSupplierId());

    if (!StringUtil.isBlank(vo.getPurchaserId())) {
      UserDto purchaser = userService.findById(vo.getPurchaserId());
      if (purchaser == null) {
        throw new InputErrorException("?????????????????????");
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

      ProductDto product = productService.findById(productVo.getProductId());
      if (product == null) {
        throw new InputErrorException("???" + orderNo + "?????????????????????");
      }

      if (!NumberUtil.isNumberPrecision(productVo.getPurchasePrice(), 2)) {
        throw new InputErrorException("???" + orderNo + "??????????????????????????????2????????????");
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

    ApprovePassPurchaseOrderEvent event = new ApprovePassPurchaseOrderEvent(this);
    event.setId(order.getId());
    event.setTotalAmount(order.getTotalAmount());
    event.setApproveTime(order.getApproveTime());

    ApplicationUtil.publishEvent(event);
  }
}

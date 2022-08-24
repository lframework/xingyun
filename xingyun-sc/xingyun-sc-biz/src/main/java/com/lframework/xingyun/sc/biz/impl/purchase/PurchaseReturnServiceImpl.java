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
import com.lframework.xingyun.basedata.facade.enums.ManageType;
import com.lframework.xingyun.chart.facade.constants.MqConstants;
import com.lframework.xingyun.chart.facade.mq.ApprovePassOrderEvent;
import com.lframework.xingyun.chart.facade.mq.ApprovePassOrderEvent.OrderType;
import com.lframework.xingyun.core.annations.OrderTimeLineLog;
import com.lframework.xingyun.core.enums.OrderTimeLineBizType;
import com.lframework.xingyun.core.enums.SettleStatus;
import com.lframework.xingyun.sc.biz.components.code.GenerateCodeTypePool;
import com.lframework.xingyun.sc.biz.mappers.PurchaseReturnMapper;
import com.lframework.xingyun.sc.biz.service.purchase.IPurchaseConfigService;
import com.lframework.xingyun.sc.biz.service.purchase.IPurchaseReturnDetailService;
import com.lframework.xingyun.sc.biz.service.purchase.IPurchaseReturnService;
import com.lframework.xingyun.sc.biz.service.purchase.IReceiveSheetDetailService;
import com.lframework.xingyun.sc.biz.service.purchase.IReceiveSheetService;
import com.lframework.xingyun.sc.biz.service.stock.IProductStockService;
import com.lframework.xingyun.sc.facade.dto.purchase.receive.GetPaymentDateDto;
import com.lframework.xingyun.sc.facade.dto.purchase.returned.PurchaseReturnFullDto;
import com.lframework.xingyun.sc.facade.entity.PurchaseConfig;
import com.lframework.xingyun.sc.facade.entity.PurchaseReturn;
import com.lframework.xingyun.sc.facade.entity.PurchaseReturnDetail;
import com.lframework.xingyun.sc.facade.entity.ReceiveSheet;
import com.lframework.xingyun.sc.facade.entity.ReceiveSheetDetail;
import com.lframework.xingyun.sc.facade.enums.ProductStockBizType;
import com.lframework.xingyun.sc.facade.enums.PurchaseReturnStatus;
import com.lframework.xingyun.sc.facade.vo.purchase.returned.ApprovePassPurchaseReturnVo;
import com.lframework.xingyun.sc.facade.vo.purchase.returned.ApproveRefusePurchaseReturnVo;
import com.lframework.xingyun.sc.facade.vo.purchase.returned.BatchApprovePassPurchaseReturnVo;
import com.lframework.xingyun.sc.facade.vo.purchase.returned.BatchApproveRefusePurchaseReturnVo;
import com.lframework.xingyun.sc.facade.vo.purchase.returned.CreatePurchaseReturnVo;
import com.lframework.xingyun.sc.facade.vo.purchase.returned.QueryPurchaseReturnVo;
import com.lframework.xingyun.sc.facade.vo.purchase.returned.ReturnProductVo;
import com.lframework.xingyun.sc.facade.vo.purchase.returned.UpdatePurchaseReturnVo;
import com.lframework.xingyun.sc.facade.vo.stock.SubProductStockVo;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PurchaseReturnServiceImpl extends
    BaseMpServiceImpl<PurchaseReturnMapper, PurchaseReturn>
    implements IPurchaseReturnService {

  @Autowired
  private IPurchaseReturnDetailService purchaseReturnDetailService;

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
  private IReceiveSheetService receiveSheetService;

  @Autowired
  private IPurchaseConfigService purchaseConfigService;

  @Autowired
  private IReceiveSheetDetailService receiveSheetDetailService;

  @Autowired
  private IProductStockService productStockService;

  @Autowired
  private MqProducer mqProducer;

  @Override
  public PageResult<PurchaseReturn> query(Integer pageIndex, Integer pageSize,
      QueryPurchaseReturnVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<PurchaseReturn> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<PurchaseReturn> query(QueryPurchaseReturnVo vo) {

    return getBaseMapper().query(vo);
  }

  @Override
  public PurchaseReturnFullDto getDetail(String id) {

    return getBaseMapper().getDetail(id);
  }

  @OpLog(type = OpLogType.OTHER, name = "创建采购退货单，单号：{}", params = "#code")
  @OrderTimeLineLog(type = OrderTimeLineBizType.CREATE, orderId = "#_result", name = "创建退单")
  @Transactional
  @Override
  public String create(CreatePurchaseReturnVo vo) {

    PurchaseReturn purchaseReturn = new PurchaseReturn();
    purchaseReturn.setId(IdUtil.getId());
    purchaseReturn.setCode(generateCodeService.generate(GenerateCodeTypePool.PURCHASE_RETURN));

    PurchaseConfig purchaseConfig = purchaseConfigService.get();

    this.create(purchaseReturn, vo, purchaseConfig.getPurchaseReturnRequireReceive());

    purchaseReturn.setStatus(PurchaseReturnStatus.CREATED);

    getBaseMapper().insert(purchaseReturn);

    OpLogUtil.setVariable("code", purchaseReturn.getCode());
    OpLogUtil.setExtra(vo);

    return purchaseReturn.getId();
  }

  @OpLog(type = OpLogType.OTHER, name = "修改采购退货单，单号：{}", params = "#code")
  @OrderTimeLineLog(type = OrderTimeLineBizType.UPDATE, orderId = "#vo.id", name = "修改退单")
  @Transactional
  @Override
  public void update(UpdatePurchaseReturnVo vo) {

    PurchaseReturn purchaseReturn = getBaseMapper().selectById(vo.getId());
    if (purchaseReturn == null) {
      throw new InputErrorException("采购退货单不存在！");
    }

    if (purchaseReturn.getStatus() != PurchaseReturnStatus.CREATED
        && purchaseReturn.getStatus() != PurchaseReturnStatus.APPROVE_REFUSE) {

      if (purchaseReturn.getStatus() == PurchaseReturnStatus.APPROVE_PASS) {
        throw new DefaultClientException("采购退货单已审核通过，无法修改！");
      }

      throw new DefaultClientException("采购退货单无法修改！");
    }

    boolean requireReceive = !StringUtil.isBlank(purchaseReturn.getReceiveSheetId());

    if (requireReceive) {
      //查询采购退货单明细
      Wrapper<PurchaseReturnDetail> queryDetailWrapper = Wrappers.lambdaQuery(
              PurchaseReturnDetail.class)
          .eq(PurchaseReturnDetail::getReturnId, purchaseReturn.getId());
      List<PurchaseReturnDetail> details = purchaseReturnDetailService.list(queryDetailWrapper);
      for (PurchaseReturnDetail detail : details) {
        if (!StringUtil.isBlank(detail.getReceiveSheetDetailId())) {
          //先恢复已退货数量
          receiveSheetDetailService.subReturnNum(detail.getReceiveSheetDetailId(),
              detail.getReturnNum());
        }
      }
    }

    // 删除采购退货单明细
    Wrapper<PurchaseReturnDetail> deleteDetailWrapper = Wrappers.lambdaQuery(
            PurchaseReturnDetail.class)
        .eq(PurchaseReturnDetail::getReturnId, purchaseReturn.getId());
    purchaseReturnDetailService.remove(deleteDetailWrapper);

    this.create(purchaseReturn, vo, requireReceive);

    purchaseReturn.setStatus(PurchaseReturnStatus.CREATED);

    List<PurchaseReturnStatus> statusList = new ArrayList<>();
    statusList.add(PurchaseReturnStatus.CREATED);
    statusList.add(PurchaseReturnStatus.APPROVE_REFUSE);

    Wrapper<PurchaseReturn> updateOrderWrapper = Wrappers.lambdaUpdate(PurchaseReturn.class)
        .set(PurchaseReturn::getApproveBy, null).set(PurchaseReturn::getApproveTime, null)
        .set(PurchaseReturn::getRefuseReason, StringPool.EMPTY_STR)
        .eq(PurchaseReturn::getId, purchaseReturn.getId())
        .in(PurchaseReturn::getStatus, statusList);
    if (getBaseMapper().update(purchaseReturn, updateOrderWrapper) != 1) {
      throw new DefaultClientException("采购退货单信息已过期，请刷新重试！");
    }

    OpLogUtil.setVariable("code", purchaseReturn.getCode());
    OpLogUtil.setExtra(vo);
  }

  @OpLog(type = OpLogType.OTHER, name = "审核通过采购退货单，单号：{}", params = "#code")
  @OrderTimeLineLog(type = OrderTimeLineBizType.APPROVE_PASS, orderId = "#vo.id", name = "审核通过")
  @Transactional
  @Override
  public void approvePass(ApprovePassPurchaseReturnVo vo) {

    PurchaseReturn purchaseReturn = getBaseMapper().selectById(vo.getId());
    if (purchaseReturn == null) {
      throw new InputErrorException("采购退货单不存在！");
    }

    if (purchaseReturn.getStatus() != PurchaseReturnStatus.CREATED
        && purchaseReturn.getStatus() != PurchaseReturnStatus.APPROVE_REFUSE) {

      if (purchaseReturn.getStatus() == PurchaseReturnStatus.APPROVE_PASS) {
        throw new DefaultClientException("采购退货单已审核通过，不允许继续执行审核！");
      }

      throw new DefaultClientException("采购退货单无法审核通过！");
    }

    PurchaseConfig purchaseConfig = purchaseConfigService.get();

    if (!purchaseConfig.getPurchaseReturnMultipleRelateReceive()) {
      Wrapper<PurchaseReturn> checkWrapper = Wrappers.lambdaQuery(PurchaseReturn.class)
          .eq(PurchaseReturn::getReceiveSheetId, purchaseReturn.getReceiveSheetId())
          .ne(PurchaseReturn::getId, purchaseReturn.getId());
      if (getBaseMapper().selectCount(checkWrapper) > 0) {
        ReceiveSheet receiveSheet = receiveSheetService.getById(purchaseReturn.getReceiveSheetId());
        throw new DefaultClientException(
            "采购收货单号：" + receiveSheet.getCode() + "，已关联其他采购退货单，不允许关联多个采购退货单！");
      }
    }

    purchaseReturn.setStatus(PurchaseReturnStatus.APPROVE_PASS);

    List<PurchaseReturnStatus> statusList = new ArrayList<>();
    statusList.add(PurchaseReturnStatus.CREATED);
    statusList.add(PurchaseReturnStatus.APPROVE_REFUSE);

    LambdaUpdateWrapper<PurchaseReturn> updateOrderWrapper = Wrappers.lambdaUpdate(
            PurchaseReturn.class)
        .set(PurchaseReturn::getApproveBy, SecurityUtil.getCurrentUser().getId())
        .set(PurchaseReturn::getApproveTime, LocalDateTime.now())
        .eq(PurchaseReturn::getId, purchaseReturn.getId())
        .in(PurchaseReturn::getStatus, statusList);
    if (!StringUtil.isBlank(vo.getDescription())) {
      updateOrderWrapper.set(PurchaseReturn::getDescription, vo.getDescription());
    }
    if (getBaseMapper().update(purchaseReturn, updateOrderWrapper) != 1) {
      throw new DefaultClientException("采购退货单信息已过期，请刷新重试！");
    }

    Wrapper<PurchaseReturnDetail> queryDetailWrapper = Wrappers.lambdaQuery(
            PurchaseReturnDetail.class)
        .eq(PurchaseReturnDetail::getReturnId, purchaseReturn.getId())
        .orderByAsc(PurchaseReturnDetail::getOrderNo);
    List<PurchaseReturnDetail> details = purchaseReturnDetailService.list(queryDetailWrapper);
    for (PurchaseReturnDetail detail : details) {
      SubProductStockVo subproductStockVo = new SubProductStockVo();

      subproductStockVo.setProductId(detail.getProductId());
      subproductStockVo.setScId(purchaseReturn.getScId());
      subproductStockVo.setSupplierId(purchaseReturn.getSupplierId());
      subproductStockVo.setStockNum(detail.getReturnNum());
      subproductStockVo.setTaxAmount(NumberUtil.mul(detail.getTaxPrice(), detail.getReturnNum()));
      subproductStockVo.setTaxRate(detail.getTaxRate());
      subproductStockVo.setBizId(purchaseReturn.getId());
      subproductStockVo.setBizDetailId(detail.getId());
      subproductStockVo.setBizCode(purchaseReturn.getCode());
      subproductStockVo.setBizType(ProductStockBizType.PURCHASE_RETURN.getCode());

      productStockService.subStock(subproductStockVo);
    }

    this.sendApprovePassEvent(purchaseReturn);

    OpLogUtil.setVariable("code", purchaseReturn.getCode());
    OpLogUtil.setExtra(vo);
  }

  @OrderTimeLineLog(type = OrderTimeLineBizType.APPROVE_PASS, orderId = "#vo.ids", name = "审核通过")
  @Transactional
  @Override
  public void batchApprovePass(BatchApprovePassPurchaseReturnVo vo) {

    int orderNo = 1;
    for (String id : vo.getIds()) {
      ApprovePassPurchaseReturnVo approvePassVo = new ApprovePassPurchaseReturnVo();
      approvePassVo.setId(id);

      try {
        IPurchaseReturnService thisService = getThis(this.getClass());
        thisService.approvePass(approvePassVo);
      } catch (ClientException e) {
        throw new DefaultClientException("第" + orderNo + "个采购退货单审核通过失败，失败原因：" + e.getMsg());
      }

      orderNo++;
    }
  }

  @OrderTimeLineLog(type = OrderTimeLineBizType.APPROVE_PASS, orderId = "#_result", name = "直接审核通过")
  @Transactional
  @Override
  public String directApprovePass(CreatePurchaseReturnVo vo) {

    IPurchaseReturnService thisService = getThis(this.getClass());

    String returnId = thisService.create(vo);

    ApprovePassPurchaseReturnVo approvePassVo = new ApprovePassPurchaseReturnVo();
    approvePassVo.setId(returnId);
    approvePassVo.setDescription(vo.getDescription());

    thisService.approvePass(approvePassVo);

    return returnId;
  }

  @OpLog(type = OpLogType.OTHER, name = "审核拒绝采购退货单，单号：{}", params = "#code")
  @OrderTimeLineLog(type = OrderTimeLineBizType.APPROVE_RETURN, orderId = "#vo.id", name = "审核拒绝，拒绝理由：{}", params = "#vo.refuseReason")
  @Transactional
  @Override
  public void approveRefuse(ApproveRefusePurchaseReturnVo vo) {

    PurchaseReturn purchaseReturn = getBaseMapper().selectById(vo.getId());
    if (purchaseReturn == null) {
      throw new InputErrorException("采购退货单不存在！");
    }

    if (purchaseReturn.getStatus() != PurchaseReturnStatus.CREATED) {

      if (purchaseReturn.getStatus() == PurchaseReturnStatus.APPROVE_PASS) {
        throw new DefaultClientException("采购退货单已审核通过，不允许继续执行审核！");
      }

      if (purchaseReturn.getStatus() == PurchaseReturnStatus.APPROVE_REFUSE) {
        throw new DefaultClientException("采购退货单已审核拒绝，不允许继续执行审核！");
      }

      throw new DefaultClientException("采购退货单无法审核拒绝！");
    }

    purchaseReturn.setStatus(PurchaseReturnStatus.APPROVE_REFUSE);

    LambdaUpdateWrapper<PurchaseReturn> updateOrderWrapper = Wrappers.lambdaUpdate(
            PurchaseReturn.class)
        .set(PurchaseReturn::getApproveBy, SecurityUtil.getCurrentUser().getId())
        .set(PurchaseReturn::getApproveTime, LocalDateTime.now())
        .set(PurchaseReturn::getRefuseReason, vo.getRefuseReason())
        .eq(PurchaseReturn::getId, purchaseReturn.getId())
        .eq(PurchaseReturn::getStatus, PurchaseReturnStatus.CREATED);
    if (getBaseMapper().update(purchaseReturn, updateOrderWrapper) != 1) {
      throw new DefaultClientException("采购退货单信息已过期，请刷新重试！");
    }

    OpLogUtil.setVariable("code", purchaseReturn.getCode());
    OpLogUtil.setExtra(vo);
  }

  @OrderTimeLineLog(type = OrderTimeLineBizType.APPROVE_RETURN, orderId = "#vo.ids", name = "审核拒绝，拒绝理由：{}", params = "#vo.refuseReason")
  @Transactional
  @Override
  public void batchApproveRefuse(BatchApproveRefusePurchaseReturnVo vo) {

    int orderNo = 1;
    for (String id : vo.getIds()) {
      ApproveRefusePurchaseReturnVo approveRefuseVo = new ApproveRefusePurchaseReturnVo();
      approveRefuseVo.setId(id);
      approveRefuseVo.setRefuseReason(vo.getRefuseReason());

      try {
        IPurchaseReturnService thisService = getThis(this.getClass());
        thisService.approveRefuse(approveRefuseVo);
      } catch (ClientException e) {
        throw new DefaultClientException("第" + orderNo + "个采购退货单审核拒绝失败，失败原因：" + e.getMsg());
      }

      orderNo++;
    }
  }

  @OpLog(type = OpLogType.OTHER, name = "删除采购退货单，单号：{}", params = "#code")
  @OrderTimeLineLog(orderId = "#id", delete = true)
  @Transactional
  @Override
  public void deleteById(String id) {

    Assert.notBlank(id);
    PurchaseReturn purchaseReturn = getBaseMapper().selectById(id);
    if (purchaseReturn == null) {
      throw new InputErrorException("采购退货单不存在！");
    }

    if (purchaseReturn.getStatus() != PurchaseReturnStatus.CREATED
        && purchaseReturn.getStatus() != PurchaseReturnStatus.APPROVE_REFUSE) {

      if (purchaseReturn.getStatus() == PurchaseReturnStatus.APPROVE_PASS) {
        throw new DefaultClientException("“审核通过”的采购退货单不允许执行删除操作！");
      }

      throw new DefaultClientException("采购退货单无法删除！");
    }

    if (!StringUtil.isBlank(purchaseReturn.getReceiveSheetId())) {
      //查询采购退货单明细
      Wrapper<PurchaseReturnDetail> queryDetailWrapper = Wrappers.lambdaQuery(
              PurchaseReturnDetail.class)
          .eq(PurchaseReturnDetail::getReturnId, purchaseReturn.getId());
      List<PurchaseReturnDetail> details = purchaseReturnDetailService.list(queryDetailWrapper);
      for (PurchaseReturnDetail detail : details) {
        if (!StringUtil.isBlank(detail.getReceiveSheetDetailId())) {
          //恢复已退货数量
          receiveSheetDetailService.subReturnNum(detail.getReceiveSheetDetailId(),
              detail.getReturnNum());
        }
      }
    }

    // 删除退货单明细
    Wrapper<PurchaseReturnDetail> deleteDetailWrapper = Wrappers.lambdaQuery(
            PurchaseReturnDetail.class)
        .eq(PurchaseReturnDetail::getReturnId, purchaseReturn.getId());
    purchaseReturnDetailService.remove(deleteDetailWrapper);

    // 删除退货单
    getBaseMapper().deleteById(id);

    OpLogUtil.setVariable("code", purchaseReturn.getCode());
  }

  @Transactional
  @OrderTimeLineLog(orderId = "#ids", delete = true)
  @Override
  public void deleteByIds(List<String> ids) {

    if (!CollectionUtil.isEmpty(ids)) {
      int orderNo = 1;
      for (String id : ids) {

        try {
          IPurchaseReturnService thisService = getThis(this.getClass());
          thisService.deleteById(id);
        } catch (ClientException e) {
          throw new DefaultClientException("第" + orderNo + "个采购退货单删除失败，失败原因：" + e.getMsg());
        }

        orderNo++;
      }
    }
  }

  @Transactional
  @Override
  public int setUnSettle(String id) {

    Wrapper<PurchaseReturn> updateWrapper = Wrappers.lambdaUpdate(PurchaseReturn.class)
        .set(PurchaseReturn::getSettleStatus, SettleStatus.UN_SETTLE)
        .set(PurchaseReturn::getOriSettleStatus, SettleStatus.PART_SETTLE)
        .eq(PurchaseReturn::getId, id)
        .eq(PurchaseReturn::getSettleStatus, SettleStatus.PART_SETTLE)
        .isNull(PurchaseReturn::getOriSettleStatus);
    int count = getBaseMapper().update(updateWrapper);

    return count;
  }

  @Transactional
  @Override
  public int setPartSettle(String id) {

    return getBaseMapper().setPartSettle(id);
  }

  @Transactional
  @Override
  public int setSettled(String id) {

    return getBaseMapper().setSettled(id);
  }

  @Override
  public List<PurchaseReturn> getApprovedList(String supplierId, LocalDateTime startTime,
      LocalDateTime endTime,
      SettleStatus settleStatus) {

    return getBaseMapper().getApprovedList(supplierId, startTime, endTime, settleStatus);
  }

  private void create(PurchaseReturn purchaseReturn, CreatePurchaseReturnVo vo,
      boolean requireReceive) {

    StoreCenter sc = storeCenterFeignClient.findById(vo.getScId()).getData();
    if (sc == null) {
      throw new InputErrorException("仓库不存在！");
    }

    purchaseReturn.setScId(vo.getScId());

    Supplier supplier = supplierFeignClient.findById(vo.getSupplierId()).getData();
    if (supplier == null) {
      throw new InputErrorException("供应商不存在！");
    }
    purchaseReturn.setSupplierId(vo.getSupplierId());

    if (!StringUtil.isBlank(vo.getPurchaserId())) {
      UserDto purchaser = userService.findById(vo.getPurchaserId());
      if (purchaser == null) {
        throw new InputErrorException("采购员不存在！");
      }

      purchaseReturn.setPurchaserId(vo.getPurchaserId());
    }

    PurchaseConfig purchaseConfig = purchaseConfigService.get();

    GetPaymentDateDto paymentDate = receiveSheetService.getPaymentDate(supplier.getId());

    purchaseReturn.setPaymentDate(
        paymentDate.getAllowModify() ? vo.getPaymentDate() : paymentDate.getPaymentDate());

    if (requireReceive) {

      ReceiveSheet receiveSheet = receiveSheetService.getById(vo.getReceiveSheetId());
      if (receiveSheet == null) {
        throw new DefaultClientException("采购收货单不存在！");
      }

      purchaseReturn.setScId(receiveSheet.getScId());
      purchaseReturn.setSupplierId(receiveSheet.getSupplierId());
      purchaseReturn.setReceiveSheetId(receiveSheet.getId());

      if (!purchaseConfig.getPurchaseReturnMultipleRelateReceive()) {
        Wrapper<PurchaseReturn> checkWrapper = Wrappers.lambdaQuery(PurchaseReturn.class)
            .eq(PurchaseReturn::getReceiveSheetId, receiveSheet.getId())
            .ne(PurchaseReturn::getId, purchaseReturn.getId());
        if (getBaseMapper().selectCount(checkWrapper) > 0) {
          throw new DefaultClientException(
              "采购收货单号：" + receiveSheet.getCode() + "，已关联其他采购退货单，不允许关联多个采购退货单！");
        }
      }
    }

    int returnNum = 0;
    int giftNum = 0;
    BigDecimal totalAmount = BigDecimal.ZERO;
    int orderNo = 1;
    for (ReturnProductVo productVo : vo.getProducts()) {
      if (requireReceive) {
        if (!StringUtil.isBlank(productVo.getReceiveSheetDetailId())) {
          ReceiveSheetDetail detail = receiveSheetDetailService.getById(
              productVo.getReceiveSheetDetailId());
          productVo.setPurchasePrice(detail.getTaxPrice());
        } else {
          productVo.setPurchasePrice(BigDecimal.ZERO);
        }
      }

      boolean isGift = productVo.getPurchasePrice().doubleValue() == 0D;

      if (requireReceive) {
        if (StringUtil.isBlank(productVo.getReceiveSheetDetailId())) {
          if (!isGift) {
            throw new InputErrorException("第" + orderNo + "行商品必须为“赠品”！");
          }
        }
      }

      if (isGift) {
        giftNum += productVo.getReturnNum();
      } else {
        returnNum += productVo.getReturnNum();
      }

      totalAmount = NumberUtil.add(totalAmount,
          NumberUtil.mul(productVo.getPurchasePrice(), productVo.getReturnNum()));

      PurchaseReturnDetail detail = new PurchaseReturnDetail();
      detail.setId(IdUtil.getId());
      detail.setReturnId(purchaseReturn.getId());

      ProductDto product = productFeignClient.findById(productVo.getProductId()).getData();
      if (product == null) {
        throw new InputErrorException("第" + orderNo + "行商品不存在！");
      }

      if (!NumberUtil.isNumberPrecision(productVo.getPurchasePrice(), 2)) {
        throw new InputErrorException("第" + orderNo + "行商品采购价最多允许2位小数！");
      }

      detail.setProductId(productVo.getProductId());
      detail.setReturnNum(productVo.getReturnNum());
      detail.setTaxPrice(productVo.getPurchasePrice());
      detail.setIsGift(isGift);
      detail.setTaxRate(product.getPoly().getTaxRate());
      detail.setDescription(
          StringUtil.isBlank(productVo.getDescription()) ? StringPool.EMPTY_STR
              : productVo.getDescription());
      detail.setOrderNo(orderNo);
      if (requireReceive && !StringUtil.isBlank(productVo.getReceiveSheetDetailId())) {
        detail.setReceiveSheetDetailId(productVo.getReceiveSheetDetailId());
        receiveSheetDetailService.addReturnNum(productVo.getReceiveSheetDetailId(),
            detail.getReturnNum());
      }

      purchaseReturnDetailService.save(detail);
      orderNo++;
    }
    purchaseReturn.setTotalNum(returnNum);
    purchaseReturn.setTotalGiftNum(giftNum);
    purchaseReturn.setTotalAmount(totalAmount);
    purchaseReturn.setDescription(
        StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());
    purchaseReturn.setSettleStatus(this.getInitSettleStatus(supplier));
  }

  /**
   * 根据供应商获取初始结算状态
   *
   * @param supplier
   * @return
   */
  private SettleStatus getInitSettleStatus(Supplier supplier) {

    if (supplier.getManageType() == ManageType.DISTRIBUTION) {
      return SettleStatus.UN_SETTLE;
    } else {
      return SettleStatus.UN_REQUIRE;
    }
  }

  private void sendApprovePassEvent(PurchaseReturn r) {

    ApprovePassOrderEvent event = new ApprovePassOrderEvent();
    event.setId(r.getId());
    event.setTotalAmount(r.getTotalAmount());
    event.setApproveTime(r.getApproveTime());
    event.setOrderType(OrderType.PURCHASE_RETURN);

    mqProducer.sendMessage(MqConstants.QUEUE_ORDER_CHART, event);
  }
}

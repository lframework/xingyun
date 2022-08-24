package com.lframework.xingyun.sc.biz.impl.retail;

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
import com.lframework.xingyun.basedata.facade.MemberFeignClient;
import com.lframework.xingyun.basedata.facade.ProductFeignClient;
import com.lframework.xingyun.basedata.facade.ProductPurchaseFeignClient;
import com.lframework.xingyun.basedata.facade.StoreCenterFeignClient;
import com.lframework.xingyun.basedata.facade.dto.product.info.ProductDto;
import com.lframework.xingyun.basedata.facade.entity.Member;
import com.lframework.xingyun.basedata.facade.entity.ProductPurchase;
import com.lframework.xingyun.basedata.facade.entity.StoreCenter;
import com.lframework.xingyun.chart.facade.constants.MqConstants;
import com.lframework.xingyun.chart.facade.mq.ApprovePassOrderEvent;
import com.lframework.xingyun.chart.facade.mq.ApprovePassOrderEvent.OrderType;
import com.lframework.xingyun.core.annations.OrderTimeLineLog;
import com.lframework.xingyun.core.enums.OrderTimeLineBizType;
import com.lframework.xingyun.core.enums.SettleStatus;
import com.lframework.xingyun.sc.biz.components.code.GenerateCodeTypePool;
import com.lframework.xingyun.sc.biz.mappers.RetailReturnMapper;
import com.lframework.xingyun.sc.biz.service.retail.IRetailConfigService;
import com.lframework.xingyun.sc.biz.service.retail.IRetailOutSheetDetailLotService;
import com.lframework.xingyun.sc.biz.service.retail.IRetailOutSheetDetailService;
import com.lframework.xingyun.sc.biz.service.retail.IRetailOutSheetService;
import com.lframework.xingyun.sc.biz.service.retail.IRetailReturnDetailService;
import com.lframework.xingyun.sc.biz.service.retail.IRetailReturnService;
import com.lframework.xingyun.sc.biz.service.stock.IProductLotService;
import com.lframework.xingyun.sc.biz.service.stock.IProductStockService;
import com.lframework.xingyun.sc.facade.dto.purchase.receive.GetPaymentDateDto;
import com.lframework.xingyun.sc.facade.dto.retail.out.RetailOutSheetDetailLotDto;
import com.lframework.xingyun.sc.facade.dto.retail.returned.RetailReturnFullDto;
import com.lframework.xingyun.sc.facade.entity.ProductLot;
import com.lframework.xingyun.sc.facade.entity.RetailConfig;
import com.lframework.xingyun.sc.facade.entity.RetailOutSheet;
import com.lframework.xingyun.sc.facade.entity.RetailOutSheetDetail;
import com.lframework.xingyun.sc.facade.entity.RetailReturn;
import com.lframework.xingyun.sc.facade.entity.RetailReturnDetail;
import com.lframework.xingyun.sc.facade.enums.ProductStockBizType;
import com.lframework.xingyun.sc.facade.enums.RetailReturnStatus;
import com.lframework.xingyun.sc.facade.vo.retail.returned.ApprovePassRetailReturnVo;
import com.lframework.xingyun.sc.facade.vo.retail.returned.ApproveRefuseRetailReturnVo;
import com.lframework.xingyun.sc.facade.vo.retail.returned.BatchApprovePassRetailReturnVo;
import com.lframework.xingyun.sc.facade.vo.retail.returned.BatchApproveRefuseRetailReturnVo;
import com.lframework.xingyun.sc.facade.vo.retail.returned.CreateRetailReturnVo;
import com.lframework.xingyun.sc.facade.vo.retail.returned.QueryRetailReturnVo;
import com.lframework.xingyun.sc.facade.vo.retail.returned.RetailReturnProductVo;
import com.lframework.xingyun.sc.facade.vo.retail.returned.UpdateRetailReturnVo;
import com.lframework.xingyun.sc.facade.vo.stock.AddProductStockVo;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RetailReturnServiceImpl extends BaseMpServiceImpl<RetailReturnMapper, RetailReturn>
    implements IRetailReturnService {

  @Autowired
  private IRetailReturnDetailService retailReturnDetailService;

  @Autowired
  private IGenerateCodeService generateCodeService;

  @Autowired
  private StoreCenterFeignClient storeCenterFeignClient;

  @Autowired
  private MemberFeignClient memberFeignClient;

  @Autowired
  private IUserService userService;

  @Autowired
  private ProductFeignClient productFeignClient;

  @Autowired
  private IRetailOutSheetService retailOutSheetService;

  @Autowired
  private IRetailConfigService retailConfigService;

  @Autowired
  private IRetailOutSheetDetailService retailOutSheetDetailService;

  @Autowired
  private IRetailOutSheetDetailLotService retailOutSheetDetailLotService;

  @Autowired
  private IProductStockService productStockService;

  @Autowired
  private IProductLotService productLotService;

  @Autowired
  private ProductPurchaseFeignClient productPurchaseFeignClient;

  @Autowired
  private MqProducer mqProducer;

  @Override
  public PageResult<RetailReturn> query(Integer pageIndex, Integer pageSize,
      QueryRetailReturnVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<RetailReturn> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<RetailReturn> query(QueryRetailReturnVo vo) {

    return getBaseMapper().query(vo);
  }

  @Override
  public RetailReturnFullDto getDetail(String id) {

    return getBaseMapper().getDetail(id);
  }

  @OpLog(type = OpLogType.OTHER, name = "创建零售退货单，单号：{}", params = "#code")
  @OrderTimeLineLog(type = OrderTimeLineBizType.CREATE, orderId = "#_result", name = "创建退单")
  @Transactional
  @Override
  public String create(CreateRetailReturnVo vo) {

    RetailReturn retailReturn = new RetailReturn();
    retailReturn.setId(IdUtil.getId());
    retailReturn.setCode(generateCodeService.generate(GenerateCodeTypePool.RETAIL_RETURN));

    RetailConfig retailConfig = retailConfigService.get();

    this.create(retailReturn, vo, retailConfig.getRetailReturnRequireOutStock());

    retailReturn.setStatus(RetailReturnStatus.CREATED);

    getBaseMapper().insert(retailReturn);

    OpLogUtil.setVariable("code", retailReturn.getCode());
    OpLogUtil.setExtra(vo);

    return retailReturn.getId();
  }

  @OpLog(type = OpLogType.OTHER, name = "修改零售退货单，单号：{}", params = "#code")
  @OrderTimeLineLog(type = OrderTimeLineBizType.UPDATE, orderId = "#vo.id", name = "修改退单")
  @Transactional
  @Override
  public void update(UpdateRetailReturnVo vo) {

    RetailReturn retailReturn = getBaseMapper().selectById(vo.getId());
    if (retailReturn == null) {
      throw new InputErrorException("零售退货单不存在！");
    }

    if (retailReturn.getStatus() != RetailReturnStatus.CREATED
        && retailReturn.getStatus() != RetailReturnStatus.APPROVE_REFUSE) {

      if (retailReturn.getStatus() == RetailReturnStatus.APPROVE_PASS) {
        throw new DefaultClientException("零售退货单已审核通过，无法修改！");
      }

      throw new DefaultClientException("零售退货单无法修改！");
    }

    boolean requireOut = !StringUtil.isBlank(retailReturn.getOutSheetId());

    if (requireOut) {
      //查询零售退货单明细
      Wrapper<RetailReturnDetail> queryDetailWrapper = Wrappers.lambdaQuery(
              RetailReturnDetail.class)
          .eq(RetailReturnDetail::getReturnId, retailReturn.getId());
      List<RetailReturnDetail> details = retailReturnDetailService.list(queryDetailWrapper);
      for (RetailReturnDetail detail : details) {
        if (!StringUtil.isBlank(detail.getOutSheetDetailId())) {
          //先恢复已退货数量
          retailOutSheetDetailLotService.subReturnNum(detail.getOutSheetDetailId(),
              detail.getReturnNum());
        }
      }
    }

    // 删除零售退货单明细
    Wrapper<RetailReturnDetail> deleteDetailWrapper = Wrappers.lambdaQuery(RetailReturnDetail.class)
        .eq(RetailReturnDetail::getReturnId, retailReturn.getId());
    retailReturnDetailService.remove(deleteDetailWrapper);

    this.create(retailReturn, vo, requireOut);

    retailReturn.setStatus(RetailReturnStatus.CREATED);

    List<RetailReturnStatus> statusList = new ArrayList<>();
    statusList.add(RetailReturnStatus.CREATED);
    statusList.add(RetailReturnStatus.APPROVE_REFUSE);

    Wrapper<RetailReturn> updateOrderWrapper = Wrappers.lambdaUpdate(RetailReturn.class)
        .set(RetailReturn::getApproveBy, null).set(RetailReturn::getApproveTime, null)
        .set(RetailReturn::getRefuseReason, StringPool.EMPTY_STR)
        .set(RetailReturn::getMemberId, retailReturn.getMemberId())
        .eq(RetailReturn::getId, retailReturn.getId())
        .in(RetailReturn::getStatus, statusList);
    if (getBaseMapper().update(retailReturn, updateOrderWrapper) != 1) {
      throw new DefaultClientException("零售退货单信息已过期，请刷新重试！");
    }

    OpLogUtil.setVariable("code", retailReturn.getCode());
    OpLogUtil.setExtra(vo);
  }

  @OpLog(type = OpLogType.OTHER, name = "审核通过零售退货单，单号：{}", params = "#code")
  @OrderTimeLineLog(type = OrderTimeLineBizType.APPROVE_PASS, orderId = "#vo.id", name = "审核通过")
  @Transactional
  @Override
  public void approvePass(ApprovePassRetailReturnVo vo) {

    RetailReturn retailReturn = getBaseMapper().selectById(vo.getId());
    if (retailReturn == null) {
      throw new InputErrorException("零售退货单不存在！");
    }

    if (retailReturn.getStatus() != RetailReturnStatus.CREATED
        && retailReturn.getStatus() != RetailReturnStatus.APPROVE_REFUSE) {

      if (retailReturn.getStatus() == RetailReturnStatus.APPROVE_PASS) {
        throw new DefaultClientException("零售退货单已审核通过，不允许继续执行审核！");
      }

      throw new DefaultClientException("零售退货单无法审核通过！");
    }

    RetailConfig retailConfig = retailConfigService.get();

    if (!retailConfig.getRetailReturnMultipleRelateOutStock()) {
      Wrapper<RetailReturn> checkWrapper = Wrappers.lambdaQuery(RetailReturn.class)
          .eq(RetailReturn::getOutSheetId, retailReturn.getOutSheetId())
          .ne(RetailReturn::getId, retailReturn.getId());
      if (getBaseMapper().selectCount(checkWrapper) > 0) {
        RetailOutSheet retailOutSheet = retailOutSheetService.getById(retailReturn.getOutSheetId());
        throw new DefaultClientException(
            "零售出库单号：" + retailOutSheet.getCode() + "，已关联其他零售退货单，不允许关联多个零售退货单！");
      }
    }

    retailReturn.setStatus(RetailReturnStatus.APPROVE_PASS);

    List<RetailReturnStatus> statusList = new ArrayList<>();
    statusList.add(RetailReturnStatus.CREATED);
    statusList.add(RetailReturnStatus.APPROVE_REFUSE);

    LambdaUpdateWrapper<RetailReturn> updateOrderWrapper = Wrappers.lambdaUpdate(RetailReturn.class)
        .set(RetailReturn::getApproveBy, SecurityUtil.getCurrentUser().getId())
        .set(RetailReturn::getApproveTime, LocalDateTime.now())
        .eq(RetailReturn::getId, retailReturn.getId())
        .in(RetailReturn::getStatus, statusList);
    if (!StringUtil.isBlank(vo.getDescription())) {
      updateOrderWrapper.set(RetailReturn::getDescription, vo.getDescription());
    }
    if (getBaseMapper().update(retailReturn, updateOrderWrapper) != 1) {
      throw new DefaultClientException("零售退货单信息已过期，请刷新重试！");
    }

    Wrapper<RetailReturnDetail> queryDetailWrapper = Wrappers.lambdaQuery(RetailReturnDetail.class)
        .eq(RetailReturnDetail::getReturnId, retailReturn.getId())
        .orderByAsc(RetailReturnDetail::getOrderNo);
    List<RetailReturnDetail> details = retailReturnDetailService.list(queryDetailWrapper);
    for (RetailReturnDetail detail : details) {
      ProductPurchase productPurchase = productPurchaseFeignClient.getById(detail.getProductId())
          .getData();
      AddProductStockVo addProductStockVo = new AddProductStockVo();
      addProductStockVo.setProductId(detail.getProductId());
      addProductStockVo.setScId(retailReturn.getScId());
      addProductStockVo.setSupplierId(detail.getSupplierId());
      addProductStockVo.setStockNum(detail.getReturnNum());
      addProductStockVo.setDefaultTaxAmount(
          NumberUtil.mul(productPurchase.getPrice(), detail.getReturnNum()));
      addProductStockVo.setTaxRate(detail.getTaxRate());
      addProductStockVo.setBizId(retailReturn.getId());
      addProductStockVo.setBizDetailId(detail.getId());
      addProductStockVo.setBizCode(retailReturn.getCode());
      addProductStockVo.setBizType(ProductStockBizType.RETAIL_RETURN.getCode());

      productStockService.addStock(addProductStockVo);
    }

    this.sendApprovePassEvent(retailReturn);

    OpLogUtil.setVariable("code", retailReturn.getCode());
    OpLogUtil.setExtra(vo);
  }

  @OrderTimeLineLog(type = OrderTimeLineBizType.APPROVE_PASS, orderId = "#vo.ids", name = "审核通过")
  @Transactional
  @Override
  public void batchApprovePass(BatchApprovePassRetailReturnVo vo) {

    int orderNo = 1;
    for (String id : vo.getIds()) {
      ApprovePassRetailReturnVo approvePassVo = new ApprovePassRetailReturnVo();
      approvePassVo.setId(id);

      try {
        IRetailReturnService thisService = getThis(this.getClass());
        thisService.approvePass(approvePassVo);
      } catch (ClientException e) {
        throw new DefaultClientException("第" + orderNo + "个零售退货单审核通过失败，失败原因：" + e.getMsg());
      }

      orderNo++;
    }
  }

  @OrderTimeLineLog(type = OrderTimeLineBizType.APPROVE_PASS, orderId = "#_result", name = "直接审核通过")
  @Transactional
  @Override
  public String directApprovePass(CreateRetailReturnVo vo) {

    IRetailReturnService thisService = getThis(this.getClass());

    String returnId = thisService.create(vo);

    ApprovePassRetailReturnVo approvePassVo = new ApprovePassRetailReturnVo();
    approvePassVo.setId(returnId);
    approvePassVo.setDescription(vo.getDescription());

    thisService.approvePass(approvePassVo);

    return returnId;
  }

  @OpLog(type = OpLogType.OTHER, name = "审核拒绝零售退货单，单号：{}", params = "#code")
  @OrderTimeLineLog(type = OrderTimeLineBizType.APPROVE_RETURN, orderId = "#vo.id", name = "审核拒绝，拒绝理由：{}", params = "#vo.refuseReason")
  @Transactional
  @Override
  public void approveRefuse(ApproveRefuseRetailReturnVo vo) {

    RetailReturn retailReturn = getBaseMapper().selectById(vo.getId());
    if (retailReturn == null) {
      throw new InputErrorException("零售退货单不存在！");
    }

    if (retailReturn.getStatus() != RetailReturnStatus.CREATED) {

      if (retailReturn.getStatus() == RetailReturnStatus.APPROVE_PASS) {
        throw new DefaultClientException("零售退货单已审核通过，不允许继续执行审核！");
      }

      if (retailReturn.getStatus() == RetailReturnStatus.APPROVE_REFUSE) {
        throw new DefaultClientException("零售退货单已审核拒绝，不允许继续执行审核！");
      }

      throw new DefaultClientException("零售退货单无法审核拒绝！");
    }

    retailReturn.setStatus(RetailReturnStatus.APPROVE_REFUSE);

    LambdaUpdateWrapper<RetailReturn> updateOrderWrapper = Wrappers.lambdaUpdate(RetailReturn.class)
        .set(RetailReturn::getApproveBy, SecurityUtil.getCurrentUser().getId())
        .set(RetailReturn::getApproveTime, LocalDateTime.now())
        .set(RetailReturn::getRefuseReason, vo.getRefuseReason())
        .eq(RetailReturn::getId, retailReturn.getId())
        .eq(RetailReturn::getStatus, RetailReturnStatus.CREATED);
    if (getBaseMapper().update(retailReturn, updateOrderWrapper) != 1) {
      throw new DefaultClientException("零售退货单信息已过期，请刷新重试！");
    }

    OpLogUtil.setVariable("code", retailReturn.getCode());
    OpLogUtil.setExtra(vo);
  }

  @OrderTimeLineLog(type = OrderTimeLineBizType.APPROVE_RETURN, orderId = "#vo.ids", name = "审核拒绝，拒绝理由：{}", params = "#vo.refuseReason")
  @Transactional
  @Override
  public void batchApproveRefuse(BatchApproveRefuseRetailReturnVo vo) {

    int orderNo = 1;
    for (String id : vo.getIds()) {
      ApproveRefuseRetailReturnVo approveRefuseVo = new ApproveRefuseRetailReturnVo();
      approveRefuseVo.setId(id);
      approveRefuseVo.setRefuseReason(vo.getRefuseReason());

      try {
        IRetailReturnService thisService = getThis(this.getClass());
        thisService.approveRefuse(approveRefuseVo);
      } catch (ClientException e) {
        throw new DefaultClientException("第" + orderNo + "个零售退货单审核拒绝失败，失败原因：" + e.getMsg());
      }

      orderNo++;
    }
  }

  @OpLog(type = OpLogType.OTHER, name = "删除零售退货单，单号：{}", params = "#code")
  @OrderTimeLineLog(orderId = "#id", delete = true)
  @Transactional
  @Override
  public void deleteById(String id) {

    Assert.notBlank(id);
    RetailReturn retailReturn = getBaseMapper().selectById(id);
    if (retailReturn == null) {
      throw new InputErrorException("零售退货单不存在！");
    }

    if (retailReturn.getStatus() != RetailReturnStatus.CREATED
        && retailReturn.getStatus() != RetailReturnStatus.APPROVE_REFUSE) {

      if (retailReturn.getStatus() == RetailReturnStatus.APPROVE_PASS) {
        throw new DefaultClientException("“审核通过”的零售退货单不允许执行删除操作！");
      }

      throw new DefaultClientException("零售退货单无法删除！");
    }

    if (!StringUtil.isBlank(retailReturn.getOutSheetId())) {
      //查询零售退货单明细
      Wrapper<RetailReturnDetail> queryDetailWrapper = Wrappers.lambdaQuery(
              RetailReturnDetail.class)
          .eq(RetailReturnDetail::getReturnId, retailReturn.getId());
      List<RetailReturnDetail> details = retailReturnDetailService.list(queryDetailWrapper);
      for (RetailReturnDetail detail : details) {
        if (!StringUtil.isBlank(detail.getOutSheetDetailId())) {
          //恢复已退货数量
          retailOutSheetDetailLotService.subReturnNum(detail.getOutSheetDetailId(),
              detail.getReturnNum());
        }
      }
    }

    // 删除退货单明细
    Wrapper<RetailReturnDetail> deleteDetailWrapper = Wrappers.lambdaQuery(RetailReturnDetail.class)
        .eq(RetailReturnDetail::getReturnId, retailReturn.getId());
    retailReturnDetailService.remove(deleteDetailWrapper);

    // 删除退货单
    getBaseMapper().deleteById(id);

    OpLogUtil.setVariable("code", retailReturn.getCode());
  }

  @OrderTimeLineLog(orderId = "#ids", delete = true)
  @Transactional
  @Override
  public void deleteByIds(List<String> ids) {

    if (!CollectionUtil.isEmpty(ids)) {
      int orderNo = 1;
      for (String id : ids) {

        try {
          IRetailReturnService thisService = getThis(this.getClass());
          thisService.deleteById(id);
        } catch (ClientException e) {
          throw new DefaultClientException("第" + orderNo + "个零售退货单删除失败，失败原因：" + e.getMsg());
        }

        orderNo++;
      }
    }
  }

  private void create(RetailReturn retailReturn, CreateRetailReturnVo vo, boolean requireOut) {

    StoreCenter sc = storeCenterFeignClient.findById(vo.getScId()).getData();
    if (sc == null) {
      throw new InputErrorException("仓库不存在！");
    }

    retailReturn.setScId(vo.getScId());

    Member member = null;
    if (!StringUtil.isBlank(vo.getMemberId())) {
      member = memberFeignClient.findById(vo.getMemberId()).getData();
      if (member == null) {
        throw new InputErrorException("客户不存在！");
      }
      retailReturn.setMemberId(vo.getMemberId());
    } else {
      retailReturn.setMemberId(null);
    }

    if (!StringUtil.isBlank(vo.getSalerId())) {
      UserDto saler = userService.findById(vo.getSalerId());
      if (saler == null) {
        throw new InputErrorException("零售员不存在！");
      }

      retailReturn.setSalerId(vo.getSalerId());
    }

    RetailConfig retailConfig = retailConfigService.get();

    GetPaymentDateDto paymentDate = retailOutSheetService.getPaymentDate(
        member == null ? null : member.getId());

    retailReturn.setPaymentDate(
        vo.getAllowModifyPaymentDate() || paymentDate.getAllowModify() ? vo.getPaymentDate()
            : paymentDate.getPaymentDate());

    if (requireOut) {

      RetailOutSheet retailOutSheet = retailOutSheetService.getById(vo.getOutSheetId());
      if (retailOutSheet == null) {
        throw new DefaultClientException("零售出库单不存在！");
      }

      retailReturn.setScId(retailOutSheet.getScId());
      retailReturn.setMemberId(retailOutSheet.getMemberId());
      retailReturn.setOutSheetId(retailOutSheet.getId());

      if (!retailConfig.getRetailReturnMultipleRelateOutStock()) {
        Wrapper<RetailReturn> checkWrapper = Wrappers.lambdaQuery(RetailReturn.class)
            .eq(RetailReturn::getOutSheetId, retailOutSheet.getId())
            .ne(RetailReturn::getId, retailReturn.getId());
        if (getBaseMapper().selectCount(checkWrapper) > 0) {
          throw new DefaultClientException(
              "零售出库单号：" + retailOutSheet.getCode() + "，已关联其他零售退货单，不允许关联多个零售退货单！");
        }
      }
    }

    int returnNum = 0;
    int giftNum = 0;
    BigDecimal totalAmount = BigDecimal.ZERO;
    int orderNo = 1;
    for (RetailReturnProductVo productVo : vo.getProducts()) {
      if (requireOut) {
        if (!StringUtil.isBlank(productVo.getOutSheetDetailId())) {
          RetailOutSheetDetailLotDto detailLot = retailOutSheetDetailLotService.findById(
              productVo.getOutSheetDetailId());
          RetailOutSheetDetail detail = retailOutSheetDetailService.getById(
              detailLot.getDetailId());
          ProductLot lot = productLotService.findById(detailLot.getLotId());
          productVo.setOriPrice(detail.getOriPrice());
          productVo.setTaxPrice(detail.getTaxPrice());
          productVo.setDiscountRate(detail.getDiscountRate());
          productVo.setSupplierId(lot.getSupplierId());
        } else {
          productVo.setTaxPrice(BigDecimal.ZERO);
          productVo.setDiscountRate(BigDecimal.valueOf(100));
        }
      }

      boolean isGift = productVo.getTaxPrice().doubleValue() == 0D;

      if (requireOut) {
        if (StringUtil.isBlank(productVo.getOutSheetDetailId())) {
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
          NumberUtil.mul(productVo.getTaxPrice(), productVo.getReturnNum()));

      RetailReturnDetail detail = new RetailReturnDetail();
      detail.setId(IdUtil.getId());
      detail.setReturnId(retailReturn.getId());

      ProductDto product = productFeignClient.findById(productVo.getProductId()).getData();
      if (product == null) {
        throw new InputErrorException("第" + orderNo + "行商品不存在！");
      }

      if (!NumberUtil.isNumberPrecision(productVo.getTaxPrice(), 2)) {
        throw new InputErrorException("第" + orderNo + "行商品价格最多允许2位小数！");
      }

      detail.setProductId(productVo.getProductId());
      detail.setSupplierId(productVo.getSupplierId());
      detail.setReturnNum(productVo.getReturnNum());
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
      if (requireOut && !StringUtil.isBlank(productVo.getOutSheetDetailId())) {
        detail.setOutSheetDetailId(productVo.getOutSheetDetailId());
        retailOutSheetDetailLotService.addReturnNum(productVo.getOutSheetDetailId(),
            detail.getReturnNum());
      }

      retailReturnDetailService.save(detail);
      orderNo++;
    }
    retailReturn.setTotalNum(returnNum);
    retailReturn.setTotalGiftNum(giftNum);
    retailReturn.setTotalAmount(totalAmount);
    retailReturn.setDescription(
        StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());
    retailReturn.setSettleStatus(this.getInitSettleStatus(member));
  }

  /**
   * 根据客户获取初始结算状态
   *
   * @param member
   * @return
   */
  private SettleStatus getInitSettleStatus(Member member) {

    return SettleStatus.UN_SETTLE;
  }

  private void sendApprovePassEvent(RetailReturn r) {

    ApprovePassOrderEvent event = new ApprovePassOrderEvent();
    event.setId(r.getId());
    event.setTotalAmount(r.getTotalAmount());
    event.setApproveTime(r.getApproveTime());
    event.setOrderType(OrderType.RETAIL_RETURN);

    mqProducer.sendMessage(MqConstants.QUEUE_ORDER_CHART, event);
  }
}

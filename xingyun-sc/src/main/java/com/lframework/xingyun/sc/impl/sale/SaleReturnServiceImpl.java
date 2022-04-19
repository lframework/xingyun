package com.lframework.xingyun.sc.impl.sale;

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
import com.lframework.common.utils.IdUtil;
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
import com.lframework.web.common.security.SecurityUtil;
import com.lframework.xingyun.basedata.dto.customer.CustomerDto;
import com.lframework.xingyun.basedata.dto.product.info.ProductDto;
import com.lframework.xingyun.basedata.dto.product.purchase.ProductPurchaseDto;
import com.lframework.xingyun.basedata.dto.storecenter.StoreCenterDto;
import com.lframework.xingyun.basedata.service.customer.ICustomerService;
import com.lframework.xingyun.basedata.service.product.IProductPurchaseService;
import com.lframework.xingyun.basedata.service.product.IProductService;
import com.lframework.xingyun.basedata.service.storecenter.IStoreCenterService;
import com.lframework.xingyun.core.events.order.impl.ApprovePassSaleReturnEvent;
import com.lframework.xingyun.sc.components.code.GenerateCodeTypePool;
import com.lframework.xingyun.sc.dto.purchase.receive.GetPaymentDateDto;
import com.lframework.xingyun.sc.dto.sale.config.SaleConfigDto;
import com.lframework.xingyun.sc.dto.sale.out.SaleOutSheetDetailDto;
import com.lframework.xingyun.sc.dto.sale.out.SaleOutSheetDetailLotDto;
import com.lframework.xingyun.sc.dto.sale.out.SaleOutSheetDto;
import com.lframework.xingyun.sc.dto.sale.returned.SaleReturnDto;
import com.lframework.xingyun.sc.dto.sale.returned.SaleReturnFullDto;
import com.lframework.xingyun.sc.dto.stock.ProductLotDto;
import com.lframework.xingyun.sc.entity.SaleReturn;
import com.lframework.xingyun.sc.entity.SaleReturnDetail;
import com.lframework.xingyun.sc.enums.ProductStockBizType;
import com.lframework.xingyun.sc.enums.SaleReturnStatus;
import com.lframework.xingyun.sc.enums.SettleStatus;
import com.lframework.xingyun.sc.mappers.SaleReturnMapper;
import com.lframework.xingyun.sc.service.sale.ISaleConfigService;
import com.lframework.xingyun.sc.service.sale.ISaleOutSheetDetailLotService;
import com.lframework.xingyun.sc.service.sale.ISaleOutSheetDetailService;
import com.lframework.xingyun.sc.service.sale.ISaleOutSheetService;
import com.lframework.xingyun.sc.service.sale.ISaleReturnDetailService;
import com.lframework.xingyun.sc.service.sale.ISaleReturnService;
import com.lframework.xingyun.sc.service.stock.IProductLotService;
import com.lframework.xingyun.sc.service.stock.IProductStockService;
import com.lframework.xingyun.sc.vo.sale.returned.ApprovePassSaleReturnVo;
import com.lframework.xingyun.sc.vo.sale.returned.ApproveRefuseSaleReturnVo;
import com.lframework.xingyun.sc.vo.sale.returned.BatchApprovePassSaleReturnVo;
import com.lframework.xingyun.sc.vo.sale.returned.BatchApproveRefuseSaleReturnVo;
import com.lframework.xingyun.sc.vo.sale.returned.CreateSaleReturnVo;
import com.lframework.xingyun.sc.vo.sale.returned.QuerySaleReturnVo;
import com.lframework.xingyun.sc.vo.sale.returned.SaleReturnProductVo;
import com.lframework.xingyun.sc.vo.sale.returned.UpdateSaleReturnVo;
import com.lframework.xingyun.sc.vo.stock.AddProductStockVo;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaleReturnServiceImpl extends
    BaseMpServiceImpl<SaleReturnMapper, SaleReturn> implements
    ISaleReturnService {

  @Autowired
  private ISaleReturnDetailService saleReturnDetailService;

  @Autowired
  private IGenerateCodeService generateCodeService;

  @Autowired
  private IStoreCenterService storeCenterService;

  @Autowired
  private ICustomerService customerService;

  @Autowired
  private IUserService userService;

  @Autowired
  private IProductService productService;

  @Autowired
  private ISaleOutSheetService saleOutSheetService;

  @Autowired
  private ISaleConfigService saleConfigService;

  @Autowired
  private ISaleOutSheetDetailService saleOutSheetDetailService;

  @Autowired
  private ISaleOutSheetDetailLotService saleOutSheetDetailLotService;

  @Autowired
  private IProductStockService productStockService;

  @Autowired
  private IProductLotService productLotService;

  @Autowired
  private IProductPurchaseService productPurchaseService;

  @Override
  public PageResult<SaleReturnDto> query(Integer pageIndex, Integer pageSize,
      QuerySaleReturnVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<SaleReturnDto> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<SaleReturnDto> query(QuerySaleReturnVo vo) {

    return getBaseMapper().query(vo);
  }

  @Override
  public SaleReturnDto getById(String id) {

    return getBaseMapper().getById(id);
  }

  @Override
  public SaleReturnFullDto getDetail(String id) {

    return getBaseMapper().getDetail(id);
  }

  @OpLog(type = OpLogType.OTHER, name = "创建销售退货单，单号：{}", params = "#code")
  @Transactional
  @Override
  public String create(CreateSaleReturnVo vo) {

    SaleReturn saleReturn = new SaleReturn();
    saleReturn.setId(IdUtil.getId());
    saleReturn.setCode(generateCodeService.generate(GenerateCodeTypePool.SALE_RETURN));

    SaleConfigDto saleConfig = saleConfigService.get();

    this.create(saleReturn, vo, saleConfig.getSaleReturnRequireOutStock());

    saleReturn.setStatus(SaleReturnStatus.CREATED);

    getBaseMapper().insert(saleReturn);

    OpLogUtil.setVariable("code", saleReturn.getCode());
    OpLogUtil.setExtra(vo);

    return saleReturn.getId();
  }

  @OpLog(type = OpLogType.OTHER, name = "修改销售退货单，单号：{}", params = "#code")
  @Transactional
  @Override
  public void update(UpdateSaleReturnVo vo) {

    SaleReturn saleReturn = getBaseMapper().selectById(vo.getId());
    if (saleReturn == null) {
      throw new InputErrorException("销售退货单不存在！");
    }

    if (saleReturn.getStatus() != SaleReturnStatus.CREATED
        && saleReturn.getStatus() != SaleReturnStatus.APPROVE_REFUSE) {

      if (saleReturn.getStatus() == SaleReturnStatus.APPROVE_PASS) {
        throw new DefaultClientException("销售退货单已审核通过，无法修改！");
      }

      throw new DefaultClientException("销售退货单无法修改！");
    }

    boolean requireOut = !StringUtil.isBlank(saleReturn.getOutSheetId());

    if (requireOut) {
      //查询销售退货单明细
      Wrapper<SaleReturnDetail> queryDetailWrapper = Wrappers.lambdaQuery(SaleReturnDetail.class)
          .eq(SaleReturnDetail::getReturnId, saleReturn.getId());
      List<SaleReturnDetail> details = saleReturnDetailService.list(queryDetailWrapper);
      for (SaleReturnDetail detail : details) {
        if (!StringUtil.isBlank(detail.getOutSheetDetailId())) {
          //先恢复已退货数量
          saleOutSheetDetailLotService.subReturnNum(detail.getOutSheetDetailId(),
              detail.getReturnNum());
        }
      }
    }

    // 删除销售退货单明细
    Wrapper<SaleReturnDetail> deleteDetailWrapper = Wrappers.lambdaQuery(SaleReturnDetail.class)
        .eq(SaleReturnDetail::getReturnId, saleReturn.getId());
    saleReturnDetailService.remove(deleteDetailWrapper);

    this.create(saleReturn, vo, requireOut);

    saleReturn.setStatus(SaleReturnStatus.CREATED);

    List<SaleReturnStatus> statusList = new ArrayList<>();
    statusList.add(SaleReturnStatus.CREATED);
    statusList.add(SaleReturnStatus.APPROVE_REFUSE);

    Wrapper<SaleReturn> updateOrderWrapper = Wrappers.lambdaUpdate(SaleReturn.class)
        .set(SaleReturn::getApproveBy, null).set(SaleReturn::getApproveTime, null)
        .set(SaleReturn::getRefuseReason, StringPool.EMPTY_STR)
        .eq(SaleReturn::getId, saleReturn.getId()).in(SaleReturn::getStatus, statusList);
    if (getBaseMapper().update(saleReturn, updateOrderWrapper) != 1) {
      throw new DefaultClientException("销售退货单信息已过期，请刷新重试！");
    }

    OpLogUtil.setVariable("code", saleReturn.getCode());
    OpLogUtil.setExtra(vo);
  }

  @OpLog(type = OpLogType.OTHER, name = "审核通过销售退货单，单号：{}", params = "#code")
  @Transactional
  @Override
  public void approvePass(ApprovePassSaleReturnVo vo) {

    SaleReturn saleReturn = getBaseMapper().selectById(vo.getId());
    if (saleReturn == null) {
      throw new InputErrorException("销售退货单不存在！");
    }

    if (saleReturn.getStatus() != SaleReturnStatus.CREATED
        && saleReturn.getStatus() != SaleReturnStatus.APPROVE_REFUSE) {

      if (saleReturn.getStatus() == SaleReturnStatus.APPROVE_PASS) {
        throw new DefaultClientException("销售退货单已审核通过，不允许继续执行审核！");
      }

      throw new DefaultClientException("销售退货单无法审核通过！");
    }

    SaleConfigDto saleConfig = saleConfigService.get();

    if (!saleConfig.getSaleReturnMultipleRelateOutStock()) {
      Wrapper<SaleReturn> checkWrapper = Wrappers.lambdaQuery(SaleReturn.class)
          .eq(SaleReturn::getOutSheetId, saleReturn.getOutSheetId())
          .ne(SaleReturn::getId, saleReturn.getId());
      if (getBaseMapper().selectCount(checkWrapper) > 0) {
        SaleOutSheetDto saleOutSheet = saleOutSheetService.getById(saleReturn.getOutSheetId());
        throw new DefaultClientException(
            "销售出库单号：" + saleOutSheet.getCode() + "，已关联其他销售退货单，不允许关联多个销售退货单！");
      }
    }

    saleReturn.setStatus(SaleReturnStatus.APPROVE_PASS);

    List<SaleReturnStatus> statusList = new ArrayList<>();
    statusList.add(SaleReturnStatus.CREATED);
    statusList.add(SaleReturnStatus.APPROVE_REFUSE);

    LambdaUpdateWrapper<SaleReturn> updateOrderWrapper = Wrappers.lambdaUpdate(SaleReturn.class)
        .set(SaleReturn::getApproveBy, SecurityUtil.getCurrentUser().getId())
        .set(SaleReturn::getApproveTime, LocalDateTime.now())
        .eq(SaleReturn::getId, saleReturn.getId()).in(SaleReturn::getStatus, statusList);
    if (!StringUtil.isBlank(vo.getDescription())) {
      updateOrderWrapper.set(SaleReturn::getDescription, vo.getDescription());
    }
    if (getBaseMapper().update(saleReturn, updateOrderWrapper) != 1) {
      throw new DefaultClientException("销售退货单信息已过期，请刷新重试！");
    }

    Wrapper<SaleReturnDetail> queryDetailWrapper = Wrappers.lambdaQuery(SaleReturnDetail.class)
        .eq(SaleReturnDetail::getReturnId, saleReturn.getId())
        .orderByAsc(SaleReturnDetail::getOrderNo);
    List<SaleReturnDetail> details = saleReturnDetailService.list(queryDetailWrapper);
    for (SaleReturnDetail detail : details) {
      ProductPurchaseDto productPurchase = productPurchaseService.getById(detail.getProductId());
      AddProductStockVo addProductStockVo = new AddProductStockVo();
      addProductStockVo.setProductId(detail.getProductId());
      addProductStockVo.setScId(saleReturn.getScId());
      addProductStockVo.setSupplierId(detail.getSupplierId());
      addProductStockVo.setStockNum(detail.getReturnNum());
      addProductStockVo.setDefaultTaxAmount(
          NumberUtil.mul(productPurchase.getPrice(), detail.getReturnNum()));
      addProductStockVo.setTaxRate(detail.getTaxRate());
      addProductStockVo.setBizId(saleReturn.getId());
      addProductStockVo.setBizDetailId(detail.getId());
      addProductStockVo.setBizCode(saleReturn.getCode());
      addProductStockVo.setBizType(ProductStockBizType.SALE_RETURN.getCode());

      productStockService.addStock(addProductStockVo);
    }

    this.sendApprovePassEvent(saleReturn);

    OpLogUtil.setVariable("code", saleReturn.getCode());
    OpLogUtil.setExtra(vo);
  }

  @Transactional
  @Override
  public void batchApprovePass(BatchApprovePassSaleReturnVo vo) {

    int orderNo = 1;
    for (String id : vo.getIds()) {
      ApprovePassSaleReturnVo approvePassVo = new ApprovePassSaleReturnVo();
      approvePassVo.setId(id);

      try {
        ISaleReturnService thisService = getThis(this.getClass());
        thisService.approvePass(approvePassVo);
      } catch (ClientException e) {
        throw new DefaultClientException("第" + orderNo + "个销售退货单审核通过失败，失败原因：" + e.getMsg());
      }

      orderNo++;
    }
  }

  @Transactional
  @Override
  public void directApprovePass(CreateSaleReturnVo vo) {

    ISaleReturnService thisService = getThis(this.getClass());

    String returnId = thisService.create(vo);

    ApprovePassSaleReturnVo approvePassVo = new ApprovePassSaleReturnVo();
    approvePassVo.setId(returnId);
    approvePassVo.setDescription(vo.getDescription());

    thisService.approvePass(approvePassVo);
  }

  @OpLog(type = OpLogType.OTHER, name = "审核拒绝销售退货单，单号：{}", params = "#code")
  @Transactional
  @Override
  public void approveRefuse(ApproveRefuseSaleReturnVo vo) {

    SaleReturn saleReturn = getBaseMapper().selectById(vo.getId());
    if (saleReturn == null) {
      throw new InputErrorException("销售退货单不存在！");
    }

    if (saleReturn.getStatus() != SaleReturnStatus.CREATED) {

      if (saleReturn.getStatus() == SaleReturnStatus.APPROVE_PASS) {
        throw new DefaultClientException("销售退货单已审核通过，不允许继续执行审核！");
      }

      if (saleReturn.getStatus() == SaleReturnStatus.APPROVE_REFUSE) {
        throw new DefaultClientException("销售退货单已审核拒绝，不允许继续执行审核！");
      }

      throw new DefaultClientException("销售退货单无法审核拒绝！");
    }

    saleReturn.setStatus(SaleReturnStatus.APPROVE_REFUSE);

    LambdaUpdateWrapper<SaleReturn> updateOrderWrapper = Wrappers.lambdaUpdate(SaleReturn.class)
        .set(SaleReturn::getApproveBy, SecurityUtil.getCurrentUser().getId())
        .set(SaleReturn::getApproveTime, LocalDateTime.now())
        .set(SaleReturn::getRefuseReason, vo.getRefuseReason())
        .eq(SaleReturn::getId, saleReturn.getId())
        .eq(SaleReturn::getStatus, SaleReturnStatus.CREATED);
    if (getBaseMapper().update(saleReturn, updateOrderWrapper) != 1) {
      throw new DefaultClientException("销售退货单信息已过期，请刷新重试！");
    }

    OpLogUtil.setVariable("code", saleReturn.getCode());
    OpLogUtil.setExtra(vo);
  }

  @Transactional
  @Override
  public void batchApproveRefuse(BatchApproveRefuseSaleReturnVo vo) {

    int orderNo = 1;
    for (String id : vo.getIds()) {
      ApproveRefuseSaleReturnVo approveRefuseVo = new ApproveRefuseSaleReturnVo();
      approveRefuseVo.setId(id);
      approveRefuseVo.setRefuseReason(vo.getRefuseReason());

      try {
        ISaleReturnService thisService = getThis(this.getClass());
        thisService.approveRefuse(approveRefuseVo);
      } catch (ClientException e) {
        throw new DefaultClientException("第" + orderNo + "个销售退货单审核拒绝失败，失败原因：" + e.getMsg());
      }

      orderNo++;
    }
  }

  @OpLog(type = OpLogType.OTHER, name = "删除销售退货单，单号：{}", params = "#code")
  @Transactional
  @Override
  public void deleteById(String id) {

    Assert.notBlank(id);
    SaleReturn saleReturn = getBaseMapper().selectById(id);
    if (saleReturn == null) {
      throw new InputErrorException("销售退货单不存在！");
    }

    if (saleReturn.getStatus() != SaleReturnStatus.CREATED
        && saleReturn.getStatus() != SaleReturnStatus.APPROVE_REFUSE) {

      if (saleReturn.getStatus() == SaleReturnStatus.APPROVE_PASS) {
        throw new DefaultClientException("“审核通过”的销售退货单不允许执行删除操作！");
      }

      throw new DefaultClientException("销售退货单无法删除！");
    }

    if (!StringUtil.isBlank(saleReturn.getOutSheetId())) {
      //查询销售退货单明细
      Wrapper<SaleReturnDetail> queryDetailWrapper = Wrappers.lambdaQuery(SaleReturnDetail.class)
          .eq(SaleReturnDetail::getReturnId, saleReturn.getId());
      List<SaleReturnDetail> details = saleReturnDetailService.list(queryDetailWrapper);
      for (SaleReturnDetail detail : details) {
        if (!StringUtil.isBlank(detail.getOutSheetDetailId())) {
          //恢复已退货数量
          saleOutSheetDetailLotService.subReturnNum(detail.getOutSheetDetailId(),
              detail.getReturnNum());
        }
      }
    }

    // 删除退货单明细
    Wrapper<SaleReturnDetail> deleteDetailWrapper = Wrappers.lambdaQuery(SaleReturnDetail.class)
        .eq(SaleReturnDetail::getReturnId, saleReturn.getId());
    saleReturnDetailService.remove(deleteDetailWrapper);

    // 删除退货单
    getBaseMapper().deleteById(id);

    OpLogUtil.setVariable("code", saleReturn.getCode());
  }

  @Transactional
  @Override
  public void deleteByIds(List<String> ids) {

    if (!CollectionUtil.isEmpty(ids)) {
      int orderNo = 1;
      for (String id : ids) {

        try {
          ISaleReturnService thisService = getThis(this.getClass());
          thisService.deleteById(id);
        } catch (ClientException e) {
          throw new DefaultClientException("第" + orderNo + "个销售退货单删除失败，失败原因：" + e.getMsg());
        }

        orderNo++;
      }
    }
  }

  private void create(SaleReturn saleReturn, CreateSaleReturnVo vo, boolean requireOut) {

    StoreCenterDto sc = storeCenterService.getById(vo.getScId());
    if (sc == null) {
      throw new InputErrorException("仓库不存在！");
    }

    saleReturn.setScId(vo.getScId());

    CustomerDto customer = customerService.getById(vo.getCustomerId());
    if (customer == null) {
      throw new InputErrorException("客户不存在！");
    }
    saleReturn.setCustomerId(vo.getCustomerId());

    if (!StringUtil.isBlank(vo.getSalerId())) {
      UserDto saler = userService.getById(vo.getSalerId());
      if (saler == null) {
        throw new InputErrorException("销售员不存在！");
      }

      saleReturn.setSalerId(vo.getSalerId());
    }

    SaleConfigDto saleConfig = saleConfigService.get();

    GetPaymentDateDto paymentDate = saleOutSheetService.getPaymentDate(customer.getId());

    saleReturn.setPaymentDate(
        paymentDate.getAllowModify() ? vo.getPaymentDate() : paymentDate.getPaymentDate());

    if (requireOut) {

      SaleOutSheetDto saleOutSheet = saleOutSheetService.getById(vo.getOutSheetId());
      if (saleOutSheet == null) {
        throw new DefaultClientException("销售出库单不存在！");
      }

      saleReturn.setScId(saleOutSheet.getScId());
      saleReturn.setCustomerId(saleOutSheet.getCustomerId());
      saleReturn.setOutSheetId(saleOutSheet.getId());

      if (!saleConfig.getSaleReturnMultipleRelateOutStock()) {
        Wrapper<SaleReturn> checkWrapper = Wrappers.lambdaQuery(SaleReturn.class)
            .eq(SaleReturn::getOutSheetId, saleOutSheet.getId())
            .ne(SaleReturn::getId, saleReturn.getId());
        if (getBaseMapper().selectCount(checkWrapper) > 0) {
          throw new DefaultClientException(
              "销售出库单号：" + saleOutSheet.getCode() + "，已关联其他销售退货单，不允许关联多个销售退货单！");
        }
      }
    }

    int returnNum = 0;
    int giftNum = 0;
    BigDecimal totalAmount = BigDecimal.ZERO;
    int orderNo = 1;
    for (SaleReturnProductVo productVo : vo.getProducts()) {
      if (requireOut) {
        if (!StringUtil.isBlank(productVo.getOutSheetDetailId())) {
          SaleOutSheetDetailLotDto detailLot = saleOutSheetDetailLotService.getById(
              productVo.getOutSheetDetailId());
          SaleOutSheetDetailDto detail = saleOutSheetDetailService.getById(detailLot.getDetailId());
          ProductLotDto lot = productLotService.getById(detailLot.getLotId());
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

      SaleReturnDetail detail = new SaleReturnDetail();
      detail.setId(IdUtil.getId());
      detail.setReturnId(saleReturn.getId());

      ProductDto product = productService.getById(productVo.getProductId());
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
      detail.setDescription(StringUtil.isBlank(productVo.getDescription()) ? StringPool.EMPTY_STR
          : productVo.getDescription());
      detail.setOrderNo(orderNo);
      detail.setSettleStatus(this.getInitSettleStatus(customer));
      if (requireOut && !StringUtil.isBlank(productVo.getOutSheetDetailId())) {
        detail.setOutSheetDetailId(productVo.getOutSheetDetailId());
        saleOutSheetDetailLotService.addReturnNum(productVo.getOutSheetDetailId(),
            detail.getReturnNum());
      }

      saleReturnDetailService.save(detail);
      orderNo++;
    }
    saleReturn.setTotalNum(returnNum);
    saleReturn.setTotalGiftNum(giftNum);
    saleReturn.setTotalAmount(totalAmount);
    saleReturn.setDescription(
        StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());
    saleReturn.setSettleStatus(this.getInitSettleStatus(customer));
  }

  /**
   * 根据客户获取初始结算状态
   *
   * @param customer
   * @return
   */
  private SettleStatus getInitSettleStatus(CustomerDto customer) {

    return SettleStatus.UN_SETTLE;
  }

  private void sendApprovePassEvent(SaleReturn r) {

    ApprovePassSaleReturnEvent event = new ApprovePassSaleReturnEvent(this);
    event.setId(r.getId());
    event.setTotalAmount(r.getTotalAmount());
    event.setApproveTime(r.getApproveTime());

    ApplicationUtil.publishEvent(event);
  }
}

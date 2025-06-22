package com.lframework.xingyun.sc.impl.sale;

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
import com.lframework.starter.web.core.utils.OpLogUtil;
import com.lframework.xingyun.basedata.entity.Customer;
import com.lframework.xingyun.basedata.entity.Product;
import com.lframework.xingyun.basedata.entity.ProductBundle;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.enums.ProductType;
import com.lframework.xingyun.basedata.service.customer.CustomerService;
import com.lframework.xingyun.basedata.service.product.ProductBundleService;
import com.lframework.xingyun.basedata.service.product.ProductService;
import com.lframework.xingyun.basedata.service.storecenter.StoreCenterService;
import com.lframework.xingyun.core.utils.SplitNumberUtil;
import com.lframework.xingyun.sc.components.code.GenerateCodeTypePool;
import com.lframework.xingyun.sc.dto.sale.SaleOrderFullDto;
import com.lframework.xingyun.sc.dto.sale.SaleOrderWithOutDto;
import com.lframework.xingyun.sc.dto.sale.SaleProductDto;
import com.lframework.xingyun.sc.entity.OrderPayType;
import com.lframework.xingyun.sc.entity.SaleConfig;
import com.lframework.xingyun.sc.entity.SaleOrder;
import com.lframework.xingyun.sc.entity.SaleOrderDetail;
import com.lframework.xingyun.sc.entity.SaleOrderDetailBundle;
import com.lframework.xingyun.sc.enums.SaleOpLogType;
import com.lframework.xingyun.sc.enums.SaleOrderStatus;
import com.lframework.xingyun.sc.events.order.impl.ApprovePassSaleOrderEvent;
import com.lframework.xingyun.sc.mappers.SaleOrderMapper;
import com.lframework.xingyun.sc.service.paytype.OrderPayTypeService;
import com.lframework.xingyun.sc.service.sale.SaleConfigService;
import com.lframework.xingyun.sc.service.sale.SaleOrderDetailBundleService;
import com.lframework.xingyun.sc.service.sale.SaleOrderDetailService;
import com.lframework.xingyun.sc.service.sale.SaleOrderService;
import com.lframework.xingyun.sc.vo.sale.ApprovePassSaleOrderVo;
import com.lframework.xingyun.sc.vo.sale.ApproveRefuseSaleOrderVo;
import com.lframework.xingyun.sc.vo.sale.CreateSaleOrderVo;
import com.lframework.xingyun.sc.vo.sale.QuerySaleOrderVo;
import com.lframework.xingyun.sc.vo.sale.QuerySaleOrderWithOutVo;
import com.lframework.xingyun.sc.vo.sale.QuerySaleProductVo;
import com.lframework.xingyun.sc.vo.sale.SaleOrderSelectorVo;
import com.lframework.xingyun.sc.vo.sale.SaleProductVo;
import com.lframework.xingyun.sc.vo.sale.UpdateSaleOrderVo;
import java.math.BigDecimal;
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
public class SaleOrderServiceImpl extends BaseMpServiceImpl<SaleOrderMapper, SaleOrder> implements
    SaleOrderService {

  @Autowired
  private SaleOrderDetailService saleOrderDetailService;

  @Autowired
  private SaleOrderDetailBundleService saleOrderDetailBundleService;

  @Autowired
  private ProductBundleService productBundleService;

  @Autowired
  private GenerateCodeService generateCodeService;

  @Autowired
  private StoreCenterService storeCenterService;

  @Autowired
  private CustomerService customerService;

  @Autowired
  private SysUserService userService;

  @Autowired
  private ProductService productService;

  @Autowired
  private SaleConfigService saleConfigService;

  @Autowired
  private OrderPayTypeService orderPayTypeService;

  @Override
  public PageResult<SaleOrder> query(Integer pageIndex, Integer pageSize, QuerySaleOrderVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<SaleOrder> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<SaleOrder> query(QuerySaleOrderVo vo) {

    return getBaseMapper().query(vo);
  }

  @Override
  public PageResult<SaleOrder> selector(Integer pageIndex, Integer pageSize,
      SaleOrderSelectorVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<SaleOrder> datas = getBaseMapper().selector(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public SaleOrderFullDto getDetail(String id) {

    SaleOrderFullDto order = getBaseMapper().getDetail(id);
    if (order == null) {
      throw new InputErrorException("订单不存在！");
    }

    return order;
  }

  @Override
  public SaleOrderWithOutDto getWithOut(String id) {

    SaleConfig saleConfig = saleConfigService.get();

    SaleOrderWithOutDto order = getBaseMapper().getWithOut(id, saleConfig.getOutStockRequireSale());
    if (order == null) {
      throw new InputErrorException("订单不存在！");
    }
    return order;
  }

  @Override
  public PageResult<SaleOrder> queryWithOut(Integer pageIndex, Integer pageSize,
      QuerySaleOrderWithOutVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    SaleConfig saleConfig = saleConfigService.get();

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<SaleOrder> datas = getBaseMapper().queryWithOut(vo,
        saleConfig.getOutStockMultipleRelateSale());

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @OpLog(type = SaleOpLogType.class, name = "创建销售订单，单号：{}", params = "#code")
  @OrderTimeLineLog(type = CreateOrderTimeLineBizType.class, orderId = "#_result", name = "创建订单")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(CreateSaleOrderVo vo) {

    SaleOrder order = new SaleOrder();
    order.setId(IdUtil.getId());
    order.setCode(generateCodeService.generate(GenerateCodeTypePool.SALE_ORDER));

    this.create(order, vo);

    order.setStatus(SaleOrderStatus.CREATED);

    getBaseMapper().insert(order);

    OpLogUtil.setVariable("code", order.getCode());
    OpLogUtil.setExtra(vo);

    return order.getId();
  }

  @OpLog(type = SaleOpLogType.class, name = "修改销售订单，单号：{}", params = "#code")
  @OrderTimeLineLog(type = UpdateOrderTimeLineBizType.class, orderId = "#vo.id", name = "修改订单")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateSaleOrderVo vo) {

    SaleOrder order = getBaseMapper().selectById(vo.getId());
    if (order == null) {
      throw new InputErrorException("订单不存在！");
    }

    if (order.getStatus() != SaleOrderStatus.CREATED
        && order.getStatus() != SaleOrderStatus.APPROVE_REFUSE) {

      if (order.getStatus() == SaleOrderStatus.APPROVE_PASS) {
        throw new DefaultClientException("订单已审核通过，无法修改！");
      }

      throw new DefaultClientException("订单无法修改！");
    }

    // 删除订单明细
    Wrapper<SaleOrderDetail> deleteDetailWrapper = Wrappers.lambdaQuery(SaleOrderDetail.class)
        .eq(SaleOrderDetail::getOrderId, order.getId());
    saleOrderDetailService.remove(deleteDetailWrapper);

    // 删除组合商品信息
    Wrapper<SaleOrderDetailBundle> deleteDetailBundleWrapper = Wrappers.lambdaQuery(
        SaleOrderDetailBundle.class).eq(SaleOrderDetailBundle::getOrderId, order.getId());
    saleOrderDetailBundleService.remove(deleteDetailBundleWrapper);

    this.create(order, vo);

    order.setStatus(SaleOrderStatus.CREATED);

    List<SaleOrderStatus> statusList = new ArrayList<>();
    statusList.add(SaleOrderStatus.CREATED);
    statusList.add(SaleOrderStatus.APPROVE_REFUSE);

    Wrapper<SaleOrder> updateOrderWrapper = Wrappers.lambdaUpdate(SaleOrder.class)
        .set(SaleOrder::getApproveBy, null).set(SaleOrder::getApproveTime, null)
        .set(SaleOrder::getRefuseReason, StringPool.EMPTY_STR).eq(SaleOrder::getId, order.getId())
        .in(SaleOrder::getStatus, statusList);
    if (getBaseMapper().updateAllColumn(order, updateOrderWrapper) != 1) {
      throw new DefaultClientException("订单信息已过期，请刷新重试！");
    }

    OpLogUtil.setVariable("code", order.getCode());
    OpLogUtil.setExtra(vo);
  }

  @OpLog(type = SaleOpLogType.class, name = "审核通过销售订单，单号：{}", params = "#code")
  @OrderTimeLineLog(type = ApprovePassOrderTimeLineBizType.class, orderId = "#vo.id", name = "审核通过")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void approvePass(ApprovePassSaleOrderVo vo) {

    SaleOrder order = getBaseMapper().selectById(vo.getId());
    if (order == null) {
      throw new InputErrorException("订单不存在！");
    }

    if (order.getStatus() != SaleOrderStatus.CREATED
        && order.getStatus() != SaleOrderStatus.APPROVE_REFUSE) {

      if (order.getStatus() == SaleOrderStatus.APPROVE_PASS) {
        throw new DefaultClientException("订单已审核通过，不允许继续执行审核！");
      }

      throw new DefaultClientException("订单无法审核通过！");
    }

    order.setStatus(SaleOrderStatus.APPROVE_PASS);

    List<SaleOrderStatus> statusList = new ArrayList<>();
    statusList.add(SaleOrderStatus.CREATED);
    statusList.add(SaleOrderStatus.APPROVE_REFUSE);

    LambdaUpdateWrapper<SaleOrder> updateOrderWrapper = Wrappers.lambdaUpdate(SaleOrder.class)
        .set(SaleOrder::getApproveBy, SecurityUtil.getCurrentUser().getId())
        .set(SaleOrder::getApproveTime, LocalDateTime.now()).eq(SaleOrder::getId, order.getId())
        .in(SaleOrder::getStatus, statusList);
    if (!StringUtil.isBlank(vo.getDescription())) {
      updateOrderWrapper.set(SaleOrder::getDescription, vo.getDescription());
    }
    if (getBaseMapper().updateAllColumn(order, updateOrderWrapper) != 1) {
      throw new DefaultClientException("订单信息已过期，请刷新重试！");
    }

    if (NumberUtil.gt(order.getTotalAmount(), BigDecimal.ZERO)) {
      List<OrderPayType> orderPayTypes = orderPayTypeService.findByOrderId(order.getId());
      if (CollectionUtil.isEmpty(orderPayTypes)) {
        throw new DefaultClientException("单据没有约定支付，请检查！");
      }
    }

    Wrapper<SaleOrderDetail> queryDetailWrapper = Wrappers.lambdaQuery(SaleOrderDetail.class)
        .eq(SaleOrderDetail::getOrderId, order.getId())
        .orderByAsc(SaleOrderDetail::getOrderNo);
    List<SaleOrderDetail> details = saleOrderDetailService.list(queryDetailWrapper);

    int totalNum = 0;
    int giftNum = 0;
    BigDecimal totalAmount = BigDecimal.ZERO;

    for (SaleOrderDetail detail : details) {
      boolean isGift = detail.getIsGift();
      totalAmount = NumberUtil.add(totalAmount,
          NumberUtil.mul(detail.getTaxPrice(), detail.getOrderNum()));

      Product product = productService.findById(detail.getProductId());
      if (product.getProductType() == ProductType.NORMAL) {
        if (isGift) {
          giftNum += detail.getOrderNum();
        } else {
          totalNum += detail.getOrderNum();
        }
      } else {
        Wrapper<SaleOrderDetailBundle> queryBundleWrapper = Wrappers.lambdaQuery(
                SaleOrderDetailBundle.class).eq(SaleOrderDetailBundle::getOrderId, order.getId())
            .eq(SaleOrderDetailBundle::getDetailId, detail.getId());
        List<SaleOrderDetailBundle> saleOrderDetailBundles = saleOrderDetailBundleService.list(
            queryBundleWrapper);
        Assert.notEmpty(saleOrderDetailBundles);

        for (SaleOrderDetailBundle saleOrderDetailBundle : saleOrderDetailBundles) {
          SaleOrderDetail newDetail = new SaleOrderDetail();
          newDetail.setId(IdUtil.getId());
          newDetail.setOrderId(order.getId());
          newDetail.setProductId(saleOrderDetailBundle.getProductId());
          newDetail.setOrderNum(saleOrderDetailBundle.getProductOrderNum());
          newDetail.setOriPrice(saleOrderDetailBundle.getProductOriPrice());
          newDetail.setTaxPrice(saleOrderDetailBundle.getProductTaxPrice());
          newDetail.setDiscountRate(detail.getDiscountRate());
          newDetail.setIsGift(detail.getIsGift());
          newDetail.setTaxRate(saleOrderDetailBundle.getProductTaxRate());
          newDetail.setDescription(detail.getDescription());
          newDetail.setOrderNo(detail.getOrderNo());
          newDetail.setOriBundleDetailId(detail.getId());

          saleOrderDetailService.save(newDetail);
          saleOrderDetailService.removeById(detail.getId());

          saleOrderDetailBundle.setProductDetailId(newDetail.getId());
          saleOrderDetailBundleService.updateById(saleOrderDetailBundle);

          if (isGift) {
            giftNum += newDetail.getOrderNum();
          } else {
            totalNum += newDetail.getOrderNum();
          }
        }
      }
    }

    // 这里需要重新统计明细信息，因为明细发生变动了
    Wrapper<SaleOrder> updateWrapper = Wrappers.lambdaUpdate(SaleOrder.class)
        .set(SaleOrder::getTotalNum, totalNum).set(SaleOrder::getTotalGiftNum, giftNum)
        .set(SaleOrder::getTotalAmount, totalAmount).eq(SaleOrder::getId, order.getId());
    this.update(updateWrapper);

    OpLogUtil.setVariable("code", order.getCode());
    OpLogUtil.setExtra(vo);

    this.sendApprovePassEvent(order);
  }

  @OrderTimeLineLog(type = ApprovePassOrderTimeLineBizType.class, orderId = "#_result", name = "直接审核通过")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public String directApprovePass(CreateSaleOrderVo vo) {

    SaleOrderService thisService = getThis(this.getClass());

    String orderId = thisService.create(vo);

    ApprovePassSaleOrderVo approvePassSaleOrderVo = new ApprovePassSaleOrderVo();
    approvePassSaleOrderVo.setId(orderId);
    approvePassSaleOrderVo.setDescription(vo.getDescription());

    thisService.approvePass(approvePassSaleOrderVo);

    return orderId;
  }

  @OpLog(type = SaleOpLogType.class, name = "审核拒绝销售订单，单号：{}", params = "#code")
  @OrderTimeLineLog(type = ApproveReturnOrderTimeLineBizType.class, orderId = "#vo.id", name = "审核拒绝，拒绝理由：{}", params = "#vo.refuseReason")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void approveRefuse(ApproveRefuseSaleOrderVo vo) {

    SaleOrder order = getBaseMapper().selectById(vo.getId());
    if (order == null) {
      throw new InputErrorException("订单不存在！");
    }

    if (order.getStatus() != SaleOrderStatus.CREATED) {

      if (order.getStatus() == SaleOrderStatus.APPROVE_PASS) {
        throw new DefaultClientException("订单已审核通过，不允许继续执行审核！");
      }

      if (order.getStatus() == SaleOrderStatus.APPROVE_REFUSE) {
        throw new DefaultClientException("订单已审核拒绝，不允许继续执行审核！");
      }

      throw new DefaultClientException("订单无法审核拒绝！");
    }

    order.setStatus(SaleOrderStatus.APPROVE_REFUSE);

    LambdaUpdateWrapper<SaleOrder> updateOrderWrapper = Wrappers.lambdaUpdate(SaleOrder.class)
        .set(SaleOrder::getApproveBy, SecurityUtil.getCurrentUser().getId())
        .set(SaleOrder::getApproveTime, LocalDateTime.now())
        .set(SaleOrder::getRefuseReason, vo.getRefuseReason()).eq(SaleOrder::getId, order.getId())
        .eq(SaleOrder::getStatus, SaleOrderStatus.CREATED);
    if (getBaseMapper().updateAllColumn(order, updateOrderWrapper) != 1) {
      throw new DefaultClientException("订单信息已过期，请刷新重试！");
    }

    OpLogUtil.setVariable("code", order.getCode());
    OpLogUtil.setExtra(vo);
  }

  @OpLog(type = SaleOpLogType.class, name = "删除销售订单，单号：{}", params = "#code")
  @OrderTimeLineLog(orderId = "#id", delete = true)
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteById(String id) {

    Assert.notBlank(id);
    SaleOrder order = getBaseMapper().selectById(id);
    if (order == null) {
      throw new InputErrorException("订单不存在！");
    }

    if (order.getStatus() != SaleOrderStatus.CREATED
        && order.getStatus() != SaleOrderStatus.APPROVE_REFUSE) {

      if (order.getStatus() == SaleOrderStatus.APPROVE_PASS) {
        throw new DefaultClientException("“审核通过”的销售单据不允许执行删除操作！");
      }

      throw new DefaultClientException("订单无法删除！");
    }

    // 删除订单明细
    Wrapper<SaleOrderDetail> deleteDetailWrapper = Wrappers.lambdaQuery(SaleOrderDetail.class)
        .eq(SaleOrderDetail::getOrderId, order.getId());
    saleOrderDetailService.remove(deleteDetailWrapper);

    // 删除组合商品信息
    Wrapper<SaleOrderDetailBundle> deleteDetailBundleWrapper = Wrappers.lambdaQuery(
        SaleOrderDetailBundle.class).eq(SaleOrderDetailBundle::getOrderId, order.getId());
    saleOrderDetailBundleService.remove(deleteDetailBundleWrapper);

    // 删除订单
    Wrapper<SaleOrder> deleteWrapper = Wrappers.lambdaQuery(SaleOrder.class)
        .eq(SaleOrder::getId, id)
        .in(SaleOrder::getStatus, SaleOrderStatus.CREATED, SaleOrderStatus.APPROVE_REFUSE);
    if (!remove(deleteWrapper)) {
      throw new DefaultClientException("订单信息已过期，请刷新重试！");
    }

    orderPayTypeService.deleteByOrderId(id);

    OpLogUtil.setVariable("code", order.getCode());
  }

  @Override
  public PageResult<SaleProductDto> querySaleByCondition(Integer pageIndex, Integer pageSize,
      String condition, Boolean isReturn) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);

    List<SaleProductDto> datas = getBaseMapper().querySaleByCondition(condition,
        isReturn);
    PageResult<SaleProductDto> pageResult = PageResultUtil.convert(new PageInfo<>(datas));

    return pageResult;
  }

  @Override
  public PageResult<SaleProductDto> querySaleList(Integer pageIndex, Integer pageSize,
      QuerySaleProductVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);

    List<SaleProductDto> datas = getBaseMapper().querySaleList(vo);
    PageResult<SaleProductDto> pageResult = PageResultUtil.convert(new PageInfo<>(datas));

    return pageResult;
  }

  @Override
  public SaleProductDto getSaleById(String id) {

    SaleProductDto data = getBaseMapper().getSaleById(id);

    return data;
  }

  private void create(SaleOrder order, CreateSaleOrderVo vo) {

    StoreCenter sc = storeCenterService.findById(vo.getScId());
    if (sc == null) {
      throw new InputErrorException("仓库不存在！");
    }

    order.setScId(vo.getScId());

    Customer customer = customerService.findById(vo.getCustomerId());
    if (customer == null) {
      throw new InputErrorException("客户不存在！");
    }
    order.setCustomerId(vo.getCustomerId());

    if (!StringUtil.isBlank(vo.getSalerId())) {
      SysUser saler = userService.findById(vo.getSalerId());
      if (saler == null) {
        throw new InputErrorException("销售员不存在！");
      }

      order.setSalerId(vo.getSalerId());
    }

    int totalNum = 0;
    int giftNum = 0;
    BigDecimal totalAmount = BigDecimal.ZERO;
    int orderNo = 1;
    for (SaleProductVo productVo : vo.getProducts()) {

      boolean isGift = productVo.getTaxPrice().doubleValue() == 0D;

      if (isGift) {
        giftNum += productVo.getOrderNum();
      } else {
        totalNum += productVo.getOrderNum();
      }

      totalAmount = NumberUtil.add(totalAmount,
          NumberUtil.mul(productVo.getTaxPrice(), productVo.getOrderNum()));

      SaleOrderDetail orderDetail = new SaleOrderDetail();
      orderDetail.setId(IdUtil.getId());
      orderDetail.setOrderId(order.getId());

      Product product = productService.findById(productVo.getProductId());
      if (product == null) {
        throw new InputErrorException("第" + orderNo + "行商品不存在！");
      }

      if (!NumberUtil.isNumberPrecision(productVo.getTaxPrice(), 2)) {
        throw new InputErrorException("第" + orderNo + "行商品价格最多允许2位小数！");
      }

      orderDetail.setProductId(productVo.getProductId());
      orderDetail.setOrderNum(productVo.getOrderNum());
      orderDetail.setOriPrice(productVo.getOriPrice());
      orderDetail.setDiscountRate(productVo.getDiscountRate());
      orderDetail.setTaxPrice(productVo.getTaxPrice());
      orderDetail.setIsGift(isGift);
      orderDetail.setTaxRate(product.getSaleTaxRate());
      orderDetail.setDescription(
          StringUtil.isBlank(productVo.getDescription()) ? StringPool.EMPTY_STR
              : productVo.getDescription());
      orderDetail.setOrderNo(orderNo);

      saleOrderDetailService.save(orderDetail);

      // 这里处理组合商品
      if (product.getProductType() == ProductType.BUNDLE) {
        List<ProductBundle> productBundles = productBundleService.getByMainProductId(
            product.getId());
        // 构建指标项
        Map<Object, Number> bundleWeight = new HashMap<>(productBundles.size());
        for (ProductBundle productBundle : productBundles) {
          bundleWeight.put(productBundle.getProductId(),
              NumberUtil.mul(productBundle.getSalePrice(), productBundle.getBundleNum()));
        }
        Map<Object, Number> splitPriceMap = SplitNumberUtil.split(orderDetail.getTaxPrice(),
            bundleWeight, 2);
        List<SaleOrderDetailBundle> saleOrderDetailBundles = productBundles.stream()
            .map(productBundle -> {
              Product bundle = productService.findById(productBundle.getProductId());
              SaleOrderDetailBundle saleOrderDetailBundle = new SaleOrderDetailBundle();
              saleOrderDetailBundle.setId(IdUtil.getId());
              saleOrderDetailBundle.setOrderId(order.getId());
              saleOrderDetailBundle.setDetailId(orderDetail.getId());
              saleOrderDetailBundle.setMainProductId(product.getId());
              saleOrderDetailBundle.setOrderNum(orderDetail.getOrderNum());
              saleOrderDetailBundle.setProductId(productBundle.getProductId());
              saleOrderDetailBundle.setProductOrderNum(
                  NumberUtil.mul(orderDetail.getOrderNum(), productBundle.getBundleNum())
                      .intValue());
              saleOrderDetailBundle.setProductOriPrice(productBundle.getSalePrice());
              // 这里会有尾差
              saleOrderDetailBundle.setProductTaxPrice(NumberUtil.getNumber(NumberUtil.div(
                  BigDecimal.valueOf(
                      splitPriceMap.get(productBundle.getProductId()).doubleValue()),
                  productBundle.getBundleNum()), 2));
              saleOrderDetailBundle.setProductTaxRate(bundle.getSaleTaxRate());

              return saleOrderDetailBundle;
            }).collect(Collectors.toList());

        saleOrderDetailBundleService.saveBatch(saleOrderDetailBundles);
      }
      orderNo++;
    }
    order.setTotalNum(totalNum);
    order.setTotalGiftNum(giftNum);
    order.setTotalAmount(totalAmount);
    order.setDescription(
        StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());

    orderPayTypeService.create(order.getId(), vo.getPayTypes());
  }

  private void sendApprovePassEvent(SaleOrder order) {

    ApprovePassOrderDto dto = new ApprovePassOrderDto();
    dto.setId(order.getId());
    dto.setTotalAmount(order.getTotalAmount());
    dto.setApproveTime(order.getApproveTime());

    ApprovePassSaleOrderEvent event = new ApprovePassSaleOrderEvent(this, dto);

    ApplicationUtil.publishEvent(event);
  }
}

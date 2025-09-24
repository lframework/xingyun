package com.lframework.xingyun.sc.impl.stock.take;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.NumberUtil;
import com.lframework.starter.common.utils.ObjectUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.annotations.oplog.OpLog;
import com.lframework.starter.web.core.components.qrtz.QrtzJob;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.starter.web.core.utils.EnumUtil;
import com.lframework.starter.web.core.utils.IdUtil;
import com.lframework.starter.web.core.utils.PageHelperUtil;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.starter.web.inner.service.GenerateCodeService;
import com.lframework.starter.web.core.utils.OpLogUtil;
import com.lframework.xingyun.basedata.entity.Product;
import com.lframework.xingyun.basedata.entity.ProductPurchase;
import com.lframework.xingyun.basedata.enums.ProductType;
import com.lframework.xingyun.basedata.service.product.ProductPurchaseService;
import com.lframework.xingyun.basedata.service.product.ProductService;
import com.lframework.xingyun.basedata.vo.product.info.QueryProductVo;
import com.lframework.xingyun.sc.components.code.GenerateCodeTypePool;
import com.lframework.xingyun.sc.dto.stock.take.plan.QueryTakeStockPlanProductDto;
import com.lframework.xingyun.sc.dto.stock.take.plan.TakeStockPlanFullDto;
import com.lframework.xingyun.sc.entity.ProductStock;
import com.lframework.xingyun.sc.entity.TakeStockConfig;
import com.lframework.xingyun.sc.entity.TakeStockPlan;
import com.lframework.xingyun.sc.entity.TakeStockPlanDetail;
import com.lframework.xingyun.sc.enums.ProductStockBizType;
import com.lframework.xingyun.sc.enums.TakeStockOpLogType;
import com.lframework.xingyun.sc.enums.TakeStockPlanStatus;
import com.lframework.xingyun.sc.enums.TakeStockPlanType;
import com.lframework.xingyun.sc.events.stock.take.DeleteTakeStockPlanEvent;
import com.lframework.xingyun.sc.mappers.TakeStockPlanMapper;
import com.lframework.xingyun.sc.service.stock.ProductStockService;
import com.lframework.xingyun.sc.service.stock.take.TakeStockConfigService;
import com.lframework.xingyun.sc.service.stock.take.TakeStockPlanDetailService;
import com.lframework.xingyun.sc.service.stock.take.TakeStockPlanService;
import com.lframework.xingyun.sc.service.stock.take.TakeStockSheetService;
import com.lframework.xingyun.sc.vo.stock.AddProductStockVo;
import com.lframework.xingyun.sc.vo.stock.SubProductStockVo;
import com.lframework.xingyun.sc.vo.stock.take.plan.CancelTakeStockPlanVo;
import com.lframework.xingyun.sc.vo.stock.take.plan.CreateTakeStockPlanVo;
import com.lframework.xingyun.sc.vo.stock.take.plan.HandleTakeStockPlanVo;
import com.lframework.xingyun.sc.vo.stock.take.plan.HandleTakeStockPlanVo.ProductVo;
import com.lframework.xingyun.sc.vo.stock.take.plan.QueryTakeStockPlanVo;
import com.lframework.xingyun.sc.vo.stock.take.plan.TakeStockPlanSelectorVo;
import com.lframework.xingyun.sc.vo.stock.take.plan.UpdateTakeStockPlanVo;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class TakeStockPlanServiceImpl extends BaseMpServiceImpl<TakeStockPlanMapper, TakeStockPlan>
    implements TakeStockPlanService {

  @Autowired
  private TakeStockPlanDetailService takeStockPlanDetailService;

  @Autowired
  private GenerateCodeService generateCodeService;

  @Autowired
  private ProductService productService;

  @Autowired
  private ProductStockService productStockService;

  @Autowired
  private TakeStockConfigService takeStockConfigService;

  @Autowired
  private TakeStockSheetService takeStockSheetService;

  @Autowired
  private ProductPurchaseService productPurchaseService;


  @Override
  public PageResult<TakeStockPlan> query(Integer pageIndex, Integer pageSize,
      QueryTakeStockPlanVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<TakeStockPlan> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<TakeStockPlan> query(QueryTakeStockPlanVo vo) {

    return getBaseMapper().query(vo);
  }

  @Override
  public PageResult<TakeStockPlan> selector(Integer pageIndex, Integer pageSize,
      TakeStockPlanSelectorVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<TakeStockPlan> datas = getBaseMapper().selector(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public TakeStockPlanFullDto getDetail(String id) {

    return getBaseMapper().getDetail(id);
  }

  @OpLog(type = TakeStockOpLogType.class, name = "新增盘点任务，ID：{}", params = {"#id"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(CreateTakeStockPlanVo vo) {

    TakeStockPlan data = new TakeStockPlan();
    data.setId(IdUtil.getId());
    data.setCode(generateCodeService.generate(GenerateCodeTypePool.TAKE_STOCK_PLAN));
    data.setScId(vo.getScId());
    data.setTakeType(EnumUtil.getByCode(TakeStockPlanType.class, vo.getTakeType()));
    if (data.getTakeType() == TakeStockPlanType.CATEGORY
        || data.getTakeType() == TakeStockPlanType.BRAND) {
      data.setBizId(StringUtil.join(",", vo.getBizIds()));
    }

    data.setTakeStatus(TakeStockPlanStatus.CREATED);
    data.setDescription(
        StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());

    getBaseMapper().insert(data);

    List<Product> products = null;
    if (data.getTakeType() != TakeStockPlanType.SIMPLE) {
      // 单品盘点不生成明细
      if (data.getTakeType() == TakeStockPlanType.ALL) {
        // 全场盘点
        // 将所有商品添加明细
        QueryProductVo queryProductVo = new QueryProductVo();
        queryProductVo.setAvailable(Boolean.TRUE);
        queryProductVo.setProductType(ProductType.NORMAL.getCode());
        Integer count = productService.queryCount(queryProductVo);
        if (count > 2000) {
          throw new DefaultClientException(
              TakeStockPlanType.ALL.getDesc() + "最多支持2000个商品，当前系统内已经超过2000个商品，无法进行"
                  + TakeStockPlanType.ALL.getDesc());
        }
        products = productService.query(queryProductVo);
      } else if (data.getTakeType() == TakeStockPlanType.CATEGORY) {
        // 分类盘点
        products = productService.getByCategoryIds(vo.getBizIds(), ProductType.NORMAL.getCode());
      } else if (data.getTakeType() == TakeStockPlanType.BRAND) {
        // 品牌盘点
        products = productService.getByBrandIds(vo.getBizIds(), ProductType.NORMAL.getCode());
      }
    }

    if (data.getTakeType() != TakeStockPlanType.SIMPLE && CollectionUtil.isEmpty(products)) {
      throw new DefaultClientException("没有查询到商品信息，无法生成盘点任务！");
    }

    if (products != null) {
      List<String> productIds = products.stream().map(Product::getId)
          .collect(Collectors.toList());
      List<ProductStock> productStocks = productStockService.getByProductIdsAndScId(productIds,
          vo.getScId(), ProductType.NORMAL.getCode());
      int orderNo = 1;
      for (Product product : products) {
        ProductStock productStock = productStocks.stream()
            .filter(t -> t.getProductId().equals(product.getId()))
            .findFirst().orElse(null);

        TakeStockPlanDetail detail = new TakeStockPlanDetail();
        detail.setId(IdUtil.getId());
        detail.setPlanId(data.getId());
        detail.setProductId(product.getId());

        detail.setStockNum(productStock == null ? 0 : productStock.getStockNum().intValue());
        detail.setTotalOutNum(0);
        detail.setTotalInNum(0);
        detail.setOrderNo(orderNo++);

        takeStockPlanDetailService.save(detail);
      }
    }

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setExtra(vo);

    return data.getId();
  }

  @OpLog(type = TakeStockOpLogType.class, name = "修改盘点任务，ID：{}", params = {"#id"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateTakeStockPlanVo vo) {

    TakeStockPlan data = getBaseMapper().selectById(vo.getId());
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("盘点任务不存在！");
    }

    LambdaUpdateWrapper<TakeStockPlan> updateWrapper = Wrappers.lambdaUpdate(TakeStockPlan.class)
        .set(TakeStockPlan::getScId, vo.getScId())
        .set(TakeStockPlan::getDescription, vo.getDescription())
        .eq(TakeStockPlan::getId, vo.getId());

    getBaseMapper().update(updateWrapper);

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setExtra(vo);
  }

  @Override
  public List<QueryTakeStockPlanProductDto> getProducts(String planId) {

    return getBaseMapper().getProducts(planId);
  }

  @OpLog(type = TakeStockOpLogType.class, name = "差异生成，盘点任务ID：{}", params = {"#id"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void createDiff(String id) {

    TakeStockPlan data = getBaseMapper().selectById(id);
    if (data == null) {
      throw new DefaultClientException("盘点任务不存在！");
    }

    // 判断是否还有没审核通过的盘点单
    if (takeStockSheetService.hasUnApprove(data.getId())) {
      throw new DefaultClientException("盘点任务存在未审核的库存盘点单，请优先处理库存盘点单！");
    }

    LambdaUpdateWrapper<TakeStockPlan> updateWrapper = Wrappers.lambdaUpdate(TakeStockPlan.class)
        .set(TakeStockPlan::getTakeStatus, TakeStockPlanStatus.DIFF_CREATED)
        .eq(TakeStockPlan::getId, data.getId())
        .eq(TakeStockPlan::getTakeStatus, TakeStockPlanStatus.CREATED);
    if (getBaseMapper().update(updateWrapper) != 1) {
      throw new DefaultClientException("盘点任务信息已过期，请刷新重试！");
    }

    Wrapper<TakeStockPlanDetail> queryDetailWrapper = Wrappers.lambdaQuery(
            TakeStockPlanDetail.class)
        .eq(TakeStockPlanDetail::getPlanId, data.getId())
        .orderByAsc(TakeStockPlanDetail::getOrderNo);
    List<TakeStockPlanDetail> details = takeStockPlanDetailService.list(queryDetailWrapper);
    if (CollectionUtil.isEmpty(details)) {
      throw new DefaultClientException("盘点任务不存在商品信息，不允许执行差异生成操作！");
    }
    for (TakeStockPlanDetail detail : details) {
      if (detail.getOriTakeNum() != null) {
        continue;
      }
      LambdaUpdateWrapper<TakeStockPlanDetail> updateDetailWrapper = Wrappers.lambdaUpdate(
              TakeStockPlanDetail.class).set(TakeStockPlanDetail::getOriTakeNum, 0)
          .eq(TakeStockPlanDetail::getId, detail.getId());

      takeStockPlanDetailService.update(updateDetailWrapper);
    }
  }

  @OpLog(type = TakeStockOpLogType.class, name = "差异处理，盘点任务ID：{}", params = {"#id"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void handleDiff(HandleTakeStockPlanVo vo) {

    TakeStockPlan data = getBaseMapper().selectById(vo.getId());
    if (data == null) {
      throw new DefaultClientException("盘点任务不存在！");
    }

    LambdaUpdateWrapper<TakeStockPlan> updateWrapper = Wrappers.lambdaUpdate(TakeStockPlan.class)
        .set(TakeStockPlan::getDescription,
            StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription())
        .set(TakeStockPlan::getTakeStatus, TakeStockPlanStatus.FINISHED)
        .eq(TakeStockPlan::getId, data.getId())
        .eq(TakeStockPlan::getTakeStatus, TakeStockPlanStatus.DIFF_CREATED);
    if (getBaseMapper().update(updateWrapper) != 1) {
      throw new DefaultClientException("盘点任务信息已过期，请刷新重试！");
    }

    TakeStockConfig config = takeStockConfigService.get();

    Wrapper<TakeStockPlanDetail> queryDetailWrapper = Wrappers.lambdaQuery(
            TakeStockPlanDetail.class)
        .eq(TakeStockPlanDetail::getPlanId, data.getId())
        .orderByAsc(TakeStockPlanDetail::getOrderNo);
    List<TakeStockPlanDetail> details = takeStockPlanDetailService.list(queryDetailWrapper);
    if (CollectionUtil.isEmpty(details)) {
      throw new DefaultClientException("盘点任务不存在商品信息，不允许执行差异处理操作！");
    }

    if (!config.getAllowChangeNum().equals(vo.getAllowChangeNum()) || !config.getAutoChangeStock()
        .equals(vo.getAutoChangeStock())) {
      throw new DefaultClientException("系统参数发生改变，请刷新页面后重试！");
    }

    for (TakeStockPlanDetail detail : details) {
      ProductVo productVo = vo.getProducts().stream()
          .filter(t -> t.getProductId().equals(detail.getProductId())).findFirst().get();
      if (config.getAllowChangeNum()) {
        // 如果允许修改盘点数量
        detail.setTakeNum(productVo.getTakeNum());
      } else {
        // 如果允许自动调整，那么盘点数量=盘点单的盘点数量 - 进项数量 + 出项数量，否则就等于盘点单的盘点数量
        detail.setTakeNum(config.getAutoChangeStock() ?
            detail.getOriTakeNum() - detail.getTotalInNum() + detail.getTotalOutNum() :
            detail.getOriTakeNum());
      }
      detail.setDescription(
          StringUtil.isBlank(productVo.getDescription()) ? StringPool.EMPTY_STR
              : productVo.getDescription());

      LambdaUpdateWrapper<TakeStockPlanDetail> updateDetailWrapper = Wrappers.lambdaUpdate(
              TakeStockPlanDetail.class).set(TakeStockPlanDetail::getTakeNum, detail.getTakeNum())
          .set(TakeStockPlanDetail::getDescription, detail.getDescription())
          .eq(TakeStockPlanDetail::getId, detail.getId());
      takeStockPlanDetailService.update(updateDetailWrapper);
    }

    // 进行出入库操作
    int orderNo = 0;
    for (TakeStockPlanDetail detail : details) {
      orderNo++;
      if (!NumberUtil.equal(detail.getStockNum(), detail.getTakeNum())) {
        if (NumberUtil.lt(detail.getStockNum(), detail.getTakeNum())) {
          Product product = productService.findById(detail.getProductId());
          ProductPurchase purchase = productPurchaseService.getById(product.getId());
          // 如果库存数量小于盘点数量，则报溢
          AddProductStockVo addProductStockVo = new AddProductStockVo();
          addProductStockVo.setProductId(detail.getProductId());
          addProductStockVo.setScId(data.getScId());
          addProductStockVo.setStockNum(NumberUtil.sub(detail.getTakeNum(), detail.getStockNum()));
          addProductStockVo.setDefaultTaxAmount(purchase.getPrice());
          addProductStockVo.setBizId(data.getId());
          addProductStockVo.setBizDetailId(detail.getId());
          addProductStockVo.setBizCode(data.getCode());
          addProductStockVo.setBizType(ProductStockBizType.TAKE_STOCK_IN.getCode());

          productStockService.addStock(addProductStockVo);
        } else {
          // 如果库存数量大于盘点数量，则报损
          SubProductStockVo subProductStockVo = new SubProductStockVo();
          subProductStockVo.setProductId(detail.getProductId());
          subProductStockVo.setScId(data.getScId());
          subProductStockVo.setStockNum(NumberUtil.sub(detail.getStockNum(), detail.getTakeNum()));
          subProductStockVo.setBizId(data.getId());
          subProductStockVo.setBizDetailId(detail.getId());
          subProductStockVo.setBizCode(data.getCode());
          subProductStockVo.setBizType(ProductStockBizType.TAKE_STOCK_OUT.getCode());

          productStockService.subStock(subProductStockVo);
        }
      }
    }

    OpLogUtil.setVariable("id", vo.getId());
    OpLogUtil.setExtra(vo);
  }

  @OpLog(type = TakeStockOpLogType.class, name = "作废盘点任务，ID：{}", params = {"#id"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void cancel(CancelTakeStockPlanVo vo) {

    TakeStockPlan data = getBaseMapper().selectById(vo.getId());
    if (data == null) {
      throw new DefaultClientException("盘点任务不存在！");
    }

    LambdaUpdateWrapper<TakeStockPlan> updateWrapper = Wrappers.lambdaUpdate(TakeStockPlan.class)
        .set(TakeStockPlan::getTakeStatus, TakeStockPlanStatus.CANCELED)
        .eq(TakeStockPlan::getId, data.getId())
        .in(TakeStockPlan::getTakeStatus, TakeStockPlanStatus.CREATED,
            TakeStockPlanStatus.DIFF_CREATED);
    if (getBaseMapper().update(updateWrapper) != 1) {
      throw new DefaultClientException("盘点任务信息已过期，请刷新重试！");
    }

    OpLogUtil.setVariable("id", vo.getId());
    OpLogUtil.setExtra(vo);
  }

  @OpLog(type = TakeStockOpLogType.class, name = "删除盘点任务，ID：{}", params = {"#id"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteById(String id) {

    TakeStockPlan data = getBaseMapper().selectById(id);
    if (data == null) {
      throw new DefaultClientException("盘点任务不存在！");
    }

    Wrapper<TakeStockPlan> deleteWrapper = Wrappers.lambdaQuery(TakeStockPlan.class)
        .eq(TakeStockPlan::getId, data.getId())
        .eq(TakeStockPlan::getTakeStatus, TakeStockPlanStatus.CANCELED);
    if (getBaseMapper().delete(deleteWrapper) != 1) {
      throw new DefaultClientException("盘点任务信息已过期，请刷新重试！");
    }

    Wrapper<TakeStockPlanDetail> deleteDetailWrapper = Wrappers.lambdaQuery(
            TakeStockPlanDetail.class)
        .eq(TakeStockPlanDetail::getPlanId, data.getId());
    takeStockPlanDetailService.remove(deleteDetailWrapper);

    DeleteTakeStockPlanEvent deleteEvent = new DeleteTakeStockPlanEvent(this, data.getId());
    ApplicationUtil.publishEvent(deleteEvent);
  }

  @Override
  public void cleanCacheByKey(Serializable key) {

  }

  /**
   * 自动作废任务
   */
  @Slf4j
  public static class AutoCancelJob extends QrtzJob {

    @Autowired
    private TakeStockPlanService takeStockPlanService;

    @Override
    public void onExecute(JobExecutionContext context) {

      String id = (String) context.getMergedJobDataMap().get("id");

      CancelTakeStockPlanVo cancelVo = new CancelTakeStockPlanVo();
      cancelVo.setId(id);
      takeStockPlanService.cancel(cancelVo);
    }
  }
}

package com.lframework.xingyun.sc.impl.stock.take;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.common.constants.StringPool;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.Assert;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.NumberUtil;
import com.lframework.common.utils.ObjectUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.enums.OpLogType;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.OpLogUtil;
import com.lframework.starter.mybatis.utils.PageHelperUtil;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.web.components.qrtz.QrtzJob;
import com.lframework.starter.web.service.IGenerateCodeService;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.utils.EnumUtil;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.xingyun.basedata.dto.product.info.ProductDto;
import com.lframework.xingyun.basedata.service.product.IProductPurchaseService;
import com.lframework.xingyun.basedata.service.product.IProductService;
import com.lframework.xingyun.basedata.vo.product.info.QueryProductVo;
import com.lframework.xingyun.core.dto.stock.ProductStockChangeDto;
import com.lframework.xingyun.core.events.stock.AddStockEvent;
import com.lframework.xingyun.core.events.stock.SubStockEvent;
import com.lframework.xingyun.core.events.stock.take.DeleteTakeStockPlanEvent;
import com.lframework.xingyun.sc.components.code.GenerateCodeTypePool;
import com.lframework.xingyun.sc.dto.stock.ProductLotWithStockDto;
import com.lframework.xingyun.sc.dto.stock.take.plan.QueryTakeStockPlanProductDto;
import com.lframework.xingyun.sc.dto.stock.take.plan.TakeStockPlanFullDto;
import com.lframework.xingyun.sc.dto.stock.take.plan.TakeStockPlanSelectorDto;
import com.lframework.xingyun.sc.entity.ProductStock;
import com.lframework.xingyun.sc.entity.TakeStockConfig;
import com.lframework.xingyun.sc.entity.TakeStockPlan;
import com.lframework.xingyun.sc.entity.TakeStockPlanDetail;
import com.lframework.xingyun.sc.enums.ProductStockBizType;
import com.lframework.xingyun.sc.enums.TakeStockPlanStatus;
import com.lframework.xingyun.sc.enums.TakeStockPlanType;
import com.lframework.xingyun.sc.mappers.TakeStockPlanDetailMapper;
import com.lframework.xingyun.sc.mappers.TakeStockPlanMapper;
import com.lframework.xingyun.sc.service.stock.IProductLotService;
import com.lframework.xingyun.sc.service.stock.IProductStockService;
import com.lframework.xingyun.sc.service.stock.take.ITakeStockConfigService;
import com.lframework.xingyun.sc.service.stock.take.ITakeStockPlanDetailService;
import com.lframework.xingyun.sc.service.stock.take.ITakeStockPlanService;
import com.lframework.xingyun.sc.service.stock.take.ITakeStockSheetService;
import com.lframework.xingyun.sc.vo.stock.AddProductStockVo;
import com.lframework.xingyun.sc.vo.stock.SubProductStockVo;
import com.lframework.xingyun.sc.vo.stock.take.plan.CancelTakeStockPlanVo;
import com.lframework.xingyun.sc.vo.stock.take.plan.CreateTakeStockPlanVo;
import com.lframework.xingyun.sc.vo.stock.take.plan.HandleTakeStockPlanVo;
import com.lframework.xingyun.sc.vo.stock.take.plan.QueryTakeStockPlanVo;
import com.lframework.xingyun.sc.vo.stock.take.plan.TakeStockPlanSelectorVo;
import com.lframework.xingyun.sc.vo.stock.take.plan.UpdateTakeStockPlanVo;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TakeStockPlanServiceImpl extends BaseMpServiceImpl<TakeStockPlanMapper, TakeStockPlan>
    implements ITakeStockPlanService {

  @Autowired
  private ITakeStockPlanDetailService takeStockPlanDetailService;

  @Autowired
  private IGenerateCodeService generateCodeService;

  @Autowired
  private IProductService productService;

  @Autowired
  private IProductStockService productStockService;

  @Autowired
  private ITakeStockConfigService takeStockConfigService;

  @Autowired
  private ITakeStockSheetService takeStockSheetService;

  @Autowired
  private IProductPurchaseService productPurchaseService;

  @Autowired
  private IProductLotService productLotService;

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
  public PageResult<TakeStockPlanSelectorDto> selector(Integer pageIndex, Integer pageSize,
      TakeStockPlanSelectorVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<TakeStockPlanSelectorDto> datas = getBaseMapper().selector(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public TakeStockPlanFullDto getDetail(String id) {

    return getBaseMapper().getDetail(id);
  }

  @OpLog(type = OpLogType.OTHER, name = "?????????????????????ID???{}", params = {"#id"})
  @Transactional
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

    List<ProductDto> products = null;
    if (data.getTakeType() != TakeStockPlanType.SIMPLE) {
      // ???????????????????????????
      if (data.getTakeType() == TakeStockPlanType.ALL) {
        // ????????????
        // ???????????????????????????
        QueryProductVo queryProductVo = new QueryProductVo();
        Integer count = productService.queryCount(queryProductVo);
        if (count > 2000) {
          throw new DefaultClientException(
              TakeStockPlanType.ALL.getDesc() + "????????????2000???????????????????????????????????????2000????????????????????????"
                  + TakeStockPlanType.ALL.getDesc());
        }
        products = productService.query(queryProductVo);
      } else if (data.getTakeType() == TakeStockPlanType.CATEGORY) {
        // ????????????
        products = productService.getByCategoryIds(vo.getBizIds());
      } else if (data.getTakeType() == TakeStockPlanType.BRAND) {
        // ????????????
        products = productService.getByBrandIds(vo.getBizIds());
      }
    }

    if (data.getTakeType() != TakeStockPlanType.SIMPLE && CollectionUtil.isEmpty(products)) {
      throw new DefaultClientException("?????????????????????????????????????????????????????????");
    }

    if (products != null) {
      List<String> productIds = products.stream().map(ProductDto::getId)
          .collect(Collectors.toList());
      List<ProductStock> productStocks = productStockService.getByProductIdsAndScId(productIds,
          vo.getScId());
      int orderNo = 1;
      for (ProductDto product : products) {
        ProductStock productStock = productStocks.stream()
            .filter(t -> t.getProductId().equals(product.getId()))
            .findFirst().orElse(null);

        TakeStockPlanDetail detail = new TakeStockPlanDetail();
        detail.setId(IdUtil.getId());
        detail.setPlanId(data.getId());
        detail.setProductId(product.getId());

        detail.setStockNum(productStock == null ? 0 : productStock.getStockNum());
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

  @OpLog(type = OpLogType.OTHER, name = "?????????????????????ID???{}", params = {"#id"})
  @Transactional
  @Override
  public void update(UpdateTakeStockPlanVo vo) {

    TakeStockPlan data = getBaseMapper().selectById(vo.getId());
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("????????????????????????");
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

  @OpLog(type = OpLogType.OTHER, name = "???????????????????????????ID???{}", params = {"#id"})
  @Transactional
  @Override
  public void createDiff(String id) {

    TakeStockPlan data = getBaseMapper().selectById(id);
    if (data == null) {
      throw new DefaultClientException("????????????????????????");
    }

    // ?????????????????????????????????????????????
    if (takeStockSheetService.hasUnApprove(data.getId())) {
      throw new DefaultClientException("?????????????????????????????????????????????????????????????????????????????????");
    }

    LambdaUpdateWrapper<TakeStockPlan> updateWrapper = Wrappers.lambdaUpdate(TakeStockPlan.class)
        .set(TakeStockPlan::getTakeStatus, TakeStockPlanStatus.DIFF_CREATED)
        .eq(TakeStockPlan::getId, data.getId())
        .eq(TakeStockPlan::getTakeStatus, TakeStockPlanStatus.CREATED);
    if (getBaseMapper().update(updateWrapper) != 1) {
      throw new DefaultClientException("????????????????????????????????????????????????");
    }

    Wrapper<TakeStockPlanDetail> queryDetailWrapper = Wrappers.lambdaQuery(
            TakeStockPlanDetail.class)
        .eq(TakeStockPlanDetail::getPlanId, data.getId())
        .orderByAsc(TakeStockPlanDetail::getOrderNo);
    List<TakeStockPlanDetail> details = takeStockPlanDetailService.list(queryDetailWrapper);
    if (CollectionUtil.isEmpty(details)) {
      throw new DefaultClientException("????????????????????????????????????????????????????????????????????????");
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

  @OpLog(type = OpLogType.OTHER, name = "???????????????????????????ID???{}", params = {"#id"})
  @Transactional
  @Override
  public void handleDiff(HandleTakeStockPlanVo vo) {

    TakeStockPlan data = getBaseMapper().selectById(vo.getId());
    if (data == null) {
      throw new DefaultClientException("????????????????????????");
    }

    LambdaUpdateWrapper<TakeStockPlan> updateWrapper = Wrappers.lambdaUpdate(TakeStockPlan.class)
        .set(TakeStockPlan::getDescription,
            StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription())
        .set(TakeStockPlan::getTakeStatus, TakeStockPlanStatus.FINISHED)
        .eq(TakeStockPlan::getId, data.getId())
        .eq(TakeStockPlan::getTakeStatus, TakeStockPlanStatus.DIFF_CREATED);
    if (getBaseMapper().update(updateWrapper) != 1) {
      throw new DefaultClientException("????????????????????????????????????????????????");
    }

    TakeStockConfig config = takeStockConfigService.get();

    Wrapper<TakeStockPlanDetail> queryDetailWrapper = Wrappers.lambdaQuery(
            TakeStockPlanDetail.class)
        .eq(TakeStockPlanDetail::getPlanId, data.getId())
        .orderByAsc(TakeStockPlanDetail::getOrderNo);
    List<TakeStockPlanDetail> details = takeStockPlanDetailService.list(queryDetailWrapper);
    if (CollectionUtil.isEmpty(details)) {
      throw new DefaultClientException("????????????????????????????????????????????????????????????????????????");
    }

    if (!config.getAllowChangeNum().equals(vo.getAllowChangeNum()) || !config.getAutoChangeStock()
        .equals(vo.getAutoChangeStock())) {
      throw new DefaultClientException("??????????????????????????????????????????????????????");
    }

    for (TakeStockPlanDetail detail : details) {
      HandleTakeStockPlanVo.ProductVo productVo = vo.getProducts().stream()
          .filter(t -> t.getProductId().equals(detail.getProductId())).findFirst().get();
      if (config.getAllowChangeNum()) {
        // ??????????????????????????????
        detail.setTakeNum(productVo.getTakeNum());
      } else {
        // ?????????????????????????????????????????????=???????????????????????? - ???????????? + ??????????????????????????????????????????????????????
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

    // ?????????????????????
    int orderNo = 0;
    for (TakeStockPlanDetail detail : details) {
      orderNo++;
      if (!NumberUtil.equal(detail.getStockNum(), detail.getTakeNum())) {
        if (NumberUtil.lt(detail.getStockNum(), detail.getTakeNum())) {
          ProductLotWithStockDto productLot = productLotService.getLastPurchaseLot(
              detail.getProductId(),
              data.getScId(), null);
          if (productLot == null) {
            throw new DefaultClientException("???" + orderNo + "????????????????????????????????????????????????????????????");
          }
          // ????????????????????????????????????????????????
          AddProductStockVo addProductStockVo = new AddProductStockVo();
          addProductStockVo.setProductId(detail.getProductId());
          addProductStockVo.setScId(data.getScId());
          addProductStockVo.setSupplierId(productLot.getSupplierId());
          addProductStockVo.setStockNum(detail.getTakeNum() - detail.getStockNum());
          addProductStockVo.setTaxRate(productLot.getTaxRate());
          addProductStockVo.setBizId(data.getId());
          addProductStockVo.setBizDetailId(detail.getId());
          addProductStockVo.setBizCode(data.getCode());
          addProductStockVo.setBizType(ProductStockBizType.TAKE_STOCK_IN.getCode());

          productStockService.addStock(addProductStockVo);
        } else {
          // ????????????????????????????????????????????????
          SubProductStockVo subProductStockVo = new SubProductStockVo();
          subProductStockVo.setProductId(detail.getProductId());
          subProductStockVo.setScId(data.getScId());
          subProductStockVo.setStockNum(detail.getStockNum() - detail.getTakeNum());
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

  @OpLog(type = OpLogType.OTHER, name = "?????????????????????ID???{}", params = {"#id"})
  @Transactional
  @Override
  public void cancel(CancelTakeStockPlanVo vo) {

    TakeStockPlan data = getBaseMapper().selectById(vo.getId());
    if (data == null) {
      throw new DefaultClientException("????????????????????????");
    }

    LambdaUpdateWrapper<TakeStockPlan> updateWrapper = Wrappers.lambdaUpdate(TakeStockPlan.class)
        .set(TakeStockPlan::getTakeStatus, TakeStockPlanStatus.CANCELED)
        .eq(TakeStockPlan::getId, data.getId())
        .in(TakeStockPlan::getTakeStatus, TakeStockPlanStatus.CREATED,
            TakeStockPlanStatus.DIFF_CREATED);
    if (getBaseMapper().update(updateWrapper) != 1) {
      throw new DefaultClientException("????????????????????????????????????????????????");
    }

    OpLogUtil.setVariable("id", vo.getId());
    OpLogUtil.setExtra(vo);
  }

  @OpLog(type = OpLogType.OTHER, name = "?????????????????????ID???{}", params = {"#id"})
  @Transactional
  @Override
  public void deleteById(String id) {

    TakeStockPlan data = getBaseMapper().selectById(id);
    if (data == null) {
      throw new DefaultClientException("????????????????????????");
    }

    Wrapper<TakeStockPlan> deleteWrapper = Wrappers.lambdaQuery(TakeStockPlan.class)
        .eq(TakeStockPlan::getId, data.getId())
        .eq(TakeStockPlan::getTakeStatus, TakeStockPlanStatus.CANCELED);
    if (getBaseMapper().delete(deleteWrapper) != 1) {
      throw new DefaultClientException("????????????????????????????????????????????????");
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

  @Service
  public static class AddStockListener implements ApplicationListener<AddStockEvent> {

    @Autowired
    private TakeStockPlanDetailMapper takeStockPlanDetailMapper;

    @Transactional
    @Override
    public void onApplicationEvent(AddStockEvent addStockEvent) {

      ProductStockChangeDto change = addStockEvent.getChange();
      takeStockPlanDetailMapper.addTotalInNum(change.getScId(), change.getProductId(),
          change.getNum());
    }
  }

  @Service
  public static class SubStockListener implements ApplicationListener<SubStockEvent> {

    @Autowired
    private TakeStockPlanDetailMapper takeStockPlanDetailMapper;

    @Transactional
    @Override
    public void onApplicationEvent(SubStockEvent addStockEvent) {

      ProductStockChangeDto change = addStockEvent.getChange();
      takeStockPlanDetailMapper.addTotalOutNum(change.getScId(), change.getProductId(),
          change.getNum());
    }
  }

  /**
   * ??????????????????
   */
  @Slf4j
  public static class AutoCancelJob extends QrtzJob {

    @Autowired
    private ITakeStockPlanService takeStockPlanService;

    @Override
    public void onExecute(JobExecutionContext context) {

      String id = (String) context.getMergedJobDataMap().get("id");

      CancelTakeStockPlanVo cancelVo = new CancelTakeStockPlanVo();
      cancelVo.setId(id);
      takeStockPlanService.cancel(cancelVo);
    }
  }
}

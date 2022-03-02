package com.lframework.xingyun.sc.impl.stock.take;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.common.constants.StringPool;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.*;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.enums.OpLogType;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.OpLogUtil;
import com.lframework.starter.mybatis.utils.PageHelperUtil;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.web.service.IGenerateCodeService;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.utils.EnumUtil;
import com.lframework.xingyun.basedata.dto.product.info.ProductDto;
import com.lframework.xingyun.basedata.dto.product.purchase.ProductPurchaseDto;
import com.lframework.xingyun.basedata.service.product.IProductPurchaseService;
import com.lframework.xingyun.basedata.service.product.IProductService;
import com.lframework.xingyun.basedata.vo.product.info.QueryProductVo;
import com.lframework.xingyun.core.events.stock.take.DeleteTakeStockPlanEvent;
import com.lframework.xingyun.sc.components.code.GenerateCodeTypePool;
import com.lframework.xingyun.sc.dto.stock.ProductStockDto;
import com.lframework.xingyun.sc.dto.stock.take.config.TakeStockConfigDto;
import com.lframework.xingyun.sc.dto.stock.take.plan.QueryTakeStockPlanProductDto;
import com.lframework.xingyun.sc.dto.stock.take.plan.TakeStockPlanDto;
import com.lframework.xingyun.sc.dto.stock.take.plan.TakeStockPlanFullDto;
import com.lframework.xingyun.sc.dto.stock.take.plan.TakeStockPlanSelectorDto;
import com.lframework.xingyun.sc.entity.TakeStockPlan;
import com.lframework.xingyun.sc.entity.TakeStockPlanDetail;
import com.lframework.xingyun.sc.enums.ProductStockBizType;
import com.lframework.xingyun.sc.enums.TakeStockPlanStatus;
import com.lframework.xingyun.sc.enums.TakeStockPlanType;
import com.lframework.xingyun.sc.mappers.TakeStockPlanDetailMapper;
import com.lframework.xingyun.sc.mappers.TakeStockPlanMapper;
import com.lframework.xingyun.sc.service.stock.IProductStockService;
import com.lframework.xingyun.sc.service.stock.take.ITakeStockConfigService;
import com.lframework.xingyun.sc.service.stock.take.ITakeStockPlanService;
import com.lframework.xingyun.sc.service.stock.take.ITakeStockSheetService;
import com.lframework.xingyun.sc.vo.stock.AddProductStockVo;
import com.lframework.xingyun.sc.vo.stock.SubProductStockVo;
import com.lframework.xingyun.sc.vo.stock.take.plan.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TakeStockPlanServiceImpl implements ITakeStockPlanService {

    @Autowired
    private TakeStockPlanMapper takeStockPlanMapper;

    @Autowired
    private TakeStockPlanDetailMapper takeStockPlanDetailMapper;

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

    @Override
    public PageResult<TakeStockPlanDto> query(Integer pageIndex, Integer pageSize, QueryTakeStockPlanVo vo) {

        Assert.greaterThanZero(pageIndex);
        Assert.greaterThanZero(pageSize);

        PageHelperUtil.startPage(pageIndex, pageSize);
        List<TakeStockPlanDto> datas = this.query(vo);

        return PageResultUtil.convert(new PageInfo<>(datas));
    }

    @Override
    public List<TakeStockPlanDto> query(QueryTakeStockPlanVo vo) {

        return takeStockPlanMapper.query(vo);
    }

    @Override
    public PageResult<TakeStockPlanSelectorDto> selector(Integer pageIndex, Integer pageSize, TakeStockPlanSelectorVo vo) {
        Assert.greaterThanZero(pageIndex);
        Assert.greaterThanZero(pageSize);

        PageHelperUtil.startPage(pageIndex, pageSize);
        List<TakeStockPlanSelectorDto> datas = takeStockPlanMapper.selector(vo);

        return PageResultUtil.convert(new PageInfo<>(datas));
    }

    @Override
    public TakeStockPlanDto getById(String id) {

        return takeStockPlanMapper.getById(id);
    }

    @Override
    public TakeStockPlanFullDto getDetail(String id) {

        return takeStockPlanMapper.getDetail(id);
    }

    @OpLog(type = OpLogType.OTHER, name = "新增盘点任务，ID：{}", params = {"#id"})
    @Transactional
    @Override
    public String create(CreateTakeStockPlanVo vo) {

        TakeStockPlan data = new TakeStockPlan();
        data.setId(IdUtil.getId());
        data.setCode(generateCodeService.generate(GenerateCodeTypePool.TAKE_STOCK_PLAN));
        data.setScId(vo.getScId());
        data.setTakeType(EnumUtil.getByCode(TakeStockPlanType.class, vo.getTakeType()));
        if (data.getTakeType() == TakeStockPlanType.CATEGORY || data.getTakeType() == TakeStockPlanType.BRAND) {
            data.setBizId(StringUtil.join(",", vo.getBizIds()));
        }

        data.setTakeStatus(TakeStockPlanStatus.CREATED);
        data.setDescription(StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());

        takeStockPlanMapper.insert(data);

        List<ProductDto> products = null;
        if (data.getTakeType() != TakeStockPlanType.SIMPLE) {
            // 单品盘点不生成明细
            if (data.getTakeType() == TakeStockPlanType.ALL) {
                // 全场盘点
                // 将所有商品添加明细
                // 性能问题 考虑如果商品过多是否禁用此种方式
                products = productService.query(new QueryProductVo());
            } else if (data.getTakeType() == TakeStockPlanType.CATEGORY) {
                // 类目盘点
                products = productService.getByCategoryIds(vo.getBizIds());
            } else if (data.getTakeType() == TakeStockPlanType.BRAND) {
                // 品牌盘点
                products = productService.getByBrandIds(vo.getBizIds());
            }
        }

        if (data.getTakeType() != TakeStockPlanType.SIMPLE && CollectionUtil.isEmpty(products)) {
            throw new DefaultClientException("没有查询到商品信息，无法生成盘点任务！");
        }

        if (products != null) {
            List<String> productIds = products.stream().map(ProductDto::getId).collect(Collectors.toList());
            List<ProductStockDto> productStocks = productStockService.getByProductIdsAndScId(productIds, vo.getScId());
            int orderNo = 1;
            for (ProductDto product : products) {
                ProductStockDto productStock = productStocks.stream().filter(t -> t.getProductId().equals(product.getId())).findFirst().orElse(null);

                TakeStockPlanDetail detail = new TakeStockPlanDetail();
                detail.setId(IdUtil.getId());
                detail.setPlanId(data.getId());
                detail.setProductId(product.getId());

                detail.setStockNum(productStock == null ? 0 : productStock.getStockNum());
                detail.setTotalOutNum(0);
                detail.setTotalInNum(0);
                detail.setOrderNo(orderNo++);

                takeStockPlanDetailMapper.insert(detail);
            }
        }

        OpLogUtil.setVariable("id", data.getId());
        OpLogUtil.setExtra(vo);

        return data.getId();
    }

    @OpLog(type = OpLogType.OTHER, name = "修改盘点任务，ID：{}", params = {"#id"})
    @Transactional
    @Override
    public void update(UpdateTakeStockPlanVo vo) {

        TakeStockPlan data = takeStockPlanMapper.selectById(vo.getId());
        if (ObjectUtil.isNull(data)) {
            throw new DefaultClientException("盘点任务不存在！");
        }

        LambdaUpdateWrapper<TakeStockPlan> updateWrapper = Wrappers.lambdaUpdate(TakeStockPlan.class)
                .set(TakeStockPlan::getScId, vo.getScId())
                .set(TakeStockPlan::getDescription, vo.getDescription())
                .eq(TakeStockPlan::getId, vo.getId());

        takeStockPlanMapper.update(updateWrapper);

        OpLogUtil.setVariable("id", data.getId());
        OpLogUtil.setExtra(vo);
    }

    @Override
    public List<QueryTakeStockPlanProductDto> getProducts(String planId) {

        return takeStockPlanMapper.getProducts(planId);
    }

    @OpLog(type = OpLogType.OTHER, name = "差异生成，盘点任务ID：{}", params = {"#id"})
    @Transactional
    @Override
    public void createDiff(String id) {

        TakeStockPlan data = takeStockPlanMapper.selectById(id);
        if (data == null) {
            throw new DefaultClientException("盘点任务不存在！");
        }

        // 判断是否还有没审核通过的盘点单
        if (takeStockSheetService.hasUnApprove(data.getId())) {
            throw new DefaultClientException("盘点任务存在未审核的库存盘点单，请优先处理库存盘点单！");
        }

        LambdaUpdateWrapper<TakeStockPlan> updateWrapper = Wrappers.lambdaUpdate(TakeStockPlan.class).set(TakeStockPlan::getTakeStatus, TakeStockPlanStatus.DIFF_CREATED).eq(TakeStockPlan::getId, data.getId()).eq(TakeStockPlan::getTakeStatus, TakeStockPlanStatus.CREATED);
        if (takeStockPlanMapper.update(updateWrapper) != 1) {
            throw new DefaultClientException("盘点任务信息已过期，请刷新重试！");
        }

        Wrapper<TakeStockPlanDetail> queryDetailWrapper = Wrappers.lambdaQuery(TakeStockPlanDetail.class).eq(TakeStockPlanDetail::getPlanId, data.getId()).orderByAsc(TakeStockPlanDetail::getOrderNo);
        List<TakeStockPlanDetail> details = takeStockPlanDetailMapper.selectList(queryDetailWrapper);
        if (CollectionUtil.isEmpty(details)) {
            throw new DefaultClientException("盘点任务不存在商品信息，不允许执行差异生成操作！");
        }
        for (TakeStockPlanDetail detail : details) {
            if (detail.getOriTakeNum() != null) {
                continue;
            }
            LambdaUpdateWrapper<TakeStockPlanDetail> updateDetailWrapper = Wrappers.lambdaUpdate(TakeStockPlanDetail.class).set(TakeStockPlanDetail::getOriTakeNum, 0).eq(TakeStockPlanDetail::getId, detail.getId());

            takeStockPlanDetailMapper.update(updateDetailWrapper);
        }
    }

    @OpLog(type = OpLogType.OTHER, name = "差异处理，盘点任务ID：{}", params = {"#id"})
    @Transactional
    @Override
    public void handleDiff(HandleTakeStockPlanVo vo) {

        TakeStockPlan data = takeStockPlanMapper.selectById(vo.getId());
        if (data == null) {
            throw new DefaultClientException("盘点任务不存在！");
        }

        LambdaUpdateWrapper<TakeStockPlan> updateWrapper = Wrappers.lambdaUpdate(TakeStockPlan.class).set(TakeStockPlan::getTakeStatus, TakeStockPlanStatus.FINISHED).eq(TakeStockPlan::getId, data.getId()).eq(TakeStockPlan::getTakeStatus, TakeStockPlanStatus.DIFF_CREATED);
        if (takeStockPlanMapper.update(updateWrapper) != 1) {
            throw new DefaultClientException("盘点任务信息已过期，请刷新重试！");
        }

        TakeStockConfigDto config = takeStockConfigService.get();

        Wrapper<TakeStockPlanDetail> queryDetailWrapper = Wrappers.lambdaQuery(TakeStockPlanDetail.class).eq(TakeStockPlanDetail::getPlanId, data.getId()).orderByAsc(TakeStockPlanDetail::getOrderNo);
        List<TakeStockPlanDetail> details = takeStockPlanDetailMapper.selectList(queryDetailWrapper);
        if (CollectionUtil.isEmpty(details)) {
            throw new DefaultClientException("盘点任务不存在商品信息，不允许执行差异处理操作！");
        }

        for (TakeStockPlanDetail detail : details) {
            HandleTakeStockPlanVo.ProductVo productVo = vo.getProducts().stream().filter(t -> t.getProductId().equals(detail.getProductId())).findFirst().get();
            if (config.getAllowChangeNum()) {
                // 如果允许修改盘点数量
                detail.setTakeNum(productVo.getTakeNum());
            } else {
                // 如果允许自动调整，那么盘点数量=盘点单的盘点数量 - 进项数量 + 出项数量，否则就等于盘点单的盘点数量
                detail.setTakeNum(config.getAutoChangeStock() ? detail.getOriTakeNum() - detail.getTotalInNum() + detail.getTotalOutNum() : detail.getOriTakeNum());
            }

            LambdaUpdateWrapper<TakeStockPlanDetail> updateDetailWrapper = Wrappers.lambdaUpdate(TakeStockPlanDetail.class).set(TakeStockPlanDetail::getTakeNum, detail.getTakeNum()).eq(TakeStockPlanDetail::getId, detail.getId());
            takeStockPlanDetailMapper.update(updateDetailWrapper);
        }

        // 进行出入库操作
        for (TakeStockPlanDetail detail : details) {
            if (!NumberUtil.equal(detail.getStockNum(), detail.getTakeNum() )) {
                if (NumberUtil.lt(detail.getStockNum(), detail.getTakeNum())) {
                    ProductPurchaseDto productPurchase = productPurchaseService.getById(detail.getProductId());
                    // 如果库存数量小于盘点数量，则报溢
                    AddProductStockVo addProductStockVo = new AddProductStockVo();
                    addProductStockVo.setProductId(detail.getProductId());
                    addProductStockVo.setScId(data.getScId());
                    //addProductStockVo.setSupplierId();
                    addProductStockVo.setStockNum(detail.getTakeNum() - detail.getStockNum());
                    //如果从来没有库存的话，按照采购价入库
                    addProductStockVo.setDefaultTaxAmount(NumberUtil.getNumber(NumberUtil.mul(productPurchase.getPrice(), addProductStockVo.getStockNum()), 2));
                    //addProductStockVo.setTaxRate();
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

    @OpLog(type = OpLogType.OTHER, name = "作废盘点任务，ID：{}", params = {"#id"})
    @Transactional
    @Override
    public void cancel(CancelTakeStockPlanVo vo) {
        TakeStockPlan data = takeStockPlanMapper.selectById(vo.getId());
        if (data == null) {
            throw new DefaultClientException("盘点任务不存在！");
        }

        LambdaUpdateWrapper<TakeStockPlan> updateWrapper = Wrappers.lambdaUpdate(TakeStockPlan.class).set(TakeStockPlan::getTakeStatus, TakeStockPlanStatus.CANCELED).eq(TakeStockPlan::getId, data.getId()).in(TakeStockPlan::getTakeStatus, TakeStockPlanStatus.CREATED, TakeStockPlanStatus.DIFF_CREATED);
        if (takeStockPlanMapper.update(updateWrapper) != 1) {
            throw new DefaultClientException("盘点任务信息已过期，请刷新重试！");
        }

        OpLogUtil.setVariable("id", vo.getId());
        OpLogUtil.setExtra(vo);
    }

    @OpLog(type = OpLogType.OTHER, name = "删除盘点任务，ID：{}", params = {"#id"})
    @Transactional
    @Override
    public void deleteById(String id) {
        TakeStockPlan data = takeStockPlanMapper.selectById(id);
        if (data == null) {
            throw new DefaultClientException("盘点任务不存在！");
        }

        Wrapper<TakeStockPlan> deleteWrapper = Wrappers.lambdaQuery(TakeStockPlan.class).eq(TakeStockPlan::getId, data.getId()).eq(TakeStockPlan::getTakeStatus, TakeStockPlanStatus.CANCELED);
        if (takeStockPlanMapper.delete(deleteWrapper) != 1) {
            throw new DefaultClientException("盘点任务信息已过期，请刷新重试！");
        }

        Wrapper<TakeStockPlanDetail> deleteDetailWrapper = Wrappers.lambdaQuery(TakeStockPlanDetail.class).eq(TakeStockPlanDetail::getPlanId, data.getId());
        takeStockPlanDetailMapper.delete(deleteDetailWrapper);

        DeleteTakeStockPlanEvent deleteEvent = new DeleteTakeStockPlanEvent(this, data.getId());
        ApplicationUtil.publishEvent(deleteEvent);
    }

    @Override
    public void cleanCacheByKey(String key) {

    }
}

package com.lframework.xingyun.api.controller.selector;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.xingyun.api.bo.basedata.customer.CustomerSelectorBo;
import com.lframework.xingyun.api.bo.basedata.member.MemberSelectorBo;
import com.lframework.xingyun.api.bo.basedata.product.brand.ProductBrandSelectorBo;
import com.lframework.xingyun.api.bo.basedata.product.brand.ProductCategorySelectorBo;
import com.lframework.xingyun.api.bo.basedata.product.saleprop.ProductSalePropGroupSelectorBo;
import com.lframework.xingyun.api.bo.basedata.product.saleprop.item.ProductSalePropItemSelectorBo;
import com.lframework.xingyun.api.bo.basedata.shop.ShopSelectorBo;
import com.lframework.xingyun.api.bo.basedata.storecenter.StoreCenterSelectorBo;
import com.lframework.xingyun.api.bo.basedata.supplier.SupplierSelectorBo;
import com.lframework.xingyun.api.bo.dic.CitySelectorBo;
import com.lframework.xingyun.api.bo.purchase.PurchaseOrderSelectorBo;
import com.lframework.xingyun.api.bo.purchase.receive.ReceiveSheetSelectorBo;
import com.lframework.xingyun.api.bo.settle.item.in.SettleInItemSelectorBo;
import com.lframework.xingyun.api.bo.settle.item.out.SettleOutItemSelectorBo;
import com.lframework.xingyun.api.bo.stock.take.plan.TakeStockPlanSelectorBo;
import com.lframework.xingyun.api.bo.stock.take.pre.PreTakeStockSheetSelectorBo;
import com.lframework.xingyun.basedata.entity.Customer;
import com.lframework.xingyun.basedata.entity.Member;
import com.lframework.xingyun.basedata.entity.ProductBrand;
import com.lframework.xingyun.basedata.entity.ProductCategory;
import com.lframework.xingyun.basedata.entity.ProductSalePropGroup;
import com.lframework.xingyun.basedata.entity.ProductSalePropItem;
import com.lframework.xingyun.basedata.entity.Shop;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.entity.Supplier;
import com.lframework.xingyun.basedata.service.customer.ICustomerService;
import com.lframework.xingyun.basedata.service.member.IMemberService;
import com.lframework.xingyun.basedata.service.product.IProductBrandService;
import com.lframework.xingyun.basedata.service.product.IProductCategoryService;
import com.lframework.xingyun.basedata.service.product.IProductSalePropGroupService;
import com.lframework.xingyun.basedata.service.product.IProductSalePropItemService;
import com.lframework.xingyun.basedata.service.shop.IShopService;
import com.lframework.xingyun.basedata.service.storecenter.IStoreCenterService;
import com.lframework.xingyun.basedata.service.supplier.ISupplierService;
import com.lframework.xingyun.basedata.vo.customer.QueryCustomerSelectorVo;
import com.lframework.xingyun.basedata.vo.member.QueryMemberSelectorVo;
import com.lframework.xingyun.basedata.vo.product.brand.QueryProductBrandSelectorVo;
import com.lframework.xingyun.basedata.vo.product.category.QueryProductCategorySelectorVo;
import com.lframework.xingyun.basedata.vo.product.saleprop.QueryProductSalePropGroupSelectorVo;
import com.lframework.xingyun.basedata.vo.product.saleprop.item.QueryProductSalePropItemSelectorVo;
import com.lframework.xingyun.basedata.vo.shop.ShopSelectorVo;
import com.lframework.xingyun.basedata.vo.storecenter.QueryStoreCenterSelectorVo;
import com.lframework.xingyun.basedata.vo.supplier.QuerySupplierSelectorVo;
import com.lframework.xingyun.core.dto.dic.city.DicCityDto;
import com.lframework.xingyun.core.service.IDicCityService;
import com.lframework.xingyun.sc.dto.stock.take.plan.TakeStockPlanSelectorDto;
import com.lframework.xingyun.sc.dto.stock.take.pre.PreTakeStockSheetSelectorDto;
import com.lframework.xingyun.sc.entity.PurchaseOrder;
import com.lframework.xingyun.sc.entity.ReceiveSheet;
import com.lframework.xingyun.sc.service.purchase.IPurchaseOrderService;
import com.lframework.xingyun.sc.service.purchase.IReceiveSheetService;
import com.lframework.xingyun.sc.service.stock.take.IPreTakeStockSheetService;
import com.lframework.xingyun.sc.service.stock.take.ITakeStockPlanService;
import com.lframework.xingyun.sc.vo.purchase.PurchaseOrderSelectorVo;
import com.lframework.xingyun.sc.vo.purchase.receive.ReceiveSheetSelectorVo;
import com.lframework.xingyun.sc.vo.stock.take.plan.TakeStockPlanSelectorVo;
import com.lframework.xingyun.sc.vo.stock.take.pre.PreTakeStockSheetSelectorVo;
import com.lframework.xingyun.settle.entity.SettleInItem;
import com.lframework.xingyun.settle.entity.SettleOutItem;
import com.lframework.xingyun.settle.service.ISettleInItemService;
import com.lframework.xingyun.settle.service.ISettleOutItemService;
import com.lframework.xingyun.settle.vo.item.in.SettleInItemSelectorVo;
import com.lframework.xingyun.settle.vo.item.out.SettleOutItemSelectorVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据选择器
 *
 * @author zmj
 */
@Api(tags = "数据选择器")
@Validated
@RestController
@RequestMapping("/selector")
public class SelectorController extends DefaultBaseController {

  @Autowired
  private IProductBrandService productBrandService;

  @Autowired
  private IProductCategoryService productCategoryService;

  @Autowired
  private IProductSalePropGroupService productSalePropGroupService;

  @Autowired
  private IProductSalePropItemService productSalePropItemService;

  @Autowired
  private IMemberService memberService;

  @Autowired
  private IStoreCenterService storeCenterService;

  @Autowired
  private ISupplierService supplierService;

  @Autowired
  private IPurchaseOrderService purchaseOrderService;

  @Autowired
  private IReceiveSheetService receiveSheetService;

  @Autowired
  private ICustomerService customerService;

  @Autowired
  private ISettleInItemService settleInItemService;

  @Autowired
  private ISettleOutItemService settleOutItemService;

  @Autowired
  private IDicCityService dicCityService;

  @Autowired
  private ITakeStockPlanService takeStockPlanService;

  @Autowired
  private IPreTakeStockSheetService preTakeStockSheetService;

  @Autowired
  private IShopService shopService;

  /**
   * 城市数据
   */
  @ApiOperation("城市数据")
  @GetMapping("/city")
  public InvokeResult<List<CitySelectorBo>> dicCity() {

    List<DicCityDto> datas = dicCityService.getAll();
    List<CitySelectorBo> results = Collections.EMPTY_LIST;
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(CitySelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(results);
  }

  /**
   * 品牌
   */
  @ApiOperation("品牌")
  @GetMapping("/brand")
  public InvokeResult<PageResult<ProductBrandSelectorBo>> brand(
      @Valid QueryProductBrandSelectorVo vo) {

    PageResult<ProductBrand> pageResult = productBrandService.selector(getPageIndex(vo),
        getPageSize(vo), vo);
    List<ProductBrand> datas = pageResult.getDatas();
    List<ProductBrandSelectorBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(ProductBrandSelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 类目
   */
  @ApiOperation("类目")
  @GetMapping("/category")
  public InvokeResult<List<ProductCategorySelectorBo>> category(
      @Valid QueryProductCategorySelectorVo vo) {

    List<ProductCategory> datas = productCategoryService.selector(vo);
    List<ProductCategorySelectorBo> results = Collections.EMPTY_LIST;
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(ProductCategorySelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(results);
  }

  /**
   * 销售属性组
   */
  @ApiOperation("销售属性组")
  @GetMapping("/salepropgroup")
  public InvokeResult<PageResult<ProductSalePropGroupSelectorBo>> salePropGroup(
      @Valid QueryProductSalePropGroupSelectorVo vo) {

    PageResult<ProductSalePropGroup> pageResult = productSalePropGroupService.selector(
        getPageIndex(vo), getPageSize(vo), vo);
    List<ProductSalePropGroup> datas = pageResult.getDatas();
    List<ProductSalePropGroupSelectorBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(ProductSalePropGroupSelectorBo::new)
          .collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 销售属性
   */
  @ApiOperation("销售属性")
  @GetMapping("/saleprop")
  public InvokeResult<PageResult<ProductSalePropItemSelectorBo>> salePropItem(
      @Valid QueryProductSalePropItemSelectorVo vo) {

    PageResult<ProductSalePropItem> pageResult = productSalePropItemService.selector(
        getPageIndex(vo), getPageSize(vo), vo);
    List<ProductSalePropItem> datas = pageResult.getDatas();
    List<ProductSalePropItemSelectorBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(ProductSalePropItemSelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 会员
   */
  @ApiOperation("会员")
  @GetMapping("/member")
  public InvokeResult<PageResult<MemberSelectorBo>> selector(@Valid QueryMemberSelectorVo vo) {

    PageResult<Member> pageResult = memberService.selector(getPageIndex(vo), getPageSize(vo), vo);
    List<Member> datas = pageResult.getDatas();
    List<MemberSelectorBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(MemberSelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 仓库
   */
  @ApiOperation("仓库")
  @GetMapping("/sc")
  public InvokeResult<PageResult<StoreCenterSelectorBo>> selector(
      @Valid QueryStoreCenterSelectorVo vo) {

    PageResult<StoreCenter> pageResult = storeCenterService.selector(getPageIndex(vo),
        getPageSize(vo), vo);
    List<StoreCenter> datas = pageResult.getDatas();
    List<StoreCenterSelectorBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(StoreCenterSelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 供应商
   */
  @ApiOperation("供应商")
  @GetMapping("/supplier")
  public InvokeResult<PageResult<SupplierSelectorBo>> selector(@Valid QuerySupplierSelectorVo vo) {

    PageResult<Supplier> pageResult = supplierService.selector(getPageIndex(vo), getPageSize(vo),
        vo);
    List<Supplier> datas = pageResult.getDatas();
    List<SupplierSelectorBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(SupplierSelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 采购订单
   */
  @ApiOperation("采购订单")
  @GetMapping("/purchaseorder")
  public InvokeResult<PageResult<PurchaseOrderSelectorBo>> selector(
      @Valid PurchaseOrderSelectorVo vo) {

    PageResult<PurchaseOrder> pageResult = purchaseOrderService.selector(getPageIndex(vo),
        getPageSize(vo), vo);

    List<PurchaseOrder> datas = pageResult.getDatas();
    List<PurchaseOrderSelectorBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(PurchaseOrderSelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 采购收货单
   */
  @ApiOperation("采购收货单")
  @GetMapping("/receivesheet")
  public InvokeResult<PageResult<ReceiveSheetSelectorBo>> selector(
      @Valid ReceiveSheetSelectorVo vo) {

    PageResult<ReceiveSheet> pageResult = receiveSheetService.selector(getPageIndex(vo),
        getPageSize(vo), vo);

    List<ReceiveSheet> datas = pageResult.getDatas();
    List<ReceiveSheetSelectorBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(ReceiveSheetSelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 客户
   */
  @ApiOperation("客户")
  @GetMapping("/customer")
  public InvokeResult<PageResult<CustomerSelectorBo>> selector(@Valid QueryCustomerSelectorVo vo) {

    PageResult<Customer> pageResult = customerService.selector(getPageIndex(vo), getPageSize(vo),
        vo);

    List<Customer> datas = pageResult.getDatas();
    List<CustomerSelectorBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(CustomerSelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 收入项目
   */
  @ApiOperation("收入项目")
  @GetMapping("/settle/item/in")
  public InvokeResult<PageResult<SettleInItemSelectorBo>> selector(
      @Valid SettleInItemSelectorVo vo) {

    PageResult<SettleInItem> pageResult = settleInItemService.selector(getPageIndex(vo),
        getPageSize(vo), vo);

    List<SettleInItem> datas = pageResult.getDatas();
    List<SettleInItemSelectorBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {

      results = datas.stream().map(SettleInItemSelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 支出项目
   */
  @ApiOperation("支出项目")
  @GetMapping("/settle/item/out")
  public InvokeResult<PageResult<SettleOutItemSelectorBo>> selector(
      @Valid SettleOutItemSelectorVo vo) {

    PageResult<SettleOutItem> pageResult = settleOutItemService.selector(getPageIndex(vo),
        getPageSize(vo), vo);

    List<SettleOutItem> datas = pageResult.getDatas();
    List<SettleOutItemSelectorBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(SettleOutItemSelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 盘点任务
   */
  @ApiOperation("盘点任务")
  @GetMapping("/takestock/plan")
  public InvokeResult<PageResult<TakeStockPlanSelectorBo>> selector(
      @Valid TakeStockPlanSelectorVo vo) {

    PageResult<TakeStockPlanSelectorDto> pageResult = takeStockPlanService.selector(
        getPageIndex(vo), getPageSize(vo), vo);

    List<TakeStockPlanSelectorDto> datas = pageResult.getDatas();
    List<TakeStockPlanSelectorBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(TakeStockPlanSelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 预先盘点单
   */
  @ApiOperation("预先盘点单")
  @GetMapping("/takestock/pre")
  public InvokeResult<PageResult<PreTakeStockSheetSelectorBo>> selector(
      @Valid PreTakeStockSheetSelectorVo vo) {

    PageResult<PreTakeStockSheetSelectorDto> pageResult = preTakeStockSheetService.selector(
        getPageIndex(vo), getPageSize(vo), vo);

    List<PreTakeStockSheetSelectorDto> datas = pageResult.getDatas();
    List<PreTakeStockSheetSelectorBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(PreTakeStockSheetSelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 预先盘点单
   */
  @ApiOperation("门店")
  @GetMapping("/shop")
  public InvokeResult<PageResult<ShopSelectorBo>> selector(@Valid ShopSelectorVo vo) {

    LambdaQueryWrapper<Shop> queryWrapper = Wrappers.lambdaQuery(Shop.class)
        .orderByAsc(Shop::getCode);
    if (!StringUtil.isEmpty(vo.getCode())) {
      queryWrapper.eq(Shop::getCode, vo.getCode());
    }
    if (!StringUtil.isEmpty(vo.getName())) {
      queryWrapper.like(Shop::getName, vo.getName());
    }
    if (vo.getAvailable() != null) {
      queryWrapper.eq(Shop::getAvailable, vo.getAvailable());
    }

    Page<Shop> page = new Page<>(getPageIndex(vo), getPageSize(vo));
    page = shopService.page(page, queryWrapper);

    PageResult<Shop> pageResult = PageResultUtil.convert(page);

    List<Shop> datas = pageResult.getDatas();
    List<ShopSelectorBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(ShopSelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }
}

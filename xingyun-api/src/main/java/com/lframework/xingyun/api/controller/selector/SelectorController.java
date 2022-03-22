package com.lframework.xingyun.api.controller.selector;

import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.xingyun.api.bo.basedata.customer.CustomerSelectorBo;
import com.lframework.xingyun.api.bo.basedata.member.MemberSelectorBo;
import com.lframework.xingyun.api.bo.basedata.product.brand.ProductBrandSelectorBo;
import com.lframework.xingyun.api.bo.basedata.product.brand.ProductCategorySelectorBo;
import com.lframework.xingyun.api.bo.basedata.product.saleprop.ProductSalePropGroupSelectorBo;
import com.lframework.xingyun.api.bo.basedata.storecenter.StoreCenterSelectorBo;
import com.lframework.xingyun.api.bo.basedata.supplier.SupplierSelectorBo;
import com.lframework.xingyun.api.bo.dic.CitySelectorBo;
import com.lframework.xingyun.api.bo.purchase.PurchaseOrderSelectorBo;
import com.lframework.xingyun.api.bo.purchase.receive.ReceiveSheetSelectorBo;
import com.lframework.xingyun.api.bo.settle.item.in.SettleInItemSelectorBo;
import com.lframework.xingyun.api.bo.settle.item.out.SettleOutItemSelectorBo;
import com.lframework.xingyun.api.bo.stock.take.plan.TakeStockPlanSelectorBo;
import com.lframework.xingyun.api.bo.stock.take.pre.PreTakeStockSheetSelectorBo;
import com.lframework.xingyun.basedata.dto.customer.CustomerDto;
import com.lframework.xingyun.basedata.dto.member.MemberDto;
import com.lframework.xingyun.basedata.dto.product.brand.ProductBrandDto;
import com.lframework.xingyun.basedata.dto.product.category.ProductCategoryDto;
import com.lframework.xingyun.basedata.dto.product.saleprop.ProductSalePropGroupDto;
import com.lframework.xingyun.basedata.dto.storecenter.StoreCenterDto;
import com.lframework.xingyun.basedata.dto.supplier.SupplierDto;
import com.lframework.xingyun.basedata.service.customer.ICustomerService;
import com.lframework.xingyun.basedata.service.member.IMemberService;
import com.lframework.xingyun.basedata.service.product.IProductBrandService;
import com.lframework.xingyun.basedata.service.product.IProductCategoryService;
import com.lframework.xingyun.basedata.service.product.IProductSalePropGroupService;
import com.lframework.xingyun.basedata.service.storecenter.IStoreCenterService;
import com.lframework.xingyun.basedata.service.supplier.ISupplierService;
import com.lframework.xingyun.basedata.vo.customer.QueryCustomerSelectorVo;
import com.lframework.xingyun.basedata.vo.member.QueryMemberSelectorVo;
import com.lframework.xingyun.basedata.vo.product.brand.QueryProductBrandSelectorVo;
import com.lframework.xingyun.basedata.vo.product.category.QueryProductCategorySelectorVo;
import com.lframework.xingyun.basedata.vo.product.saleprop.QueryProductSalePropGroupSelectorVo;
import com.lframework.xingyun.basedata.vo.storecenter.QueryStoreCenterSelectorVo;
import com.lframework.xingyun.basedata.vo.supplier.QuerySupplierSelectorVo;
import com.lframework.xingyun.core.dto.dic.city.DicCityDto;
import com.lframework.xingyun.core.service.IDicCityService;
import com.lframework.xingyun.sc.dto.purchase.PurchaseOrderDto;
import com.lframework.xingyun.sc.dto.purchase.receive.ReceiveSheetDto;
import com.lframework.xingyun.sc.dto.stock.take.plan.TakeStockPlanSelectorDto;
import com.lframework.xingyun.sc.dto.stock.take.pre.PreTakeStockSheetSelectorDto;
import com.lframework.xingyun.sc.service.purchase.IPurchaseOrderService;
import com.lframework.xingyun.sc.service.purchase.IReceiveSheetService;
import com.lframework.xingyun.sc.service.stock.take.IPreTakeStockSheetService;
import com.lframework.xingyun.sc.service.stock.take.ITakeStockPlanService;
import com.lframework.xingyun.sc.vo.purchase.PurchaseOrderSelectorVo;
import com.lframework.xingyun.sc.vo.purchase.receive.ReceiveSheetSelectorVo;
import com.lframework.xingyun.sc.vo.stock.take.plan.TakeStockPlanSelectorVo;
import com.lframework.xingyun.sc.vo.stock.take.pre.PreTakeStockSheetSelectorVo;
import com.lframework.xingyun.settle.dto.item.in.SettleInItemDto;
import com.lframework.xingyun.settle.dto.item.out.SettleOutItemDto;
import com.lframework.xingyun.settle.service.ISettleInItemService;
import com.lframework.xingyun.settle.service.ISettleOutItemService;
import com.lframework.xingyun.settle.vo.item.in.SettleInItemSelectorVo;
import com.lframework.xingyun.settle.vo.item.out.SettleOutItemSelectorVo;
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

  /**
   * 城市数据
   */
  @GetMapping("/city")
  public InvokeResult dicCity() {

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
  @GetMapping("/brand")
  public InvokeResult brand(@Valid QueryProductBrandSelectorVo vo) {

    PageResult<ProductBrandDto> pageResult = productBrandService
        .selector(getPageIndex(vo), getPageSize(vo), vo);
    List<ProductBrandDto> datas = pageResult.getDatas();

    if (!CollectionUtil.isEmpty(datas)) {
      List<ProductBrandSelectorBo> results = datas.stream().map(ProductBrandSelectorBo::new)
          .collect(Collectors.toList());

      PageResultUtil.rebuild(pageResult, results);
    }

    return InvokeResultBuilder.success(pageResult);
  }

  /**
   * 类目
   */
  @GetMapping("/category")
  public InvokeResult category(@Valid QueryProductCategorySelectorVo vo) {

    List<ProductCategoryDto> datas = productCategoryService.selector(vo);
    List<ProductCategorySelectorBo> results = Collections.EMPTY_LIST;
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(ProductCategorySelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(results);
  }

  /**
   * 销售属性组
   */
  @GetMapping("/salepropgroup")
  public InvokeResult salePropGroup(@Valid QueryProductSalePropGroupSelectorVo vo) {

    PageResult<ProductSalePropGroupDto> pageResult = productSalePropGroupService
        .selector(getPageIndex(vo), getPageSize(vo), vo);
    List<ProductSalePropGroupDto> datas = pageResult.getDatas();

    if (!CollectionUtil.isEmpty(datas)) {
      List<ProductSalePropGroupSelectorBo> results = datas.stream()
          .map(ProductSalePropGroupSelectorBo::new)
          .collect(Collectors.toList());

      PageResultUtil.rebuild(pageResult, results);
    }

    return InvokeResultBuilder.success(pageResult);
  }

  /**
   * 会员
   */
  @GetMapping("/member")
  public InvokeResult selector(@Valid QueryMemberSelectorVo vo) {

    PageResult<MemberDto> pageResult = memberService
        .selector(getPageIndex(vo), getPageSize(vo), vo);
    List<MemberDto> datas = pageResult.getDatas();

    if (!CollectionUtil.isEmpty(datas)) {
      List<MemberSelectorBo> results = datas.stream().map(MemberSelectorBo::new)
          .collect(Collectors.toList());

      PageResultUtil.rebuild(pageResult, results);
    }

    return InvokeResultBuilder.success(pageResult);
  }

  /**
   * 仓库
   */
  @GetMapping("/sc")
  public InvokeResult selector(@Valid QueryStoreCenterSelectorVo vo) {

    PageResult<StoreCenterDto> pageResult = storeCenterService
        .selector(getPageIndex(vo), getPageSize(vo), vo);
    List<StoreCenterDto> datas = pageResult.getDatas();

    if (!CollectionUtil.isEmpty(datas)) {
      List<StoreCenterSelectorBo> results = datas.stream().map(StoreCenterSelectorBo::new)
          .collect(Collectors.toList());

      PageResultUtil.rebuild(pageResult, results);
    }

    return InvokeResultBuilder.success(pageResult);
  }

  /**
   * 供应商
   */
  @GetMapping("/supplier")
  public InvokeResult selector(@Valid QuerySupplierSelectorVo vo) {

    PageResult<SupplierDto> pageResult = supplierService
        .selector(getPageIndex(vo), getPageSize(vo), vo);
    List<SupplierDto> datas = pageResult.getDatas();

    if (!CollectionUtil.isEmpty(datas)) {
      List<SupplierSelectorBo> results = datas.stream().map(SupplierSelectorBo::new)
          .collect(Collectors.toList());

      PageResultUtil.rebuild(pageResult, results);
    }

    return InvokeResultBuilder.success(pageResult);
  }

  /**
   * 采购订单
   */
  @GetMapping("/purchaseorder")
  public InvokeResult selector(@Valid PurchaseOrderSelectorVo vo) {

    PageResult<PurchaseOrderDto> pageResult = purchaseOrderService
        .selector(getPageIndex(vo), getPageSize(vo), vo);

    List<PurchaseOrderDto> datas = pageResult.getDatas();

    if (!CollectionUtil.isEmpty(datas)) {
      List<PurchaseOrderSelectorBo> results = datas.stream().map(PurchaseOrderSelectorBo::new)
          .collect(Collectors.toList());

      PageResultUtil.rebuild(pageResult, results);
    }

    return InvokeResultBuilder.success(pageResult);
  }

  /**
   * 采购收货单
   */
  @GetMapping("/receivesheet")
  public InvokeResult selector(@Valid ReceiveSheetSelectorVo vo) {

    PageResult<ReceiveSheetDto> pageResult = receiveSheetService
        .selector(getPageIndex(vo), getPageSize(vo), vo);

    List<ReceiveSheetDto> datas = pageResult.getDatas();

    if (!CollectionUtil.isEmpty(datas)) {
      List<ReceiveSheetSelectorBo> results = datas.stream().map(ReceiveSheetSelectorBo::new)
          .collect(Collectors.toList());

      PageResultUtil.rebuild(pageResult, results);
    }

    return InvokeResultBuilder.success(pageResult);
  }

  /**
   * 客户
   */
  @GetMapping("/customer")
  public InvokeResult selector(@Valid QueryCustomerSelectorVo vo) {

    PageResult<CustomerDto> pageResult = customerService
        .selector(getPageIndex(vo), getPageSize(vo), vo);

    List<CustomerDto> datas = pageResult.getDatas();

    if (!CollectionUtil.isEmpty(datas)) {
      List<CustomerSelectorBo> results = datas.stream().map(CustomerSelectorBo::new)
          .collect(Collectors.toList());

      PageResultUtil.rebuild(pageResult, results);
    }

    return InvokeResultBuilder.success(pageResult);
  }

  /**
   * 收入项目
   */
  @GetMapping("/settle/item/in")
  public InvokeResult selector(@Valid SettleInItemSelectorVo vo) {

    PageResult<SettleInItemDto> pageResult = settleInItemService
        .selector(getPageIndex(vo), getPageSize(vo), vo);

    List<SettleInItemDto> datas = pageResult.getDatas();

    if (!CollectionUtil.isEmpty(datas)) {
      List<SettleInItemSelectorBo> results = datas.stream().map(SettleInItemSelectorBo::new)
          .collect(Collectors.toList());

      PageResultUtil.rebuild(pageResult, results);
    }

    return InvokeResultBuilder.success(pageResult);
  }

  /**
   * 支出项目
   */
  @GetMapping("/settle/item/out")
  public InvokeResult selector(@Valid SettleOutItemSelectorVo vo) {

    PageResult<SettleOutItemDto> pageResult = settleOutItemService
        .selector(getPageIndex(vo), getPageSize(vo), vo);

    List<SettleOutItemDto> datas = pageResult.getDatas();

    if (!CollectionUtil.isEmpty(datas)) {
      List<SettleOutItemSelectorBo> results = datas.stream().map(SettleOutItemSelectorBo::new)
          .collect(Collectors.toList());

      PageResultUtil.rebuild(pageResult, results);
    }

    return InvokeResultBuilder.success(pageResult);
  }

  /**
   * 盘点任务
   *
   * @param vo
   * @return
   */
  @GetMapping("/takestock/plan")
  public InvokeResult selector(@Valid TakeStockPlanSelectorVo vo) {
    PageResult<TakeStockPlanSelectorDto> pageResult = takeStockPlanService
        .selector(getPageIndex(vo), getPageSize(vo), vo);

    List<TakeStockPlanSelectorDto> datas = pageResult.getDatas();

    if (!CollectionUtil.isEmpty(datas)) {
      List<TakeStockPlanSelectorBo> results = datas.stream().map(TakeStockPlanSelectorBo::new)
          .collect(Collectors.toList());

      PageResultUtil.rebuild(pageResult, results);
    }

    return InvokeResultBuilder.success(pageResult);
  }

  /**
   * 预先盘点单
   *
   * @param vo
   * @return
   */
  @GetMapping("/takestock/pre")
  public InvokeResult selector(@Valid PreTakeStockSheetSelectorVo vo) {
    PageResult<PreTakeStockSheetSelectorDto> pageResult = preTakeStockSheetService
        .selector(getPageIndex(vo), getPageSize(vo), vo);

    List<PreTakeStockSheetSelectorDto> datas = pageResult.getDatas();

    if (!CollectionUtil.isEmpty(datas)) {
      List<PreTakeStockSheetSelectorBo> results = datas.stream()
          .map(PreTakeStockSheetSelectorBo::new)
          .collect(Collectors.toList());

      PageResultUtil.rebuild(pageResult, results);
    }

    return InvokeResultBuilder.success(pageResult);
  }
}

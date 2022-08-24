package com.lframework.xingyun.basedata.api.controller;

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
import com.lframework.xingyun.basedata.api.bo.customer.CustomerSelectorBo;
import com.lframework.xingyun.basedata.api.bo.member.MemberSelectorBo;
import com.lframework.xingyun.basedata.api.bo.product.brand.ProductBrandSelectorBo;
import com.lframework.xingyun.basedata.api.bo.product.brand.ProductCategorySelectorBo;
import com.lframework.xingyun.basedata.api.bo.product.saleprop.ProductSalePropGroupSelectorBo;
import com.lframework.xingyun.basedata.api.bo.product.saleprop.item.ProductSalePropItemSelectorBo;
import com.lframework.xingyun.basedata.api.bo.shop.ShopSelectorBo;
import com.lframework.xingyun.basedata.api.bo.storecenter.StoreCenterSelectorBo;
import com.lframework.xingyun.basedata.api.bo.supplier.SupplierSelectorBo;
import com.lframework.xingyun.basedata.biz.service.customer.ICustomerService;
import com.lframework.xingyun.basedata.biz.service.member.IMemberService;
import com.lframework.xingyun.basedata.biz.service.product.IProductBrandService;
import com.lframework.xingyun.basedata.biz.service.product.IProductCategoryService;
import com.lframework.xingyun.basedata.biz.service.product.IProductSalePropGroupService;
import com.lframework.xingyun.basedata.biz.service.product.IProductSalePropItemService;
import com.lframework.xingyun.basedata.biz.service.shop.IShopService;
import com.lframework.xingyun.basedata.biz.service.storecenter.IStoreCenterService;
import com.lframework.xingyun.basedata.biz.service.supplier.ISupplierService;
import com.lframework.xingyun.basedata.facade.entity.Customer;
import com.lframework.xingyun.basedata.facade.entity.Member;
import com.lframework.xingyun.basedata.facade.entity.ProductBrand;
import com.lframework.xingyun.basedata.facade.entity.ProductCategory;
import com.lframework.xingyun.basedata.facade.entity.ProductSalePropGroup;
import com.lframework.xingyun.basedata.facade.entity.ProductSalePropItem;
import com.lframework.xingyun.basedata.facade.entity.Shop;
import com.lframework.xingyun.basedata.facade.entity.StoreCenter;
import com.lframework.xingyun.basedata.facade.entity.Supplier;
import com.lframework.xingyun.basedata.facade.vo.customer.QueryCustomerSelectorVo;
import com.lframework.xingyun.basedata.facade.vo.member.QueryMemberSelectorVo;
import com.lframework.xingyun.basedata.facade.vo.product.brand.QueryProductBrandSelectorVo;
import com.lframework.xingyun.basedata.facade.vo.product.category.QueryProductCategorySelectorVo;
import com.lframework.xingyun.basedata.facade.vo.product.saleprop.QueryProductSalePropGroupSelectorVo;
import com.lframework.xingyun.basedata.facade.vo.product.saleprop.item.QueryProductSalePropItemSelectorVo;
import com.lframework.xingyun.basedata.facade.vo.shop.ShopSelectorVo;
import com.lframework.xingyun.basedata.facade.vo.storecenter.QueryStoreCenterSelectorVo;
import com.lframework.xingyun.basedata.facade.vo.supplier.QuerySupplierSelectorVo;
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
 * 基础信息数据选择器
 *
 * @author zmj
 */
@Api(tags = "基础信息数据选择器")
@Validated
@RestController
@RequestMapping("/selector")
public class BaseDataSelectorController extends DefaultBaseController {

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
  private ICustomerService customerService;

  @Autowired
  private IShopService shopService;

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
   * 门店
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

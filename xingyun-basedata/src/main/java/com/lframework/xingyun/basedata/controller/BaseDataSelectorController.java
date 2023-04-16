package com.lframework.xingyun.basedata.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.web.components.validation.IsEnum;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.xingyun.basedata.bo.address.AddressSelectorBo;
import com.lframework.xingyun.basedata.bo.customer.CustomerSelectorBo;
import com.lframework.xingyun.basedata.bo.member.MemberSelectorBo;
import com.lframework.xingyun.basedata.bo.paytype.PayTypeSelectorBo;
import com.lframework.xingyun.basedata.bo.product.brand.ProductBrandSelectorBo;
import com.lframework.xingyun.basedata.bo.product.brand.ProductCategorySelectorBo;
import com.lframework.xingyun.basedata.bo.shop.ShopSelectorBo;
import com.lframework.xingyun.basedata.bo.storecenter.StoreCenterSelectorBo;
import com.lframework.xingyun.basedata.bo.supplier.SupplierSelectorBo;
import com.lframework.xingyun.basedata.entity.Address;
import com.lframework.xingyun.basedata.entity.Customer;
import com.lframework.xingyun.basedata.entity.Member;
import com.lframework.xingyun.basedata.entity.PayType;
import com.lframework.xingyun.basedata.entity.ProductBrand;
import com.lframework.xingyun.basedata.entity.ProductCategory;
import com.lframework.xingyun.basedata.entity.Shop;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.entity.Supplier;
import com.lframework.xingyun.basedata.enums.AddressEntityType;
import com.lframework.xingyun.basedata.enums.AddressType;
import com.lframework.xingyun.basedata.service.address.AddressService;
import com.lframework.xingyun.basedata.service.customer.CustomerService;
import com.lframework.xingyun.basedata.service.member.MemberService;
import com.lframework.xingyun.basedata.service.paytype.PayTypeService;
import com.lframework.xingyun.basedata.service.product.ProductBrandService;
import com.lframework.xingyun.basedata.service.product.ProductCategoryService;
import com.lframework.xingyun.basedata.service.shop.ShopService;
import com.lframework.xingyun.basedata.service.storecenter.StoreCenterService;
import com.lframework.xingyun.basedata.service.supplier.SupplierService;
import com.lframework.xingyun.basedata.vo.address.AddressSelectorVo;
import com.lframework.xingyun.basedata.vo.customer.QueryCustomerSelectorVo;
import com.lframework.xingyun.basedata.vo.member.QueryMemberSelectorVo;
import com.lframework.xingyun.basedata.vo.paytype.PayTypeSelectorVo;
import com.lframework.xingyun.basedata.vo.product.brand.QueryProductBrandSelectorVo;
import com.lframework.xingyun.basedata.vo.product.category.QueryProductCategorySelectorVo;
import com.lframework.xingyun.basedata.vo.shop.ShopSelectorVo;
import com.lframework.xingyun.basedata.vo.storecenter.QueryStoreCenterSelectorVo;
import com.lframework.xingyun.basedata.vo.supplier.QuerySupplierSelectorVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 基础数据数据选择器
 *
 * @author zmj
 */
@Api(tags = "基础数据数据选择器")
@Validated
@RestController
@RequestMapping("/selector")
public class BaseDataSelectorController extends DefaultBaseController {

  @Autowired
  private ProductBrandService productBrandService;

  @Autowired
  private ProductCategoryService productCategoryService;

  @Autowired
  private MemberService memberService;

  @Autowired
  private StoreCenterService storeCenterService;

  @Autowired
  private SupplierService supplierService;

  @Autowired
  private CustomerService customerService;

  @Autowired
  private ShopService shopService;

  @Autowired
  private AddressService addressService;

  @Autowired
  private PayTypeService payTypeService;

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
   * 加载品牌
   */
  @ApiOperation("加载品牌")
  @PostMapping("/brand/load")
  public InvokeResult<List<ProductBrandSelectorBo>> loadBrand(
      @RequestBody(required = false) List<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return InvokeResultBuilder.success(CollectionUtil.emptyList());
    }

    List<ProductBrand> datas = ids.stream().filter(StringUtil::isNotBlank)
        .map(t -> productBrandService.findById(t))
        .filter(Objects::nonNull).collect(Collectors.toList());
    List<ProductBrandSelectorBo> results = datas.stream().map(ProductBrandSelectorBo::new).collect(
        Collectors.toList());
    return InvokeResultBuilder.success(results);
  }

  /**
   * 类目
   */
  @ApiOperation("类目")
  @GetMapping("/category")
  public InvokeResult<List<ProductCategorySelectorBo>> category(
      @Valid QueryProductCategorySelectorVo vo) {

    List<ProductCategory> datas = productCategoryService.selector(vo);
    List<ProductCategorySelectorBo> results = CollectionUtil.emptyList();
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(ProductCategorySelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(results);
  }

  /**
   * 加载类目
   */
  @ApiOperation("加载类目")
  @PostMapping("/category/load")
  public InvokeResult<List<ProductCategorySelectorBo>> loadCustomList(
      @RequestBody(required = false) List<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return InvokeResultBuilder.success(CollectionUtil.emptyList());
    }

    List<ProductCategory> datas = ids.stream().filter(StringUtil::isNotBlank)
        .map(t -> productCategoryService.findById(t))
        .filter(Objects::nonNull).collect(Collectors.toList());
    List<ProductCategorySelectorBo> results = datas.stream()
        .map(ProductCategorySelectorBo::new).collect(
            Collectors.toList());
    return InvokeResultBuilder.success(results);
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
   * 加载会员
   */
  @ApiOperation("加载会员")
  @PostMapping("/member/load")
  public InvokeResult<List<MemberSelectorBo>> loadMember(
      @RequestBody(required = false) List<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return InvokeResultBuilder.success(CollectionUtil.emptyList());
    }

    List<Member> datas = ids.stream().filter(StringUtil::isNotBlank)
        .map(t -> memberService.findById(t))
        .filter(Objects::nonNull).collect(Collectors.toList());
    List<MemberSelectorBo> results = datas.stream().map(MemberSelectorBo::new).collect(
        Collectors.toList());
    return InvokeResultBuilder.success(results);
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
   * 加载仓库
   */
  @ApiOperation("加载仓库")
  @PostMapping("/sc/load")
  public InvokeResult<List<StoreCenterSelectorBo>> loadSc(
      @RequestBody(required = false) List<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return InvokeResultBuilder.success(CollectionUtil.emptyList());
    }

    List<StoreCenter> datas = ids.stream().filter(StringUtil::isNotBlank)
        .map(t -> storeCenterService.findById(t))
        .filter(Objects::nonNull).collect(Collectors.toList());
    List<StoreCenterSelectorBo> results = datas.stream().map(StoreCenterSelectorBo::new).collect(
        Collectors.toList());
    return InvokeResultBuilder.success(results);
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
   * 加载供应商
   */
  @ApiOperation("加载供应商")
  @PostMapping("/supplier/load")
  public InvokeResult<List<SupplierSelectorBo>> loadSupplier(
      @RequestBody(required = false) List<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return InvokeResultBuilder.success(CollectionUtil.emptyList());
    }

    List<Supplier> datas = ids.stream().filter(StringUtil::isNotBlank)
        .map(t -> supplierService.findById(t))
        .filter(Objects::nonNull).collect(Collectors.toList());
    List<SupplierSelectorBo> results = datas.stream().map(SupplierSelectorBo::new).collect(
        Collectors.toList());
    return InvokeResultBuilder.success(results);
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
   * 加载客户
   */
  @ApiOperation("加载客户")
  @PostMapping("/customer/load")
  public InvokeResult<List<CustomerSelectorBo>> loadCustomer(
      @RequestBody(required = false) List<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return InvokeResultBuilder.success(CollectionUtil.emptyList());
    }

    List<Customer> datas = ids.stream().filter(StringUtil::isNotBlank)
        .map(t -> customerService.findById(t))
        .filter(Objects::nonNull).collect(Collectors.toList());
    List<CustomerSelectorBo> results = datas.stream().map(CustomerSelectorBo::new).collect(
        Collectors.toList());
    return InvokeResultBuilder.success(results);
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

  /**
   * 加载门店
   */
  @ApiOperation("加载门店")
  @PostMapping("/shop/load")
  public InvokeResult<List<ShopSelectorBo>> loadShop(
      @RequestBody(required = false) List<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return InvokeResultBuilder.success(CollectionUtil.emptyList());
    }

    List<Shop> datas = ids.stream().filter(StringUtil::isNotBlank)
        .map(t -> shopService.findById(t))
        .filter(Objects::nonNull).collect(Collectors.toList());
    List<ShopSelectorBo> results = datas.stream().map(ShopSelectorBo::new)
        .collect(
            Collectors.toList());
    return InvokeResultBuilder.success(results);
  }

  /**
   * 地址
   */
  @ApiOperation("地址")
  @GetMapping("/address")
  public InvokeResult<PageResult<AddressSelectorBo>> selector(@Valid AddressSelectorVo vo) {

    PageResult<Address> pageResult = addressService.selector(getPageIndex(vo), getPageSize(vo),
        vo);

    List<Address> datas = pageResult.getDatas();
    List<AddressSelectorBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(AddressSelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 加载地址
   */
  @ApiOperation("加载地址")
  @PostMapping("/address/load")
  public InvokeResult<List<AddressSelectorBo>> loadAddress(
      @RequestBody(required = false) List<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return InvokeResultBuilder.success(CollectionUtil.emptyList());
    }

    List<Address> datas = ids.stream().filter(StringUtil::isNotBlank)
        .map(t -> addressService.findById(t))
        .filter(Objects::nonNull).collect(Collectors.toList());
    List<AddressSelectorBo> results = datas.stream().map(AddressSelectorBo::new)
        .collect(
            Collectors.toList());
    return InvokeResultBuilder.success(results);
  }

  /**
   * 默认地址
   */
  @ApiOperation("默认地址")
  @GetMapping("/address/default")
  public InvokeResult<AddressSelectorBo> selector(
      @NotBlank(message = "实体ID不能为空！") String entityId,
      @NotNull(message = "实体类型不能为空！") @IsEnum(message = "实体类型格式错误！", enumClass = AddressEntityType.class) Integer entityType,
      @NotNull(message = "地址类型不能为空！") @IsEnum(message = "地址类型格式错误！", enumClass = AddressType.class) Integer addressType) {

    Wrapper<Address> queryWrapper = Wrappers.lambdaQuery(Address.class)
        .eq(Address::getEntityId, entityId).eq(Address::getEntityType, entityType)
        .eq(Address::getAddressType, addressType)
        .eq(Address::getIsDefault, Boolean.TRUE);
    Address address = addressService.getOne(queryWrapper);

    return InvokeResultBuilder.success(address == null ? null : new AddressSelectorBo(address));
  }

  /**
   * 支付方式
   */
  @ApiOperation("支付方式")
  @GetMapping("/paytype")
  public InvokeResult<PageResult<PayTypeSelectorBo>> selector(@Valid PayTypeSelectorVo vo) {

    PageResult<PayType> pageResult = payTypeService.selector(getPageIndex(vo), getPageSize(vo),
        vo);

    List<PayType> datas = pageResult.getDatas();
    List<PayTypeSelectorBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(PayTypeSelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 加载支付方式
   */
  @ApiOperation("加载支付方式")
  @PostMapping("/paytype/load")
  public InvokeResult<List<PayTypeSelectorBo>> loadPayType(
      @RequestBody(required = false) List<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return InvokeResultBuilder.success(CollectionUtil.emptyList());
    }

    List<PayType> datas = ids.stream().filter(StringUtil::isNotBlank)
        .map(t -> payTypeService.findById(t))
        .filter(Objects::nonNull).collect(Collectors.toList());
    List<PayTypeSelectorBo> results = datas.stream().map(PayTypeSelectorBo::new)
        .collect(
            Collectors.toList());
    return InvokeResultBuilder.success(results);
  }
}

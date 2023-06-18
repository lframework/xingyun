package com.lframework.xingyun.basedata.excel.address;

import com.alibaba.excel.context.AnalysisContext;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.mybatis.components.excel.ExcelImportListener;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import com.lframework.starter.web.utils.EnumUtil;
import com.lframework.xingyun.basedata.entity.Customer;
import com.lframework.xingyun.basedata.entity.Member;
import com.lframework.xingyun.basedata.entity.Shop;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.entity.Supplier;
import com.lframework.xingyun.basedata.enums.AddressEntityType;
import com.lframework.xingyun.basedata.enums.AddressType;
import com.lframework.xingyun.basedata.service.address.AddressService;
import com.lframework.xingyun.basedata.service.customer.CustomerService;
import com.lframework.xingyun.basedata.service.member.MemberService;
import com.lframework.xingyun.basedata.service.shop.ShopService;
import com.lframework.xingyun.basedata.service.storecenter.StoreCenterService;
import com.lframework.xingyun.basedata.service.supplier.SupplierService;
import com.lframework.xingyun.basedata.vo.address.CreateAddressVo;
import com.lframework.xingyun.core.dto.dic.city.DicCityDto;
import com.lframework.xingyun.core.service.DicCityService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AddressImportListener extends ExcelImportListener<AddressImportModel> {

  @Override
  protected void doInvoke(AddressImportModel data, AnalysisContext context) {
    if (StringUtil.isBlank(data.getEntityCode())) {
      throw new DefaultClientException("第" + context.readRowHolder().getRowIndex() + "行“实体编号”不能为空");
    }
    AddressEntityType entityType = EnumUtil.getByDesc(AddressEntityType.class,
        data.getEntityType());
    if (entityType == null) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“实体类型”只能填写“" + CollectionUtil.join(
              EnumUtil.getDescs(AddressEntityType.class), "、") + "”");
    }
    data.setEntityTypeEnum(entityType);
    if (entityType == AddressEntityType.SC) {
      StoreCenterService storeCenterService = ApplicationUtil.getBean(StoreCenterService.class);
      Wrapper<StoreCenter> queryWrapper = Wrappers.lambdaQuery(StoreCenter.class)
          .eq(StoreCenter::getCode, data.getEntityCode());
      StoreCenter sc = storeCenterService.getOne(queryWrapper);
      if (sc == null) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“实体编号”不存在");
      }
      data.setEntityId(sc.getId());
    } else if (entityType == AddressEntityType.CUSTOMER) {
      CustomerService customerService = ApplicationUtil.getBean(CustomerService.class);
      Wrapper<Customer> queryWrapper = Wrappers.lambdaQuery(Customer.class)
          .eq(Customer::getCode, data.getEntityCode());
      Customer customer = customerService.getOne(queryWrapper);
      if (customer == null) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“实体编号”不存在");
      }
      data.setEntityId(customer.getId());
    } else if (entityType == AddressEntityType.SUPPLIER) {
      SupplierService supplierService = ApplicationUtil.getBean(SupplierService.class);
      Wrapper<Supplier> queryWrapper = Wrappers.lambdaQuery(Supplier.class)
          .eq(Supplier::getCode, data.getEntityCode());
      Supplier supplier = supplierService.getOne(queryWrapper);
      if (supplier == null) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“实体编号”不存在");
      }
      data.setEntityId(supplier.getId());
    } else if (entityType == AddressEntityType.MEMBER) {
      MemberService memberService = ApplicationUtil.getBean(MemberService.class);
      Wrapper<Member> queryWrapper = Wrappers.lambdaQuery(Member.class)
          .eq(Member::getCode, data.getEntityCode());
      Member member = memberService.getOne(queryWrapper);
      if (member == null) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“实体编号”不存在");
      }
      data.setEntityId(member.getId());
    } else if (entityType == AddressEntityType.SHOP) {
      ShopService shopService = ApplicationUtil.getBean(ShopService.class);
      Wrapper<Shop> queryWrapper = Wrappers.lambdaQuery(Shop.class)
          .eq(Shop::getCode, data.getEntityCode());
      Shop shop = shopService.getOne(queryWrapper);
      if (shop == null) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“实体编号”不存在");
      }
      data.setEntityId(shop.getId());
    }
    AddressType addressType = EnumUtil.getByDesc(AddressType.class,
        data.getAddressType());
    if (addressType == null) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“地址类型”只能填写“" + CollectionUtil.join(
              EnumUtil.getDescs(AddressType.class), "、") + "”");
    }
    data.setAddressTypeEnum(addressType);
    if (StringUtil.isBlank(data.getName())) {
      throw new DefaultClientException("第" + context.readRowHolder().getRowIndex() + "行“姓名”不能为空");
    }
    if (StringUtil.isBlank(data.getTelephone())) {
      throw new DefaultClientException("第" + context.readRowHolder().getRowIndex() + "行“联系电话”不能为空");
    }
    if (!StringUtil.isBlank(data.getCity())) {
      String[] arr = data.getCity().split("/");
      if (arr.length != 3) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“地区”格式错误，应为省/市/区（县），例如：北京市/市辖区/东城区");
      }

      DicCityService dicCityService = ApplicationUtil.getBean(DicCityService.class);
      List<DicCityDto> allCitys = dicCityService.getAll();
      DicCityDto province = allCitys.stream()
          .filter(t -> StringUtil.isEmpty(t.getParentId()) && t.getName().equals(arr[0]))
          .findFirst().orElse(null);
      if (province == null) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“地区”错误，省份不存在");
      }
      DicCityDto city = allCitys.stream()
          .filter(t -> province.getId().equals(t.getParentId()) && t.getName().equals(arr[1]))
          .findFirst().orElse(null);
      if (city == null) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“地区”错误，市不存在");
      }
      DicCityDto area = allCitys.stream()
          .filter(t -> city.getId().equals(t.getParentId()) && t.getName().equals(arr[2]))
          .findFirst().orElse(null);
      if (area == null) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“地区”错误，区（县）不存在");
      }

      data.setAreaId(area.getId());
    }
    if (StringUtil.isBlank(data.getAddress())) {
      throw new DefaultClientException("第" + context.readRowHolder().getRowIndex() + "行“详细地址”不能为空");
    }
    if (StringUtil.isBlank(data.getIsDefault())) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“是否默认地址”不能为空");
    }

    if (!"是".equals(data.getIsDefault()) && !"否".equals(data.getIsDefault())) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“是否默认地址”只能填写“是、否”");
    }
  }

  @Override
  protected void afterAllAnalysed(AnalysisContext context) {

    AddressService addressService = ApplicationUtil.getBean(AddressService.class);

    List<AddressImportModel> datas = this.getDatas();
    for (int i = 0; i < datas.size(); i++) {
      AddressImportModel data = datas.get(i);
      CreateAddressVo createAddressVo = new CreateAddressVo();
      createAddressVo.setEntityId(data.getEntityId());
      createAddressVo.setEntityType(data.getEntityTypeEnum().getCode());
      createAddressVo.setAddressType(data.getAddressTypeEnum().getCode());
      createAddressVo.setName(data.getName());
      createAddressVo.setTelephone(data.getTelephone());
      createAddressVo.setCityId(data.getAreaId());
      createAddressVo.setAddress(data.getAddress());
      createAddressVo.setIsDefault("是".equals(data.getIsDefault()));

      try {
        addressService.create(createAddressVo);
      } catch (Exception e) {
        throw new DefaultClientException(
            "第" + (i + 1) + "行新增失败，失败原因：" + e.getMessage());
      }

      this.setSuccessProcess(i);
    }
  }

  @Override
  protected void doComplete() {
  }
}

package com.lframework.xingyun.basedata.excel.address;

import com.alibaba.excel.annotation.ExcelProperty;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import com.lframework.starter.web.components.excel.ExcelModel;
import com.lframework.xingyun.basedata.entity.Address;
import com.lframework.xingyun.basedata.entity.Customer;
import com.lframework.xingyun.basedata.entity.Member;
import com.lframework.xingyun.basedata.entity.Shop;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.entity.Supplier;
import com.lframework.xingyun.basedata.enums.AddressEntityType;
import com.lframework.xingyun.basedata.service.customer.CustomerService;
import com.lframework.xingyun.basedata.service.member.MemberService;
import com.lframework.xingyun.basedata.service.shop.ShopService;
import com.lframework.xingyun.basedata.service.storecenter.StoreCenterService;
import com.lframework.xingyun.basedata.service.supplier.SupplierService;
import com.lframework.xingyun.core.dto.dic.city.DicCityDto;
import com.lframework.xingyun.core.service.DicCityService;
import lombok.Data;

@Data
public class AddressExportModel extends BaseBo<Address> implements ExcelModel {

  /**
   * 实体名称
   */
  @ExcelProperty("实体名称")
  private String entityName;

  /**
   * 实体类型
   */
  @ExcelProperty("实体类型")
  private String entityType;

  /**
   * 地址类型
   */
  @ExcelProperty("地址类型")
  private String addressType;

  /**
   * 姓名
   */
  @ExcelProperty("姓名")
  private String name;

  /**
   * 联系电话
   */
  @ExcelProperty("联系电话")
  private String telephone;

  /**
   * 省
   */
  @ExcelProperty("省")
  private String provinceName;

  /**
   * 市
   */
  @ExcelProperty("市")
  private String cityName;

  /**
   * 区
   */
  @ExcelProperty("区")
  private String districtName;

  /**
   * 详细地址
   */
  @ExcelProperty("详细地址")
  private String address;

  /**
   * 是否默认地址
   */
  @ExcelProperty("是否默认地址")
  private String isDefault;

  public AddressExportModel() {

  }

  public AddressExportModel(Address dto) {

    super(dto);
  }

  @Override
  protected void afterInit(Address dto) {
    if (dto.getEntityType() == AddressEntityType.SC) {
      StoreCenterService storeCenterService = ApplicationUtil.getBean(StoreCenterService.class);
      StoreCenter sc = storeCenterService.findById(dto.getEntityId());
      this.entityName = sc.getName();
    } else if (dto.getEntityType() == AddressEntityType.CUSTOMER) {
      CustomerService customerService = ApplicationUtil.getBean(CustomerService.class);
      Customer customer = customerService.findById(dto.getEntityId());
      this.entityName = customer.getName();
    } else if (dto.getEntityType() == AddressEntityType.SUPPLIER) {
      SupplierService supplierService = ApplicationUtil.getBean(SupplierService.class);
      Supplier supplier = supplierService.findById(dto.getEntityId());
      this.entityName = supplier.getName();
    } else if (dto.getEntityType() == AddressEntityType.MEMBER) {
      MemberService memberService = ApplicationUtil.getBean(MemberService.class);
      Member member = memberService.findById(dto.getEntityId());
      this.entityName = member.getName();
    } else if (dto.getEntityType() == AddressEntityType.SHOP) {
      ShopService shopService = ApplicationUtil.getBean(ShopService.class);
      Shop shop = shopService.findById(dto.getEntityId());
      this.entityName = shop.getName();
    }

    this.entityType = dto.getEntityType().getDesc();
    this.addressType = dto.getAddressType().getDesc();

    DicCityService dicCityService = ApplicationUtil.getBean(DicCityService.class);
    DicCityDto province = dicCityService.findById(dto.getProvinceId());
    this.provinceName = province.getName();

    DicCityDto city = dicCityService.findById(dto.getCityId());
    this.cityName = city.getName();

    DicCityDto district = dicCityService.findById(dto.getDistrictId());
    this.districtName = district.getName();

    this.isDefault = dto.getIsDefault() ? "是" : "否";
  }
}

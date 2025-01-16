package com.lframework.xingyun.basedata.bo.address;

import com.lframework.starter.web.annotations.convert.EnumConvert;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.utils.ApplicationUtil;
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
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class QueryAddressBo extends BaseBo<Address> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 实体ID
   */
  @ApiModelProperty("实体ID")
  private String entityId;

  /**
   * 实体名称
   */
  @ApiModelProperty("实体名称")
  private String entityName;

  /**
   * 实体类型
   */
  @ApiModelProperty("实体类型")
  @EnumConvert
  private Integer entityType;

  /**
   * 地址类型
   */
  @ApiModelProperty("地址类型")
  @EnumConvert
  private Integer addressType;

  /**
   * 姓名
   */
  @ApiModelProperty("姓名")
  private String name;

  /**
   * 手机号
   */
  @ApiModelProperty("手机号")
  private String telephone;

  /**
   * 省ID
   */
  @ApiModelProperty("省ID")
  private String provinceId;

  /**
   * 省名称
   */
  @ApiModelProperty("省名称")
  private String provinceName;

  /**
   * 市ID
   */
  @ApiModelProperty("市ID")
  private String cityId;

  /**
   * 市名称
   */
  @ApiModelProperty("市名称")
  private String cityName;

  /**
   * 区ID
   */
  @ApiModelProperty("区ID")
  private String districtId;

  /**
   * 区名称
   */
  @ApiModelProperty("区名称")
  private String districtName;

  /**
   * 详细地址
   */
  @ApiModelProperty("详细地址")
  private String address;

  /**
   * 是否默认地址
   */
  @ApiModelProperty("是否默认地址")
  private Boolean isDefault;

  /**
   * 创建人ID
   */
  @ApiModelProperty("创建人ID")
  private String createBy;

  /**
   * 创建时间
   */
  @ApiModelProperty("创建时间")
  private LocalDateTime createTime;

  public QueryAddressBo() {

  }

  public QueryAddressBo(Address dto) {

    super(dto);
  }

  @Override
  protected void afterInit(Address dto) {
    DicCityService dicCityService = ApplicationUtil.getBean(DicCityService.class);
    DicCityDto province = dicCityService.findById(dto.getProvinceId());
    this.provinceName = province.getName();

    DicCityDto city = dicCityService.findById(dto.getCityId());
    this.cityName = city.getName();

    DicCityDto district = dicCityService.findById(dto.getDistrictId());
    this.districtName = district.getName();

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
  }
}

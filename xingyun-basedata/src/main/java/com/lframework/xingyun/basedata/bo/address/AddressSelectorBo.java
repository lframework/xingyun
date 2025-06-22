package com.lframework.xingyun.basedata.bo.address;

import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.web.core.annotations.convert.EnumConvert;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.Address;
import com.lframework.starter.web.inner.dto.dic.city.DicCityDto;
import com.lframework.starter.web.inner.service.DicCityService;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AddressSelectorBo extends BaseBo<Address> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

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
   * 地区全称
   */
  @ApiModelProperty("地区全称")
  private String areaFullName;

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

  public AddressSelectorBo() {

  }

  public AddressSelectorBo(Address dto) {

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

    this.areaFullName =
        this.provinceName + StringPool.CITY_SPLIT + this.cityName + StringPool.CITY_SPLIT
            + this.districtName;
  }
}

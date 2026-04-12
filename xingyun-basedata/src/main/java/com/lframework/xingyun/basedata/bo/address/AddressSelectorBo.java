package com.lframework.xingyun.basedata.bo.address;

import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.web.core.annotations.convert.EnumConvert;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.Address;
import com.lframework.starter.web.inner.dto.dic.city.DicCityDto;
import com.lframework.starter.web.inner.service.DicCityService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AddressSelectorBo extends BaseBo<Address> {

  /**
   * ID
   */
  @Schema(description = "ID")
  private String id;

  /**
   * 地址类型
   */
  @Schema(description = "地址类型")
  @EnumConvert
  private Integer addressType;

  /**
   * 姓名
   */
  @Schema(description = "姓名")
  private String name;

  /**
   * 手机号
   */
  @Schema(description = "手机号")
  private String telephone;

  /**
   * 省ID
   */
  @Schema(description = "省ID")
  private String provinceId;

  /**
   * 省名称
   */
  @Schema(description = "省名称")
  private String provinceName;

  /**
   * 市ID
   */
  @Schema(description = "市ID")
  private String cityId;

  /**
   * 市名称
   */
  @Schema(description = "市名称")
  private String cityName;

  /**
   * 区ID
   */
  @Schema(description = "区ID")
  private String districtId;

  /**
   * 区名称
   */
  @Schema(description = "区名称")
  private String districtName;

  /**
   * 地区全称
   */
  @Schema(description = "地区全称")
  private String areaFullName;

  /**
   * 详细地址
   */
  @Schema(description = "详细地址")
  private String address;

  /**
   * 是否默认地址
   */
  @Schema(description = "是否默认地址")
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

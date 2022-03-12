package com.lframework.xingyun.api.bo.basedata.storecenter;

import com.lframework.common.constants.StringPool;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.dto.storecenter.StoreCenterDto;
import com.lframework.xingyun.core.dto.dic.city.DicCityDto;
import com.lframework.xingyun.core.service.IDicCityService;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetStoreCenterBo extends BaseBo<StoreCenterDto> {

    /**
     * ID
     */
    private String id;

    /**
     * 编号
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 联系人
     */
    private String contact;

    /**
     * 联系人手机号码
     */
    private String telephone;

    /**
     * 地区ID
     */
    private List<String> city;

    /**
     * 地区名称
     */
    private String cityName;

    /**
     * 地址
     */
    private String address;

    /**
     * 仓库人数
     */
    private Integer peopleNum;

    /**
     * 状态
     */
    private Boolean available;

    /**
     * 备注
     */
    private String description;

    public GetStoreCenterBo() {

    }

    public GetStoreCenterBo(StoreCenterDto dto) {

        super(dto);
    }

    @Override
    protected void afterInit(StoreCenterDto dto) {

        if (!StringUtil.isBlank(dto.getCityId())) {
            IDicCityService dicCityService = ApplicationUtil.getBean(IDicCityService.class);
            List<DicCityDto> cityList = dicCityService.getChainById(dto.getCityId());
            this.city = cityList.stream().map(DicCityDto::getId).collect(Collectors.toList());

            this.cityName = cityList.stream().map(DicCityDto::getName).collect(Collectors.joining(StringPool.CITY_SPLIT));
        }
    }
}

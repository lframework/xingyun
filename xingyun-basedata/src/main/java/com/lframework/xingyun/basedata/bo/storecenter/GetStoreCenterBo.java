package com.lframework.xingyun.basedata.bo.storecenter;

import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.starter.web.inner.dto.dic.city.DicCityDto;
import com.lframework.starter.web.inner.service.DicCityService;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class GetStoreCenterBo extends BaseBo<StoreCenter> {

    /**
     * ID
     */
    @ApiModelProperty("ID")
    private String id;

    /**
     * 编号
     */
    @ApiModelProperty("编号")
    private String code;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String name;

    /**
     * 联系人
     */
    @ApiModelProperty("联系人")
    private String contact;

    /**
     * 联系人手机号码
     */
    @ApiModelProperty("联系人手机号码")
    private String telephone;

    /**
     * 地区ID
     */
    @ApiModelProperty("地区ID")
    private List<String> city;

    /**
     * 地区名称
     */
    @ApiModelProperty("地区名称")
    private String cityName;

    /**
     * 地址
     */
    @ApiModelProperty("地址")
    private String address;

    /**
     * 仓库人数
     */
    @ApiModelProperty("仓库人数")
    private Integer peopleNum;

    /**
     * 状态
     */
    @ApiModelProperty("状态")
    private Boolean available;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String description;

    public GetStoreCenterBo() {

    }

    public GetStoreCenterBo(StoreCenter dto) {

        super(dto);
    }

    @Override
    protected void afterInit(StoreCenter dto) {

        if (!StringUtil.isBlank(dto.getCityId())) {
            DicCityService dicCityService = ApplicationUtil.getBean(DicCityService.class);
            List<DicCityDto> cityList = dicCityService.getChainById(dto.getCityId());
            this.city = cityList.stream().map(DicCityDto::getId).collect(Collectors.toList());

            this.cityName = cityList.stream().map(DicCityDto::getName)
                    .collect(Collectors.joining(StringPool.CITY_SPLIT));
        }
    }
}

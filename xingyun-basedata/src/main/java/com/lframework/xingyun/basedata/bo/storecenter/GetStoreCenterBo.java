package com.lframework.xingyun.basedata.bo.storecenter;

import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.starter.web.inner.dto.dic.city.DicCityDto;
import com.lframework.starter.web.inner.service.DicCityService;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class GetStoreCenterBo extends BaseBo<StoreCenter> {

    /**
     * ID
     */
    @Schema(description = "ID")
    private String id;

    /**
     * 编号
     */
    @Schema(description = "编号")
    private String code;

    /**
     * 名称
     */
    @Schema(description = "名称")
    private String name;

    /**
     * 联系人
     */
    @Schema(description = "联系人")
    private String contact;

    /**
     * 联系人手机号码
     */
    @Schema(description = "联系人手机号码")
    private String telephone;

    /**
     * 地区ID
     */
    @Schema(description = "地区ID")
    private List<String> city;

    /**
     * 地区名称
     */
    @Schema(description = "地区名称")
    private String cityName;

    /**
     * 地址
     */
    @Schema(description = "地址")
    private String address;

    /**
     * 仓库人数
     */
    @Schema(description = "仓库人数")
    private Integer peopleNum;

    /**
     * 备注
     */
    @Schema(description = "备注")
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

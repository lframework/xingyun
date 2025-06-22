package com.lframework.xingyun.basedata.bo.supplier;

import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.Supplier;
import com.lframework.starter.web.inner.dto.dic.city.DicCityDto;
import com.lframework.starter.web.inner.service.DicCityService;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class GetSupplierBo extends BaseBo<Supplier> {

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
     * 简码
     */
    @ApiModelProperty("简码")
    private String mnemonicCode;

    /**
     * 联系人
     */
    @ApiModelProperty("联系人")
    private String contact;

    /**
     * 联系电话
     */
    @ApiModelProperty("联系电话")
    private String telephone;

    /**
     * 电子邮箱
     */
    @ApiModelProperty("电子邮箱")
    private String email;

    /**
     * 邮编
     */
    @ApiModelProperty("邮编")
    private String zipCode;

    /**
     * 传真
     */
    @ApiModelProperty("传真")
    private String fax;

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
     * 送货周期（天）
     */
    @ApiModelProperty("送货周期（天）")
    private Integer deliveryCycle;

    /**
     * 经营方式
     */
    @ApiModelProperty("经营方式")
    private Integer manageType;

    /**
     * 结算方式
     */
    @ApiModelProperty("结算方式")
    private Integer settleType;

    /**
     * 统一社会信用代码
     */
    @ApiModelProperty("统一社会信用代码")
    private String creditCode;

    /**
     * 纳税人识别号
     */
    @ApiModelProperty("纳税人识别号")
    private String taxIdentifyNo;

    /**
     * 开户银行
     */
    @ApiModelProperty("开户银行")
    private String bankName;

    /**
     * 户名
     */
    @ApiModelProperty("户名")
    private String accountName;

    /**
     * 银行账号
     */
    @ApiModelProperty("银行账号")
    private String accountNo;

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

    public GetSupplierBo() {

    }

    public GetSupplierBo(Supplier dto) {

        super(dto);
    }

    @Override
    public BaseBo<Supplier> convert(Supplier dto) {

        return super.convert(dto, GetSupplierBo::getSettleType, GetSupplierBo::getManageType);
    }

    @Override
    protected void afterInit(Supplier dto) {

        if (dto.getManageType() != null) {
            this.manageType = dto.getManageType().getCode();
        }

        if (dto.getSettleType() != null) {
            this.settleType = dto.getSettleType().getCode();
        }

        if (!StringUtil.isBlank(dto.getCityId())) {
            DicCityService dicCityService = ApplicationUtil.getBean(DicCityService.class);
            List<DicCityDto> cityList = dicCityService.getChainById(dto.getCityId());
            this.city = cityList.stream().map(DicCityDto::getId).collect(Collectors.toList());

            this.cityName = cityList.stream().map(DicCityDto::getName)
                    .collect(Collectors.joining(StringPool.CITY_SPLIT));
        }
    }
}

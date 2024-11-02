package com.lframework.xingyun.basedata.bo.customer;

import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.Customer;
import com.lframework.xingyun.core.dto.dic.city.DicCityDto;
import com.lframework.xingyun.core.service.DicCityService;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class GetCustomerBo extends BaseBo<Customer> {

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

    public GetCustomerBo() {

    }

    public GetCustomerBo(Customer dto) {

        super(dto);
    }

    @Override
    public BaseBo<Customer> convert(Customer dto) {

        return super.convert(dto, GetCustomerBo::getSettleType);
    }

    @Override
    protected void afterInit(Customer dto) {

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

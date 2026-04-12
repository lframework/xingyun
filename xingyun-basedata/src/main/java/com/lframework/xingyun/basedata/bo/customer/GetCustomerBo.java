package com.lframework.xingyun.basedata.bo.customer;

import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.Customer;
import com.lframework.starter.web.inner.dto.dic.city.DicCityDto;
import com.lframework.starter.web.inner.service.DicCityService;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class GetCustomerBo extends BaseBo<Customer> {

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
     * 简码
     */
    @Schema(description = "简码")
    private String mnemonicCode;

    /**
     * 联系人
     */
    @Schema(description = "联系人")
    private String contact;

    /**
     * 联系电话
     */
    @Schema(description = "联系电话")
    private String telephone;

    /**
     * 电子邮箱
     */
    @Schema(description = "电子邮箱")
    private String email;

    /**
     * 邮编
     */
    @Schema(description = "邮编")
    private String zipCode;

    /**
     * 传真
     */
    @Schema(description = "传真")
    private String fax;

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
     * 结算方式
     */
    @Schema(description = "结算方式")
    private Integer settleType;

    /**
     * 统一社会信用代码
     */
    @Schema(description = "统一社会信用代码")
    private String creditCode;

    /**
     * 纳税人识别号
     */
    @Schema(description = "纳税人识别号")
    private String taxIdentifyNo;

    /**
     * 开户银行
     */
    @Schema(description = "开户银行")
    private String bankName;

    /**
     * 户名
     */
    @Schema(description = "户名")
    private String accountName;

    /**
     * 银行账号
     */
    @Schema(description = "银行账号")
    private String accountNo;

    /**
     * 备注
     */
    @Schema(description = "备注")
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

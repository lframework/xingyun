package com.lframework.xingyun.basedata.vo.customer;

import com.lframework.starter.web.components.validation.IsEnum;
import com.lframework.starter.web.vo.BaseVo;
import com.lframework.xingyun.basedata.enums.SettleType;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class CreateCustomerVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @NotBlank(message = "请输入编号！")
    private String code;

    /**
     * 名称
     */
    @NotBlank(message = "请输入名称！")
    private String name;

    /**
     * 助记码
     */
    private String mnemonicCode;

    /**
     * 联系人
     */
    private String contact;

    /**
     * 联系电话
     */
    private String telephone;

    /**
     * 电子邮箱
     */
    @Email(message = "电子邮箱格式不正确！")
    private String email;

    /**
     * 邮编
     */
    private String zipCode;

    /**
     * 传真
     */
    private String fax;

    /**
     * 地区ID
     */
    private String cityId;

    /**
     * 地址
     */
    private String address;

    /**
     * 收货人
     */
    private String receiver;

    /**
     * 收货手机号
     */
    private String receiveTelephone;

    /**
     * 收货地址
     */
    private String receiveAddress;

    /**
     * 结账方式
     */
    @NotNull(message = "请选择结账方式！")
    @IsEnum(message = "请选择结账方式！", enumClass = SettleType.class)
    private Integer settleType;

    /**
     * 统一社会信用代码
     */
    private String creditCode;

    /**
     * 纳税人识别号
     */
    private String taxIdentifyNo;

    /**
     * 开户银行
     */
    private String bankName;

    /**
     * 户名
     */
    private String accountName;

    /**
     * 银行账号
     */
    private String accountNo;

    /**
     * 备注
     */
    private String description;
}

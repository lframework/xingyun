package com.lframework.xingyun.basedata.vo.storecenter;

import com.lframework.starter.web.vo.BaseVo;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class CreateStoreCenterVo implements BaseVo, Serializable {

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
    private String cityId;

    /**
     * 地址
     */
    private String address;

    /**
     * 仓库人数
     */
    private Integer peopleNum;

    /**
     * 备注
     */
    private String description;
}

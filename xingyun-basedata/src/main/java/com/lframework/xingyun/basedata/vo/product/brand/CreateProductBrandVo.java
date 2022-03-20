package com.lframework.xingyun.basedata.vo.product.brand;

import com.lframework.starter.web.components.validation.UploadUrl;
import com.lframework.starter.web.vo.BaseVo;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class CreateProductBrandVo implements BaseVo, Serializable {

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
     * 简称
     */
    private String shortName;

    /**
     * logo
     */
    @UploadUrl(message = "logo文件格式有误！")
    private String logo;

    /**
     * 简介
     */
    private String introduction;

    /**
     * 备注
     */
    private String description;
}

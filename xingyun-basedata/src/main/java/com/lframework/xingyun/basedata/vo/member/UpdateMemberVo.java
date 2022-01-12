package com.lframework.xingyun.basedata.vo.member;

import com.lframework.starter.mybatis.enums.Gender;
import com.lframework.starter.web.components.validation.IsEnum;
import com.lframework.starter.web.vo.BaseVo;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class UpdateMemberVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @NotBlank(message = "ID不能为空！")
    private String id;

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
     * 性别
     */
    @NotNull(message = "请选择性别！")
    @IsEnum(message = "请选择性别！", enumClass = Gender.class)
    private Integer gender;

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
     * 出生日期
     */
    private LocalDate birthday;

    /**
     * 入会日期
     */
    @NotNull(message = "入会日期不能为空！")
    private LocalDate joinDay;

    /**
     * 状态
     */
    @NotNull(message = "状态不能为空！")
    private Boolean available;

    /**
     * 备注
     */
    private String description;
}

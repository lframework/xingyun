package com.lframework.xingyun.template.inner.vo.system.user;

import com.lframework.starter.common.constants.PatternPool;
import com.lframework.starter.web.components.validation.Pattern;
import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 手机号绑定用户
 *
 * @author zmj
 * @since 2022/4/25
 */
@Data
public class TelephoneBindUserVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号", required = true)
    @Pattern(regexp = PatternPool.PATTERN_STR_CN_TEL, message = "手机号格式不正确！")
    @NotBlank(message = "手机号不能为空！")
    private String telephone;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名", required = true)
    @NotBlank(message = "用户名不能为空！")
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码", required = true)
    @NotBlank(message = "密码不能为空！")
    private String password;
}

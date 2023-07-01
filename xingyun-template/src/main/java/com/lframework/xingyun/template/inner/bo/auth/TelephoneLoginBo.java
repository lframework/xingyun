package com.lframework.xingyun.template.inner.bo.auth;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.template.inner.dto.LoginDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 手机号登录Bo
 *
 * @author zmj
 * @since 2022/4/25
 */
@Data
public class TelephoneLoginBo extends BaseBo<LoginDto> {

    /**
     * 登录信息
     */
    @ApiModelProperty("登录信息")
    private LoginBo loginInfo;

    /**
     * 是否绑定用户
     */
    @ApiModelProperty("是否绑定用户")
    private Boolean isBind = Boolean.TRUE;

    public TelephoneLoginBo() {

    }

    public TelephoneLoginBo(LoginDto dto) {

        this.afterInit(dto);
    }

    @Override
    protected void afterInit(LoginDto dto) {

        this.loginInfo = new LoginBo(dto);
    }
}

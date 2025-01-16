package com.lframework.xingyun.template.inner.vo.system.user;

import com.lframework.starter.common.constants.PatternPool;
import com.lframework.starter.web.components.validation.Pattern;
import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GetTelephoneLoginCaptchaVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 手机号
   */
  @ApiModelProperty(value = "手机号", required = true)
  @Pattern(regexp = PatternPool.PATTERN_STR_CN_TEL, message = "手机号格式不正确！")
  @NotBlank(message = "手机号不能为空！")
  private String telephone;
}

package com.lframework.xingyun.template.inner.bo.system.generate;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.utils.JsonUtil;
import com.lframework.xingyun.core.dto.GenerateCodeDto;
import com.lframework.xingyun.core.service.GenerateCodeService;
import com.lframework.xingyun.template.inner.entity.SysGenerateCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 编号规则 GetBo
 * </p>
 *
 * @author zmj
 */
@Data
public class GetSysGenerateCodeBo extends BaseBo<SysGenerateCode> {

  /**
   * ID
   */
  @JsonSerialize(using = ToStringSerializer.class)
  @ApiModelProperty("ID")
  private Integer id;

  /**
   * 名称
   */
  @ApiModelProperty("名称")
  private String name;

  /**
   * 配置规则
   */
  @ApiModelProperty("配置规则")
  private String configStr;

  public GetSysGenerateCodeBo() {

  }

  public GetSysGenerateCodeBo(SysGenerateCode dto) {

    super(dto);
  }

  @Override
  protected void afterInit(SysGenerateCode dto) {
    if (!StringUtil.isBlank(dto.getConfigStr())) {
      GenerateCodeService generateCodeService = ApplicationUtil.getBean(GenerateCodeService.class);
      GenerateCodeDto generateCodeDto = new GenerateCodeDto();
      generateCodeDto.setId(dto.getId());
      generateCodeDto.setConfigStr(dto.getConfigStr());

      this.configStr = JsonUtil.toJsonString(generateCodeService.getRules(generateCodeDto));
    }
  }
}

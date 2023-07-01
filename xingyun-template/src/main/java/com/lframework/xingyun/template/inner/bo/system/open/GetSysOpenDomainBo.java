package com.lframework.xingyun.template.inner.bo.system.open;

import com.lframework.xingyun.template.inner.entity.SysOpenDomain;
import com.lframework.starter.web.annotations.convert.EncryptConvert;
import com.lframework.starter.web.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GetSysOpenDomainBo extends BaseBo<SysOpenDomain> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 名称
   */
  @ApiModelProperty("名称")
  private String name;

  /**
   * API密钥
   */
  @ApiModelProperty("API密钥")
  @EncryptConvert
  private String apiSecret;

  /**
   * 租户ID
   */
  @ApiModelProperty("租户ID")
  private Integer tenantId;

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

  public GetSysOpenDomainBo() {

  }

  public GetSysOpenDomainBo(SysOpenDomain dto) {

    super(dto);
  }
}

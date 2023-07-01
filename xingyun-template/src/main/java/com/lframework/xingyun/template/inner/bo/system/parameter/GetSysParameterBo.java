package com.lframework.xingyun.template.inner.bo.system.parameter;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lframework.xingyun.template.inner.entity.SysParameter;
import com.lframework.starter.web.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 系统参数 GetBo
 * </p>
 *
 * @author zmj
 */
@Data
public class GetSysParameterBo extends BaseBo<SysParameter> {

  /**
   * ID
   */
  @JsonSerialize(using = ToStringSerializer.class)
  @ApiModelProperty("ID")
  private Long id;

  /**
   * 键
   */
  @ApiModelProperty("键")
  private String pmKey;

  /**
   * 值
   */
  @ApiModelProperty("值")
  private String pmValue;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  public GetSysParameterBo() {

  }

  public GetSysParameterBo(SysParameter dto) {

    super(dto);
  }

}

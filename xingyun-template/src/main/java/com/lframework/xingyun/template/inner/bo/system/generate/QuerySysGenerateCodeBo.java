package com.lframework.xingyun.template.inner.bo.system.generate;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.template.inner.entity.SysGenerateCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 编号规则 QueryBo
 * </p>
 *
 * @author zmj
 */
@Data
public class QuerySysGenerateCodeBo extends BaseBo<SysGenerateCode> {

  /**
   * ID
   */
  @JsonSerialize(using = ToStringSerializer.class)
  @ApiModelProperty("ID")
  private Long id;

  /**
   * 名称
   */
  @ApiModelProperty("名称")
  private String name;

  public QuerySysGenerateCodeBo() {

  }

  public QuerySysGenerateCodeBo(SysGenerateCode dto) {

    super(dto);
  }

  @Override
  protected void afterInit(SysGenerateCode dto) {
  }
}

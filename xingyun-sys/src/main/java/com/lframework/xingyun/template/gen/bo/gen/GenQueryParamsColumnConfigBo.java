package com.lframework.xingyun.template.gen.bo.gen;

import com.lframework.xingyun.template.gen.dto.gen.GenQueryParamsColumnConfigDto;
import com.lframework.starter.web.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GenQueryParamsColumnConfigBo extends BaseBo<GenQueryParamsColumnConfigDto> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 查询类型
   */
  @ApiModelProperty("查询类型")
  private Integer queryType;

  /**
   * 排序编号
   */
  @ApiModelProperty("排序编号")
  private Integer orderNo;

  public GenQueryParamsColumnConfigBo() {

  }

  public GenQueryParamsColumnConfigBo(GenQueryParamsColumnConfigDto dto) {

    super(dto);
  }

  @Override
  public <A> BaseBo<GenQueryParamsColumnConfigDto> convert(GenQueryParamsColumnConfigDto dto) {

    return super.convert(dto, GenQueryParamsColumnConfigBo::getQueryType);
  }

  @Override
  protected void afterInit(GenQueryParamsColumnConfigDto dto) {

    this.queryType = dto.getQueryType().getCode();
  }
}

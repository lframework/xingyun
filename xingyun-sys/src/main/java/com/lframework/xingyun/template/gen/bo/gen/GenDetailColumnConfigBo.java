package com.lframework.xingyun.template.gen.bo.gen;

import com.lframework.xingyun.template.gen.dto.gen.GenDetailColumnConfigDto;
import com.lframework.starter.web.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GenDetailColumnConfigBo extends BaseBo<GenDetailColumnConfigDto> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 列宽
   */
  @ApiModelProperty("列宽")
  private Integer span;

  /**
   * 排序编号
   */
  @ApiModelProperty("排序编号")
  private Integer orderNo;

  public GenDetailColumnConfigBo() {

  }

  public GenDetailColumnConfigBo(GenDetailColumnConfigDto dto) {

    super(dto);
  }

  @Override
  public <A> BaseBo<GenDetailColumnConfigDto> convert(GenDetailColumnConfigDto dto) {

    return super.convert(dto);
  }

  @Override
  protected void afterInit(GenDetailColumnConfigDto dto) {

    super.afterInit(dto);
  }
}

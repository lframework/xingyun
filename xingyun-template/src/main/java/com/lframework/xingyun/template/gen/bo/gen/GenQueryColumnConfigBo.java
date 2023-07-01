package com.lframework.xingyun.template.gen.bo.gen;

import com.lframework.xingyun.template.gen.dto.gen.GenQueryColumnConfigDto;
import com.lframework.starter.web.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GenQueryColumnConfigBo extends BaseBo<GenQueryColumnConfigDto> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 宽度类型
   */
  @ApiModelProperty("宽度类型")
  private Integer widthType;

  /**
   * 宽度
   */
  @ApiModelProperty("宽度")
  private Integer width;

  /**
   * 是否页面排序
   */
  @ApiModelProperty("是否页面排序")
  private Boolean sortable;

  /**
   * 排序编号
   */
  @ApiModelProperty("排序编号")
  private Integer orderNo;

  public GenQueryColumnConfigBo() {

  }

  public GenQueryColumnConfigBo(GenQueryColumnConfigDto dto) {

    super(dto);
  }

  @Override
  public <A> BaseBo<GenQueryColumnConfigDto> convert(GenQueryColumnConfigDto dto) {

    return super.convert(dto, GenQueryColumnConfigBo::getWidthType);
  }

  @Override
  protected void afterInit(GenQueryColumnConfigDto dto) {

    this.widthType = dto.getWidthType().getCode();
  }
}

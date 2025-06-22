package com.lframework.xingyun.basedata.bo.print;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.xingyun.basedata.entity.PrintTemplate;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GetPrintTemplateBo extends BaseBo<PrintTemplate> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private Integer id;

  /**
   * 名称
   */
  @ApiModelProperty("名称")
  private String name;

  public GetPrintTemplateBo() {

  }

  public GetPrintTemplateBo(PrintTemplate dto) {

    super(dto);
  }
}

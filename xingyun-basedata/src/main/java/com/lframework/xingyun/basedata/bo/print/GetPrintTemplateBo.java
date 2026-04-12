package com.lframework.xingyun.basedata.bo.print;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.xingyun.basedata.entity.PrintTemplate;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class GetPrintTemplateBo extends BaseBo<PrintTemplate> {

  /**
   * ID
   */
  @Schema(description = "ID")
  private Integer id;

  /**
   * 名称
   */
  @Schema(description = "名称")
  private String name;

  public GetPrintTemplateBo() {

  }

  public GetPrintTemplateBo(PrintTemplate dto) {

    super(dto);
  }
}

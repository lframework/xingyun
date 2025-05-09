package com.lframework.xingyun.basedata.bo.print;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.utils.JsonUtil;
import com.lframework.xingyun.basedata.entity.PrintTemplateComp;
import io.swagger.annotations.ApiModelProperty;
import java.util.Map;
import lombok.Data;

@Data
public class GetPrintTemplateCompSettingBo extends BaseBo<PrintTemplateComp> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 组件配置
   */
  @ApiModelProperty("组件配置")
  private Map<String, Object> compJson;

  public GetPrintTemplateCompSettingBo() {

  }

  public GetPrintTemplateCompSettingBo(PrintTemplateComp dto) {

    super(dto);
  }

  @Override
  public <A> BaseBo<PrintTemplateComp> convert(PrintTemplateComp dto) {
    return super.convert(dto, GetPrintTemplateCompSettingBo::getCompJson);
  }

  @Override
  protected void afterInit(PrintTemplateComp dto) {
    this.compJson = JsonUtil.parseMap(dto.getCompJson(), String.class, Object.class);
  }
}

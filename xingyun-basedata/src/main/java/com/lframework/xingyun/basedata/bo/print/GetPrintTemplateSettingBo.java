package com.lframework.xingyun.basedata.bo.print;

import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.utils.JsonUtil;
import com.lframework.xingyun.basedata.entity.PrintTemplate;
import com.lframework.xingyun.basedata.service.print.PrintTemplateCompService;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class GetPrintTemplateSettingBo extends BaseBo<PrintTemplate> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private Integer id;

  /**
   * JSON配置
   */
  @ApiModelProperty("JSON配置")
  private Map<String, Object> templateJson;

  /**
   * 示例数据
   */
  @ApiModelProperty("示例数据")
  private Map<String, Object> demoData;

  /**
   * 附加组件配置
   */
  @ApiModelProperty("附加组件配置")
  private List<Map<String, Object>> compJsonList;

  public GetPrintTemplateSettingBo() {

  }

  public GetPrintTemplateSettingBo(PrintTemplate dto) {

    super(dto);
  }

  @Override
  public <A> BaseBo<PrintTemplate> convert(PrintTemplate dto) {
    return super.convert(dto, GetPrintTemplateSettingBo::getTemplateJson,
        GetPrintTemplateSettingBo::getDemoData);
  }

  @Override
  protected void afterInit(PrintTemplate dto) {
    this.templateJson = JsonUtil.parseMap(dto.getTemplateJson(), String.class, Object.class);

    if (!StringUtil.isBlank(dto.getDemoData())) {
      this.demoData = JsonUtil.parseMap(dto.getDemoData(), String.class, Object.class);
    }

    PrintTemplateCompService printTemplateCompService = ApplicationUtil.getBean(
        PrintTemplateCompService.class);
    List<String> compJsonList = printTemplateCompService.getCompJsonByTemplateId(dto.getId());
    this.compJsonList = compJsonList.stream()
        .map(t -> JsonUtil.parseMap(t, String.class, Object.class)).collect(Collectors.toList());
  }
}

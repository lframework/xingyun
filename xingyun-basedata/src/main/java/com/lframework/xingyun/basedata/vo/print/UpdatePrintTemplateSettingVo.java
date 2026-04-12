package com.lframework.xingyun.basedata.vo.print;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdatePrintTemplateSettingVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "ID不能为空！")
  private Integer id;

  /**
   * 模板配置
   */
  @Schema(description = "模板配置", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "模板配置不能为空！")
  private String templateJson;
}

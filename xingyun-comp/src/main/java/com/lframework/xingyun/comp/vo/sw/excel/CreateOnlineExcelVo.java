package com.lframework.xingyun.comp.vo.sw.excel;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateOnlineExcelVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 名称
   */
  @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "请输入名称！")
  private String name;

  /**
   * 内容
   */
  @Schema(description = "内容", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "请输入内容！")
  private String content;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;

}

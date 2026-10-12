package com.lframework.xingyun.produce.vo.assemble;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 修改组装单VO
 *
 * @author zmj
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateAssembleSheetVo extends CreateAssembleSheetVo {

  private static final long serialVersionUID = 1L;

  /**
   * 组装单ID
   */
  @Schema(description = "组装单ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "组装单ID不能为空！")
  private String id;
}

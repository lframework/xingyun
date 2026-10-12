package com.lframework.xingyun.produce.vo.assemble;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import lombok.Data;

/**
 * 审核通过组装单VO
 *
 * @author zmj
 */
@Data
public class ApprovePassAssembleSheetVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 组装单ID
   */
  @Schema(description = "组装单ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "组装单ID不能为空！")
  private String id;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;
}

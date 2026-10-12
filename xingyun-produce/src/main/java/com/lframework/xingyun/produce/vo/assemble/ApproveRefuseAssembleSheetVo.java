package com.lframework.xingyun.produce.vo.assemble;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import lombok.Data;

/**
 * 审核拒绝组装单VO
 *
 * @author zmj
 */
@Data
public class ApproveRefuseAssembleSheetVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 组装单ID
   */
  @Schema(description = "组装单ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "组装单ID不能为空！")
  private String id;

  /**
   * 拒绝原因
   */
  @Schema(description = "拒绝原因", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "请输入拒绝原因！")
  private String refuseReason;
}

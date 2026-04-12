package com.lframework.xingyun.comp.vo.sw.excel;

import com.lframework.starter.web.core.components.validation.TypeMismatch;
import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BatchSendOnlineExcelVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotEmpty(message = "ID不能为空！")
  private List<String> ids;

  /**
   * 用户ID
   */
  @Schema(description = "用户ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "用户ID不能为空！")
  private String userId;

  /**
   * 是否留存副本
   */
  @Schema(description = "是否留存副本", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "是否留存副本不能为空！")
  @TypeMismatch(message = "是否留存副本格式错误！")
  private Boolean selfSave;
}

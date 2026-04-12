package com.lframework.xingyun.sc.vo.logistics;

import com.lframework.starter.web.core.components.validation.IsEnum;
import com.lframework.starter.web.core.vo.BaseVo;
import com.lframework.xingyun.sc.enums.LogisticsSheetDetailBizType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LogisticsSheetBizOrderVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 业务单据ID
   */
  @Schema(description = "业务单据ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "业务单据ID不能为空！")
  private String bizId;

  /**
   * 业务类型
   */
  @NotNull(message = "业务类型不能为空！")
  @IsEnum(message = "业务类型格式错误！", enumClass = LogisticsSheetDetailBizType.class)
  private Integer bizType;
}

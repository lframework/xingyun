package com.lframework.xingyun.chart.vo;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.List;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class GetOrderChartVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 业务类型
   */
  @Schema(description = "业务类型", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotEmpty(message = "业务类型不能为空！")
  private List<Integer> bizTypes;
}

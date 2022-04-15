package com.lframework.xingyun.chart.vo;

import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class QueryOrderChartVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 业务类型
   */
  @ApiModelProperty(value = "业务类型", required = true)
  @NotEmpty(message = "业务类型不能为空！")
  private List<Integer> bizTypes;
}

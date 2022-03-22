package com.lframework.xingyun.chart.vo;

import com.lframework.starter.web.vo.BaseVo;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class GetOrderChartVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 业务类型
   */
  @NotEmpty(message = "业务类型不能为空！")
  private List<Integer> bizTypes;
}

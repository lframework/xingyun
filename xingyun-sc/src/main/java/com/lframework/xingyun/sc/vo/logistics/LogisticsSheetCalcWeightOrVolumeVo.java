package com.lframework.xingyun.sc.vo.logistics;

import com.lframework.starter.web.components.validation.IsEnum;
import com.lframework.starter.web.vo.BaseVo;
import com.lframework.xingyun.sc.enums.LogisticsSheetDetailBizType;
import java.io.Serializable;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LogisticsSheetCalcWeightOrVolumeVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 业务单据
   */
  @NotEmpty(message = "业务单据不能为空！")
  @Valid
  private List<BizOrderVo> bizOrders;

  @Data
  public static class BizOrderVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 业务单据ID
     */
    @NotBlank(message = "业务单据ID不能为空！")
    private String bizId;

    @NotNull(message = "业务类型不能为空！")
    @IsEnum(message = "业务类型格式错误！", enumClass = LogisticsSheetDetailBizType.class)
    private Integer bizType;
  }
}

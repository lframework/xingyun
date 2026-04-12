package com.lframework.xingyun.comp.bo.components;

import com.lframework.starter.web.core.bo.BaseBo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class MapLocationBo extends BaseBo {

  /**
   * 经度
   */
  @Schema(description = "经度")
  private BigDecimal lng;

  /**
   * 纬度
   */
  @Schema(description = "纬度")
  private BigDecimal lat;
}

package com.lframework.xingyun.comp.bo.components;

import com.lframework.starter.web.core.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class MapLocationBo extends BaseBo {

  /**
   * 经度
   */
  @ApiModelProperty("经度")
  private BigDecimal lng;

  /**
   * 纬度
   */
  @ApiModelProperty("纬度")
  private BigDecimal lat;
}

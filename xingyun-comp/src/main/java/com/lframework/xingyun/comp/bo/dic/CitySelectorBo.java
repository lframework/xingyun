package com.lframework.xingyun.comp.bo.dic;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.inner.dto.dic.city.DicCityDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CitySelectorBo extends BaseBo<DicCityDto> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 名称
   */
  @ApiModelProperty("名称")
  private String name;

  /**
   * 父级ID
   */
  @ApiModelProperty("父级ID")
  private String parentId;

  public CitySelectorBo() {

  }

  public CitySelectorBo(DicCityDto dto) {

    super(dto);
  }
}

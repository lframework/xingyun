package com.lframework.xingyun.comp.bo.dic;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.inner.dto.dic.city.DicCityDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CitySelectorBo extends BaseBo<DicCityDto> {

  /**
   * ID
   */
  @Schema(description = "ID")
  private String id;

  /**
   * 名称
   */
  @Schema(description = "名称")
  private String name;

  /**
   * 父级ID
   */
  @Schema(description = "父级ID")
  private String parentId;

  public CitySelectorBo() {

  }

  public CitySelectorBo(DicCityDto dto) {

    super(dto);
  }
}

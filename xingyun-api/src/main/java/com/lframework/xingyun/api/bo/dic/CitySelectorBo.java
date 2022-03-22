package com.lframework.xingyun.api.bo.dic;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.core.dto.dic.city.DicCityDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CitySelectorBo extends BaseBo<DicCityDto> {

  /**
   * ID
   */
  private String id;

  /**
   * 名称
   */
  private String name;

  /**
   * 父级ID
   */
  private String parentId;

  public CitySelectorBo() {

  }

  public CitySelectorBo(DicCityDto dto) {

    super(dto);
  }
}

package com.lframework.xingyun.template.gen.bo.custom.selector.category;

import com.lframework.xingyun.template.gen.entity.GenCustomSelectorCategory;
import com.lframework.starter.web.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GenCustomSelectorCategorySelectorBo extends BaseBo<GenCustomSelectorCategory> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 编号
   */
  @ApiModelProperty("编号")
  private String code;

  /**
   * 名称
   */
  @ApiModelProperty("名称")
  private String name;

  public GenCustomSelectorCategorySelectorBo() {
  }

  public GenCustomSelectorCategorySelectorBo(GenCustomSelectorCategory dto) {
    super(dto);
  }
}

package com.lframework.xingyun.template.gen.bo.custom.selector.category;

import com.lframework.xingyun.template.gen.entity.GenCustomSelectorCategory;
import com.lframework.starter.web.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GetGenCustomSelectorCategoryBo extends BaseBo<GenCustomSelectorCategory> {

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


  public GetGenCustomSelectorCategoryBo() {

  }

  public GetGenCustomSelectorCategoryBo(GenCustomSelectorCategory dto) {

    super(dto);
  }
}

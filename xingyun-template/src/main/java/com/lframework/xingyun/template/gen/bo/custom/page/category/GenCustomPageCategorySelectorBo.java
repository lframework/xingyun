package com.lframework.xingyun.template.gen.bo.custom.page.category;

import com.lframework.xingyun.template.gen.entity.GenCustomPageCategory;
import com.lframework.starter.web.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GenCustomPageCategorySelectorBo extends BaseBo<GenCustomPageCategory> {

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

  /**
   * 父级ID
   */
  @ApiModelProperty("父级ID")
  private String parentId;

  public GenCustomPageCategorySelectorBo() {
  }

  public GenCustomPageCategorySelectorBo(GenCustomPageCategory dto) {
    super(dto);
  }
}

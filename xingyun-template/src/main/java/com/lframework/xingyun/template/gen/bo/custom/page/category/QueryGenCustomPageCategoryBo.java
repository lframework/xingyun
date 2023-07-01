package com.lframework.xingyun.template.gen.bo.custom.page.category;

import com.lframework.xingyun.template.gen.entity.GenCustomPageCategory;
import com.lframework.starter.web.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryGenCustomPageCategoryBo extends BaseBo<GenCustomPageCategory> {

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


  public QueryGenCustomPageCategoryBo() {

  }

  public QueryGenCustomPageCategoryBo(GenCustomPageCategory dto) {

    super(dto);
  }
}

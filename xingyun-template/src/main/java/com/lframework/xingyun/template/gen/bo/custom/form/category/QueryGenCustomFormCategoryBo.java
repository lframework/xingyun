package com.lframework.xingyun.template.gen.bo.custom.form.category;

import com.lframework.xingyun.template.gen.entity.GenCustomFormCategory;
import com.lframework.starter.web.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryGenCustomFormCategoryBo extends BaseBo<GenCustomFormCategory> {

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


  public QueryGenCustomFormCategoryBo() {

  }

  public QueryGenCustomFormCategoryBo(GenCustomFormCategory dto) {

    super(dto);
  }
}

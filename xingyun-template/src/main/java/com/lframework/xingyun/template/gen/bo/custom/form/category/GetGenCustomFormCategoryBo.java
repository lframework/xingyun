package com.lframework.xingyun.template.gen.bo.custom.form.category;

import com.lframework.xingyun.template.gen.entity.GenCustomFormCategory;
import com.lframework.starter.web.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GetGenCustomFormCategoryBo extends BaseBo<GenCustomFormCategory> {

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


  public GetGenCustomFormCategoryBo() {

  }

  public GetGenCustomFormCategoryBo(GenCustomFormCategory dto) {

    super(dto);
  }
}

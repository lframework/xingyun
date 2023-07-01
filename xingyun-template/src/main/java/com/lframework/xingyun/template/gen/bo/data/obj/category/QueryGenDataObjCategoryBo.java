package com.lframework.xingyun.template.gen.bo.data.obj.category;

import com.lframework.xingyun.template.gen.entity.GenDataObjCategory;
import com.lframework.starter.web.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryGenDataObjCategoryBo extends BaseBo<GenDataObjCategory> {

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


  public QueryGenDataObjCategoryBo() {

  }

  public QueryGenDataObjCategoryBo(GenDataObjCategory dto) {

    super(dto);
  }
}

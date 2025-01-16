package com.lframework.xingyun.template.inner.bo.system.dept;

import com.lframework.xingyun.template.inner.entity.SysDept;
import com.lframework.starter.web.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GetSysDeptBo extends BaseBo<SysDept> {

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

  /**
   * 简称
   */
  @ApiModelProperty("简称")
  private String shortName;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  private Boolean available;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  public GetSysDeptBo() {

  }

  public GetSysDeptBo(SysDept dto) {

    super(dto);
  }
}

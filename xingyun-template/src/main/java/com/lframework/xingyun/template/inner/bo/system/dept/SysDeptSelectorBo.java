package com.lframework.xingyun.template.inner.bo.system.dept;

import com.lframework.xingyun.template.inner.entity.SysDept;
import com.lframework.starter.web.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SysDeptSelectorBo extends BaseBo<SysDept> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

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
   * 状态
   */
  @ApiModelProperty("状态")
  private Boolean available;

  public SysDeptSelectorBo() {

  }

  public SysDeptSelectorBo(SysDept dto) {

    super(dto);
  }
}

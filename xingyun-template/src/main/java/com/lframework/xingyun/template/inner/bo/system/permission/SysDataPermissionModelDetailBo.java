package com.lframework.xingyun.template.inner.bo.system.permission;

import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.web.annotations.convert.EnumConvert;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.template.core.entity.SysDataPermissionModelDetail;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SysDataPermissionModelDetailBo extends BaseBo<SysDataPermissionModelDetail> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private Integer id;

  /**
   * 名称
   */
  @ApiModelProperty("名称")
  private String name;

  /**
   * 模型ID
   */
  @ApiModelProperty("模型ID")
  private Integer modelId;

  /**
   * 条件
   */
  @ApiModelProperty("条件")
  private String[] conditionTypes;

  /**
   * 输入类型
   */
  @ApiModelProperty("输入类型")
  @EnumConvert
  private Integer inputType;

  /**
   * 前段枚举名
   */
  @ApiModelProperty("前端枚举名")
  private String enumName;

  public SysDataPermissionModelDetailBo() {
  }

  public SysDataPermissionModelDetailBo(SysDataPermissionModelDetail dto) {
    super(dto);
  }

  @Override
  protected void afterInit(SysDataPermissionModelDetail dto) {
    this.conditionTypes = dto.getConditionType().split(StringPool.STR_SPLIT);
  }
}

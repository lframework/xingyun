package com.lframework.xingyun.template.gen.bo.data.entity;

import com.lframework.xingyun.template.gen.entity.GenDataEntityDetail;
import com.lframework.starter.web.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GenDataEntityDetailGenerateBo extends BaseBo<GenDataEntityDetail> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 字段显示名称
   */
  @ApiModelProperty("字段显示名称")
  private String name;

  /**
   * 字段名称
   */
  @ApiModelProperty("字段名称")
  private String columnName;

  /**
   * 是否主键
   */
  @ApiModelProperty("是否主键")
  private Boolean isKey;

  /**
   * 数据类型
   */
  @ApiModelProperty("数据类型")
  private Integer dataType;

  /**
   * 排序编号
   */
  @ApiModelProperty("排序编号")
  private Integer columnOrder;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  /**
   * 显示类型
   */
  @ApiModelProperty("显示类型")
  private Integer viewType;

  /**
   * 是否内置枚举
   */
  @ApiModelProperty("是否内置枚举")
  private Boolean fixEnum;

  /**
   * 后端枚举名
   */
  @ApiModelProperty("后端枚举名")
  private String enumBack;

  /**
   * 前端枚举名
   */
  @ApiModelProperty("前端枚举名")
  private String enumFront;

  /**
   * 正则表达式
   */
  @ApiModelProperty("正则表达式")
  private String regularExpression;

  /**
   * 是否排序字段
   */
  @ApiModelProperty("是否排序字段")
  private Boolean isOrder;

  /**
   * 排序类型
   */
  @ApiModelProperty("排序类型")
  private String orderType;

  public GenDataEntityDetailGenerateBo() {

  }

  public GenDataEntityDetailGenerateBo(GenDataEntityDetail dto) {

    super(dto);
  }

  @Override
  public <A> BaseBo<GenDataEntityDetail> convert(GenDataEntityDetail dto) {

    return super.convert(dto, GenDataEntityDetailGenerateBo::getDataType,
        GenDataEntityDetailGenerateBo::getViewType,
        GenDataEntityDetailGenerateBo::getOrderType);
  }

  @Override
  protected void afterInit(GenDataEntityDetail dto) {

    this.dataType = dto.getDataType().getCode();
    this.viewType = dto.getViewType().getCode();
    this.orderType = dto.getOrderType() == null ? null : dto.getOrderType().getCode();
  }
}

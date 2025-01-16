package com.lframework.xingyun.template.gen.bo.data.obj;

import com.lframework.xingyun.template.gen.entity.GenDataObjQueryDetail;
import com.lframework.starter.web.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GenDataObjQueryDetailBo extends BaseBo<GenDataObjQueryDetail> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 显示名称
   */
  @ApiModelProperty("显示名称")
  private String customName;

  /**
   * 自定义SQL
   */
  @ApiModelProperty("自定义SQL")
  private String customSql;

  /**
   * 别名
   */
  @ApiModelProperty("别名")
  private String customAlias;

  /**
   * 数据类型
   */
  @ApiModelProperty("数据类型")
  private Integer dataType;

  public GenDataObjQueryDetailBo() {

  }

  public GenDataObjQueryDetailBo(GenDataObjQueryDetail dto) {

    super(dto);
  }

  @Override
  public <A> BaseBo<GenDataObjQueryDetail> convert(GenDataObjQueryDetail dto) {

    return super.convert(dto);
  }

  @Override
  protected void afterInit(GenDataObjQueryDetail dto) {
    this.dataType = dto.getDataType().getCode();
  }
}

package com.lframework.xingyun.api.model.settle.item.out;

import com.alibaba.excel.annotation.ExcelProperty;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.components.excel.ExcelModel;
import com.lframework.xingyun.settle.dto.item.out.SettleOutItemDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SettleOutItemExportModel extends BaseBo<SettleOutItemDto> implements ExcelModel {

  @ExcelProperty("编号")
  private String code;

  @ExcelProperty("名称")
  private String name;

  @ExcelProperty("备注")
  private String description;

  public SettleOutItemExportModel() {

  }

  public SettleOutItemExportModel(SettleOutItemDto dto) {

    super(dto);
  }

  @Override
  public <A> BaseBo<SettleOutItemDto> convert(SettleOutItemDto dto) {

    return this;
  }

  @Override
  protected void afterInit(SettleOutItemDto dto) {

    this.setCode(dto.getCode());
    this.setName(dto.getName());
    this.setDescription(dto.getDescription());
  }
}

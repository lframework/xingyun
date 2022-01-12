package com.lframework.xingyun.api.model.settle.item.in;

import com.alibaba.excel.annotation.ExcelProperty;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.components.excel.ExcelModel;
import com.lframework.xingyun.settle.dto.item.in.SettleInItemDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SettleInItemExportModel extends BaseBo<SettleInItemDto> implements ExcelModel {

    @ExcelProperty("编号")
    private String code;

    @ExcelProperty("名称")
    private String name;

    @ExcelProperty("备注")
    private String description;

    public SettleInItemExportModel() {

    }

    public SettleInItemExportModel(SettleInItemDto dto) {

        super(dto);
    }

    @Override
    public <A> BaseBo<SettleInItemDto> convert(SettleInItemDto dto) {

        return this;
    }

    @Override
    protected void afterInit(SettleInItemDto dto) {

        this.setCode(dto.getCode());
        this.setName(dto.getName());
        this.setDescription(dto.getDescription());
    }
}

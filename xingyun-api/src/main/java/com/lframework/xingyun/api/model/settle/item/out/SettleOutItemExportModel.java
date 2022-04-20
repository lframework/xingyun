package com.lframework.xingyun.api.model.settle.item.out;

import com.alibaba.excel.annotation.ExcelProperty;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.components.excel.ExcelModel;
import com.lframework.xingyun.settle.entity.SettleOutItem;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SettleOutItemExportModel extends BaseBo<SettleOutItem> implements ExcelModel {

    @ExcelProperty("编号")
    private String code;

    @ExcelProperty("名称")
    private String name;

    @ExcelProperty("备注")
    private String description;

    public SettleOutItemExportModel() {

    }

    public SettleOutItemExportModel(SettleOutItem dto) {

        super(dto);
    }

    @Override
    public <A> BaseBo<SettleOutItem> convert(SettleOutItem dto) {

        return this;
    }

    @Override
    protected void afterInit(SettleOutItem dto) {

        this.setCode(dto.getCode());
        this.setName(dto.getName());
        this.setDescription(dto.getDescription());
    }
}

package com.lframework.xingyun.settle.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.enums.BaseEnum;

public enum SettleFeeSheetType implements BaseEnum<Integer> {
    RECEIVE(1, "应收款"), PAY(2, "应付款");

    @EnumValue
    private final Integer code;

    private final String desc;

    SettleFeeSheetType(Integer code, String desc) {

        this.code = code;
        this.desc = desc;
    }

    @Override
    public Integer getCode() {

        return this.code;
    }

    @Override
    public String getDesc() {

        return this.desc;
    }
}

package com.lframework.xingyun.sc.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.enums.BaseEnum;

public enum TakeStockPlanType implements BaseEnum<Integer> {
    ALL(0, "全场盘点"),
    SIMPLE(1, "单品盘点"),
    CATEGORY(2, "类目盘点"),
    BRAND(3, "品牌盘点")
    ;

    @EnumValue
    private final Integer code;

    private final String desc;

    TakeStockPlanType(Integer code, String desc) {

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

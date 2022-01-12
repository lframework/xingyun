package com.lframework.xingyun.basedata.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.enums.BaseEnum;

public enum ManageType implements BaseEnum<Integer> {
    DISTRIBUTION(1, "经销");

    @EnumValue
    private final Integer code;

    private final String desc;

    ManageType(Integer code, String desc) {

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

package com.lframework.xingyun.settle.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.enums.BaseEnum;

public enum SettleCheckSheetBizType implements BaseEnum<Integer> {
    RECEIVE_SHEET(1, "采购收货单"), PURCHASE_RETURN(2, "采购退单"), SETTLE_FEE_SHEET(3, "供应商费用单"), SETTLE_PRE_SHEET(4,
            "供应商预付款单");

    @EnumValue
    private final Integer code;

    private final String desc;

    SettleCheckSheetBizType(Integer code, String desc) {

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

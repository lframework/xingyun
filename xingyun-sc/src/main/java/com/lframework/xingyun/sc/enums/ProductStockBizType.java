package com.lframework.xingyun.sc.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.enums.BaseEnum;

public enum ProductStockBizType implements BaseEnum<Integer> {
  PURCHASE(1, "采购入库"), PURCHASE_RETURN(2, "采购退货出库"), SALE(3, "销售出库"), SALE_RETURN(4,
      "销售退货入库"), RETAIL(5,
      "零售出库"), RETAIL_RETURN(6, "零售退货入库"), TAKE_STOCK_IN(7, "盘点入库"), TAKE_STOCK_OUT(8,
      "盘点出库"), STOCK_COST_ADJUST(
      9, "库存成本调整"), STOCK_ADJUST(10, "库存调整"), SC_TRANSFER(11, "仓库调拨");

  @EnumValue
  private final Integer code;

  private final String desc;

  ProductStockBizType(Integer code, String desc) {

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

package com.lframework.xingyun.template.gen.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.enums.BaseEnum;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public enum GenDataType implements BaseEnum<Integer> {

  STRING(0, String.class, "String"), INTEGER(1, Integer.class, "Integer"), SHORT(2, Short.class, "Short"), LONG(3, Long.class, "Long"), DOUBLE(4,
      Double.class, "Double"), LOCAL_DATE(5, LocalDate.class, "LocalDate"), LOCAL_DATE_TIME(6, LocalDateTime.class, "LocalDateTime"), LOCAL_TIME(7,
          LocalTime.class, "LocalTime"), BOOLEAN(8, Boolean.class, "Boolean"), BIG_DECIMAL(9, BigDecimal.class, "BigDecimal"),
  ;

  @EnumValue
  private final Integer code;

  private final Class<?> clazz;

  private final String desc;

  GenDataType(Integer code, Class<?> clazz, String desc) {

    this.code = code;
    this.clazz = clazz;
    this.desc = desc;
  }

  /**
   * 是否是数字类型
   *
   * @param type
   * @return
   */
  public static Boolean isNumberType(GenDataType type) {

    if (type == null) {
      return false;
    }

    return type == INTEGER || type == SHORT || type == LONG || type == DOUBLE
        || type == BIG_DECIMAL;
  }

  /**
   * 是否是小数类型
   *
   * @param type
   * @return
   */
  public static Boolean isDecimalType(GenDataType type) {
    if (type == null) {
      return false;
    }

    return type == DOUBLE || type == BIG_DECIMAL;
  }

  @Override
  public Integer getCode() {

    return this.code;
  }

  @Override
  public String getDesc() {

    return this.desc;
  }

  public Class<?> getClazz() {
    return clazz;
  }
}

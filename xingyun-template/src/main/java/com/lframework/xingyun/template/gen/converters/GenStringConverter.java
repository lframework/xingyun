package com.lframework.xingyun.template.gen.converters;

import com.lframework.starter.common.utils.StringUtil;
import com.lframework.xingyun.template.gen.enums.GenConvertType;

/**
 * @author zmj
 * @since 2022/8/18
 */
public class GenStringConverter {

  /**
   * 转换
   *
   * @param type
   * @param oriStr
   * @return
   */
  public static String convert(GenConvertType type, String oriStr) {
    if (StringUtil.isBlank(oriStr)) {
      return oriStr;
    }

    if (type == GenConvertType.UNDERLINE_TO_CAMEL) {
      return StringUtil.toCamelCase(oriStr);
    }

    return null;
  }

  /**
   * 强制转换成驼峰
   *
   * @param type
   * @param oriStr
   * @return
   */
  public static String convertToCamelCase(GenConvertType type, String oriStr) {

    if (type == GenConvertType.UNDERLINE_TO_CAMEL) {
      return StringUtil.toCamelCase(oriStr);
    }

    return null;
  }

  /**
   * 强制转换成普通小写 即为：不含分割线的小写字符
   *
   * @param type
   * @param oriStr
   * @return
   */
  public static String convertToNormalLowerCase(GenConvertType type, String oriStr) {
    if (type == GenConvertType.UNDERLINE_TO_CAMEL) {
      return StringUtil.toCamelCase(oriStr).toLowerCase();
    }

    return null;
  }
}

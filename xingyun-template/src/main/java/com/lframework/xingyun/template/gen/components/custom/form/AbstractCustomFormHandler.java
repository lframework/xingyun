package com.lframework.xingyun.template.gen.components.custom.form;

import com.lframework.starter.common.utils.ArrayUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.utils.JsonUtil;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class AbstractCustomFormHandler<R, O, P> implements CustomFormHandler<R, O, P> {

  private Type[] rawTypes = null;

  @Override
  public O convertGetParam(String s) {
    if (StringUtil.isBlank(s)) {
      return null;
    }
    Type[] rawTypes = getRawTypes();
    if (ArrayUtil.isEmpty(rawTypes)) {
      return null;
    }
    if (rawTypes.length < 2) {
      return null;
    }
    return (O) JsonUtil.parseObject(s, (Class) rawTypes[1]);
  }

  @Override
  public P convertHandleParam(String s) {
    if (StringUtil.isBlank(s)) {
      return null;
    }
    Type[] rawTypes = getRawTypes();
    if (ArrayUtil.isEmpty(rawTypes)) {
      return null;
    }
    if (rawTypes.length < 3) {
      return null;
    }
    return (P) JsonUtil.parseObject(s, (Class) rawTypes[2]);
  }

  private Type[] getRawTypes() {
    if (ArrayUtil.isEmpty(this.rawTypes)) {
      Type[] rawTypes = ((ParameterizedType) getClass().getGenericSuperclass())
          .getActualTypeArguments();
      this.rawTypes = rawTypes;
    }

    return this.rawTypes;
  }
}

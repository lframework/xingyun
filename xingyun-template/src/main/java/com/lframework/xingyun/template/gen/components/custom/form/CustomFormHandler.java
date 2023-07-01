package com.lframework.xingyun.template.gen.components.custom.form;

/**
 * 自定义表单处理器
 *
 * @param <R> 返回数据类型
 * @param <O> 查询参数类型
 * @param <P> 操作参数类型
 */
public interface CustomFormHandler<R, O, P> {

  /**
   * 转换查询参数
   *
   * @param s
   * @return
   */
  O convertGetParam(String s);

  /**
   * 查询
   *
   * @param o
   * @return
   */
  R getOne(O o);

  /**
   * 转换操作参数
   *
   * @param s
   * @return
   */
  P convertHandleParam(String s);

  /**
   * 操作数据
   *
   * @param p
   */
  void handle(P p);
}

package com.lframework.xingyun.template.core.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 系统日志注解
 * <p>
 * 使用方法： 比如关于用户的日志可以分为“登录、退出登录” 此时type可以使用统一的字符串，这样可以将关于用户行为的日志全部查出 然后再根据不同的name将这些日志进一步细分
 * name同时也用于前端显示 在上述例子中，可以将type设置为“user.operation” 登录的name设置为“用户登录” 退出登录的name设置为“退出登录”
 *
 * @author zmj
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OpLog {

  /**
   * 日志类型 用于做业务区分
   *
   * @return
   */
  int type();

  /**
   * 日志名称 需要填充参数部分用{}占位，会根据params的值进行填充
   *
   * @return
   */
  String name() default "";

  /**
   * 需要保存的参数 Spel表达式
   *
   * @return
   */
  String[] params() default {};

  /**
   * 是否循环填充日志名称 会将params中的collection循环format
   *
   * @return
   */
  boolean loopFormat() default false;

  /**
   * 是否自动保存参数
   *
   * @return
   */
  boolean autoSaveParams() default false;
}

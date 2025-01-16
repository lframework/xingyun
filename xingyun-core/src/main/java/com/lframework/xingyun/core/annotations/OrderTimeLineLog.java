package com.lframework.xingyun.core.annotations;

import com.lframework.xingyun.core.enums.OrderTimeLineBizType;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 单据时间轴记录
 * 如果出现嵌套那么以最外层的为准，不会进行嵌套隔离
 * @author zmj
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OrderTimeLineLog {

  /**
   * 业务类型
   *
   * @return
   */
  OrderTimeLineBizType type() default OrderTimeLineBizType.NORMAL;

  /**
   * 单据ID
   * @return
   */
  String[] orderId();

  /**
   * 内容 需要填充参数部分用{}占位，会根据params的值进行填充
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
   * 是否循环填充内容 会将params中的collection循环format
   *
   * @return
   */
  boolean loopFormat() default false;

  /**
   * 是否为删除操作
   * 如果为删除操作，那么忽略type、name、loopFormat，直接根据orderId进行删除操作
   * @return
   */
  boolean delete() default false;
}

package com.lframework.xingyun.core.annotations.sort;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Sort {

    /**
     * 传入列名
     *
     * @return
     */
    String value();

    /**
     * SQL列名
     *
     * @return
     */
    String alias() default "";

    /**
     * 是否自动解析
     * <p>
     * 如果自动解析，那么alias只需要指定表的别名即可，会直接将alias和转为下划线的value拼接作为SQL列名
     *
     * @return
     */
    boolean autoParse() default false;
}
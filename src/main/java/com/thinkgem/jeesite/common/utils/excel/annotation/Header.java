package com.thinkgem.jeesite.common.utils.excel.annotation;

import java.lang.annotation.*;

/**
 * @Description 表头注解
 *
 * @author yu
 * @create 2019/3/22 10:10
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target(ElementType.FIELD)
public @interface Header {
  String value() default "";
}

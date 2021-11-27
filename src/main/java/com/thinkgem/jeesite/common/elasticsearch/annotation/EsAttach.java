package com.thinkgem.jeesite.common.elasticsearch.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EsAttach {
    String index() default "es_attachment";
    String srcIdField() default "id";
}


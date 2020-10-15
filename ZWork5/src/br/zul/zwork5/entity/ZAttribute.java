package br.zul.zwork5.entity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author luizh
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface ZAttribute {
 
    String name() default "";
    String defaultValue() default "-.-";
    int length() default -1;
    int scale() default -1;
    boolean nullable() default true;
    boolean primaryKey() default false;
    boolean autoIncrement() default false;
    boolean unique() default false;
    boolean lazy() default false;
    
}
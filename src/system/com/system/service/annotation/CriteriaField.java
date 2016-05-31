package com.system.service.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 标识实体类字段在查询中的应用类型
 * @author HB102
 *
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CriteriaField {
	CriteriaType value() default CriteriaType.EQ;
}

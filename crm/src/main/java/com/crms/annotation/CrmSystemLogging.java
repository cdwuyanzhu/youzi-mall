package com.crms.annotation;

import java.lang.annotation.*;

/**
 * 自定义一个注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CrmSystemLogging {

    /**
     * 描述信息
     * @return
     */
    String logDescription() default "";

    /**
     * 方法返回值
     * @return
     */
    String methodType() default "java.lang.String";

}

package com.webmaple.worker.annotation;

import java.lang.annotation.*;

/**
 * @author lyifee
 * on 2021/1/25
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MaplePipeline {
    String site() default "";
}

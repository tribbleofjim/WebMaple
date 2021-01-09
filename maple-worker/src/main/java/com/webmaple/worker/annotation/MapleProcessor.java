package com.webmaple.worker.annotation;

import java.lang.annotation.*;

/**
 * @author lyifee
 * on 2021/1/9
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MapleProcessor {
    String site() default "";
}

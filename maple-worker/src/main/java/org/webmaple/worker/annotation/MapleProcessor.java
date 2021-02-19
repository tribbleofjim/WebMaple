package org.webmaple.worker.annotation;

import java.lang.annotation.*;

/**
 * 标明项目中的PageProcessor
 * site : processor对应的网站，如https://www.jd.com
 *
 * @author lyifee
 * on 2021/1/9
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MapleProcessor {
    String site() default "";
}

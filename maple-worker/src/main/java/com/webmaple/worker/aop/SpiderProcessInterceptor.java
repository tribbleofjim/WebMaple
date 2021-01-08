package com.webmaple.worker.aop;

import com.webmaple.worker.SpiderProcess;
import com.webmaple.worker.util.ConfigUtil;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author lyifee
 * on 2021/1/8
 */
@Aspect
@Component
public class SpiderProcessInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpiderProcessInterceptor.class);

    @Pointcut("execution(com.webmaple.worker.SpiderProcess.init())")
    public void SpiderProcessAspect(){}

    @Before("SpiderProcessAspect()")
    public void insertThreadNum(ProceedingJoinPoint joinPoint) {
        LOGGER.info("spider process : insert threadNum...");
        Object spiderProcessBean = joinPoint.getTarget();
        String rawThreadNum = ConfigUtil.getValueToString("application.yml", "webmaple.spiderProcess.threadNum");
        if (StringUtils.isNotBlank(rawThreadNum)) {
            Integer threadNum = Integer.parseInt(rawThreadNum);
            try {
                Class<?> clazz = spiderProcessBean.getClass();
                Method method = clazz.getMethod("setThreadNum");
                method.setAccessible(true);
                method.invoke(spiderProcessBean, threadNum);

            } catch (NoSuchMethodException e) {
                LOGGER.error("no_set_thread_num_method_in_class:{}", spiderProcessBean.getClass());
            } catch (IllegalAccessException | InvocationTargetException e) {
                LOGGER.error("insert_invoke_exception:", e);
            }
        }
    }
}

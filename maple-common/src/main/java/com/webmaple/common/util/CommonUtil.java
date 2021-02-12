package com.webmaple.common.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author lyifee
 * on 2021/2/11
 */
public class CommonUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtil.class);

    public static String getCron(String rawCron) {
        // 5m / 4h
        if (StringUtils.isBlank(rawCron)) {
            LOGGER.error("invalid_rawCron : {}", rawCron);
            return null;
        }
        String baseCron = "0 * * * * ?";
        String num = "0/" + rawCron.substring(0, rawCron.length() - 1);
        if (rawCron.endsWith("m")) {
            return baseCron.replaceFirst("\\*", num);

        } else if (rawCron.endsWith("h")){
            String cron = baseCron.replaceFirst("\\*", "0");
            cron = cron.replaceFirst("\\*", num);
            return cron;

        } else {
            throw new RuntimeException("invalid_rawCron : " + rawCron);
        }
    }

    /**
     * 对使用toString()方法序列化的对象进行反序列化
     * @param s 序列化后的string对象
     * @param clazz 原对象类型
     * @param <T> 原对象类型
     * @return T
     */
    public static <T> T getModelFromString(String s, Class<T> clazz) {
        if (StringUtils.isBlank(s) || clazz == null) {
            return null;
        }

        String simpleName = clazz.getSimpleName();
        if (!s.startsWith(simpleName)) {
            return null;
        }

        s = s.replaceFirst(simpleName, "")
                .replace("{", "")
                .replace("}", "")
                .replace(" ", "");
        String[] fields = s.split(",");
        try {
            T model = clazz.newInstance();
            for (String field : fields) {
                String[] param = field.split("=");
                if (param.length < 2) {
                    LOGGER.warn("invalid_param:{}", param[0]);
                    continue;
                }
                Field clazzField = clazz.getDeclaredField(param[0]);
                Class<?> fieldClass = clazzField.getType();
                Method getMethod = clazz.getMethod("set" + captureName(param[0]), fieldClass);
                getMethod.invoke(model, validParam(param[1]));
            }
            return model;
            
        } catch (Exception e) {
            LOGGER.error("get_model_from_string_exception:", e);
        }
        return null;
    }

    /**
     * 单词首字母大写
     * @param str 单词字符串
     * @return 首字母大写后的单词字符串
     */
    private static String captureName(String str) {
        char[] cs = str.toCharArray();
        if (cs[0] >= 'a' && cs[0] <= 'z') {
            cs[0] -= 32;
        }
        return String.valueOf(cs);
    }

    /**
     * 标准化参数形式
     * @param param 参数字符串
     * @return 标准化后的参数字符串
     */
    private static String validParam(String param) {
        if (param.startsWith("'")) {
            return param.replace("'", "");
        }
        return param;
    }
}

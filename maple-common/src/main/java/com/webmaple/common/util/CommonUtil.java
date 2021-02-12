package com.webmaple.common.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
}

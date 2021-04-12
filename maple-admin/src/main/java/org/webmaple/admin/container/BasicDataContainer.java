package org.webmaple.admin.container;

/**
 * @author lyifee
 * on 2021/4/2
 */
public class BasicDataContainer {
    private static Integer spiderNum = 0;

    private static Integer timedJobNum = 0;

    public static void setSpiderNum(int num) {
        spiderNum = num;
    }

    public static void setTimedJobNum(int num) {
        timedJobNum = num;
    }

    public static int getSpiderNum() {
        return spiderNum;
    }

    public static int getTimedJobNum() {
        return timedJobNum;
    }
}

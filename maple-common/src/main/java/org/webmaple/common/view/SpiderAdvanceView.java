package org.webmaple.common.view;


/**
 * @author lyifee
 * on 2021/5/5
 */
public class SpiderAdvanceView {
    /**
     * 爬虫uuid
     */
    private String uuid;

    /**
     * 爬虫所在worker
     */
    private String worker;

    /**
     * 爬虫目标爬取页数/URL条数
     */
    private int target;

    /**
     * 爬虫当前爬取页数/url条数
     */
    private int temp;

    /**
     * 进度百分比，如40%
     */
    private String percent;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }
}

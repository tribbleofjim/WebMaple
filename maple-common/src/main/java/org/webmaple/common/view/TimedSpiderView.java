package org.webmaple.common.view;

/**
 * @author lyifee
 * on 2021/1/27
 */
public class TimedSpiderView {
    /**
     * worker名称
     */
    private String worker;

    /**
     * 爬虫uuid
     */
    private String uuid;

    /**
     * 定时任务类型
     */
    private String type;

    /**
     * 定时数
     */
    private int maintain;

    /**
     * 定时间隔
     */
    private int cronNum;

    /**
     * 定时单位
     * 和定时间隔加在一起组成简易的表达式，如：2h 5m
     */
    private String cronUnit;

    @Override
    public String toString() {
        return "TimedSpiderView{" +
                "worker='" + worker + '\'' +
                ", uuid='" + uuid + '\'' +
                ", type='" + type + '\'' +
                ", maintain=" + maintain +
                ", cronNum=" + cronNum +
                ", cronUnit='" + cronUnit + '\'' +
                '}';
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMaintain() {
        return maintain;
    }

    public void setMaintain(int maintain) {
        this.maintain = maintain;
    }

    public int getCronNum() {
        return cronNum;
    }

    public void setCronNum(int cronNum) {
        this.cronNum = cronNum;
    }

    public String getCronUnit() {
        return cronUnit;
    }

    public void setCronUnit(String cronUnit) {
        this.cronUnit = cronUnit;
    }
}

package org.webmaple.common.model;

/**
 * @author lyifee
 * on 2021/1/12
 */
public class SpiderJobDTO {
    /**
     * 任务所在机器ip
     */
    private String ip;

    /**
     * 任务所在worker名称
     */
    private String worker;

    /**
     * 任务开启的spider uuid
     */
    private String spiderUUID;

    /**
     * 任务定时类型
     */
    private String type;

    /**
     * 任务定时数
     */
    private int maintain;

    /**
     * 任务当前状态
     */
    private String state;

    /**
     * 任务名称
     */
    private String jobName;

    /**
     * QuartzJobBean类型的全限定名
     */
    private String jobClazz;

    /**
     * 任务开始时间
     */
    private String startTime;

    /**
     * corn表达式
     */
    private String cronExpression;

    /**
     * 需要传递的参数
     */
    private String extraInfo;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public String getSpiderUUID() {
        return spiderUUID;
    }

    public void setSpiderUUID(String spiderUUID) {
        this.spiderUUID = spiderUUID;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobClazz() {
        return jobClazz;
    }

    public void setJobClazz(String jobClazz) {
        this.jobClazz = jobClazz;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }
}

package org.webmaple.admin.model;

import java.util.Date;

/**
 * @author lyifee
 * on 2021/4/23
 */
public class MapleJob {
    private String worker; // worker名称

    private String jobName;  //任务名称

    private String jobClazz;  //QuartzJobBean类型的全限定名

    private Date startTime;  //任务开始时间

    private String cronExpression;  //corn表达式

    private String extraInfo;//需要传递的参数

    private boolean isExecuting; // 是否在执行中

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
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

    public boolean isExecuting() {
        return isExecuting;
    }

    public void setExecuting(boolean executing) {
        isExecuting = executing;
    }
}

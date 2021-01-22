package com.webmaple.worker.job.model;

import java.util.Date;

/**
 * @author lyifee
 * on 2021/1/11
 */
public class QuartzJob {
    private String jobName;  //任务名称

    private String jobClazz;  //QuartzJobBean类型的全限定名

    private Date startTime;  //任务开始时间

    private String cronExpression;  //corn表达式

    private String extraInfo;//需要传递的参数

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
}

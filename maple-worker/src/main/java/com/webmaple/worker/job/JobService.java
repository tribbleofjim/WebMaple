package com.webmaple.worker.job;

import com.webmaple.worker.job.model.QuartzJob;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lyifee
 * on 2021/1/11
 */
@Service
public class JobService implements InitializingBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(JobService.class);

    @Autowired
    private Scheduler scheduler;

    public void start() throws SchedulerException {
        scheduler.start();
    }

    /**
     * 新建一个任务
     *
     */
    public void addJob(QuartzJob quartzJob) throws Exception  {
        if (!CronExpression.isValidExpression(quartzJob.getCronExpression())) {
            LOGGER.error("Illegal cron expression : {}", quartzJob.getCronExpression());
            return;
        }
        JobDetail jobDetail;

        //构建job信息
        String clazz = quartzJob.getJobClazz();
        Class<? extends Job> jobClazz = (Class<? extends Job>) Class.forName(clazz);
        jobDetail = JobBuilder.newJob(jobClazz).withIdentity(quartzJob.getJobName(), quartzJob.getJobClazz()).build();

        //表达式调度构建器(即任务执行的时间,不立即执行)
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(quartzJob.getCronExpression()).withMisfireHandlingInstructionDoNothing();

        //按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(quartzJob.getJobName(), quartzJob.getJobClazz())
                .startNow()
                .withSchedule(scheduleBuilder).build();

        //传递参数
        if(quartzJob.getExtraInfo()!=null && !"".equals(quartzJob.getExtraInfo())) {
            jobDetail.getJobDataMap().put(JobMapDataKey.EXTRA_INFO.getKey(), quartzJob.getExtraInfo());
        }
        scheduler.scheduleJob(jobDetail, trigger);
    }

    public List<QuartzJob> getAllJob() throws SchedulerException {
        // TODO : 用mysql存储元数据信息
        return null;
    }

    /**
     * 获取Job状态
     * @param jobName
     * @param jobGroup
     * @return
     * @throws SchedulerException
     */
    public String getJobState(String jobName, String jobGroup) throws SchedulerException {
        TriggerKey triggerKey = new TriggerKey(jobName, jobGroup);
        return scheduler.getTriggerState(triggerKey).name();
    }

    //暂停所有任务
    public void pauseAllJob() throws SchedulerException {
        scheduler.pauseAll();
    }

    //暂停任务
    public String pauseJob(String jobName, String jobGroup) throws SchedulerException {
        JobKey jobKey = new JobKey(jobName, jobGroup);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            return "fail";
        }else {
            scheduler.pauseJob(jobKey);
            return "success";
        }

    }

    //恢复所有任务
    public void resumeAllJob() throws SchedulerException {
        scheduler.resumeAll();
    }

    // 恢复某个任务
    public String resumeJob(String jobName, String jobGroup) throws SchedulerException {

        JobKey jobKey = new JobKey(jobName, jobGroup);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            return "fail";
        }else {
            scheduler.resumeJob(jobKey);
            return "success";
        }
    }

    //删除某个任务
    public String  deleteJob(QuartzJob quartzJob) throws SchedulerException {
        JobKey jobKey = new JobKey(quartzJob.getJobName(), quartzJob.getJobClazz());
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null ) {
            return "jobDetail is null";
        }else if(!scheduler.checkExists(jobKey)) {
            return "jobKey is not exists";
        }else {
            scheduler.deleteJob(jobKey);
            return "success";
        }

    }

    //修改任务
    public String  modifyJob(QuartzJob quartzJob) throws SchedulerException {
        if (!CronExpression.isValidExpression(quartzJob.getCronExpression())) {
            return "Illegal cron expression";
        }
        TriggerKey triggerKey = TriggerKey.triggerKey(quartzJob.getJobName(),quartzJob.getJobClazz());
        JobKey jobKey = new JobKey(quartzJob.getJobName(),quartzJob.getJobClazz());
        if (scheduler.checkExists(jobKey) && scheduler.checkExists(triggerKey)) {
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            //表达式调度构建器,不立即执行
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(quartzJob.getCronExpression()).withMisfireHandlingInstructionDoNothing();

            //按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

            //修改参数
            if(!trigger.getJobDataMap().get("extraInfo").equals(quartzJob.getExtraInfo())) {
                trigger.getJobDataMap().put("extraInfo",quartzJob.getExtraInfo());
            }

            //按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
            return "success";
        }else {
            return "job or trigger not exists";
        }

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        LOGGER.info("starting jobService...");
        start();
    }
}

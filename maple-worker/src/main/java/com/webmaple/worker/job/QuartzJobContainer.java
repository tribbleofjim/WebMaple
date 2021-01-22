package com.webmaple.worker.job;

import com.webmaple.worker.job.model.QuartzJob;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 为了删除任务，将quartzJob的状态保存在容器中
 *
 * @author lyifee
 * on 2021/1/16
 */
public class QuartzJobContainer {
    private static final ConcurrentHashMap<String, QuartzJob> jobMap = new ConcurrentHashMap<>();

    public static List<QuartzJob> getJobList() {
        List<QuartzJob> list = new ArrayList<>();
        for (Map.Entry<String, QuartzJob> entry : jobMap.entrySet()) {
            list.add(entry.getValue());
        }
        return list;
    }

    public static void pauseJob(String jobName) {
        QuartzJob quartzJob = jobMap.get(jobName);
        if (quartzJob == null) {
            return;
        }
        quartzJob.setExecuting(false);
    }

    public static void pauseAllJob() {
        for (Map.Entry<String, QuartzJob> entry : jobMap.entrySet()) {
            entry.getValue().setExecuting(false);
        }
    }

    public static void resumeJob(String jobName) {
        QuartzJob quartzJob = jobMap.get(jobName);
        if (quartzJob == null) {
            return;
        }
        quartzJob.setExecuting(true);
    }

    public static void resumeAllJob() {
        for (Map.Entry<String, QuartzJob> entry : jobMap.entrySet()) {
            entry.getValue().setExecuting(true);
        }
    }

    public static void put(String jobName, QuartzJob job) {
        jobMap.put(jobName, job);
    }

    public static QuartzJob get(String jobName) {
        return jobMap.get(jobName);
    }

    public static boolean containsKey(String jobName) {
        return jobMap.containsKey(jobName);
    }

    public static void remove(String jobName) {
        jobMap.remove(jobName);
    }
}

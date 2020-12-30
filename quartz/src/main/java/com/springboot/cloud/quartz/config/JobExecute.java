package com.springboot.cloud.quartz.config;

import com.springboot.cloud.common.core.util.SpringContextUtils;
import com.springboot.cloud.quartz.service.impl.JobEntityServiceImpl;
import org.quartz.*;

/**
 * 定时任务的执行的类。在这个里面写逻辑，不管是直接执行。还是通过反射去调用其他的方法，不可缺少
 */
@DisallowConcurrentExecution
public class JobExecute implements Job {
    @Override
    public void execute(JobExecutionContext context)  {
        try {
            JobEntityServiceImpl jobEntityService  = (JobEntityServiceImpl) SpringContextUtils.getBean("jobEntityServiceImpl");
            jobEntityService.doJob(context.getJobDetail().getJobDataMap());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

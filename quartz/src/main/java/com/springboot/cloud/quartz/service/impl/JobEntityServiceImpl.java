package com.springboot.cloud.quartz.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.springboot.cloud.common.core.util.SpringContextUtils;
import com.springboot.cloud.quartz.config.JobExecute;
import com.springboot.cloud.quartz.entity.JobEntity;
import com.springboot.cloud.quartz.mapper.JobEntityMapper;
import com.springboot.cloud.quartz.service.IJobEntityService;
import org.quartz.*;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author DM_hzx
 * @since 2020-11-25
 */
@Service("jobEntityServiceImpl")
public class JobEntityServiceImpl extends ServiceImpl<JobEntityMapper, JobEntity> implements IJobEntityService {
    @Resource
    private Scheduler scheduler;

    @Resource
    private JobEntityMapper jobEntityMapper;

    private static final String JOB_DATA_KEY = "JOB_DATA_KEY";

    @Override
    public void saveJob(JobEntity jobEntity) {
        String name = jobEntity.getJobName();

        JobKey jobKey = JobKey.jobKey(name);
        //定义任务详情
        JobDetail jobDetail = JobBuilder.newJob(JobExecute.class).storeDurably()
                .withDescription(jobEntity.getDescription()).withIdentity(jobKey).build();
        //想定时任务中添加参数数据。便于传参使用
        jobDetail.getJobDataMap().put(JOB_DATA_KEY,jobEntity);

        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(jobEntity.getCron());
        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger"+name,jobEntity.getJobGroup())//设置触发器的key=定时任务名称
                .withSchedule(cronScheduleBuilder)
                .forJob(jobKey)
                .build();
        try {
            //检查定时任务是否存在
            boolean exists = scheduler.checkExists(jobKey);
            if (exists) {
                scheduler.deleteJob(jobKey);
                scheduler.scheduleJob(jobDetail, cronTrigger);
                //scheduler.rescheduleJob(new TriggerKey(name), cronTrigger);
                //scheduler.addJob(jobDetail, true);
            } else {
                scheduler.scheduleJob(jobDetail, cronTrigger);
            }
            addOrUpdateJobEntity(jobEntity);


        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doJob(JobDataMap jobDataMap) {
        JobEntity jobEntity = (JobEntity)jobDataMap.get(JOB_DATA_KEY);
        this.update(new UpdateWrapper<JobEntity>().lambda().set(JobEntity::getJobIsWork,0).eq(JobEntity::getUuId,jobEntity.getUuId()));
        String beanName = jobEntity.getSpringBeanName();
        String methodName = jobEntity.getMethodName();
        Object object = SpringContextUtils.getBean(beanName);
        try {
            Method method = object.getClass().getDeclaredMethod(methodName, JobEntity.class);
            //Object 表示需要执行的bean的具体方法，jobEntity作为参数传递
            method.invoke(object,jobEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteJob(Long id) throws SchedulerException {

    }

    /**
     * 对定时任务封装类的操作
     * @param jobEntity
     */
    @Override
    public void addOrUpdateJobEntity(JobEntity jobEntity) {
        this.saveOrUpdate(jobEntity);
    }

    @Override
    public JobEntity queryJobEntity(Integer jobEntityId) {
        QueryWrapper<JobEntity> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",jobEntityId);
        return jobEntityMapper.selectById(jobEntityId);
    }

    @Override
    public void deleteJobEntity(Long id) throws SchedulerException {


    }
}

package com.springboot.cloud.quartz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.springboot.cloud.quartz.entity.JobEntity;
import org.quartz.JobDataMap;
import org.quartz.SchedulerException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author DM_hzx
 * @since 2020-11-25
 */
public interface IJobEntityService extends IService<JobEntity> {

    void saveJob(JobEntity jobEntity);

    void doJob(JobDataMap jobDataMap);

    void deleteJob(Long id) throws SchedulerException;


    void addOrUpdateJobEntity(JobEntity jobEntity);

    JobEntity queryJobEntity(Integer jobEntityId);

    void deleteJobEntity(Long id) throws SchedulerException;

}

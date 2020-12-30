package com.springboot.cloud.quartz.service.impl;

import com.springboot.cloud.quartz.entity.JobEntity;
import org.springframework.stereotype.Service;

@Service
public class JobExecuteMethod {

    public void jobTest(JobEntity jobEntity){
        String description = jobEntity.getDescription();
        System.out.println(description);

    }
}

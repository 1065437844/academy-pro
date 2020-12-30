package com.springboot.cloud.quartz.controller;


import com.springboot.cloud.common.core.entity.vo.Result;
import com.springboot.cloud.common.core.util.CronUtil;
import com.springboot.cloud.quartz.entity.JobEntity;
import com.springboot.cloud.quartz.service.IJobEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author DM_hzx
 * @since 2020-11-25
 */
@RestController
@RequestMapping("/job_entity")
public class JobEntityController{
    @Autowired
    private IJobEntityService jobEntityService;

    @PostMapping("/add")
    public Result add(@RequestBody(required = true)  JobEntity jobEntity) {
        if(jobEntity.getId()==null||"".equals(jobEntity.getId())){
            return new Result("1000","该任务已经存在",null);
        }
        jobEntityService.saveJob(jobEntity);
        return Result.success("添加定时任务成功");
    }

    @PostMapping
    public void addTest(String string) {
        JobEntity jobEntity=new JobEntity();
        try {
//            String string = "2020-11-25 15:15:00";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime localDateTime = LocalDateTime.parse(string, formatter);
            ZoneId zoneId = ZoneId.systemDefault();
            Date date = Date.from(localDateTime.atZone(zoneId).toInstant());
            String cron = CronUtil.getCron(date);
            jobEntity.setCron(cron);
        } catch (Exception e) {
            e.printStackTrace();
        }
        jobEntity.setDescription("测试的定时任务");
        jobEntity.setJobName("test");
        jobEntity.setJobIsWork(1);
        jobEntity.setJobGroup("测试");
        jobEntity.setMethodName("jobTest");
        jobEntity.setSpringBeanName("jobExecuteMethod");
        try {
            jobEntityService.saveJob(jobEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        JobEntityServiceImpl jobEntityService  = (JobEntityServiceImpl) SpringContextUtils.getBean("jobEntityServiceImpl");
//        System.out.println(jobEntityService);

    }

}

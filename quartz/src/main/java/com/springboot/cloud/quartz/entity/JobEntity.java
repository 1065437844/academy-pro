package com.springboot.cloud.quartz.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author DM_hzx
 * @since 2020-11-25
 */
@Data
@TableName("job_entity")
public class JobEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 定时任务封装类id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    /**
     * 唯一标识符
     */
    private String uuId=Integer.toHexString((int)new Date().getTime());





    /**
     * 定时任务名称
     */
    private String jobName;

    /**
     * 定时任务描述
     */
    private String description;

    /**
     * cron表达式
     */
    private String cron;

    /**
     * springBean名称
     */
    private String springBeanName;

    /**
     * 方法名称
     */
    private String methodName;

    /**
     * 参数
     */
    private String params;

    /**
     * 任务是否启用 0：未启用 1：启用
     */
    private Integer jobIsWork;

    /**
     * 任务小组
     */
    private String jobGroup;


}

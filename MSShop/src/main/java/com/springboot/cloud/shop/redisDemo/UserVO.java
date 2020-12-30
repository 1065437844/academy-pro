package com.springboot.cloud.shop.redisDemo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

/**
 * 用于测试布隆过滤器的实体类
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserVO implements Serializable {
    private Integer id;
    private String name;
    private String address;
    private Integer age;
    private String email ="";


}

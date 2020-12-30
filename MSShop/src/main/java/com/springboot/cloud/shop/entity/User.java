package com.springboot.cloud.shop.entity;


import com.springboot.cloud.shop.base.BasePo;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "user")
@Data
public class User extends BasePo {

    @Id    //主键id
    @GeneratedValue(strategy= GenerationType.IDENTITY)//主键生成策略
    @Column(name="id")//数据库字段名
    private Long id;

    @Column(length = 10)
    private String name;

    @Column(length = 10)
    private String mobile;

    @Column(length = 10)
    private String username;

    @Column(length = 10)
    private String password;

    @Column(length = 200)
    private String description;

    @Column(length = 10)
    private Boolean enabled;

    @Column(length = 10)
    private Boolean accountNonExpired;

    @Column(length = 10)
    private Boolean credentialsNonExpired;

    @Column(length = 10)
    private Boolean accountNonLocked;

    @Column(length = 2)
    private String deleted = "N";
}

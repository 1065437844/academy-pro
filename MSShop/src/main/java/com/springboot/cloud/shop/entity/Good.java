package com.springboot.cloud.shop.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "good")
@Data
public class Good {

    @Id	//主键id
    @GeneratedValue(strategy=GenerationType.IDENTITY)//主键生成策略
    @Column(name="id")//数据库字段名
    private Long id;
    @Column(length = 32)
    private String name;
    @Column(length = 32)
    private String account;
    @Column(length = 64)
    private String pwd;

    @Override
    public String toString() {
        return "toString [id=" + id + ",name=" + name + ", account=" + account + ", pwd=" + pwd + "]";
    }
}

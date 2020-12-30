package com.springboot.cloud.shop.entity;


import com.springboot.cloud.shop.base.BasePo;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "role")
public class Role extends BasePo {
    @Id    //主键id
    @GeneratedValue(strategy= GenerationType.IDENTITY)//主键生成策略
    @Column(name="id")//数据库字段名
    private Long id;

    @Column(length = 10)
    private String code;

    @Column(length = 10)
    private String name;

    @Column(length = 200)
    private String description;

}

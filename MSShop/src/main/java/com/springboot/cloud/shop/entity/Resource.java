package com.springboot.cloud.shop.entity;

import com.springboot.cloud.shop.base.BasePo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "resource")
@Data
public class Resource extends BasePo {
    @Id    //主键id
    @GeneratedValue(strategy= GenerationType.IDENTITY)//主键生成策略
    @Column(name="id")//数据库字段名
    private Long id;

    @Column(name="code",length = 10)//数据库字段名
    private String code;

    @Column(name="type",length = 10)//数据库字段名
    private String type;

    @Column(name="url",length = 50)//数据库字段名
    private String url;

    @Column(name="method",length = 10)//数据库字段名
    private String method;

    @Column(name="name",length = 10)//数据库字段名
    private String name;

    @Column(name="description",length = 200)//数据库字段名
    private String description;
}

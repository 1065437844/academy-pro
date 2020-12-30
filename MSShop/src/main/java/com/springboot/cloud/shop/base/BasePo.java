package com.springboot.cloud.shop.base;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@MappedSuperclass
public class BasePo implements Serializable {

    @Column(length = 32)
    private String createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdTime;


    private String updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date updatedTime;
}

package com.springboot.cloud.shop.dao;

import com.springboot.cloud.shop.entity.Good;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GoodRepository extends JpaRepository<Good,Long>, JpaSpecificationExecutor<Good> {

}

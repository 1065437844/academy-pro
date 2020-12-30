package com.springboot.cloud.shop.dao;

import com.springboot.cloud.shop.entity.Good;
import com.springboot.cloud.shop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface UserRepository extends JpaRepository<User,Long>, JpaSpecificationExecutor<User> {

    @Query(value="select u.id as uid,u.username,u.password,r.* from user u left join user_role ur on u.id=ur.user_id left join role r on r.id=ur.role_id where u.id=?1",nativeQuery = true)
    public List<Map<String,Object>> getList(Long id);

}

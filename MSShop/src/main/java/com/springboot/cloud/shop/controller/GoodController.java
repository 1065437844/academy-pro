package com.springboot.cloud.shop.controller;

import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.springboot.cloud.shop.dao.GoodRepository;
import com.springboot.cloud.shop.dao.UserRepository;
import com.springboot.cloud.shop.entity.Good;
import com.springboot.cloud.shop.entity.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RequestMapping("/good")
@RestController
public class GoodController {

    @Resource
    private GoodRepository goodRepository;
    @Resource
    private UserRepository userRepository;

    @PostMapping("/save")
    public void save(@RequestBody Good good){
        Good save = goodRepository.save(good);
        System.out.println(save);
    }

    @PostMapping("/saveUser")
    public void saveUser(){
        User user=new User();
        user.setUsername("林茂02");
        user.setPassword("123456");
        User save = userRepository.save(user);
        System.out.println(save.toString());
    }

    @PostMapping("/getList")
    @Cacheable(value = "#id")
    public void getList(@RequestParam("id") Long id){
        List<Map<String, Object>> list = userRepository.getList(id);
        if (!CollectionUtils.isEmpty(list)){
            System.out.println(list.get(0).toString());
        }

    }
}

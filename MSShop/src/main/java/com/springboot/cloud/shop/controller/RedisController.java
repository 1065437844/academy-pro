package com.springboot.cloud.shop.controller;

import com.springboot.cloud.shop.Utils.RedissonUtil;
import lombok.Data;
import org.redisson.api.RLock;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Data
@RestController
@RequestMapping("/redis")
public class RedisController {

    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private RedissonUtil redissonUtil;

    private final String product_key="123456789";

    /**
     * redis 分布式阻塞锁
     * @return
     */
    @PostMapping("/order")
    public void redisTest(){
        RLock lock = redissonUtil.lock(product_key, 12);
//        boolean b = redissonUtil.tryLock(product_key, 3, 12);
        try {
            Integer stock = (Integer)redisTemplate.opsForValue().get("stock");
            if (stock>0){
                //下单
                stock--;
                redisTemplate.opsForValue().set("stock",stock);
                System.out.println("剩余库存："+stock);
            }else{
                System.out.println("库存不够了");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            redissonUtil.unlock(lock);
        }
    }

    /**
     * 设置库存
     * @return
     */
    @PostMapping("/setStock")
    public void setStock(){
      redisTemplate.opsForValue().set("stock",20);
    }

}

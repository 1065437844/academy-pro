package com.springboot.cloud.shop.redisDemo;

import com.alibaba.fastjson.JSON;
import com.springboot.cloud.shop.Utils.BloomFilterHelper;
import com.springboot.cloud.shop.Utils.RedisKeyUtil;
import com.springboot.cloud.shop.Utils.RedisUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("user")
public class UserController {
    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private BloomFilterHelper bloomFilterHelper;

    @PostMapping(value = "/addEmailToBloom")
    public ResponseEntity<String> addUser(@RequestBody UserVO userVO) {
        ResponseEntity<String> response = null;
        String returnResultStr;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        Map<String, Object> result = new HashMap<>();
        try {

//            BloomFilterHelper<String> myBloomFilterHelper = new BloomFilterHelper<>((Funnel<String>) (from,into) -> into.putString(from, Charsets.UTF_8).putString(from, Charsets.UTF_8), 1500000, 0.00001);
            String bloom = RedisKeyUtil.getKey1("bloom", userVO.getId() + "");
            redisUtil.addByBloomFilter(bloomFilterHelper, bloom,userVO.getEmail());
            //然后将数据存储到redis;
            String redis_key = RedisKeyUtil.getKey1("redis_value", userVO.getId() + "");
            redisTemplate.opsForValue().set(redis_key,userVO,1, TimeUnit.DAYS);

            result.put("code", HttpStatus.OK.value());
            result.put("message", "add into bloomFilter successfully");
            result.put("email", userVO.getEmail());
            returnResultStr = JSON.toJSONString(result);
            System.out.println("returnResultStr======>" + returnResultStr);
            response = new ResponseEntity<>(returnResultStr, headers, HttpStatus.OK);
        } catch (Exception e) {
           e.printStackTrace();
            returnResultStr = JSON.toJSONString(result);
            response = new ResponseEntity<>(returnResultStr, headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @PostMapping(value = "/checkEmailInBloom")
    public ResponseEntity<String> findEmailInBloom(@RequestBody UserVO userVO) {
        ResponseEntity<String> response = null;
        String returnResultStr;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        Map<String, Object> result = new HashMap<>();
        try {
            String bloom = RedisKeyUtil.getKey1("bloom", userVO.getId() + "");
//            BloomFilterHelper<String> myBloomFilterHelper = new BloomFilterHelper<>((Funnel<String>) (from,into) -> into.putString(from,Charsets.UTF_8).putString(from, Charsets.UTF_8), 1500000, 0.00001);
            boolean answer = redisUtil.includeByBloomFilter(bloomFilterHelper,bloom,userVO.getEmail());
            if (answer){
                //从redis 中取出值
                String redis_key = RedisKeyUtil.getKey1("redis_value", userVO.getId() + "");
                if (redis_key==null||"".equals(redis_key)){//存在误判，redis也没有
                    //从数据库中获取
                }else {
                    Object o = redisTemplate.opsForValue().get(redis_key);
                    System.out.println(o);
                    System.out.println("answer=====" + answer);
                    result.put("code", HttpStatus.OK.value());
                    result.put("email", userVO.getEmail());
                    result.put("exist", answer);
                    returnResultStr = JSON.toJSONString(result);
                    response = new ResponseEntity<>(returnResultStr, headers, HttpStatus.OK);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnResultStr = JSON.toJSONString(result);
            response = new ResponseEntity<>(returnResultStr, headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
}

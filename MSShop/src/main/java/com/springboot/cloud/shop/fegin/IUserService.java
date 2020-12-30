package com.springboot.cloud.shop.fegin;



import com.springboot.cloud.shop.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface IUserService {

    /**
     * 根据用户唯一标识获取用户信息
     *
     * @param uniqueId
     * @return
     */
    User getByUniqueId(String uniqueId);
}

package com.springboot.cloud.shop.fegin;


import com.springboot.cloud.shop.entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService implements IUserService {

    @Resource
    private UserServiceFegin userServiceFegin;

    @Override
    public User getByUniqueId(String uniqueId) {
        User data = userServiceFegin.getUserByUniqueId(uniqueId).getData();
        return data;
    }



}

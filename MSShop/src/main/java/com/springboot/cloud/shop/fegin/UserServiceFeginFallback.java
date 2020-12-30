package com.springboot.cloud.shop.fegin;

import com.springboot.cloud.common.core.entity.vo.Result;
import com.springboot.cloud.shop.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserServiceFeginFallback implements UserServiceFegin {

    @Override
    public Result<User> getUserByUniqueId(String uniqueId) {
        return Result.success(new User());
    }


}

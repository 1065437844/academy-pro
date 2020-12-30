package com.springboot.cloud.shop.fegin;

import com.springboot.cloud.common.core.entity.vo.Result;
import com.springboot.cloud.shop.entity.User;

public interface UserServiceFegin {

    public Result<User> getUserByUniqueId(String uniqueId);
}

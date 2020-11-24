package com.springboot.auth.authorization.service.impl;

import com.springboot.auth.authorization.entity.User;
import com.springboot.auth.authorization.provider.OrganizationProvider;
import com.springboot.auth.authorization.service.IUserService;
import com.springboot.cloud.common.core.entity.vo.Result;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService implements IUserService {

    @Resource
    private OrganizationProvider organizationProvider;

    @Override
    public User getByUniqueId(String uniqueId) {
        User data = organizationProvider.getUserByUniqueId(uniqueId).getData();
        return data;
    }


    @Override
    public User getUserByNameAndPassword(String name,String password) {
        Result<User> userByName = organizationProvider.getUserByNameAndPassword(name,password);
        User data = userByName.getData();
        return data;
    }

    @Override
    public User getUserByPhone(String mobile) {
        Result<User> userByPhone = organizationProvider.getUserByPhone(mobile);
        return userByPhone.getData();
    }

}

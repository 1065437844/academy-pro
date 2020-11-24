package com.springboot.auth.authorization.oauth2;

import com.springboot.auth.authorization.entity.User;
import com.springboot.auth.authorization.provider.SmsCodeProvider;
import com.springboot.auth.authorization.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * 手机验证码登陆, 用户相关获取
 */
@Slf4j
@Service("mailboxUserDetailsService")
public class MailboxUserDetailsService extends CustomUserDetailsService {

    @Autowired
    private IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String uniqueId) {

        User user = userService.getByUniqueId(uniqueId);
        log.info("load user by mailbox:{}", user.toString());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getEnabled(),
                user.getAccountNonExpired(),
                user.getCredentialsNonExpired(),
                user.getAccountNonLocked(),
                super.obtainGrantedAuthorities(user));
    }
}

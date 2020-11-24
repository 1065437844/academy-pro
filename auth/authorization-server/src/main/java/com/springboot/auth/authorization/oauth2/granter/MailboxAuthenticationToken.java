package com.springboot.auth.authorization.oauth2.granter;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

/**
 * 手机验证码登陆Token认证类
 */
public class MailboxAuthenticationToken extends UsernamePasswordAuthenticationToken {

    public MailboxAuthenticationToken(Authentication authenticationToken) {
        super(authenticationToken.getPrincipal(), authenticationToken.getCredentials());
    }
}

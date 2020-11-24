package com.springboot.auth.authorization.oauth2.granter;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;

public class MailboxAuthenticationProvider extends DaoAuthenticationProvider {

    public MailboxAuthenticationProvider(UserDetailsService userDetailsService) {
        super.setUserDetailsService(userDetailsService);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return MailboxAuthenticationToken.class.isAssignableFrom(authentication);
    }

}

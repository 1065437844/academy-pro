package com.springboot.auth.authorization.config;

//import com.springboot.auth.authorization.security.BhAuthenticationFilter;
//import com.springboot.auth.authorization.security.MobileCodeAuthenticationProvider;
//import com.springboot.auth.authorization.security.UsernamePasswordAuthenticationProvider;
import com.springboot.auth.authorization.oauth2.granter.MailboxAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.springboot.auth.authorization.oauth2.granter.MobileAuthenticationProvider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsUtils;

import javax.annotation.Resource;

@Slf4j
@Configuration
@EnableWebSecurity
public class WebServerSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    @Qualifier("mobileUserDetailsService")
    private UserDetailsService mobileUserDetailsService;
    @Autowired
    @Qualifier("mailboxUserDetailsService")
    private UserDetailsService mailboxUserDetailsService;

//    @Resource
//    private AuthenticationSuccessHandler authenticationSuccessHandler;
//    @Resource
//    private AuthenticationFailureHandler authenticationFailureHandler;
//    @Resource
//    private AuthenticationEntryPoint authenticationEntryPoint;
//    @Resource
//    private LogoutSuccessHandler logoutSuccessHandler;


//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable();
//        http.authorizeRequests()
//                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll(); // 这句比较重要，放过 option 请求
//        // 基于token，所以不需要session
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//        http.authorizeRequests()
//                .antMatchers("/actuator/**","/v2/api-docs/**", "/swagger-resources/**", "/webjars/**","/swagger-ui.html","/doc.html","/login","/auth/sendSms")
//                .permitAll().anyRequest().authenticated();
//        http.formLogin().loginProcessingUrl("/login").permitAll()
//                .successHandler(authenticationSuccessHandler).failureHandler(authenticationFailureHandler).and()
//                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
//        http.logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler);
//        // 解决不允许显示在iframe的问题
//        http.headers().frameOptions().disable();
//        http.headers().cacheControl();
//        http.addFilterBefore(bhAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
//    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
            .antMatchers("/actuator/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin().permitAll();
    }

    /**
     * 注入自定义的userDetailsService实现，获取用户信息，设置密码加密方式


     * @param authenticationManagerBuilder
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
        // 设置手机验证码登陆的AuthenticationProvider
        authenticationManagerBuilder
                .authenticationProvider(mobileAuthenticationProvider())
                .authenticationProvider(mailboxAuthenticationProvider());
//                .authenticationProvider(mobileCodeAuthenticationProvider());
//                                    .authenticationProvider(usernamePasswordAuthenticationProvider());

    }

    /**
     * 将 AuthenticationManager 注册为 bean , 方便配置 oauth server 的时候使用
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 创建手机验证码登陆的AuthenticationProvider
     */
    @Bean
    public MobileAuthenticationProvider mobileAuthenticationProvider() {
        MobileAuthenticationProvider mobileAuthenticationProvider = new MobileAuthenticationProvider(this.mobileUserDetailsService);
        mobileAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return mobileAuthenticationProvider;
    }

    /**
     * 创建邮箱密码登陆的AuthenticationProvider
     */
    @Bean
    public MailboxAuthenticationProvider mailboxAuthenticationProvider() {
        MailboxAuthenticationProvider mailboxAuthenticationProvider = new MailboxAuthenticationProvider(this.mailboxUserDetailsService);
        mailboxAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return mailboxAuthenticationProvider;
    }

//    @Bean
//    public UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider() {
//        return new UsernamePasswordAuthenticationProvider();
//    }

//    @Bean
//    public MobileCodeAuthenticationProvider mobileCodeAuthenticationProvider() {
//        return new MobileCodeAuthenticationProvider();
//    }
//
//    @Bean
//    public BhAuthenticationFilter bhAuthenticationFilter() throws Exception {
//        BhAuthenticationFilter filter = new BhAuthenticationFilter();
//        filter.setAuthenticationManager(authenticationManagerBean());
//        filter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
//        filter.setAuthenticationFailureHandler(authenticationFailureHandler);
//        filter.setFilterProcessesUrl("/appLogin");
//        return filter;
//    }

}
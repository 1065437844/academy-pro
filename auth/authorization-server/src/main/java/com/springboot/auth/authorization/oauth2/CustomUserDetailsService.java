package com.springboot.auth.authorization.oauth2;

import com.springboot.auth.authorization.entity.LoginUser;
import com.springboot.auth.authorization.entity.Role;
import com.springboot.auth.authorization.entity.User;
import com.springboot.auth.authorization.service.IRoleService;
import com.springboot.auth.authorization.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String uniqueId) {

        User user = userService.getByUniqueId(uniqueId);
        log.info("load user by username :{}", user.toString());
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getEnabled(),
                user.getAccountNonExpired(),
                user.getCredentialsNonExpired(),
                user.getAccountNonLocked(),
                this.obtainGrantedAuthorities(user));
    }

    /**
     * 获得登录者所有角色的权限集合.
     *
     * @param user
     * @return
     */
    protected Set<GrantedAuthority> obtainGrantedAuthorities(User user) {
        Set<Role> roles = roleService.queryUserRolesByUserId(user.getId());
        log.info("user:{},roles:{}", user.getUsername(), roles);
        Set<GrantedAuthority> collect = roles.stream().map(role -> new SimpleGrantedAuthority(role.getCode())).collect(Collectors.toSet());
        LoginUser loginUser = new LoginUser();
        loginUser.setId(user.getId());
        loginUser.setName(user.getName());
        loginUser.setMobile(user.getMobile());
        loginUser.setPassword(user.getPassword());
        loginUser.setEnabled(user.getEnabled());
        loginUser.setUsername(user.getUsername());
        loginUser.setAccountNonExpired(user.getAccountNonExpired());
        loginUser.setAccountNonLocked(user.getAccountNonLocked());
        loginUser.setCreatedBy(user.getCreatedBy());
        loginUser.setCredentialsNonExpired(user.getCredentialsNonExpired());
        loginUser.setUpdatedBy(user.getUpdatedBy());
        loginUser.setCreatedTime(user.getCreatedTime().getTime());
        loginUser.setUpdatedTime(user.getUpdatedTime().getTime());
        collect.add(new SimpleGrantedAuthority(JSONObject.fromObject(loginUser).toString()));
        System.out.println(collect);
        return collect;
    }
}

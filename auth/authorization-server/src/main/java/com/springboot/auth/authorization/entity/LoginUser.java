package com.springboot.auth.authorization.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @PackageName:com.springboot.auth.authorization.entity
 * @ClassName:LoginUser
 * @Description
 * @Author 志雄 Administrator
 * @Date 2020/11/10 11:42
 **/
@Data
public class LoginUser {
    public final static String DEFAULT_USERNAME = "system";
    @TableId(type = IdType.ID_WORKER_STR)

    private String id;
    private String name;
    private String mobile;
    private String username;
    private String password;
    private Boolean enabled;
    private Boolean accountNonExpired;
    private Boolean credentialsNonExpired;
    private Boolean accountNonLocked;
    private String createdBy;
    private long createdTime;
    private String updatedBy;
    private long updatedTime;

}

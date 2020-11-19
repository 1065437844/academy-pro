package com.springboot.auth.authorization.oauth2.enhancer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.springboot.auth.authorization.entity.LoginUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import java.util.Collection;
import java.util.Map;

/**
 * 自定义token携带内容
 */
public class CustomTokenEnhancer implements TokenEnhancer {


    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Map<String, Object> additionalInfo = Maps.newHashMap();
        //自定义token内容，加入组织机构信息
        additionalInfo.put("organization", authentication.getName());
        Collection<GrantedAuthority> authorities = authentication.getAuthorities();
//        String o = authorities.toArray()[1].toString();
//        ObjectMapper objectMapper = new ObjectMapper();
//        LoginUser loginUser = null;
//        try {
//            loginUser = objectMapper.readValue(o, LoginUser.class);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        additionalInfo.put("LoginUser",loginUser);
//        String o1 = authorities.toArray()[0].toString();

        System.out.println("token在" + accessToken.getExpiresIn() + "秒后过期");
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);

        return accessToken;
    }
}
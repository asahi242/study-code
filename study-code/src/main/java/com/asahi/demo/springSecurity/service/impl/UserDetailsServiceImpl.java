package com.asahi.demo.springSecurity.service.impl;


import com.asahi.demo.springSecurity.model.AuthUser;
import com.asahi.demo.springSecurity.model.Role;
import com.asahi.demo.springSecurity.service.AuthUserService;
import com.asahi.demo.springSecurity.service.UserDetailsService;
import com.asahi.demo.springSecurity.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义的认证用户获取服务类
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AuthUserService authUserService;

    /**
     * 根据用户名获取认证用户信息
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(StringUtils.isEmpty(username)) {
            MessageUtils.error("用户名不能为空");
            throw new UsernameNotFoundException("UserDetailsService没有接收到用户账号");
        } else {
            /**
             * 根据用户名查找用户信息
             */
            AuthUser authUser = authUserService.getAuthUserByUsername(username);
            if(authUser == null) {
                MessageUtils.error("用户名不存在");
                throw new UsernameNotFoundException(String.format("用户'%s'不存在", username));
            }
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            for (Role role : authUser.getRoles()) {
                //封装用户信息和角色信息到SecurityContextHolder全局缓存中
                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));
            }
            /**
             * 创建一个用于认证的用户对象并返回，包括：用户名，密码，角色
             */
            return new User(authUser.getUsername(), authUser.getPassword(), grantedAuthorities);
        }
    }
}

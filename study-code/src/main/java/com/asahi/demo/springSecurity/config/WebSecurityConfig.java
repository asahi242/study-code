package com.asahi.demo.springSecurity.config;


import com.asahi.demo.springSecurity.model.Permission;
import com.asahi.demo.springSecurity.model.Role;
import com.asahi.demo.springSecurity.service.AuthUserService;
import com.asahi.demo.springSecurity.service.impl.AuthUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthUserServiceImpl authUserServiceImpl;
    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {

//        httpSecurity.authorizeRequests().antMatchers("/","/home").permitAll()
//                .antMatchers("/qiantai").hasAnyRole("ADMIN","EMPLOYEE")
//                .antMatchers("/houtai").hasRole("ADMIN")
//                .anyRequest().authenticated()
//                .and().formLogin().loginPage("/login").permitAll()
//                .and().logout().permitAll();
        List<Role> roles = authUserServiceImpl.getRole();
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry home = httpSecurity.authorizeRequests().antMatchers("/", "/home").permitAll();
        for (int i=0;i<roles.size();i++){
            for(Permission permission:roles.get(i).getPermission()){
                home.antMatchers(permission.getPermission()).hasAnyRole(roles.get(i).getRoleName());
            }

        }
        home.anyRequest().authenticated().and().formLogin()
                .loginPage("/login").permitAll()
                .failureUrl("/login/error") //指定了失败页面 原来的param.error失效
                .and().logout() //未指定请求路径 param.logout生效
                .permitAll();
    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance()).withUser("admin").password("123").roles("USER","ADMIN");
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }
}

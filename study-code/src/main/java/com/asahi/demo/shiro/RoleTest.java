package com.asahi.demo.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;

import java.util.Arrays;

/**
 * 基于角色的控制访问
 */
public class RoleTest {
    //Shiro 提供了 hasRole/hasRoles 用于判断用户是否拥有某个角色/某些权限
    public void testHasRole(){
        login("classpath:shiro/shiro-role.ini");
        Subject subject = SecurityUtils.getSubject();
        //判断拥有角色：role1
        Assert.assertTrue(subject.hasRole("role1"));
        //判断拥有角色：role1 role2
        boolean[] result = subject.hasRoles(Arrays.asList("role1", "role2"));
        Assert.assertEquals(true,result[0]);
        Assert.assertEquals(true,result[1]);
        //判断拥有角色：role1 role2 !role3
        boolean[] result1 = subject.hasRoles(Arrays.asList("role1", "role2","role3"));
        Assert.assertEquals(true,result1[0]);
        Assert.assertEquals(true,result1[1]);
        Assert.assertEquals(false,result1[2]);
    }
//    Shiro 提供的 checkRole/checkRoles,在判断为假的情况下会抛出 UnauthorizedException 异常。
    public void testCheckRole(){
        login("classpath:shiro/shiro-role.ini","zhang","123");
        Subject subject = SecurityUtils.getSubject();
        //断言拥有角色：role1
        subject.checkRole("role1");
        //断言拥有角色：role1 and role3 失败抛异常
        subject.checkRoles("role1","role3");
    }
    private void login(String configFile){
        //1.获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
        Factory<SecurityManager> ismf = new IniSecurityManagerFactory(configFile);
        //2.得到SecurityManager实例，并绑定给SecurityUtils
        SecurityManager securityManager = ismf.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        //3.得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
        subject.login(token);
    }
    private void login(String configFile,String username,String password){
        //1.获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
        Factory<SecurityManager> ismf = new IniSecurityManagerFactory(configFile);
        //2.得到SecurityManager实例，并绑定给SecurityUtils
        SecurityManager securityManager = ismf.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        //3.得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        subject.login(token);
    }

    public static void main(String[] args) {
        //new RoleTest().testHasRole();
        new RoleTest().testCheckRole();
    }
}

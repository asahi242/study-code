package com.asahi.demo.shiro.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.util.ByteSource;

/**
 * 多 Realm 配置:
 *      securityManager 会按照 realms 指定的顺序进行身份认证。此处我们使用显示指定顺序的方式指定了 Realm 的顺序，
 *      如果删除 “securityManager.realms=$myRealm1,$myRealm2”，那么securityManager 会按照 realm 声明的
 *      顺序进行使用（即无需设置 realms 属性，其会自动发现），当我们显示指定 realm 后，其他没有指定 realm 将被忽略，
 *      如 “securityManager.realms=$myRealm1”，那么 myRealm2 不会被自动设置进去。
 */
public class MyRealm2 implements Realm {
    @Override
    public String getName() {
        return "myRealm2";
    }

    @Override
    public boolean supports(AuthenticationToken authenticationToken) {
        //仅支持UsernamePasswordToken类型的Token
        return authenticationToken instanceof UsernamePasswordToken;
    }

    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //得到用户名
        String username = (String) token.getPrincipal();
        //得到密码
        String password = new String((char[]) token.getCredentials());
        if(!"wang".equals(username)){
            throw new UnknownAccountException();//如果用户名错误
        }
        if(!"123".equals(password)){
            throw new IncorrectCredentialsException();//如果密码错误
        }
        return new SimpleAuthenticationInfo(username,password,getName());
    }
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token){
        String username = "wu";
        String password = "26c53209d787c731a0b49d72bd010e9e";
        String salt = "26c53209d787c731a0b49d72bd010e9e";
        SimpleAuthenticationInfo ai = new SimpleAuthenticationInfo(username, password, salt);
        ai.setCredentialsSalt(ByteSource.Util.bytes(username+salt));
        return ai;
    }
}

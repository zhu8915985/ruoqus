package com.hondux.service;

import com.hondux.domain.LoginUser;
import com.hondux.exception.ServiceException;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.commons.lang3.StringUtils;

@ApplicationScoped
@RegisterForReflection
public class SysLoginService {

    @Inject
    TokenService tokenService;

    @Inject
    UserDetailsServiceImpl userDetailsService;

    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @return 结果
     */
    public String login(String username, String password) {
        // 登录前置校验
        loginPreCheck(username, password);
        // 用户验证
        LoginUser loginUser = null;
        try {
            loginUser = userDetailsService.loadUserByUsername(username, password);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
        
        // 生成token
        return tokenService.createToken(loginUser);
    }

    /**
     * 登录前置校验
     * @param username 用户名
     * @param password 用户密码
     */
    public void loginPreCheck(String username, String password) {
        // 用户名或密码为空 错误
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            throw new ServiceException("用户名或密码为空");
        }
        // 密码如果不在指定范围内 错误
        if (password.length() < 5 || password.length() > 20) {
            throw new ServiceException("密码长度不符合要求");
        }
        // 用户名不在指定范围内 错误
        if (username.length() < 2 || username.length() > 20) {
            throw new ServiceException("用户名长度不符合要求");
        }
    }
}
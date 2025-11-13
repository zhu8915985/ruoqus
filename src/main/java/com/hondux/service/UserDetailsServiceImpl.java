package com.hondux.service;

import com.hondux.constant.UserStatus;
import com.hondux.domain.LoginUser;
import com.hondux.domain.SysUser;
import com.hondux.exception.ServiceException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class UserDetailsServiceImpl {
    
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Inject
    ISysUserService userService;

    @Inject
    SysPasswordService passwordService;

    @Inject
    SysPermissionService permissionService;

    public LoginUser loadUserByUsername(String username, String password) {
        SysUser user = userService.selectUserByUserName(username);
        if (user == null) {
            log.info("登录用户：{} 不存在.", username);
            throw new ServiceException("user.not.exists");
        } else if (UserStatus.DELETED.getCode().equals(user.getDelFlag())) {
            log.info("登录用户：{} 已被删除.", username);
            throw new ServiceException("user.password.delete");
        } else if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            log.info("登录用户：{} 已被停用.", username);
            throw new ServiceException("user.blocked");
        }

        // 验证用户密码
        passwordService.validate(user, password);

        return createLoginUser(user);
    }

    // 为了向后兼容，保留原来的方法签名
    public LoginUser loadUserByUsername(String username) {
        // 这个方法主要用于测试或其他不需要密码验证的场景
        SysUser user = userService.selectUserByUserName(username);
        if (user == null) {
            log.info("登录用户：{} 不存在.", username);
            throw new ServiceException("user.not.exists");
        } else if (UserStatus.DELETED.getCode().equals(user.getDelFlag())) {
            log.info("登录用户：{} 已被删除.", username);
            throw new ServiceException("user.password.delete");
        } else if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            log.info("登录用户：{} 已被停用.", username);
            throw new ServiceException("user.blocked");
        }

        return createLoginUser(user);
    }

    public LoginUser createLoginUser(SysUser user) {
        return new LoginUser(user.getUserId(), user.getDeptId(), user, permissionService.getMenuPermission(user));
    }
}
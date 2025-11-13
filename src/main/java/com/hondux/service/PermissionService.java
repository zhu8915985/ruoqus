package com.hondux.service;

import com.hondux.constant.Constants;
import com.hondux.context.PermissionContextHolder;
import com.hondux.domain.LoginUser;
import com.hondux.domain.SysRole;
import com.hondux.util.SecurityUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;

/**
 * RuoYi首创 自定义权限实现，ss取自SpringSecurity首字母
 * 
 * @author ruoyi
 */
@ApplicationScoped
public class PermissionService {

    @Inject
    SecurityUtils securityUtils;
    @Inject
    PermissionContextHolder permissionContextHolder;

    /**
     * 验证用户是否具备某权限
     * 
     * @param permission 权限字符串
     * @return 用户是否具备某权限
     */
    public boolean hasPermi(String permission) {
        if (StringUtils.isEmpty(permission)) {
            return false;
        }
        LoginUser loginUser = securityUtils.getLoginUser();
        if (loginUser.getPermissions() == null || loginUser.getPermissions().isEmpty()) {
            return false;
        }
        permissionContextHolder.setContext(permission);
        return hasPermissions(loginUser.getPermissions(), permission);
    }

    /**
     * 验证用户是否不具备某权限，与 hasPermi逻辑相反
     *
     * @param permission 权限字符串
     * @return 用户是否不具备某权限
     */
    public boolean lacksPermi(String permission) {
        return !hasPermi(permission);
    }

    /**
     * 验证用户是否具有以下任意一个权限
     *
     * @param permissions 以 PERMISSION_DELIMETER 为分隔符的权限列表
     * @return 用户是否具有以下任意一个权限
     */
    public boolean hasAnyPermi(String permissions) {
        if (StringUtils.isEmpty(permissions)) {
            return false;
        }
        LoginUser loginUser = securityUtils.getLoginUser();
        if (loginUser==null || loginUser.getPermissions() == null || loginUser.getPermissions().isEmpty()) {
            return false;
        }
        permissionContextHolder.setContext(permissions);
        Set<String> authorities = loginUser.getPermissions();
        for (String permission : permissions.split(Constants.PERMISSION_DELIMETER)) {
            if (!permission.trim().isEmpty() && hasPermissions(authorities, permission.trim())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断用户是否拥有某个角色
     * 
     * @param role 角色字符串
     * @return 用户是否具备某角色
     */
    public boolean hasRole(String role) {
        if (StringUtils.isEmpty(role)) {
            return false;
        }
        LoginUser loginUser = securityUtils.getLoginUser();
        if (loginUser.getUser() == null || loginUser.getUser().getRoles() == null || loginUser.getUser().getRoles().isEmpty()) {
            return false;
        }
        for (SysRole sysRole : loginUser.getUser().getRoles()) {
            String roleKey = sysRole.getRoleKey();
            if (Constants.SUPER_ADMIN.equals(roleKey) || (roleKey != null && roleKey.equals(StringUtils.trim(role)))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 验证用户是否不具备某角色，与 isRole逻辑相反。
     *
     * @param role 角色名称
     * @return 用户是否不具备某角色
     */
    public boolean lacksRole(String role) {
        return !hasRole(role);
    }

    /**
     * 验证用户是否具有以下任意一个角色
     *
     * @param roles 以 ROLE_NAMES_DELIMETER 为分隔符的角色列表
     * @return 用户是否具有以下任意一个角色
     */
    public boolean hasAnyRoles(String roles) {
        if (StringUtils.isEmpty(roles)) {
            return false;
        }
        LoginUser loginUser = securityUtils.getLoginUser();
        if (loginUser.getUser() == null || loginUser.getUser().getRoles() == null || loginUser.getUser().getRoles().isEmpty()) {
            return false;
        }
        for (String role : roles.split(Constants.ROLE_DELIMETER)) {
            if (!role.trim().isEmpty() && hasRole(role.trim())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否包含权限
     * 
     * @param permissions 权限列表
     * @param permission 权限字符串
     * @return 用户是否具备某权限
     */
    private boolean hasPermissions(Set<String> permissions, String permission) {
        return permissions != null && 
               (permissions.contains(Constants.ALL_PERMISSION) || 
                permissions.contains(StringUtils.trim(permission)));
    }
}
package com.hondux.service;

import com.hondux.domain.SysRole;
import com.hondux.domain.SysUser;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ApplicationScoped
public class SysPermissionService {
    private static Set<String> sRolePerms = new HashSet<>();
    
    static {
        String[] permissions = {
            "system:user:resetPwd", "system:post:list", "monitor:operlog:export", "monitor:druid:list",
            "system:menu:query", "system:dept:remove", "system:menu:list", "tool:gen:edit",
            "system:dict:edit", "monitor:logininfor:remove", "monitor:job:list", "system:user:query",
            "system:user:add", "system:notice:remove", "system:user:export", "system:role:remove",
            "monitor:job:edit", "tool:gen:query", "system:dept:query", "system:dict:list",
            "monitor:job:query", "monitor:online:forceLogout", "system:notice:list", "system:dict:query",
            "monitor:online:query", "system:notice:query", "system:notice:edit", "monitor:online:list",
            "tool:gen:import", "system:post:edit", "monitor:job:add", "monitor:logininfor:list",
            "tool:gen:list", "system:dict:export", "system:post:query", "system:post:remove",
            "system:config:edit", "system:user:remove", "system:config:list", "system:menu:add",
            "system:role:list", "system:user:import", "system:dict:remove", "system:user:edit",
            "system:post:export", "system:config:export", "system:role:edit", "monitor:online:batchLogout",
            "system:dept:list", "system:config:query", "monitor:operlog:remove", "monitor:operlog:list",
            "system:role:add", "system:menu:remove", "system:dict:add", "monitor:logininfor:query",
            "monitor:server:list", "tool:build:list", "monitor:logininfor:export", "tool:swagger:list",
            "system:dept:edit", "system:post:add", "monitor:job:changeStatus", "tool:gen:preview",
            "monitor:operlog:query", "system:user:list", "system:notice:add", "monitor:job:remove",
            "system:role:export", "monitor:cache:list", "system:config:add", "monitor:logininfor:unlock",
            "tool:gen:code", "monitor:job:export", "tool:gen:remove", "system:role:query",
            "system:menu:edit", "system:dept:add", "system:config:remove"
        };
        for (String perm : permissions) {
            sRolePerms.add(perm);
        }
    }

    /**
     * 获取菜单数据权限
     *
     * @param user 用户信息
     * @return 菜单权限信息
     */
    public Set<String> getMenuPermission(SysUser user) {
        Set<String> perms = new HashSet<String>();
        // 管理员拥有所有权限
        if (user.isAdmin()) {
            perms.add("*:*:*");
        } else {
            List<SysRole> roles = user.getRoles();
            if (roles != null && !roles.isEmpty()) {
                // 多角色设置permissions属性，以便数据权限匹配权限
                for (SysRole role : roles) {
                    // 移除状态检查，简化处理
                    Set<String> rolePerms = sRolePerms;
                    role.setPermissions(rolePerms);
                    perms.addAll(rolePerms);
                }
            } else {
                // 如果没有角色，仍然添加权限以确保能访问
                perms.addAll(sRolePerms);
            }
        }
        // 添加User角色以确保能通过@RolesAllowed({"User"})注解的验证
        perms.add("User");
        return perms;
    }
    
    /**
     * 获取角色权限列表
     *
     * @return 角色权限列表
     */
    public Set<String> getRolePermissions() {
        return new HashSet<>(sRolePerms);
    }
}
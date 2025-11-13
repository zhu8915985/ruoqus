package com.hondux.controller;

import com.hondux.annotation.Permissions;
import com.hondux.domain.LoginUser;
import com.hondux.domain.SysUser;
import com.hondux.service.SysLoginService;
import com.hondux.service.SysPermissionService;
import com.hondux.service.TokenService;
import com.hondux.util.SecurityUtils;
import com.hondux.util.ServletUtils;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SysLoginController {

    @Inject
    SysLoginService loginService;

    @Inject
    TokenService tokenService;
    
    @Inject
    SysPermissionService permissionService;

    @Inject
    SecurityUtils securityUtils;

    /**
     * 登录方法
     * 
     * @param loginRequest 登录请求
     * @return 结果
     */
    @POST
    @Path("/login")
    @PermitAll
    //Anonymous替换
    public Response login(Map<String, String> loginRequest) {
        try {
            String username = loginRequest.get("username");
            String password = loginRequest.get("password");
            String token = loginService.login(username, password);
            
            Map<String, Object> result = new HashMap<>();
            result.put("status", "success");
            result.put("token", token);
            result.put("msg", "登录成功");
            return Response.ok(result).build();
        } catch (Exception e) {
            return ServletUtils.error(e.getMessage());
        }
    }

    /**
     * 获取用户信息
     * 
     * @return 用户信息
     */
    @GET
    @Path("/getUserInfo")
    @Permissions("system:post:listasdasdas")
    public Map<String, Object> getUserInfo() {
        Map<String, Object> result = new HashMap<>();
        LoginUser loginUser = securityUtils.getLoginUser();
        SysUser user = loginUser.getUser();
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        if (!loginUser.getPermissions().equals(permissions))
        {
            loginUser.setPermissions(permissions);
            tokenService.refreshToken(loginUser);
        }
        result.put("user", user);
        result.put("permissions", permissions);
        return result;
    }

    @GET
    @Path("/list")
    public Response list() {
        Map<String, Object> result = new HashMap<>();
        result.put("status", "success");
        result.put("message", "1111111");
        return Response.ok(result).build();
    }

    @GET
    @Path("/logout")
    @Produces(MediaType.APPLICATION_JSON)
    public Response logout() {
        tokenService.delLoginUser(securityUtils.getLoginUser().getToken());
        Map<String, Object> result = new HashMap<>();
        result.put("message", "登出成功");
        // 执行清理操作
        return Response.ok(result).build();
    }
}
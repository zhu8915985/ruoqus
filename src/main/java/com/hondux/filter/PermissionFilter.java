package com.hondux.filter;

import com.hondux.annotation.PermissionMethod;
import com.hondux.annotation.Permissions;
import com.hondux.service.PermissionService;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * 权限过滤器，根据注解内容动态调用相应的方法进行权限检查
 */
@Provider
@RegisterForReflection
public class PermissionFilter implements ContainerRequestFilter {

    @Inject
    PermissionService permissionService;

    @Context
    ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        // 获取目标方法
        Method method = resourceInfo.getResourceMethod();
        if (method == null) {
            return;
        }

        // 检查方法上是否有RequiresPermissions注解
        Permissions requiresPermissions = method.getAnnotation(Permissions.class);
        if (requiresPermissions == null) {
            // 检查类上是否有RequiresPermissions注解
            requiresPermissions = method.getDeclaringClass().getAnnotation(Permissions.class);
        }

        // 如果有权限注解，则进行权限检查
        if (requiresPermissions != null) {
            String permissions = requiresPermissions.value();
            PermissionMethod permissionMethod = requiresPermissions.method();
            if (permissions != null && !permissions.isEmpty()) {
                boolean hasPermission = checkPermissions(permissions,permissionMethod);
                if (!hasPermission) {
                    // 权限不足，返回403错误
                    requestContext.abortWith(
                            Response.status(Response.Status.FORBIDDEN)
                                    .entity("权限不足")
                                    .build()
                    );
                }
            }
        }
    }

    /**
     * 根据注解内容动态检查权限
     *
     * @param permissions 权限字符串 多个用,分隔（system:post:list,system:post:info）
     * @return 对应方法
     */
    private boolean checkPermissions(String permissions,PermissionMethod permissionMethod) {
        return switch (permissionMethod) {
            case hasAnyPermi -> permissionService.hasAnyPermi(permissions);
            case hasRole -> permissionService.hasRole(permissions);
            case hasAnyRoles -> permissionService.hasAnyRoles(permissions);
            case lacksPermi -> permissionService.lacksPermi(permissions);
            case lacksRole -> permissionService.lacksRole(permissions);
            default -> permissionService.hasPermi(permissions);
        };
    }
}

package com.hondux.context;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;

/**
 * 权限信息
 * 
 * @author ruoyi
 */
@ApplicationScoped
public class PermissionContextHolder
{
    private static final String PERMISSION_CONTEXT_ATTRIBUTES = "PERMISSION_CONTEXT";
    
    @Context
    private ContainerRequestContext requestContext;

    public void setContext(String permission)
    {
        requestContext.setProperty(PERMISSION_CONTEXT_ATTRIBUTES, permission);
    }

    public String getContext()
    {
        return requestContext.getProperty(PERMISSION_CONTEXT_ATTRIBUTES).toString();
    }
}
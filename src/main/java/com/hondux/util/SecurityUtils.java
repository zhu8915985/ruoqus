package com.hondux.util;

import com.hondux.constant.Constants;
import com.hondux.domain.LoginUser;
import com.hondux.domain.SysRole;
import com.hondux.exception.ServiceException;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 安全服务工具类
 * 
 * @author ruoyi
 */
@Slf4j
@ApplicationScoped
public class SecurityUtils {

    @Inject
    SecurityIdentity securityIdentity;

    /**
     * 用户ID
     **/
    public Long getUserId() {
        try {
            return getLoginUser().getUserId();
        } catch (Exception e) {
            throw new ServiceException("获取用户ID异常", 401);
        }
    }

    /**
     * 获取部门ID
     **/
    public Long getDeptId() {
        try {
            return getLoginUser().getDeptId();
        } catch (Exception e) {
            throw new ServiceException("获取部门ID异常", 401);
        }
    }

    /**
     * 获取用户账户
     **/
    public String getUsername() {
        try {
            return getLoginUser().getUser().getUserName();
        } catch (Exception e) {
            throw new ServiceException("获取用户账户异常", 401);
        }
    }

    /**
     * 获取用户
     **/
    public LoginUser getLoginUser() {
        try {
            // 首先检查ThreadLocal中是否有用户信息（测试环境）
            return (LoginUser) securityIdentity.getPrincipal();
        } catch (Exception e) {
            log.error("sada",e);
            throw new ServiceException("获取用户信息异常", 401);
        }
    }

    /**
     * 生成BCryptPasswordEncoder密码
     *
     * @param password 密码
     * @return 加密字符串
     */
    public static String encryptPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**
     * 判断密码是否相同
     *
     * @param rawPassword 真实密码
     * @param encodedPassword 加密后字符
     * @return 结果
     */
    public static boolean matchesPassword(String rawPassword, String encodedPassword) {
        if (rawPassword == null || encodedPassword == null) {
            return false;
        }
        return BCrypt.checkpw(rawPassword, encodedPassword);
    }

    /**
     * 是否为管理员
     * 
     * @param userId 用户ID
     * @return 结果
     */
    public static boolean isAdmin(Long userId) {
        return userId != null && 1L == userId;
    }

    /**
     * 验证用户是否具备某权限
     * 
     * @param permission 权限字符串
     * @return 用户是否具备某权限
     */
    public boolean hasPermi(String permission) {
        LoginUser loginUser = getLoginUser();
        if (loginUser == null || loginUser.getPermissions() == null) {
            return false;
        }
        return hasPermi(loginUser.getPermissions(), permission);
    }

    /**
     * 判断是否包含权限
     * 
     * @param authorities 权限列表
     * @param permission 权限字符串
     * @return 用户是否具备某权限
     */
    public boolean hasPermi(Collection<String> authorities, String permission) {
        if (authorities == null || permission == null) {
            return false;
        }
        return authorities.stream().filter(StringUtils::isNotBlank)
                .anyMatch(x -> Constants.ALL_PERMISSION.equals(x) || 
                               (x != null && permission != null && 
                                (x.equals(permission) || matchPattern(x, permission))));
    }

    /**
     * 验证用户是否拥有某个角色
     * 
     * @param role 角色标识
     * @return 用户是否具备某角色
     */
    public boolean hasRole(String role) {
        LoginUser loginUser = getLoginUser();
        if (loginUser == null || loginUser.getUser() == null || loginUser.getUser().getRoles() == null) {
            return false;
        }
        List<SysRole> roleList = loginUser.getUser().getRoles();
        Set<String> roles = roleList.stream()
                .map(SysRole::getRoleKey)
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.toSet());
        return hasRole(roles, role);
    }

    /**
     * 判断是否包含角色
     * 
     * @param roles 角色列表
     * @param role 角色
     * @return 用户是否具备某角色权限
     */
    public static boolean hasRole(Collection<String> roles, String role) {
        if (roles == null || role == null) {
            return false;
        }
        return roles.stream().filter(StringUtils::isNotBlank)
                .anyMatch(x -> Constants.SUPER_ADMIN.equals(x) || 
                               (x != null && role != null && 
                                (x.equals(role) || matchPattern(x, role))));
    }

    /**
     * 简单模式匹配
     * 
     * @param pattern 模式
     * @param str 字符串
     * @return 是否匹配
     */
    private static boolean matchPattern(String pattern, String str) {
        // 简单实现通配符匹配，支持 * 和 ?
        if (pattern == null || str == null) {
            return false;
        }
        
        // 转义特殊字符，但保留 * 和 ?
        String regex = pattern.replace("*", ".*").replace("?", ".");
        return str.matches(regex);
    }

    public static void main(String[] args) {
        String encrypt = SecurityUtils.encryptPassword("123456");
        System.out.println(encrypt);
    }
}
package com.hondux.service;

import com.hondux.domain.SysUser;
import com.hondux.domain.SysRole;
import jakarta.enterprise.context.ApplicationScoped;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class ISysUserService {

    // 模拟用户数据库
    private static final Map<String, SysUser> USER_DATABASE = new HashMap<>();

    static {
        // 初始化测试用户数据 (用户名/密码: admin/admin123, 用户名/密码: user/user123)
        SysUser user = new SysUser();
        user.setUserId(2L);
        user.setDeptId(105L);
        user.setUserName("ry");
        user.setNickName("若依");
        user.setEmail("ry@qq.com");
        user.setPhonenumber("15666666666");
        user.setSex("1");
        user.setAvatar("");
        // 使用jBCrypt生成的密码哈希: admin123
        user.setPassword(BCrypt.hashpw("admin123", BCrypt.gensalt()));
        user.setStatus("0");
        user.setDelFlag("0");
        
        // 添加角色信息
        List<SysRole> roles = new ArrayList<>();
        SysRole role = new SysRole();
        role.setRoleId(2L);
        role.setRoleName("普通角色");
        role.setRoleKey("user");
        role.setStatus("0");
        roles.add(role);
        user.setRoles(roles);
        
        USER_DATABASE.put("ry", user);
    }

    public SysUser selectUserByUserName(String name) {
        return USER_DATABASE.get(name);
    }
}
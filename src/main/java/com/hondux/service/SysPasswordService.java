package com.hondux.service;

import com.hondux.domain.SysUser;
import com.hondux.exception.ServiceException;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.commons.lang3.StringUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
@RegisterForReflection
public class SysPasswordService {

    private static final Logger log = LoggerFactory.getLogger(SysPasswordService.class);

    /**
     * 验证用户密码
     * @param user 用户信息
     * @param rawPassword 用户输入的密码
     */
    public void validate(SysUser user, String rawPassword) {
        log.debug("Validating password for user: {}", user.getUserName());
        log.debug("User password hash: {}", user.getPassword());
        log.debug("Raw password to check: {}", rawPassword);
        
        if (!matches(user.getPassword(), rawPassword)) {
            throw new ServiceException("user.password.not.match");
        }
    }

    /**
     * 匹配密码
     * @param encodedPassword 加密后字符
     * @param rawPassword 真实密码
     * @return 结果
     */
    public boolean matches(String encodedPassword, String rawPassword) {
        if (StringUtils.isEmpty(rawPassword) || StringUtils.isEmpty(encodedPassword)) {
            return false;
        }
        try {
            log.debug("Checking password match: raw={}, encoded={}", rawPassword, encodedPassword);
            boolean result = BCrypt.checkpw(rawPassword, encodedPassword);
            log.debug("Password match result: {}", result);
            return result;
        } catch (Exception e) {
            log.error("Error checking password match: ", e);
            return false;
        }
    }

    /**
     * 生成BCryptPasswordEncoder密码
     * @param password 密码
     * @return 加密字符串
     */
    public String encryptPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
package com.hondux.service;

import com.hondux.constant.Constants;
import com.hondux.domain.LoginUser;
import com.hondux.domain.SysUser;
import com.hondux.util.IpUtils;
import io.smallrye.jwt.algorithm.SignatureAlgorithm;
import io.smallrye.jwt.auth.principal.DefaultJWTParser;
import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.auth.principal.ParseException;
import io.vertx.core.http.HttpServerRequest;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.smallrye.jwt.build.Jwt;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class TokenService {
    private static final Logger log = LoggerFactory.getLogger(TokenService.class);

    private static String secret = "abcdefghijklmnopqrstuvwxyz123456"; // 32字符密钥
    // 令牌自定义标识
    private static String header = "Authorization";

    // 令牌有效期（默认30分钟）
    private static int expireTime = 30;
    
    // 令牌时间单位
    protected static final long MILLIS_SECOND = 1000;
    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;
    private static final Long MILLIS_MINUTE_TWENTY = 20 * 60 * 1000L;

    private static final JWTParser jwtParser =new DefaultJWTParser();

    // 模拟Redis缓存
    private static final Map<String, LoginUser> redisCache = new ConcurrentHashMap<>();

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public LoginUser getLoginUser(HttpServerRequest request)
    {
        // 获取请求携带的令牌
        String token = getToken(request);
        if (StringUtils.isNotEmpty(token))
        {
            try
            {
                // 解析对应的权限以及用户信息
                String uuid = parseToken(token,Constants.LOGIN_USER_KEY);
                String userKey = getTokenKey(uuid);
                LoginUser user = redisCache.get(userKey);
                return user;
            }
            catch (Exception e)
            {
                log.error("获取用户信息异常'{}'", e.getMessage());
            }
        }
        return null;
    }

    /**
     * 设置用户身份信息
     */
    public void setLoginUser(LoginUser loginUser) {
        if (loginUser != null && StringUtils.isNotEmpty(loginUser.getToken())) {
            refreshToken(loginUser);
        }
    }

    /**
     * 删除用户身份信息
     */
    public void delLoginUser(String token) {
        if (StringUtils.isNotEmpty(token)) {
            String userKey = getTokenKey(token);
            redisCache.remove(userKey);
        }
    }

    /**
     * 创建令牌
     *
     * @param loginUser 用户信息
     * @return 令牌
     */
    public String createToken(LoginUser loginUser) {
        String token = UUID.randomUUID().toString();
        loginUser.setToken(token);
        setUserAgent(loginUser);
        refreshToken(loginUser);
        //生成JWT令牌
        return getJwtToken(loginUser);
    }

    /**
     * 获取JWT令牌
     * @return JWT令牌
     */
    public static String getJwtToken(LoginUser loginUser) {
        SecretKey secretKey = new SecretKeySpec(secret.getBytes(), SignatureAlgorithm.HS256.getAlgorithm());
        return Jwt.issuer("https://hongdux.com")
                .subject(loginUser.getUser().getUserName())
                .claim(Constants.LOGIN_USER_KEY, loginUser.getToken())
                .expiresIn(3600L * 24 * 30) //最大30天
                .sign(secretKey);
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private static String parseToken(String token,String key)
    {
        SecretKey secretKey = new SecretKeySpec(secret.getBytes(), SignatureAlgorithm.HS256.getAlgorithm());
        try {
            return jwtParser.verify(token,secretKey).getClaim(key);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 验证令牌有效期，相差不足20分钟，自动刷新缓存
     *
     * @param loginUser 登录信息
     */
    public void verifyToken(LoginUser loginUser) {
        long expireTime = loginUser.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= MILLIS_MINUTE_TWENTY) {
            refreshToken(loginUser);
        }
    }

    /**
     * 刷新令牌有效期
     *
     * @param loginUser 登录信息
     */
    public void refreshToken(LoginUser loginUser) {
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + expireTime * MILLIS_MINUTE);
        // 根据uuid将loginUser缓存
        String userKey = getTokenKey(loginUser.getToken());
        redisCache.put(userKey, loginUser);
    }

    /**
     * 设置用户代理信息
     *
     * @param loginUser 登录信息
     */
    public void setUserAgent(LoginUser loginUser) {
        // 在Quarkus中获取User-Agent的方式可能不同，这里简化处理
        String ip = IpUtils.getIpAddr();
        loginUser.setIpaddr(ip);
        loginUser.setBrowser("Unknown");
        loginUser.setOs("Unknown");
    }

    /**
     * 获取请求token
     *
     * @param request 请求
     * @return token
     */
    private String getToken(HttpServerRequest request)
    {
        String token = request.getHeader(header);
        if (StringUtils.isNotEmpty(token) && token.startsWith(Constants.TOKEN_PREFIX))
        {
            token = token.replace(Constants.TOKEN_PREFIX, "");
        }
        return token;
    }

    private String getTokenKey(String uuid) {
        return Constants.LOGIN_TOKEN_KEY + uuid;
    }


    public static void main(String[] args) {
        LoginUser loginUser = new LoginUser();
        SysUser sysUser = new SysUser();
        sysUser.setUserName("asdasd");
        loginUser.setUser(sysUser);
        loginUser.setPermissions(new HashSet<>(List.of("asdasd")));
        loginUser.setToken(UUID.randomUUID().toString());
        String jwt = getJwtToken(loginUser);
        System.out.println(jwt);
        String claims = parseToken(jwt,"sub");
        System.out.println(claims);
    }

}
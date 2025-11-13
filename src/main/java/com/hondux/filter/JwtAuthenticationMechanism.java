package com.hondux.filter;

import com.hondux.domain.LoginUser;
import com.hondux.service.TokenService;
import io.quarkus.security.AuthenticationFailedException;
import io.quarkus.security.identity.IdentityProviderManager;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.runtime.QuarkusSecurityIdentity;
import io.quarkus.vertx.http.runtime.security.ChallengeData;
import io.quarkus.vertx.http.runtime.security.HttpAuthenticationMechanism;
import io.smallrye.mutiny.Uni;
import io.vertx.ext.web.RoutingContext;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.Arrays;

@ApplicationScoped
@Priority(100)
@Slf4j
public class JwtAuthenticationMechanism implements HttpAuthenticationMechanism {
    
    @Inject
    TokenService tokenService;
    @ConfigProperty(name = "auth.ignore.url")
    String ignoreUrl;
    @Override
    public Uni<SecurityIdentity> authenticate(RoutingContext context,
                                              IdentityProviderManager identityProviderManager) {
        if(Arrays.stream(ignoreUrl.split(",")).anyMatch(url->context.request().path().startsWith(url))){
            return Uni.createFrom().item(new QuarkusSecurityIdentity.Builder().setAnonymous(true).build());
        }
        LoginUser loginUser = tokenService.getLoginUser(context.request());
        if (loginUser!=null) {
            tokenService.verifyToken(loginUser);
            return Uni.createFrom().item(createSecurityIdentity(loginUser));
        }else{
            return Uni.createFrom().failure(new AuthenticationFailedException("Token无效"));
        }
    }


    private SecurityIdentity createSecurityIdentity(LoginUser loginUser) {
        // 创建并返回SecurityIdentity对象
        QuarkusSecurityIdentity.Builder builder = QuarkusSecurityIdentity.builder();
        builder.setPrincipal(loginUser);
        return builder.build();
    }

    @Override
    public Uni<ChallengeData> getChallenge(RoutingContext context) {
        log.warn("请求访问：{}，认证失败，无法访问系统资源", context.request().path());

        // 创建 ChallengeData，设置状态码和响应头
        ChallengeData challengeData = new ChallengeData(
                401,  // 状态码
                "Content-Type",  // 响应头名称
                "application/json;charset=UTF-8"  // 响应头值
        );

        return Uni.createFrom().item(challengeData);
    }

}

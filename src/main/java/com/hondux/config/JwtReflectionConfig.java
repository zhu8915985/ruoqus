package com.hondux.config;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection(targets = {
    io.smallrye.jwt.build.impl.JwtProviderImpl.class,
    io.smallrye.jwt.build.Jwt.class,
})
public class JwtReflectionConfig {
}

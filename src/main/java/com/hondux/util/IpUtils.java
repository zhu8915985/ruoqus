package com.hondux.util;

import io.vertx.core.http.HttpServerRequest;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IpUtils {
    
    /**
     * 获取客户端IP地址
     * @return IP地址
     */
    public static String getIpAddr() {
        // 在Quarkus中，可以通过HttpServerRequest获取IP地址
        // 这里简化处理，实际应该注入HttpServerRequest
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return "127.0.0.1";
        }
    }

    /**
     * 获取客户端IP地址
     * @param request HTTP请求
     * @return IP地址
     */
    public static String getIpAddr(HttpServerRequest request) {
        if (request == null) {
            return getIpAddr();
        }

        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.remoteAddress().hostAddress();
        }
        return ip;
    }
}
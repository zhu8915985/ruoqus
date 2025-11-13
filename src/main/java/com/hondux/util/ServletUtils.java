package com.hondux.util;

import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import jakarta.ws.rs.core.Response;

import java.util.HashMap;
import java.util.Map;

public class ServletUtils {
    
    /**
     * 获取request对象
     * @return request对象
     */
    public static HttpServerRequest getRequest() {
        // 在Quarkus中，可以通过CDI获取当前请求
        // 这里返回null，实际使用时应该通过注入或上下文获取
        return null;
    }

    /**
     * 获取response对象
     * @return response对象
     */
    public static HttpServerResponse getResponse() {
        // 在Quarkus中，可以通过CDI获取当前响应
        // 这里返回null，实际使用时应该通过注入或上下文获取
        return null;
    }

    /**
     * 将对象转换为JSON字符串
     * @param obj 对象
     * @return JSON字符串
     */
    public static String toJson(Object obj) {
        // 在Quarkus中，可以使用Jackson ObjectMapper
        // 这里简化处理，实际应该注入ObjectMapper
        return obj.toString();
    }

    /**
     * 创建成功响应
     * @param data 数据
     * @return 响应对象
     */
    public static Response success(Object data) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("msg", "操作成功");
        result.put("data", data);
        return Response.ok(result).build();
    }

    /**
     * 创建成功响应
     * @param msg 消息
     * @return 响应对象
     */
    public static Response success(String msg) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("msg", msg);
        return Response.ok(result).build();
    }

    /**
     * 创建错误响应
     * @param msg 错误消息
     * @return 响应对象
     */
    public static Response error(String msg) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 500);
        result.put("msg", msg);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(result).build();
    }

    /**
     * 创建错误响应
     * @param code 错误码
     * @param msg 错误消息
     * @return 响应对象
     */
    public static Response error(int code, String msg) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", code);
        result.put("msg", msg);
        return Response.status(code == 500 ? Response.Status.INTERNAL_SERVER_ERROR : Response.Status.BAD_REQUEST).entity(result).build();
    }
}
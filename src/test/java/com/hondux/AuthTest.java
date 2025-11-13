package com.hondux;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
public class AuthTest {

    @Test
    public void testLoginEndpoint() {
        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("username", "ry");
        loginRequest.put("password", "admin123");
        
        given()
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .when().post("/login")
                .then()
                .statusCode(200)
                .body("status", equalTo("success"))
                .body("token", notNullValue());
    }

    @Test
    public void testListEndpoint() {
        // 先登录获取token
        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("username", "ry");
        loginRequest.put("password", "admin123");
        
        String token = given()
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .when().post("/login")
                .then()
                .extract().path("token");

        // 使用token访问受保护的端点
        given()
                .header("Authorization", "Bearer " + token)
                .when().get("/list")
                .then()
                .statusCode(200)
                .body("status", equalTo("success"));
    }
}
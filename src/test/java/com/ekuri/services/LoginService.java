package com.ekuri.services;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.runner.Request;

import static io.restassured.RestAssured.*;

public class LoginService extends BaseTest {

    public Response token(String input, String password) {
        Response response = given(spec)
                .when()
                .formParam("Input", input)
                .formParam("Password", password)
                .formParam("client_id", "ekuri_web_client")
                .formParam("client_secret", "d3f44a6c-3da0-4822-bbc9-8cd41539e6b1")
                .formParam("grant_type", "multi_provider")
                .formParam("scope", "offline_access")
                .header("content-type", "application/x-www-form-urlencoded")
                .post("auth/connect/token");
        response
                .then()
                .statusCode(200);
        return response;
    }

    public Response userMe(String accessToken) {
        Response response = given(spec)
                .when()
                .header("authorization", "Bearer " + accessToken)
                .get("core/user/me");
        response
                .then()
                .statusCode(200);
        return response;
    }

    public Response getWallet(String accessToken) {
        Response response = given(spec)
                .when()
                .header("authorization", "Bearer " + accessToken)
                .get("balance/wallet/get-wallet");
        response
                .then()
                .statusCode(200);
        return response;
    }
}

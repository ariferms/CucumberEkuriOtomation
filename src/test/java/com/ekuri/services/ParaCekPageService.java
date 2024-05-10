package com.ekuri.services;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ParaCekPageService extends BaseTest{
    public Response bankAccount(String token){
        Response response = given(spec)
                .when()
                .header("authorization", "Bearer " + token)
                .get("balance/bank-account");
        response
                .then()
                .statusCode(200);
        return response;
    }
    public Response withdraw(String token){
        Response response = given(spec)
                .when()
                .header("authorization", "Bearer " + token)
                .get("balance/withdraw");
        response
                .then()
                .statusCode(200);
        return response;
    }
}

package com.ekuri.services;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class BankaHesaplarimPageService extends BaseTest{
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
}

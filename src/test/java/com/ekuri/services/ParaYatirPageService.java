package com.ekuri.services;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ParaYatirPageService extends BaseTest{
    public Response depositInfo(String token){
        Response response = given(spec)
                .when()
                .header("authorization", "Bearer " + token)
                .get("balance/deposit/info");
        response
                .then()
                .statusCode(200);
        return response;
    }
}

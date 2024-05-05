package com.ekuri.services;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class TjkTvService extends BaseTest{
    public Response tjkTv(){
        Response response = given(spec)
                .when()
                .get("core/home/tjk-tv");
        response
                .then()
                .statusCode(200);
        return response;
    }
}

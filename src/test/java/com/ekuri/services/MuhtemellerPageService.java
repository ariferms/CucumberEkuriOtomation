package com.ekuri.services;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class MuhtemellerPageService extends BaseTest{
    public String getHippodromeKey(){
        Response response = given(spec)
                .when()
                .get("core/bulletin/get-hippodrome-list-bulletin");
        response
                .then()
                .statusCode(200);
        return response.jsonPath().getString("payload[0].hippodromeKey");
    }
    public Response getHippodromeListBulletinParamDate(String date) {
        Response response = given(spec)
                .when()
                .get("core/bulletin/get-hippodrome-list-bulletin?Date=" + date);
        response
                .then()
                .statusCode(200);
        return response;
    }

    public Response getPossiblesHippodromParam(String hippodromKey) {
        spec.queryParam("HippodromeKey", hippodromKey);
        Response response = given(spec)
                .when()
                .get("core/possibles/get-possibles");
        response
                .then()
                .statusCode(200);
        return response;
    }

}

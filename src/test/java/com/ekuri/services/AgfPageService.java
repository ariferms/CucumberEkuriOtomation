package com.ekuri.services;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class AgfPageService extends BaseTest{
    public Response getHippodromeListBulletin() {
        Response response = given(spec)
                .when()
                .get("core/bulletin/get-hippodrome-list-bulletin");
        response
                .then()
                .statusCode(200);
        return response;
    }
    public Response getHippodromeListBulletin(String date) {
        Response response = given(spec)
                .when()
                .get("core/bulletin/get-hippodrome-list-bulletin?Date=" + date);
        response
                .then()
                .statusCode(200);
        return response;
    }
    public Response getAgf(String hippodromeKey) {
        Response response = given(spec)
                .when()
                .queryParam("HippodromeKey", hippodromeKey)
                .get("core/agf/get-agf");
        response
                .then()
                .statusCode(200);
        return response;
    }
}

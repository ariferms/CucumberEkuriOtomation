package com.ekuri.services;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class SonuclarPageService extends BaseTest {
    public Response getHippodromeListBulletin() {
        Response response = given(spec)
                .when()
                .get("core/bulletin/get-hippodrome-list-bulletin");
        response
                .then()
                .statusCode(200);
        return response;
    }

    public Response getHippodromeListBulletinResult(String date) {
        Response response = given(spec)
                .when()
                .get("core/bulletin/get-hippodrome-list-bulletin-result?Date=" + date);
        response
                .then()
                .statusCode(200);
        return response;
    }

    public Response getBulletinResult(String hippodromeKey, String date){
        Response response = given(spec)
                .when()
                .queryParam("HippodromeKey", hippodromeKey)
                .queryParam("Date", date)
                .get("core/bulletin/get-bulletin-result");
        response
                .then()
                .statusCode(200);
        return response;
    }
}

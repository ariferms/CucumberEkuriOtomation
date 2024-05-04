package com.ekuri.services;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CreateBetPageService extends BaseTest {

    public Response getHippodromeListBetProgram() {
        Response response = given(spec)
                .when()
                .get("core/betprogram/get-hippodrome-list-bet-program");
        response
                .then()
                .statusCode(200);
        return response;
    }

    public Response getBetProgram(String hippodromKey, String date) {
        Response response = given(spec)
                .when()
                .queryParam("HippodromeKey", hippodromKey)
                .queryParam("Date", date)
                .get("core/betprogram/get-bet-program");
        response
                .then()
                .statusCode(200);
        return response;
    }
}

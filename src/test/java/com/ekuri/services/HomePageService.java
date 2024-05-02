package com.ekuri.services;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class HomePageService extends BaseTest {
    public void paramSpec(String hippodromeKey){
        spec.queryParam("HippodromeKey", hippodromeKey);
    }

    public Response consentDefinitionAll() {
        Response response = given(spec)
                .when()
                .get("core/consent-definition/all");
        response
                .then()
                .statusCode(200);
        return response;
    }

    public Response homeSliders() {
        Response response = given(spec)
                .when()
                .get("core/home/sliders");
        response
                .then()
                .statusCode(200);
        return response;
    }

    public Response getHippodromeListBulletin() {
        Response response = given(spec)
                .when()
                .get("core/bulletin/get-hippodrome-list-bulletin");
        response
                .then()
                .statusCode(200);
        return response;
    }

    public Response getAgf() {
        Response response = given(spec)
                .when()
                .get("core/agf/get-agf");
        response
                .then()
                .statusCode(200);
        return response;
    }

    public Response getBulletinSummary() {
        Response response = given(spec)
                .when()
                .get("core/bulletin/get-bulletin-summary");
        response
                .then()
                .statusCode(200);
        return response;
    }

    public Response getPossibles() {
        Response response = given(spec)
                .when()
                .get("core/possibles/get-possibles");
        response
                .then()
                .statusCode(200);
        return response;
    }

    public Response getBetProgram() {
        Response response = given(spec)
                .when()
                .get("core/betprogram/get-bet-program");
        response
                .then()
                .statusCode(200);
        return response;
    }
}

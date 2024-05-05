package com.ekuri.services;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class ProgramPageService extends BaseTest{
    public void paramSpec(String hippodromeKey){
        spec.queryParam("HippodromeKey", hippodromeKey);
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

    public Response getBulletin() {
        Response response = given(spec)
                .when()
                .get("core/bulletin/get-bulletin");
        response
                .then()
                .statusCode(200);
        return response;
    }
}

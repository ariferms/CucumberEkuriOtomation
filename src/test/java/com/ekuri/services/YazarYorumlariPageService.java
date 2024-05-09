package com.ekuri.services;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class YazarYorumlariPageService extends BaseTest{
    public Response dailyCommentsByExpert(){
        Response response = given(spec)
                .when()
                .get("core/expert-comment/daily-comments-by-expert");
        response
                .then()
                .statusCode(200);
        return response;
    }
}

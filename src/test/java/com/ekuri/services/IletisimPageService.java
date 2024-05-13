package com.ekuri.services;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class IletisimPageService extends BaseTest{
    public Response permissions(String token, String permissionsBody){
        Response response = given(spec)
                .when()
                .contentType(ContentType.JSON)
                .header("authorization", "Bearer " + token)
                .body(permissionsBody)
                .put("core/user/permissions");
        response
                .then()
                .statusCode(200);
        return response;
    }
}

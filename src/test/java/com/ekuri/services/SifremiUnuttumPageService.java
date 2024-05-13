package com.ekuri.services;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class SifremiUnuttumPageService extends BaseTest{
    public Response tcknCheck(String token, String tcknBody){
        Response response = given(spec)
                .when()
                .contentType(ContentType.JSON)
                .header("authorization", "Bearer " + token)
                .body(tcknBody)
                .post("core/user/tckn-check");
        response
                .then()
                .statusCode(200);
        return response;
    }

    public Response sendResetByIdentityNumber(String token, String  typeBody){
        Response response = given(spec)
                .when()
                .contentType(ContentType.JSON)
                .header("authorization", "Bearer " + token)
                .body(typeBody)
                .post("core/user/send-reset-by-identity-number");
        response
                .then()
                .statusCode(200);
        return response;
    }
}

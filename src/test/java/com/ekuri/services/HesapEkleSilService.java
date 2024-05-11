package com.ekuri.services;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class HesapEkleSilService extends BaseTest {
    public Response createAccount(String token, String hesapEkleBody) {
        Response response = given(spec)
                .when()
                .contentType(ContentType.JSON)
                .header("authorization", "Bearer " + token)
                .body(hesapEkleBody)
                .post("balance/bank-account");
        response
                .then()
                .statusCode(200);
        return response;
    }

    public Response deleteToAccount(String token, int id) {
        Response response = given(spec)
                .when()
                .header("authorization", "Bearer " + token)
                .delete("balance/bank-account/" + id);
        response
                .then()
                .statusCode(200);
        return response;
    }
}

package com.ekuri.services;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ProfilAyarlariPageService extends BaseTest {

    // E-posta adresi dogrulama ve guncelleme
    public Response sendEmailVerifyLink(String token) {
        Response response = given(spec)
                .when()
                .header("authorization", "Bearer " + token)
                .get("auth/account/send-email-verify-link");
        response
                .then()
                .statusCode(200);
        return response;
    }

    // Telefon numarasi dogrulama ve guncelleme
    /*public void phoneNumber(String token, String phoneNumberBody){
        Response response = given(spec)
                .when()
                .contentType(ContentType.JSON)
                .header("authorization", "Bearer " + token)
                .body(phoneNumberBody)
                .put("core/user/phone-number");
        response
                .then()
                .statusCode(200);
    }

    public void phoneNumberVerify(String token, String phoneNumberBody){
        Response response = given(spec)
                .when()
                .contentType(ContentType.JSON)
                .header("authorization", "Bearer " + token)
                .body(phoneNumberBody)
                .put("core/user/phone-number-verify");
        response
                .then()
                .statusCode(200);
    }*/
    // Sifre yenileme
    public Response updatePassword(String token, String passwordBody) {
        Response response = given(spec)
                .when()
                .contentType(ContentType.JSON)
                .header("authorization", "Bearer " + token)
                .body(passwordBody)
                .put("core/user/update-password");
        response
                .then()
                .statusCode(200);
        return response;
    }

    // Adres dogrulama ve guncelleme
    public Response counties(String token) {
        Response response = given(spec)
                .when()
                .header("authorization", "Bearer " + token)
                .get("core/location/counties?cityId=34");
        response
                .then()
                .statusCode(200);
        return response;
    }

    public Response address(String token, String addressdBody) {
        Response response = given(spec)
                .when()
                .contentType(ContentType.JSON)
                .header("authorization", "Bearer " + token)
                .body(addressdBody)
                .put("core/user/address");
        response
                .then()
                .statusCode(200);
        return response;
    }
}

package com.ekuri.services;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;

public class CreateBetPageService extends BaseTest {

    public String  currentDate(){
        LocalDate localDate = LocalDate.now();
        System.out.println("Tarih Formatı: " + localDate);

        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime threeHoursAgo = localDateTime.minusHours(3);
        LocalDate justDate = threeHoursAgo.toLocalDate();
        String date = justDate.toString();
        return date;
    }

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

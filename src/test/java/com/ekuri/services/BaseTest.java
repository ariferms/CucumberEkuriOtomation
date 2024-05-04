package com.ekuri.services;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

public class BaseTest {
    RequestSpecification spec;
    public BaseTest() {
        spec = new RequestSpecBuilder()
                .setBaseUri("https://api.ekuri.com/api/v1/")
                .addFilters(Arrays.asList(new RequestLoggingFilter(), new ResponseLoggingFilter()))
                .build();
    }

    public String  currentDate(){
        LocalDate localDate = LocalDate.now();
        System.out.println("Tarih Formatı: " + localDate);

        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime threeHoursAgo = localDateTime.minusHours(3);
        LocalDate justDate = threeHoursAgo.toLocalDate();
        String date = justDate.toString();
        return date;
    }
}

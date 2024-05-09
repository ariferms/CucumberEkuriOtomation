package com.ekuri.steps;

import com.ekuri.services.SonuclarPageService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SonuclarPageSteps {
    SonuclarPageService sonuclarPageService = new SonuclarPageService();
    String date,
            hippodromeKey;
    Response getHippodromeListBulletinResult,
            getBulletinResult;

    @Given("Sonuclar servisleri icin gerekli parametreler toplanir")
    public void parameterInfo() {
        hippodromeKey = sonuclarPageService.getHippodromeListBulletin().jsonPath().getString("payload[0].hippodromeKey");
        date = sonuclarPageService.currentDate();
    }
    @When("Sonuclar sayfasina istek atilir")
    public void requestSonuclarPage(){
        getHippodromeListBulletinResult = sonuclarPageService.getHippodromeListBulletinResult(date);
        getBulletinResult = sonuclarPageService.getBulletinResult(hippodromeKey, date);
    }
    @Then("Sonuclar sayfasindaki servisler kontrol edilir")
    public void SonuclarKontrol(){
        Assertions.assertEquals(getHippodromeListBulletinResult.jsonPath().getJsonObject("processStatus"), "Success");
        Assertions.assertEquals(getBulletinResult.jsonPath().getJsonObject("processStatus"), "Success");
    }
}

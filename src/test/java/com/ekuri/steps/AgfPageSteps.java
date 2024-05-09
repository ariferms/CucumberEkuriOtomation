package com.ekuri.steps;

import com.ekuri.services.AgfPageService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

public class AgfPageSteps {
    AgfPageService agfPageService = new AgfPageService();
    Response getHippodromeListBulletin,
            getAgf;
    String hippodromeKey,
            date;
    @Given("AGF servisleri icin gerekli parametreler toplanir")
    public void parameterInfo(){
        hippodromeKey = agfPageService.getHippodromeListBulletin().jsonPath().getString("payload[0].hippodromeKey");
        date = agfPageService.currentDate();
    }
    @When("AGF sayfasina istek atilir")
    public void requestAgfPage(){
        getHippodromeListBulletin = agfPageService.getHippodromeListBulletin(date);
        getAgf = agfPageService.getAgf(hippodromeKey);
    }
    @Then("AGF sayfasindaki servisler kontrol edilir")
    public void AgfPageKontrol(){
        Assertions.assertEquals(getHippodromeListBulletin.jsonPath().getJsonObject("processStatus"), "Success");
        Assertions.assertEquals(getAgf.jsonPath().getJsonObject("processStatus"), "Success");
        Assertions.assertEquals(getAgf.jsonPath().getString("payload.hippodromeKey"), hippodromeKey);
    }
}

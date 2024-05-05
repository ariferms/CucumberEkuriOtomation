package com.ekuri.steps;

import com.ekuri.services.ProgramPageService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

public class ProgramPageSteps {
    ProgramPageService programPageService = new ProgramPageService();
    String hippodromeKey;
    Response getHippodromeListBulletin,
            getBulletin;
    @Given("Program sayfasi icin gerekli parametreler alinir")
    public void parameterInfo(){
        hippodromeKey = programPageService.getHippodromeListBulletin().jsonPath().getString("payload[0].hippodromeKey");
    }
    @When("Program sayfasina istek atilir")
    public void callProgram(){
        getHippodromeListBulletin = programPageService.getHippodromeListBulletin();
        programPageService.paramSpec(hippodromeKey);
        getBulletin = programPageService.getBulletin();
    }
    @Then("Program sayfasi servis kontrolleri yapilir")
    public void programServiceKontrol(){
        Assertions.assertEquals(getHippodromeListBulletin.jsonPath().getJsonObject("processStatus"), "Success");
        Assertions.assertEquals(getBulletin.jsonPath().getJsonObject("processStatus"), "Success");
        Assertions.assertEquals(getBulletin.jsonPath().getString("payload.hippodromeKey"), hippodromeKey);
    }
}

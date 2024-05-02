package com.ekuri.steps;

import com.ekuri.services.HomePageService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

public class HomePageSteps {
    HomePageService homePageService = new HomePageService();
    String hippodromeKey;
    Response consentDefinitionAll,
            homeSliders,
            getHippodromeListBulletin,
            getAgf,
            getBulletinSummary,
            getPossibles,
            getBetProgram;

    @Given("Ana sayfa icin gerekli degiskenler aliniyor")
    public void parameterInfo() {
        hippodromeKey = homePageService.getHippodromeListBulletin().jsonPath().getString("payload[0].hippodromeKey");
    }

    @When("Ana sayfa servislerine istek atiliyor")
    public void callHomePage() {
        consentDefinitionAll = homePageService.consentDefinitionAll();
        homeSliders = homePageService.homeSliders();
        getHippodromeListBulletin = homePageService.getHippodromeListBulletin();

        // Bu adımda sonrasında calisacak servisler icin parametre tanımlanır
        homePageService.paramSpec(hippodromeKey);

        getAgf = homePageService.getAgf();
        getBulletinSummary = homePageService.getBulletinSummary();
        getPossibles = homePageService.getPossibles();
        getBetProgram = homePageService.getBetProgram();
    }
    @Then("Servis kontrolleri yapiliyor")
    public void homePageKontrol(){
        Assertions.assertEquals(consentDefinitionAll.jsonPath().getJsonObject("processStatus"), "Success");
        Assertions.assertEquals(homeSliders.jsonPath().getJsonObject("processStatus"), "Success");
        Assertions.assertEquals(getHippodromeListBulletin.jsonPath().getJsonObject("processStatus"), "Success");
        Assertions.assertEquals(getAgf.jsonPath().getJsonObject("processStatus"), "Success");
        Assertions.assertEquals(getBulletinSummary.jsonPath().getJsonObject("processStatus"), "Success");
        Assertions.assertEquals(getPossibles.jsonPath().getJsonObject("processStatus"), "Success");
        Assertions.assertEquals(getBetProgram.jsonPath().getJsonObject("processStatus"), "Success");
    }
}

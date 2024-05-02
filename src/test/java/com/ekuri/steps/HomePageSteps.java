package com.ekuri.steps;

import com.ekuri.services.HomePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

public class HomePageSteps {
    HomePage homePage = new HomePage();
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
        hippodromeKey = homePage.getHippodromeListBulletin().jsonPath().getString("payload[0].hippodromeKey");
    }

    @When("Ana sayfa servislerine istek atiliyor")
    public void callHomePage() {
        consentDefinitionAll = homePage.consentDefinitionAll();
        homeSliders = homePage.homeSliders();
        getHippodromeListBulletin = homePage.getHippodromeListBulletin();

        // Bu adımda sonrasında calisacak servisler icin parametre tanımlanır
        homePage.paramSpec(hippodromeKey);

        getAgf = homePage.getAgf();
        getBulletinSummary = homePage.getBulletinSummary();
        getPossibles = homePage.getPossibles();
        getBetProgram = homePage.getBetProgram();
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

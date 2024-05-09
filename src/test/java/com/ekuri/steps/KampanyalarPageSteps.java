package com.ekuri.steps;

import com.ekuri.services.KampanyalarPageService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

public class KampanyalarPageSteps {
    KampanyalarPageService kampanyalarPageService = new KampanyalarPageService();
    Response getCampaigns;
    @Given("Kampanyalar sayfasi menude secilir")
    public void selectYazarYorumlariPage() {
        System.out.println("Menüden Yazar Yorumları sayfası seçilir...");
    }

    @When("Kampanyalar sayfasina istek atilir")
    public void requestYazarYorumlariPage() {
        getCampaigns = kampanyalarPageService.getCampaigns();
    }
    @Then("Kampanyalar sayfasindaki servisler kontrol edilir")
    public void YazarYorumlariPageKontrol(){
        Assertions.assertEquals(getCampaigns.jsonPath().getJsonObject("processStatus"), "Success");
        Assertions.assertNotEquals(getCampaigns.jsonPath().getString("payload[0].webMediaPath"), null);
        Assertions.assertNotEquals(getCampaigns.jsonPath().getString("payload[0].mobileMediaPath"), null);
    }
}

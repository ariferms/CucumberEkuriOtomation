package com.ekuri.steps;

import com.ekuri.services.MuhtemellerPageService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

public class MuhtemellerPageSteps {
    MuhtemellerPageService muhtemellerPageService = new MuhtemellerPageService();
    String date,
            hippodromKey;
    Response getHippodromeListBulletinDateParam,
            getPossiblesHippodromParam;

    @Given("Muhtemeller sayfasi icin gerekli parametreler alinir")
    public void parametersInfo() {
        date = muhtemellerPageService.currentDate();
        hippodromKey = muhtemellerPageService.getHippodromeKey();
    }

    @When("Muhtemeller sayfasina gidilir")
    public void requestToMuhtemellerPage() {
        getHippodromeListBulletinDateParam = muhtemellerPageService.getHippodromeListBulletinParamDate(date);
        getPossiblesHippodromParam = muhtemellerPageService.getPossiblesHippodromParam(hippodromKey);
    }

    @Then("Sayafada istek atilan servisler kontrol edilir")
    public void servicesKontrol() {
        Assertions.assertEquals(getHippodromeListBulletinDateParam.jsonPath().getJsonObject("processStatus"), "Success");
        Assertions.assertEquals(getPossiblesHippodromParam.jsonPath().getJsonObject("processStatus"), "Success");
        Assertions.assertEquals(getPossiblesHippodromParam.jsonPath().getString("payload.hippodromeKey"), hippodromKey);
    }
}

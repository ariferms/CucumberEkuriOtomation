package com.ekuri.steps;

import com.ekuri.services.CreateBetPageService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

public class CreateBetPageSteps {
    CreateBetPageService createBetPageSevice = new CreateBetPageService();
    String date;
    String hippodromKey;
    Response getHippodromeListBetProgram,
            getBetProgram;

    @Given("Gerekli parametreler olusturulur")
    public void parameterInfoHippodrom() {
        hippodromKey = createBetPageSevice.getHippodromeListBetProgram().jsonPath().getString("payload[0].hippodromeKey");
        date = createBetPageSevice.currentDate();
    }

    @When("Bahis yap sayfasındaki servislere istek atilir")
    public void callCreateBetPage() {
        getHippodromeListBetProgram = createBetPageSevice.getHippodromeListBetProgram();
        getBetProgram = createBetPageSevice.getBetProgram(hippodromKey, date);
    }
    @Then("Bahis yap servisleri kontrol edilir")
    public void setCreateBetPageKontrol(){
        Assertions.assertEquals(getHippodromeListBetProgram.jsonPath().getJsonObject("processStatus"), "Success");
        Assertions.assertEquals(getBetProgram.jsonPath().getJsonObject("processStatus"), "Success");
    }
}

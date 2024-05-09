package com.ekuri.steps;

import com.ekuri.services.YazarYorumlariPageService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

public class YazarYorumlariPageSteps {
    YazarYorumlariPageService yazarYorumlariPageService = new YazarYorumlariPageService();
    Response dailyCommentsByExpert;

    @Given("Yazar yorumlari sayfasi menude secilir")
    public void selectYazarYorumlariPage() {
        System.out.println("Menüden Yazar Yorumları sayfası seçilir...");
    }

    @When("Yazar yorumlari sayfasina istek atilir")
    public void requestYazarYorumlariPage() {
        dailyCommentsByExpert = yazarYorumlariPageService.dailyCommentsByExpert();
    }
    @Then("Yazar yorumlari sayfasindaki servisler kontrol edilir")
    public void YazarYorumlariPageKontrol(){
        Assertions.assertEquals(dailyCommentsByExpert.jsonPath().getJsonObject("processStatus"), "Success");
    }
}

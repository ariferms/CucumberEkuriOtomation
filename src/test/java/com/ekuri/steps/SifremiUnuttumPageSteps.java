package com.ekuri.steps;

import com.ekuri.services.SifremiUnuttumPageService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;

public class SifremiUnuttumPageSteps {
    SifremiUnuttumPageService sifremiUnuttumPageService = new SifremiUnuttumPageService();
    String token,
            identityBody,
            tcknCheckBody;

    JsonNode identityNode;
    Response tcknCheck,
            sendResetByIdentityNumber;
    int verificationDurationInSecond;

    @Given("Sifremi unuttum sureci icin gerekli parametreler olusturulur")
    public void parameterInfo() throws IOException {
        token = sifremiUnuttumPageService.token();
        tcknCheckBody = sifremiUnuttumPageService.readJsonToFile("src/test/java/com/ekuri/requestJson/tcknCheckRequest.json").toString();
        identityNode = sifremiUnuttumPageService.readJsonToFile("src/test/java/com/ekuri/requestJson/identityNumberRequest.json");
    }

    @When("Sifremi unuttum {} servisine {} istegi atilir")
    public void requestSifremiUnuttum(String passwordOptions, int type) {
        System.out.println(passwordOptions + " Opsiyonu: ");
        tcknCheck = sifremiUnuttumPageService.tcknCheck(token, tcknCheckBody);
        ((ObjectNode) identityNode).put("type", type);
        identityBody = identityNode.toString();
        sendResetByIdentityNumber = sifremiUnuttumPageService.sendResetByIdentityNumber(token, identityBody);
    }

    @Then("{} Type'i icin sifremi unuttum servisleri kotrol edilir")
    public void sifremiUnuttumKontrol(int type) {
        switch (type) {
            case 1:
                verificationDurationInSecond = 600;
                break;
            case 2:
                verificationDurationInSecond = 180;
                break;
            default:
                System.out.println("verificationDurationInSecond parameter is null...!");
        }
        Assertions.assertEquals(sendResetByIdentityNumber.jsonPath().getInt("payload.verificationDurationInSecond"), verificationDurationInSecond);
        Assertions.assertEquals(sendResetByIdentityNumber.jsonPath().getJsonObject("processStatus"), "Success");
        Assertions.assertEquals(tcknCheck.jsonPath().getJsonObject("processStatus"), "Success");
        Assertions.assertEquals(tcknCheck.jsonPath().getBoolean("payload.result"), true);
    }
}

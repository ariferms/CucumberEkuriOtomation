package com.ekuri.steps;

import com.ekuri.services.KuponlarimPageService;
import com.fasterxml.jackson.databind.JsonNode;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KuponlarimPageSteps {
    KuponlarimPageService kuponlarimPageService = new KuponlarimPageService();
    Response getCoupons;
    String token;
    String getCouponsJson;
    JsonNode jsonKupon,
            itemsNode;
    List<Integer> stateList;

    @Given("Kuponlarim sayfasi icin gerekli parametreler toplanir")
    public void parameterInfo() {
        token = kuponlarimPageService.token();
    }

    @Given("Kullanici kuponlarim sayfasindan {} numarali, {} kupon menusunu secer")
    public void selectedKuponType(int state, String kuponType) {
        System.out.println("Kullanıcı " + state + "numaralı " + kuponType + "kuponlar menüsünü seçer...");
    }

    @When("{} menusune istek atilir")
    public void requestKuponMenu(int state) throws IOException {
        stateList = new ArrayList<>();
        getCoupons = kuponlarimPageService.getCoupons(token, state);
        getCouponsJson = getCoupons.getBody().asString();
        kuponlarimPageService.writeJsonToFile(getCouponsJson, "src/test/java/com/ekuri/responseJson/kuponResponse.json");
        jsonKupon = kuponlarimPageService.readJsonToFile("src/test/java/com/ekuri/responseJson/kuponResponse.json");
        itemsNode = jsonKupon.get("payload").get("items");
        for (JsonNode itemNode : itemsNode) {
            int states = itemNode.get("state").asInt();
            stateList.add(states);
        }
        /*System.out.println("*******************************************");
        for (int stateKontrol : stateList) {
            System.out.println("StateNumber: " + stateKontrol);
        }
        System.out.println("*******************************************");*/
    }

    @Then("Kuponlarim servislerinin kupon turleri kontrol edilir - {}")
    public void KuponlarimPageKontrol(int state) {
        for (int stateKontrol : stateList) {
            Assertions.assertEquals(state, stateKontrol);
        }
        Assertions.assertEquals(getCoupons.jsonPath().getJsonObject("processStatus"), "Success");
    }
}

package com.ekuri.steps;

import com.ekuri.services.KuponSavingService;
import com.fasterxml.jackson.databind.JsonNode;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KuponSavingSteps {
    KuponSavingService kuponSavingService = new KuponSavingService();
    List<String> availableHours = new ArrayList<>();
    String[] targetRaceInfo = new String[3];
    String token,
            couponBody,
            raceDate,
            hippodrome,
            targetRaceTime,
            betType,
            getCouponsJson,
            name = "Test";
    JsonNode couponNode,
            runnersNode,
            couponNodeJson,
            jsonKupon,
            itemsNode;
    int runnerSize,
            misli = 2,
            raceNo,
            cardId,
            couponId,
            state = 4;
    boolean complete;
    Response couponSavingOrder,
            cancelSavingOrder,
            getCoupons;


    @Given("Kaydedilecek olan, {} bahis turu icin uygun atlar ve {} turu secilir")
    public void getParameters(String betTypeValue, boolean completeValue) throws IOException {
        betType = betTypeValue;
        complete = completeValue;
        token = kuponSavingService.token();
        targetRaceInfo = kuponSavingService.getCorrectReserveRaceTimes();
        couponNodeJson = kuponSavingService.readJsonToFile("src/test/java/com/ekuri/requestJson/kuponRequest.json");
        raceDate = kuponSavingService.getRaceDateReserve();
        runnersNode = kuponSavingService.readJsonToFile("src/test/java/com/ekuri/responseJson/runnersResponse.json");
        runnerSize = kuponSavingService.runnerSize(runnersNode);
        availableHours = kuponSavingService.avaiableLegList(runnerSize, runnersNode, misli, betType);

        // Oynanacak hipodrom, koşu ve saat bulunur
        hippodrome = targetRaceInfo[0];
        targetRaceTime = targetRaceInfo[1];
        raceNo = Integer.parseInt(targetRaceInfo[2]);
        cardId = Integer.parseInt(targetRaceInfo[3]);

        // Kupona maclar ve gerekli parametreler eklenir
        couponNode = kuponSavingService.couponSavingRequestUpdate(availableHours, betType, couponNodeJson, misli, 0, raceDate, raceNo, hippodrome, cardId, complete, name);
        couponBody = couponNode.toString();
        System.out.println("Yeni Kupon Request: " + couponNode);
    }

    @When("Kupon kaydedilir")
    public void requestGanyanCoupon() throws IOException {
        couponSavingOrder = kuponSavingService.couponSavingOrder(token, couponBody);
        getCoupons = kuponSavingService.getCoupons(token, state);
        getCouponsJson = getCoupons.getBody().asString();
        kuponSavingService.writeJsonToFile(getCouponsJson, "src/test/java/com/ekuri/responseJson/kuponSavingResponse.json");
        jsonKupon = kuponSavingService.readJsonToFile("src/test/java/com/ekuri/responseJson/kuponSavingResponse.json");
        itemsNode = jsonKupon.get("payload").get("items");
        for (JsonNode itemNode : itemsNode) {
            String itemName = itemNode.get("name").asText();
            if (itemName.equals(name)){
                couponId = itemNode.get("id").asInt();
                break;
            }
        }
    }

    @Then("Kaydedilen kupon kontrol edilir")
    public void couponKontrol() {
        Assertions.assertEquals(couponSavingOrder.jsonPath().getJsonObject("processStatus"), "Success");
        Assertions.assertEquals(couponSavingOrder.jsonPath().getJsonObject("payload"), true);
    }

    @Then("Kaydedilen kupon silinir")
    public void deleteCoupon() {
        cancelSavingOrder = kuponSavingService.cancelSavingOrder(token, couponId);
    }

    @Then("Silinen kayıtlı kupon kontrol edilir")
    public void deleteCouponKotrol() {
        Assertions.assertEquals(cancelSavingOrder.jsonPath().getJsonObject("processStatus"), "Success");
    }
}

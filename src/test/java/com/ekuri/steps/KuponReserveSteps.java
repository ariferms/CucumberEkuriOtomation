package com.ekuri.steps;

import com.ekuri.services.KuponReserveService;
import com.fasterxml.jackson.databind.JsonNode;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KuponReserveSteps {
    KuponReserveService kuponReserveService = new KuponReserveService();
    List<String> availableHours = new ArrayList<>();
    String[] targetRaceInfo = new String[3];
    String token,
            couponBody,
            raceDate,
            hippodrome,
            targetRaceTime,
            betType;
    JsonNode couponNode,
            runnersNode,
            couponNodeJson;
    int runnerSize,
            misli = 2,
            raceNo,
            cardId,
            couponId;
    boolean complete;
    Response couponReserveOrder,
            cancelReserveOrder;


    @Given("Rezerve {} bahis turu icin uygun atlar ve {} turu secilir")
    public void getParameters(String betTypeValue, boolean completeValue) throws IOException {
        betType = betTypeValue;
        complete = completeValue;
        token = kuponReserveService.token();
        targetRaceInfo = kuponReserveService.getCorrectReserveRaceTimes();
        couponNodeJson = kuponReserveService.readJsonToFile("src/test/java/com/ekuri/requestJson/kuponRequest.json");
        raceDate = kuponReserveService.getRaceDateReserve();
        runnersNode = kuponReserveService.readJsonToFile("src/test/java/com/ekuri/responseJson/runnersResponse.json");
        runnerSize = kuponReserveService.runnerSize(runnersNode);
        availableHours = kuponReserveService.avaiableLegList(runnerSize, runnersNode, misli, betType);

        // Oynanacak hipodrom, koşu ve saat bulunur
        hippodrome = targetRaceInfo[0];
        targetRaceTime = targetRaceInfo[1];
        raceNo = Integer.parseInt(targetRaceInfo[2]);
        cardId = Integer.parseInt(targetRaceInfo[3]);

        // Kupona maclar ve gerekli parametreler eklenir
        couponNode = kuponReserveService.couponRequestUpdate(availableHours, betType, couponNodeJson, misli, 0, raceDate, raceNo, hippodrome, cardId, complete);
        couponBody = couponNode.toString();
        System.out.println("Yeni Kupon Request: " + couponNode);
    }

    @When("Rezerve kupon oynanir")
    public void requestGanyanCoupon() {
        couponReserveOrder = kuponReserveService.couponReserveOrder(token, couponBody);

        couponId = couponReserveOrder.jsonPath().getInt("payload.id");
    }

    @Then("Oynanan Rezerve kupon kontrol edilir")
    public void couponKontrol() {
        Assertions.assertEquals(couponReserveOrder.jsonPath().getJsonObject("processStatus"), "Success");
        Assertions.assertEquals(couponReserveOrder.jsonPath().getString("payload.couponCode"), "");
        Assertions.assertEquals(couponReserveOrder.jsonPath().getBoolean("payload.isReservedCoupon"), true);
        Assertions.assertEquals(couponReserveOrder.jsonPath().getInt("payload.approveCode"), 1);
        Assertions.assertEquals(couponReserveOrder.jsonPath().getInt("payload.id"), couponId);
    }

    @Then("Oynanan Rezerve kupon iptal edilir")
    public void deleteCoupon() {
        cancelReserveOrder = kuponReserveService.cancelReserveOrder(token, couponId);
    }

    @Then("Silinen Rezerve kupon kontrol edilir")
    public void deleteCouponKotrol() {
        Assertions.assertEquals(cancelReserveOrder.jsonPath().getJsonObject("processStatus"), "Success");
        Assertions.assertEquals(cancelReserveOrder.jsonPath().getBoolean("payload.isCancellationSuccess"), true);
    }
}

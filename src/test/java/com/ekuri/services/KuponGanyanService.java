package com.ekuri.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.*;

import static io.restassured.RestAssured.given;

public class KuponGanyanService extends BaseTest {

    @Test
    public List getHippodromeList() throws IOException {
        List<String> hippodromeKeyList = new ArrayList<>();
        JsonNode hippodromesNode;
        int paylaodSize;
        Response response = given(spec)
                .when()
                .get("core/bulletin/get-hippodrome-list-bulletin");
        response
                .then()
                .statusCode(200);
        String body = response.getBody().asString();

        writeJsonToFile(body, "src/test/java/com/ekuri/responseJson/hippodromeListResponse.json");
        hippodromesNode = readJsonToFile("src/test/java/com/ekuri/responseJson/hippodromeListResponse.json");
        JsonNode payloadNode = hippodromesNode.get("payload");
        paylaodSize = payloadNode.size();
        System.out.println("Payload: " + payloadNode);
        System.out.println("Payload Size: " + paylaodSize);
        String currentDate = currentDate();

        for (int i = 0; i < paylaodSize; i++) {
            String raceDate = payloadNode.get(i).get("raceDate").asText();
            if (Objects.equals(payloadNode.get(i).get("currentRaceTime").asText(), "")) {
                continue;
            } else {
                if ((Objects.equals(raceDate, currentDate)) && (payloadNode.get(i).get("currentRaceTime").asText() != null)) {
                    String hippodromeName = payloadNode.get(i).get("hippodromeKey").asText();
                    hippodromeKeyList.add(hippodromeName);
                } else {
                    continue;
                }
            }
        }
        System.out.println("Uygun olan Hipodromlar: " + hippodromeKeyList);
        return hippodromeKeyList;
    }

    @Test
    public void getCorrectRaceTimes() throws IOException {
        String date = currentDate();
        System.out.println("Date: " + date);
        String racesJson;
        String startTime;
        String targetStartTime = null;
        JsonNode racesNode;
        List<Map<String, Object>> racesList;
        List<String> hippodromeList = getHippodromeList();
        //String hippodromeKey = "ROGERSDOWN";
        JsonNode times;
        LocalTime currentTime;
        long minutesUntilRace;
        int racesSize;
        boolean shouldBreakOuterLoop = false;

        for (String hippodromeKey : hippodromeList) {
            // Servise istek atilir
            Response response = given(spec)
                    .when()
                    .get("core/betprogram/get-bet-program?HippodromeKey=" + hippodromeKey + "&Date=" + date);
            response
                    .then()
                    .statusCode(200);
            racesList = response.jsonPath().getList("payload.races");

            ObjectMapper mapper = new ObjectMapper();
            racesJson = mapper.writeValueAsString(racesList);

            writeJsonToFile(racesJson, "src/test/java/com/ekuri/responseJson/racesToHippodromeResponse.json");
            racesNode = readJsonToFile("src/test/java/com/ekuri/responseJson/racesToHippodromeResponse.json");
            racesSize = racesNode.size();

            // Belirtilen saat ile şu anki saat arasındaki farkı hesapla
            currentTime = LocalTime.now();

            times = readJsonToFile("src/test/java/com/ekuri/requestJson/times.json");

            for (int i = 0; i < racesSize; i++) {
                startTime = racesNode.get(i).get("startTime").asText();
                LocalTime raceTime = LocalTime.parse(startTime); // Örnek olarak 10:16 saatini kullanıyoruz

                minutesUntilRace = Duration.between(currentTime, raceTime).toMinutes();

                // Eğer belirtilen saat bugünkü saatle aynı veya daha önceyse, oynanabilir değil
                if (minutesUntilRace <= 0) {
                    System.out.println("Koşu saati geçmiş bir saat olarak işaretlendi.");
                } else if (minutesUntilRace < 60) {
                    System.out.println("Koşuya oynamak için yeterli süre yok.");
                } else {
                    System.out.println("Koşu oynanabilir durumda.");
                    targetStartTime = racesNode.get(i).get("startTime").asText();
                    shouldBreakOuterLoop = true;
                    break;
                }
            }


            //System.out.println("Yarış Sayısı: " + racesSize);
            System.out.println("Uygun Yarış saati: " + targetStartTime);
            //System.out.println("Koşan Atlar: " + racesNode.get(0).get("runners").get(0).get("horseNo"));
            if (shouldBreakOuterLoop) {
                break; // Dıştaki döngüyü sonlandır
            }
        }
        //return targetStartTime;
    }
}

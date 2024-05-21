package com.ekuri.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static io.restassured.RestAssured.given;

public class BaseTest {
    RequestSpecification spec;

    public BaseTest() {
        spec = new RequestSpecBuilder()
                .setBaseUri("https://api.ekuri.com/api/v1/")
                .addFilters(Arrays.asList(new RequestLoggingFilter(), new ResponseLoggingFilter()))
                .build();
    }

    public String token() {
        Response response = given(spec)
                .when()
                .formParam("Input", "14798329878")
                .formParam("Password", "asd387389")
                .formParam("client_id", "ekuri_web_client")
                .formParam("client_secret", "d3f44a6c-3da0-4822-bbc9-8cd41539e6b1")
                .formParam("grant_type", "multi_provider")
                .formParam("scope", "offline_access")
                .header("content-type", "application/x-www-form-urlencoded")
                .post("auth/connect/token");
        response
                .then()
                .statusCode(200);
        return response.jsonPath().getJsonObject("payload.accessToken").toString();
    }

    public String currentTime() {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedTime = localDateTime.format(formatter);
        System.out.println("Saat Formatı: " + formattedTime);
        return formattedTime;
    }

    public String currentDate() {
        LocalDate localDate = LocalDate.now();

        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime threeHoursAgo = localDateTime.minusHours(3);
        LocalDate justDate = threeHoursAgo.toLocalDate();
        String date = justDate.toString();
        return date;
    }

    public void writeJsonToFile(String json, String filePath) throws IOException {
        // JSON verisini bir dosyaya yazma
        File file = new File(filePath);
        FileWriter writer = new FileWriter(file);
        writer.write(json);
        writer.close();
        System.out.println("Response JSON yazıldı: " + filePath);
    }

    public JsonNode readJsonToFile(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(new File(filePath));
        return rootNode;
    }

    public String getRaceDate() {
        LocalDate localDate = LocalDate.now();

        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDate justDate = localDateTime.toLocalDate();
        String date = justDate.toString();
        return date;
    }

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

    public String[] getCorrectRaceTimes() throws IOException {
        String[] result = new String[4];
        String date = currentDate();
        System.out.println("Date: " + date);
        String racesJson;
        String runnersJson;
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
                    System.out.println(hippodromeKey + " Hipodromunun, " + racesNode.get(i).get("raceNo") + " koşusu, koşu saati geçmiş bir saat olarak işaretlendi.");
                } else if (minutesUntilRace < 60) {
                    System.out.println(hippodromeKey + " Hipodromunun, " + racesNode.get(i).get("raceNo") + " koşusuna oynamak için yeterli süre yok.");
                } else {
                    System.out.println(hippodromeKey + " Hipodromunun, " + racesNode.get(i).get("raceNo") + " koşusu oynanabilir durumda.");
                    targetStartTime = racesNode.get(i).get("startTime").asText();
                    System.out.println("Uygun Yarış No: " + racesNode.get(i).get("raceNo"));
                    result[2] = racesNode.get(i).get("raceNo").toString();
                    System.out.println("Uygun Koşunun Bulunduğu Hippodrome: " + hippodromeKey);
                    result[0] = hippodromeKey;
                    shouldBreakOuterLoop = true;
                    runnersJson = racesNode.get(i).get("runners").toString();
                    writeJsonToFile(runnersJson, "src/test/java/com/ekuri/responseJson/runnersResponse.json");
                    result[3] = response.jsonPath().getString("payload.cardId");
                    break;
                }
            }


            //System.out.println("Yarış Sayısı: " + racesSize);
            System.out.println("Uygun Yarış saati: " + targetStartTime);
            System.out.println("CardId: " + result[3]);
            //System.out.println("Koşan Atlar: " + racesNode.get(0).get("runners").get(0).get("horseNo"));
            if (shouldBreakOuterLoop) {
                break; // Dıştaki döngüyü sonlandır
            }
        }
        result[1] = targetStartTime;

        return result;
    }

    public int runnerSize(JsonNode runners) {
        int runnerSize;
        runnerSize = runners.size();
        return runnerSize;
    }

    public List avaiableLegList(int runnerSize, JsonNode runnersNode, int misli, int poolUnit) {
        List<String> availableHours = new ArrayList<>();
        int couponPrice = 0;
        int say = 0;
        for (int i = 0; i < runnerSize; i++) {
            if (runnersNode.get(i).get("runStatus").asBoolean()) {
                if (couponPrice < 10) {
                    say += poolUnit;
                availableHours.add(runnersNode.get(i).get("horseNo").asText());
                    couponPrice = misli * say;
                } else {
                    break;
                }
            }
        }
        return availableHours;
    }

    public int getPrice(int runnerSize, JsonNode runnersNode, int misli, int poolUnit) {
        List<Integer> availableHours = new ArrayList<>();
        int couponPrice = 0;
        int price = 0;
        int say = 0;
        for (int i = 0; i < runnerSize; i++) {
            if (runnersNode.get(i).get("runStatus").asBoolean()) {
                if (couponPrice < 10) {
                    say += poolUnit;
                availableHours.add(runnersNode.get(i).get("horseNo").asInt());
                couponPrice = misli * say;
                } else {
                    break;
                }
            }
            price = couponPrice * 100;
        }
        return price;
    }

    public JsonNode couponRequestUpdate(List<String> availableHours, String betType, JsonNode couponNode, int misli, int legsIndex, String raceDate, int raceNo, String hippodromeKey, int cardId, int poolUnit, int price) throws IOException {
        int betTypeId;
        // Json dosyasını oku
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> betTypesMap = readBetTypes("src/test/java/com/ekuri/requestJson/betTypes.json");
        betTypeId = Integer.parseInt(getBetTypeKey(betTypesMap, betType));
        ArrayNode legsArrayNode = objectMapper.valueToTree(availableHours);
        ((ObjectNode) couponNode).withArray("legs").set(legsIndex, legsArrayNode);

        ((ObjectNode) couponNode).put("multiplier", misli);
        ((ObjectNode) couponNode).put("raceDate", raceDate);
        ((ObjectNode) couponNode).put("raceNo", raceNo);
        ((ObjectNode) couponNode).put("hippodromeKey", hippodromeKey);
        ((ObjectNode) couponNode).put("betType", betTypeId);
        ((ObjectNode) couponNode).put("cardId", cardId);
        ((ObjectNode) couponNode).put("poolUnit", poolUnit);
        ((ObjectNode) couponNode).put("price", price);
        return couponNode;
    }

    public static Map<String, String> readBetTypes(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(new File(filePath));

        Map<String, String> betTypesMap = new HashMap<>();
        JsonNode betTypesNode = rootNode.get("BetTypes");

        Iterator<Map.Entry<String, JsonNode>> fields = betTypesNode.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> entry = fields.next();
            betTypesMap.put(entry.getKey(), entry.getValue().asText());
        }

        return betTypesMap;
    }

    // BetType değerine göre anahtar değeri getiren fonksiyon
    public static String getBetTypeKey(Map<String, String> betTypesMap, String betTypeValue) {
        for (Map.Entry<String, String> entry : betTypesMap.entrySet()) {
            if (entry.getValue().equals(betTypeValue)) {
                return entry.getKey();
            }
        }
        return null; // BetType değeri bulunamazsa null döner
    }

}

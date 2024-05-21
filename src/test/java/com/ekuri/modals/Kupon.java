package com.ekuri.modals;

import java.util.List;

public class Kupon {
    private int betType;
    private int cardId;
    private int raceNo;
    private String hippodromeKey;
    private int multiplier;
    private List legs;
    private int poolUnit;
    private int price;
    private boolean isUsePointFirstly = true;
    private String raceDate;
    private boolean complete = false;

    public int getBetType() {
        return betType;
    }

    public void setBetType(int betType) {
        this.betType = betType;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public int getRaceNo() {
        return raceNo;
    }

    public void setRaceNo(int raceNo) {
        this.raceNo = raceNo;
    }

    public String getHippodromeKey() {
        return hippodromeKey;
    }

    public void setHippodromeKey(String hippodromeKey) {
        this.hippodromeKey = hippodromeKey;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }

    public List getLegs() {
        return legs;
    }

    public void setLegs(List legs) {
        this.legs = legs;
    }

    public int getPoolUnit() {
        return poolUnit;
    }

    public void setPoolUnit(int poolUnit) {
        this.poolUnit = poolUnit;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isUsePointFirstly() {
        return isUsePointFirstly;
    }

    public void setUsePointFirstly(boolean usePointFirstly) {
        isUsePointFirstly = usePointFirstly;
    }

    public String getRaceDate() {
        return raceDate;
    }

    public void setRaceDate(String raceDate) {
        this.raceDate = raceDate;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }
}

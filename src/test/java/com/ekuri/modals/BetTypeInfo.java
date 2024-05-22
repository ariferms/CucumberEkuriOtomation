package com.ekuri.modals;

public class BetTypeInfo {
    int betTypeId;
    int poolUnit;

    public BetTypeInfo(int betTypeId, int poolUnit) {
        this.betTypeId = betTypeId;
        this.poolUnit = poolUnit;
    }

    public int getBetTypeId() {
        return betTypeId;
    }

    public void setBetTypeId(int betTypeId) {
        this.betTypeId = betTypeId;
    }

    public int getPoolUnit() {
        return poolUnit;
    }

    public void setPoolUnit(int poolUnit) {
        this.poolUnit = poolUnit;
    }
}

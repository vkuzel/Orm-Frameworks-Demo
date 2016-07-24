package com.github.vkuzel.orm_frameworks_demo.speedment.transport;

import java.math.BigDecimal;

public class PlaneDimensions {

    private BigDecimal lengthMeters;
    private BigDecimal wingspanMeters;
    private BigDecimal heightMeters;

    public BigDecimal getLengthMeters() {
        return lengthMeters;
    }

    public void setLengthMeters(BigDecimal lengthMeters) {
        this.lengthMeters = lengthMeters;
    }

    public BigDecimal getWingspanMeters() {
        return wingspanMeters;
    }

    public void setWingspanMeters(BigDecimal wingspanMeters) {
        this.wingspanMeters = wingspanMeters;
    }

    public BigDecimal getHeightMeters() {
        return heightMeters;
    }

    public void setHeightMeters(BigDecimal heightMeters) {
        this.heightMeters = heightMeters;
    }
}

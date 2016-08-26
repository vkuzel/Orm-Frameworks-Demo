package com.github.vkuzel.orm_frameworks_demo.transport;

import java.io.Serializable;
import java.math.BigDecimal;

public class DetailPlaneDimensions implements Serializable {

    private final BigDecimal lengthMeters;
    private final BigDecimal wingspanMeters;
    private final BigDecimal heightMeters;

    public DetailPlaneDimensions(BigDecimal lengthMeters, BigDecimal wingspanMeters, BigDecimal heightMeters) {
        this.lengthMeters = lengthMeters;
        this.wingspanMeters = wingspanMeters;
        this.heightMeters = heightMeters;
    }

    public BigDecimal getLengthMeters() {
        return lengthMeters;
    }

    public BigDecimal getWingspanMeters() {
        return wingspanMeters;
    }

    public BigDecimal getHeightMeters() {
        return heightMeters;
    }
}

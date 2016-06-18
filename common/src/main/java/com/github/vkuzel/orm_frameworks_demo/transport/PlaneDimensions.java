package com.github.vkuzel.orm_frameworks_demo.transport;

public class PlaneDimensions {

    private final double lengthMeters;
    private final double wingspanMeters;
    private final double heightMeters;

    public PlaneDimensions(double lengthMeters, double wingspanMeters, double heightMeters) {
        this.lengthMeters = lengthMeters;
        this.wingspanMeters = wingspanMeters;
        this.heightMeters = heightMeters;
    }

    public double getLengthMeters() {
        return lengthMeters;
    }

    public double getWingspanMeters() {
        return wingspanMeters;
    }

    public double getHeightMeters() {
        return heightMeters;
    }
}

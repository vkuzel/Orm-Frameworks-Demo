package com.github.vkuzel.orm_frameworks_demo.eclipselink.repository;

public interface PlaneRegistrant {

    long registerNewPlane(long planeId, long operatorId, String registrationNumber);
}

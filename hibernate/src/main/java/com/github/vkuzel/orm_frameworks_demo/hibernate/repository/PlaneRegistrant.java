package com.github.vkuzel.orm_frameworks_demo.hibernate.repository;

import com.github.vkuzel.orm_frameworks_demo.hibernate.domain.Operator;
import com.github.vkuzel.orm_frameworks_demo.hibernate.domain.Plane;
import com.github.vkuzel.orm_frameworks_demo.hibernate.domain.Registration;

public interface PlaneRegistrant {

    long registerNewPlane(long planeId, long operatorId, String registrationNumber);
}

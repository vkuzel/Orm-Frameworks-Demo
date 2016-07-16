package com.github.vkuzel.orm_frameworks_demo.transport;

public interface RegistrationDetail {

    Long getId();

    void setId(Long id);

    long getPlaneId();

    void setPlaneId(long planeId);

    long getOperatorId();

    void setOperatorId(long operatorId);

    String getRegistrationNumber();

    void setRegistrationNumber(String registrationNumber);
}

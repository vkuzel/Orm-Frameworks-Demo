package com.github.vkuzel.orm_frameworks_demo.transport;

import com.github.vkuzel.orm_frameworks_demo.common.Utils;

public interface RegistrationDetail {

    Long getId();

    void setId(Long id);

    PlaneDetail getPlane();

    void setPlane(PlaneDetail plane);

    OperatorDetail getOperator();

    void setOperator(OperatorDetail operator);

    String getRegistrationNumber();

    void setRegistrationNumber(String registrationNumber);
}

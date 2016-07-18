package com.github.vkuzel.orm_frameworks_demo.mybatis.adapter;

import com.github.vkuzel.orm_frameworks_demo.mybatis.model.Registration;
import com.github.vkuzel.orm_frameworks_demo.transport.RegistrationDetail;

public class RegistrationWrapper extends Registration implements RegistrationDetail {

    public RegistrationWrapper() {
    }

    public RegistrationWrapper(Registration registration) {
        setId(registration.getId());
        setPlaneId(registration.getPlaneId());
        setRegistrationNumber(registration.getRegistrationNumber());
        setOperatorId(registration.getOperatorId());
    }
}

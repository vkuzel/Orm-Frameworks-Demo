package com.github.vkuzel.orm_frameworks_demo.reladomo;

import com.github.vkuzel.orm_frameworks_demo.reladomo.model.Plane;
import com.github.vkuzel.orm_frameworks_demo.service.AirlinesService;
import com.github.vkuzel.orm_frameworks_demo.transport.OperatorDetail;
import com.github.vkuzel.orm_frameworks_demo.transport.PlaneDetail;
import com.github.vkuzel.orm_frameworks_demo.transport.RegistrationDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class ReladomoAirlinesService implements AirlinesService {
    @Override
    public PlaneDetail newPlaneDetailInstance() {
        return null;
    }

    @Override
    public PlaneDetail createPlane(PlaneDetail plane) {
        return null;
    }

    @Override
    public PlaneDetail updatePlane(PlaneDetail plane) {
        return null;
    }

    @Override
    public PlaneDetail updatePlaneTransactionalThatThrowsException(PlaneDetail plane) {
        return null;
    }

    @Override
    public PlaneDetail findPlaneByName(String name) {
        return null;
    }

    @Override
    public OperatorDetail findOperatorByName(String languageCode, String name) {
        return null;
    }

    @Override
    public Page<OperatorDetail> findAllOperators(String languageCode, Pageable pageable) {
        return null;
    }

    @Override
    public RegistrationDetail registerNewPlane(PlaneDetail plane, OperatorDetail operator, String registrationNumber) {
        return null;
    }

    @Override
    public RegistrationDetail findRegistrationByRegistrationNumber(String registrationNumber) {
        return null;
    }
}

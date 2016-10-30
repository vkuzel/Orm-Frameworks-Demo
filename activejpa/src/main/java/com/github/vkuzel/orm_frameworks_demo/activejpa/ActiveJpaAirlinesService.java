package com.github.vkuzel.orm_frameworks_demo.activejpa;

import com.github.vkuzel.orm_frameworks_demo.activejpa.model.Plane;
import com.github.vkuzel.orm_frameworks_demo.service.AirlinesService;
import com.github.vkuzel.orm_frameworks_demo.transport.OperatorDetail;
import com.github.vkuzel.orm_frameworks_demo.transport.PlaneDetail;
import com.github.vkuzel.orm_frameworks_demo.transport.RegistrationDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ActiveJpaAirlinesService implements AirlinesService {

    @Override
    public PlaneDetail newPlaneDetailInstance() {
        return new Plane();
    }

    @Override
    public PlaneDetail createPlane(PlaneDetail plane) {
        ((Plane) plane).persist();
        return plane;
    }

    @Override
    public PlaneDetail updatePlane(PlaneDetail plane) {
        ((Plane) plane).persist();
        return plane;
    }

    @Override
    public PlaneDetail updatePlaneTransactionalThatThrowsException(PlaneDetail plane) {
        ((Plane)plane).persist();
        throw new IllegalStateException();
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

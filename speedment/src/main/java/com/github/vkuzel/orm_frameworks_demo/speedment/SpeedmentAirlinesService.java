package com.github.vkuzel.orm_frameworks_demo.speedment;

import com.github.vkuzel.orm_frameworks_demo.service.AirlinesService;
import com.github.vkuzel.orm_frameworks_demo.speedment.adapter.PlaneWrapper;
import com.github.vkuzel.orm_frameworks_demo.speedment.ormframeworksdemo.public_.operators.Operators;
import com.github.vkuzel.orm_frameworks_demo.speedment.ormframeworksdemo.public_.planes.Planes;
import com.github.vkuzel.orm_frameworks_demo.speedment.ormframeworksdemo.public_.registrations.Registrations;
import com.github.vkuzel.orm_frameworks_demo.transport.OperatorDetail;
import com.github.vkuzel.orm_frameworks_demo.transport.PlaneDetail;
import com.github.vkuzel.orm_frameworks_demo.transport.RegistrationDetail;
import com.speedment.manager.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SpeedmentAirlinesService implements AirlinesService {

    @Autowired
    Manager<Planes> planesManager;

    @Autowired
    Manager<Operators> operatorsManager;

    @Autowired
    Manager<Registrations> registrationsManager;

    @Override
    public PlaneDetail newPlaneDetailInstance() {
        Planes entity = planesManager.newEmptyEntity();
        return new PlaneWrapper(entity);
    }

    @Override
    @Transactional
    public PlaneDetail createPlane(PlaneDetail plane) {
        Registrations registration = registrationsManager.newEmptyEntity();
        registration.setOperatorId(1L)
                .setPlaneId(1L)
                .setRegistrationNumber("23456");
        registrationsManager.persist(registration);

        Planes entity = ((PlaneWrapper) plane).toEntity();
        Planes savedEntity = planesManager.persist(entity);
        return new PlaneWrapper(savedEntity);
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

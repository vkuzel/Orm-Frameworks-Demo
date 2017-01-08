package com.github.vkuzel.orm_frameworks_demo.eclipselink.repository;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class RegistrationRepositoryImpl implements PlaneRegistrant {

    private final EntityManager entityManager;

    @Autowired
    public RegistrationRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public long registerNewPlane(long planeId, long operatorId, String registrationNumber) {
        Query query = entityManager.createNamedQuery("registerNewPlane", Long.class);
        return (long) query
                .setParameter(1, planeId)
                .setParameter(2, operatorId)
                .setParameter(3, registrationNumber)
                .getSingleResult();
    }
}

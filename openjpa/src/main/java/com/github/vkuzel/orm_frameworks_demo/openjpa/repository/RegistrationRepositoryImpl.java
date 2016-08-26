package com.github.vkuzel.orm_frameworks_demo.openjpa.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class RegistrationRepositoryImpl implements PlaneRegistrant {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public long registerNewPlane(long planeId, long operatorId, String registrationNumber) {
        Object registrationId = entityManager
                .createNativeQuery("SELECT register_new_plane(?, ?, ?)")
                .setParameter(1, planeId)
                .setParameter(2, operatorId)
                .setParameter(3, registrationNumber)
                .getSingleResult();
        return (long) registrationId;
    }
}

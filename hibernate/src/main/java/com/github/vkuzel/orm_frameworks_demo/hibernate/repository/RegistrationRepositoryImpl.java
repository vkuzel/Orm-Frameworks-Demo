package com.github.vkuzel.orm_frameworks_demo.hibernate.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

public class RegistrationRepositoryImpl implements PlaneRegistrant {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public long registerNewPlane(long planeId, long operatorId, String registrationNumber) {
        StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery("registerNewPlane");
        query.setParameter("plane_id", planeId)
                .setParameter("operator_id", operatorId)
                .setParameter("registration_number", registrationNumber)
                .execute();
        return (long) query.getOutputParameterValue("registration_id");
    }
}

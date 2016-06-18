package com.github.vkuzel.orm_frameworks_demo.hibernate.repository;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

public class RegistrationRepositoryImpl implements PlaneRegistrant {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public long registerNewPlane(long planeId, long operatorId, String registrationNumber) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("register_new_plane");
        query.registerStoredProcedureParameter("plane_id", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("operator_id", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("registration_number", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("registration_id", Long.class, ParameterMode.OUT);
        query.setParameter("plane_id", planeId);
        query.setParameter("operator_id", operatorId);
        query.setParameter("registration_number", registrationNumber);
        query.execute();
        return (long) query.getOutputParameterValue("registration_id");
    }
}

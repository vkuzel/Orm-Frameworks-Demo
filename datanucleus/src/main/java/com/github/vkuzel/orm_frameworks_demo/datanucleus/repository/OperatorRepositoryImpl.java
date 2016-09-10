package com.github.vkuzel.orm_frameworks_demo.datanucleus.repository;

import com.github.vkuzel.orm_frameworks_demo.datanucleus.domain.Operator;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class OperatorRepositoryImpl implements OperatorFinder {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Operator findByName(String languageCode, String name) {
        Query query = entityManager.createNamedQuery("findOperatorByName", Operator.class);
        return (Operator) query
                .setParameter(1, languageCode)
                .setParameter(2, name)
                .getSingleResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Operator> findAllOrderByNameDesc(String languageCode, int offset, int pageSize) {
        Query query = entityManager.createNamedQuery("findAllOperatorsOrderByNameDesc", Operator.class);
        return query
                .setParameter(1, languageCode)
                .setParameter(2, pageSize)
                .setParameter(3, offset)
                .getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Operator> findAllOrderByNameAsc(String languageCode, int offset, int pageSize) {
        Query query = entityManager.createNamedQuery("findAllOperatorsOrderByNameAsc", Operator.class);
        return query
                .setParameter(1, languageCode)
                .setParameter(2, pageSize)
                .setParameter(3, offset)
                .getResultList();
    }
}

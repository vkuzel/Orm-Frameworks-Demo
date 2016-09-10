package com.github.vkuzel.orm_frameworks_demo.datanucleus.repository;

import com.github.vkuzel.orm_frameworks_demo.datanucleus.domain.Operator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperatorRepository extends JpaRepository<Operator, Long>, OperatorFinder {
}

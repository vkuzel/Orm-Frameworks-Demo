package com.github.vkuzel.orm_frameworks_demo.datanucleus.repository;

import com.github.vkuzel.orm_frameworks_demo.datanucleus.domain.Plane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaneRepository extends JpaSpecificationExecutor<Plane>, JpaRepository<Plane, Long> {

    Plane findByName(String name);
}

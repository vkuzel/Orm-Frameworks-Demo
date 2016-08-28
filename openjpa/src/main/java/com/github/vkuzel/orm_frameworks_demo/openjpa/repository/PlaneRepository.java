package com.github.vkuzel.orm_frameworks_demo.openjpa.repository;

import com.github.vkuzel.orm_frameworks_demo.openjpa.domain.Plane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaneRepository extends JpaRepository<Plane, Long> {

    Plane findByName(String name);
}

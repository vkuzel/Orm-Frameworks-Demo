package com.github.vkuzel.orm_frameworks_demo.eclipselink.repository;

import com.github.vkuzel.orm_frameworks_demo.eclipselink.domain.Plane;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaneRepository extends JpaRepository<Plane, Long> {

    Plane findByName(String name);
}

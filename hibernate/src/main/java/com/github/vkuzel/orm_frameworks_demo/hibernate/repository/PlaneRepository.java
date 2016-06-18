package com.github.vkuzel.orm_frameworks_demo.hibernate.repository;

import com.github.vkuzel.orm_frameworks_demo.hibernate.domain.Plane;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaneRepository extends JpaRepository<Plane, Long> {

}

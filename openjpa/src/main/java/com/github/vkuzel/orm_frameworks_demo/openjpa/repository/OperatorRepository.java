package com.github.vkuzel.orm_frameworks_demo.openjpa.repository;

import com.github.vkuzel.orm_frameworks_demo.openjpa.domain.Operator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperatorRepository extends JpaRepository<Operator, Long> {

    @Query(value = "SELECT * FROM operators WHERE name ->> ? = ?", nativeQuery = true)
    Operator findByName(String languageCode, String name);

    @Query(value = "SELECT * FROM operators ORDER BY name ->> ? DESC LIMIT ? OFFSET ?", nativeQuery = true)
    List<Operator> findAllOrderByNameDesc(String languageCode, int pageSize, int offset);

    @Query(value = "SELECT * FROM operators ORDER BY name ->> ? ASC LIMIT ? OFFSET ?", nativeQuery = true)
    List<Operator> findAllOrderByNameAsc(String languageCode, int pageSize, int offset);
}

package com.github.vkuzel.orm_frameworks_demo.hibernate.repository;

import com.github.vkuzel.orm_frameworks_demo.hibernate.domain.Operator;
import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OperatorRepository extends JpaRepository<Operator, Long> {

    @Query(value = "SELECT * FROM operators WHERE name ->> :language = :name", nativeQuery = true)
    Operator findByName(@Param("language") String languageCode, @Param("name") String name);

    @Query(value = "SELECT * FROM operators ORDER BY name ->> :language DESC LIMIT :pageSize OFFSET :offset", nativeQuery = true)
    List<Operator> findAllOrderByNameDesc(@Param("language") String languageCode, @Param("offset") int offset, @Param("pageSize") int pageSize);

    @Query(value = "SELECT * FROM operators ORDER BY name ->> :language ASC LIMIT :pageSize OFFSET :offset", nativeQuery = true)
    List<Operator> findAllOrderByNameAsc(@Param("language") String languageCode, @Param("offset") int offset, @Param("pageSize") int pageSize);
}

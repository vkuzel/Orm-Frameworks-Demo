package com.github.vkuzel.orm_frameworks_demo.datanucleus.repository;

import com.github.vkuzel.orm_frameworks_demo.datanucleus.domain.Operator;

import java.util.List;

public interface OperatorFinder {

    Operator findByName(String languageCode, String name);

    List<Operator> findAllOrderByNameDesc(String languageCode, int offset, int pageSize);

    List<Operator> findAllOrderByNameAsc(String languageCode, int offset, int pageSize);
}

package com.github.vkuzel.orm_frameworks_demo.eclipselink.repository;

import com.github.vkuzel.orm_frameworks_demo.eclipselink.domain.Operator;

import java.util.List;

public interface OperatorFinder {

    Operator findByName(String languageCode, String name);

    List<Operator> findAllOrderByNameDesc(String languageCode, int offset, int pageSize);

    List<Operator> findAllOrderByNameAsc(String languageCode, int offset, int pageSize);
}